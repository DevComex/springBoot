package cn.gyyx.action.service.wdsigned;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedConstantBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedSendPrizeBean;
import cn.gyyx.action.bll.challenger.SameDataBll;
import cn.gyyx.action.bll.wdsigned.IWdAppSignBll;
import cn.gyyx.action.bll.wdsigned.IWdAppSignLogBll;
import cn.gyyx.action.bll.wdsigned.IWdAppSignSendPrizeBll;
import cn.gyyx.action.bll.wdsigned.WdAppSignBllImpl;
import cn.gyyx.action.bll.wdsigned.WdAppSignLogBllImpl;
import cn.gyyx.action.bll.wdsigned.WdAppSignSendPrizeBllImpl;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.core.security.MD5;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
//import cn.gyyx.message.MessageClient;

/**
 * @ClassName: WdAppSignLogServiceImpl
 * @description WdAppSignLogServiceImpl
 * @author luozhenyu
 * @date 2016年12月10日
 */
public class WdAppSignLogServiceImpl implements IWdAppSignLogService {

	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignLogServiceImpl.class);

	IWdAppSignLogBll wdAppSignBll = new WdAppSignLogBllImpl();
	IWdAppSignBll iWdAppSignBll = new WdAppSignBllImpl();
	IWdAppSignSendPrizeBll iWdAppSignSendPrizeBll = new WdAppSignSendPrizeBllImpl();

	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcache = memcacheUtil.getMemcache();
	private SameDataBll sameDataBll = new SameDataBll();
	
	/**
	 * 签到
	 */
	public ResultBean<String> sign(String qrCodeContent, String account) {
		ResultBean<String> result = new ResultBean<>(false, "签到失败", null);
		// 验证签名
		// 判断二维码是否有效
		// http://xx.gyyx.cn/abc?qrCodeContent=gyc://gyyx.cn/97439969a555fe51de09c89?t=GL&s=独孤求败&account=xxxx&account=xxxxx
		String qrContent;
		SqlSession session = getSession();
		try {
			String batch = memcache.get(WdAppSignedConstantBean.MEM_KEY_PREFIX, String.class);
			String dataBatch = "";
			if (StringUtils.isEmpty(batch) || "null".equals(batch)) {
				SameDataBean sameDataBean = sameDataBll.getSameDataBean(WdAppSignedConstantBean.MEM_KEY_PREFIX,
						WdAppSignedConstantBean.ACTIVITY_CODE);
				if (sameDataBean == null) {
					result.setProperties(false, "请设置活动开始时间", null);
					return result;
				} else {
					dataBatch = sameDataBean.getContent();
					if (StringUtils.isEmpty(dataBatch) || "null".equals(dataBatch)) {
						result.setProperties(false, "请设置活动开始时间", null);
						return result;
					}
					batch = batch == null ? dataBatch : batch;
					memcache.set(WdAppSignedConstantBean.MEM_KEY_PREFIX, 3600 * 30 * 24, dataBatch);
					if (activeTime(dataBatch)) {
						result.setProperties(false, "不在活动时间范围", null);
						return result;
					}
				}
			} else {
				if (activeTime(batch)) {
					result.setProperties(false, "不在活动时间范围", null);
					return result;
				}
			}
			
			qrContent = URLDecoder.decode(qrCodeContent, "UTF-8");
			logger.info("编码后二维码信息:{}   appAccount:{}", qrContent, account);
			if (!qrContent.contains("gyc://gyyx.cn/") && !qrCodeContent.contains("?")) {
				result.setProperties(false, "非光宇二维码", null);
				return result;
			}
			Map<String, String> paramMap = parseQueryParams(qrContent);
			String gameAccount = paramMap.get("a");
			String serverName = paramMap.get("s");
			String type = paramMap.get("t");
			// String serverIdStr = paramMap.get("id");

			if (StringUtils.isEmpty(gameAccount) || StringUtils.isEmpty(serverName)) {
				result.setProperties(false, "非光宇活动二维码", null);
				return result;
			}
			if (!StringUtils.isEmpty(account)) {
				// 判断账号是否有效
				if (!gameAccount.equalsIgnoreCase(account)) {
					result.setProperties(false, "您登录游戏账号与扫描二维码账号不匹配", null);
					return result;
				}
			}
			// 根据serverName获取serverId、
			// serverIdStr = serverIdStr == null ? getRegion(serverName) + "" :
			// serverIdStr;
			int serverId = getRegion(serverName);
			if (serverId == 0) {
				result.setProperties(false, "获取区服失败", null);
				return result;
			}
			if ("QD".equals(type)) {
				int endLocation = qrContent.indexOf("?");
				int startLocation = 14;
				String qrId = qrContent.substring(startLocation, endLocation);
				int userId = getUserIdByAccount(gameAccount);
				if (!validQrCode(qrId, "QD", userId + "")) {
					result.setProperties(false, "您的二维码失效", null);
					return result;
				}
			}

			int signErrorCode = sign(gameAccount, serverId, serverName, batch, session);
			
			if(signErrorCode == 0) {
				result.setProperties(true, "签到成功", null);
			} else if(signErrorCode == 1) {
				result.setProperties(false, "您该区服今天已经签到", null);
			} else {
				result.setProperties(false, "插入签到日志失败", null);
			}
		} catch (Exception e) {
			logger.info("签到服务器异常- 签到异常 Message:{}, Cause:{}, StackTrace:{}", new Object[]{e.getMessage(), e.getCause(), e.getStackTrace()});
			if (session != null) {
				session.close();
			}
			result.setProperties(false, "网络异常", null);
			return result;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}
	
	/*public void gameLoginSign(MessageClient client) {
		
		client.receive(new MessageClient.Handler(){

			@Override
			public boolean on(byte[] message) {
				SqlSession session = getSession();
				try {
					String content = new String(message,"utf-8");
					
					JSONObject o = (JSONObject)JSON.parse(content);
					String gameAccount = o.getString("GameAccount");
					String serverName = o.getString("ServerName");
					int serverId = getRegion(serverName);
					
					Map<String, Object> result = new HashMap<>();
					
					String batch = getValidBatch(result);
			        if(batch == null){
			            return true;
			        }
					int errorCode = sign(gameAccount, serverId, serverName, batch, session);
					
					if(errorCode == 0 || errorCode == 1) {
						return true;
					} else {
						return false;
					}
				} catch(Exception e) {
					logger.info("游戏登录签到服务器异常- 签到异常 Message:{}, Cause:{}, StackTrace:{}", new Object[]{e.getMessage(), e.getCause(), e.getStackTrace()});
					if (session != null) {
						session.close();
					}
					return false;
				} finally {
					if (session != null) {
						session.close();
					}
				}
			}});
		
	}*/
	
	
	
	private int sign(String gameAccount, int serverId, String serverName, String batch, SqlSession session) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		WdAppSignedLogBean signedLogBean = new WdAppSignedLogBean();
		signedLogBean.setAccount(gameAccount);
		signedLogBean.setCreateTime(new Date());
		signedLogBean.setServerId(serverId);
		signedLogBean.setServerName(serverName);
		// ------------------获取batch获取批次

		signedLogBean.setBatch(batch);
		// 判断今天是否签到

		Date today = new Date();
		
		WdAppSignedLogBean todayLog = wdAppSignBll.getTodaySignLog(signedLogBean.getAccount(),
				signedLogBean.getServerId(), signedLogBean.getBatch(), sdf.format(today), session);
		if (todayLog != null) {
			
			return 1;
		}
		// 插入签到日志记录
		if (!(wdAppSignBll.insert(signedLogBean, session) > 0)) {
			
			return -1;
		}
		
		// 获取累计签到记录
		int total = wdAppSignBll.getSignCountByAccount(signedLogBean.getAccount(), signedLogBean.getServerId(),
				signedLogBean.getBatch(), session);
		// 获取昨天签到日志是否存在
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, -1);
		String yesterday = sdf.format(calendar.getTime());
		WdAppSignedLogBean yesterdayLog = wdAppSignBll.getTodaySignLog(signedLogBean.getAccount(),
				signedLogBean.getServerId(), signedLogBean.getBatch(), yesterday, session);
		// 获取签到记录表是否存在
		WdAppSignedBean signedBean = iWdAppSignBll.getSign(signedLogBean.getAccount(), signedLogBean.getServerId(),
				signedLogBean.getBatch(), session);
		// 如果签到日志昨天没有数据
		if (yesterdayLog == null) {
			// 签到记录表里也没有记录，插入一条新数据 else 更新连续天数和总天数
			if (signedBean == null) {
				WdAppSignedBean wdAppSignedBean = new WdAppSignedBean();
				BeanUtils.copyProperties(signedLogBean, wdAppSignedBean);
				wdAppSignedBean.setTotalDay(total);
				wdAppSignedBean.setSerialDay(1);
				iWdAppSignBll.insert(wdAppSignedBean, session);
			} else {
				iWdAppSignBll.updateSign(1, total, signedLogBean.getAccount(), serverId, signedBean.getBatch(),
						session);
			}
			// 如果签到日志记录有数据，签到记录表也肯定存在数据，更新连续天数和总天数
		} else {
			if(signedBean != null){
				iWdAppSignBll.updateSign(signedBean.getSerialDay() + 1, total, signedLogBean.getAccount(), serverId,
						signedBean.getBatch(), session);
				
			}
		}
		// 插入问道app签到礼物表
		WdAppSignedBean newSignedBean = iWdAppSignBll.getSign(signedLogBean.getAccount(),
				signedLogBean.getServerId(), signedLogBean.getBatch(), session);
		WdAppSignedSendPrizeBean prizeBean = new WdAppSignedSendPrizeBean();
		BeanUtils.copyProperties(signedLogBean, prizeBean);
		prizeBean.setCreateTime(new Date());
		prizeBean.setStatus(0);
		// 根据签到连续天数，进行发放奖品
		sendGiftBySerialDay(session, newSignedBean, prizeBean);
		
		session.commit(true);
		
		//getSignDataInDb(gameAccount, serverId, batch, true);
		setSignCache(gameAccount, serverId, batch, today);
		return 0;
	}

	
	/**
	 * 根据连续天数发放奖品
	 * @param session
	 * @param newSignedBean
	 * @param prizeBean
	 */
	private void sendGiftBySerialDay(SqlSession session, WdAppSignedBean newSignedBean,
			WdAppSignedSendPrizeBean prizeBean) {
		if (newSignedBean.getSerialDay() == 3
				&& iWdAppSignSendPrizeBll.getSignSendPrizeByPrize(newSignedBean.getAccount(),
						newSignedBean.getServerId(), WdAppSignedConstantBean.serialThreeGift,
						newSignedBean.getBatch(), session) == null) {
			prizeBean.setGift(WdAppSignedConstantBean.serialThreeGift);
			iWdAppSignSendPrizeBll.insert(prizeBean, session);

		} else if (newSignedBean.getSerialDay() == 5
				&& iWdAppSignSendPrizeBll.getSignSendPrizeByPrize(newSignedBean.getAccount(),
						newSignedBean.getServerId(), WdAppSignedConstantBean.serialFiveGift,
						newSignedBean.getBatch(), session) == null) {
			prizeBean.setGift(WdAppSignedConstantBean.serialFiveGift);
			iWdAppSignSendPrizeBll.insert(prizeBean, session);
		}
		if (newSignedBean.getTotalDay() == 8
				&& iWdAppSignSendPrizeBll.getSignSendPrizeByPrize(newSignedBean.getAccount(),
						newSignedBean.getServerId(), WdAppSignedConstantBean.totalEightGift,
						newSignedBean.getBatch(), session) == null) {
			prizeBean.setGift(WdAppSignedConstantBean.totalEightGift);
			iWdAppSignSendPrizeBll.insert(prizeBean, session);
		} else if (newSignedBean.getTotalDay() == 12
				&& iWdAppSignSendPrizeBll.getSignSendPrizeByPrize(newSignedBean.getAccount(),
						newSignedBean.getServerId(), WdAppSignedConstantBean.totalTwelveGift,
						newSignedBean.getBatch(), session) == null) {
			prizeBean.setGift(WdAppSignedConstantBean.totalTwelveGift);
			iWdAppSignSendPrizeBll.insert(prizeBean, session);
		}
	}

	/**
	 * 获取签到列表 包括连续签到天数和累计签到天数 二维码信息，是否有奖励领取
	 */
	/* (non-Javadoc)
	 * @see cn.gyyx.action.service.wdsigned.IWdAppSignLogService#signList(java.lang.String, int)
	 */
	@Override
	public Map<String, Object> signList(String account, int serverId) {
	    
        Map<String, Object> result = new HashMap<>();
        
        String batch = getValidBatch(result);
        if(batch == null){
            return result;
        }
		
		String cacheKey = account + "_" + serverId + "_" + batch;
		
		Date date = new Date();
		
		Map<String,Object> cacheResult=memcache.get(cacheKey, Map.class);
		// 获取缓存中的签到记录
		if(cacheResult != null){
			
			boolean isTodaySign = isTodaySign(account, serverId, batch, date);
			
			cacheResult.put("isConnect", !isTodaySign);
			
			// 判断是否可以抽奖
			boolean gitIsSend = isGitIsSend(account, serverId, batch);
			if(gitIsSend){
				cacheResult.put("isGetGift", true);
			}else{
				cacheResult.put("isGetGift", false);
			}
			return cacheResult;
		}
		
		return getSignDataInDb(account, serverId, batch, false, date);
	}

    /**
     * 检查是否在批次时间内并获取
     * @param result
     * @return
     */
    private String getValidBatch(Map<String, Object> result) {
        String batch = memcache.get(WdAppSignedConstantBean.MEM_KEY_PREFIX, String.class);

		if (batch == null) {
			SameDataBean dateConf = sameDataBll.getSameDataBean(WdAppSignedConstantBean.MEM_KEY_PREFIX, WdAppSignedConstantBean.ACTIVITY_CODE);
			
			if (dateConf == null) {
				result.put("isSuccess", false);
				result.put("isConnect", false);
				result.put("message", "请设置活动开始时间");
				return null;
			}

			if (StringUtils.isEmpty(dateConf.getContent()) || "null".equals(dateConf.getContent())) {
				result.put("isSuccess", false);
				result.put("isConnect", false);
				result.put("message", "请设置活动开始时间");
				return null;
			}
			
			batch = dateConf.getContent();
		}
		
        if (activeTime(batch)) {
            memcache.set(WdAppSignedConstantBean.MEM_KEY_PREFIX, 3600 * 30 * 24, batch);
            result.put("isSuccess", false);
            result.put("isConnect", false);
            result.put("message", "不在活动时间范围");
            return null;
        }
        
        return batch;
    }

	/**
	 * @param account
	 * @param serverId
	 * @param result
	 * @param batchStartDate
	 * @param cacheKey
	 * @return
	 */
	private Map<String, Object> getSignDataInDb(String account, int serverId, String batchStartDate, boolean updateFlag, Date date) {
        
        try {
            
    		// 获取签到历史记录
    		List<String> historySigned = wdAppSignBll.getSignHistoryDate(account, serverId, batchStartDate);
    		logger.debug("用户{}历史签到列表：{}", account, historySigned);
    
    		DateTime nextDay = DateTime.parse(batchStartDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
            String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
            
            List<Integer> signDay = new ArrayList<>();
            List<Integer> unsignDay = new ArrayList<>();
    
            for (int i = 0; i < 14; i++) {
                if(i > 0){
                    nextDay = nextDay.plusDays(1);
                }
                String day = DateTimeFormat.forPattern("yyyy-MM-dd").print(nextDay);
                if (historySigned.contains(day)) {
                    signDay.add(i);
                    if (day.equals(today)) {
                        break;
                    }
                } else {
                    if (day.equals(today)) {
                        break;
                    }
                    unsignDay.add(i);
                }
            }
            logger.debug("用户{}签到天列表[{}]，未签到天列表[{}]", new Object[]{account, signDay, unsignDay});
			
	        Map<String, Object> result = new HashMap<>();
			result.put("signDay", signDay);
			result.put("unsignDay", unsignDay);
			
			// 获取之前签到的总数量
			WdAppSignedBean signedBean = iWdAppSignBll.getSign(account, serverId, batchStartDate);
			
			if (signedBean == null) {
				result.put("isConnect", true);
				result.put("serialDay", 0);
				result.put("totalDay", 0);
			} else {
				result.put("serialDay", signedBean.getSerialDay());
				result.put("totalDay", signedBean.getTotalDay());

                boolean isTodaySign = isTodaySign(account, serverId, batchStartDate, date);
                result.put("isConnect", !isTodaySign);

				boolean gitIsSend = isGitIsSend(account, serverId, batchStartDate);
                result.put("isGetGift", gitIsSend);
			}
			
			result.put("isSuccess", true);
			result.put("message", "获取签到列表成功");
			String cacheTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			result.put("currentTime", cacheTime);

            logger.debug("Now Start execute {}!", updateFlag?"sign":"list");
            logger.debug("Sign data to be operated！{}", result);
	        String cacheKey = account + "_" + serverId + "_" + batchStartDate;
			if(updateFlag){
				logger.debug("Start delete the key '{}'", cacheKey);
				boolean deleteFlag = memcache.delete(cacheKey);
				logger.debug("The cache key '{}' will be removed form memcache. [Time:{},DeleteFlag:{}]", new Object[]{cacheKey, cacheTime, deleteFlag});
			} else if(!signDay.isEmpty() || !unsignDay.isEmpty()){
				logger.debug("Start add the key '{}'", cacheKey);
				boolean addMFlag = memcache.set(cacheKey, 60, result);
				logger.debug("The cache key '{}' will be added into memcache. [Time:{}, DeleteFlag:{}, Value:{}]", new Object[]{cacheKey, result, cacheTime, addMFlag});
			}
			
			return result;
		} catch (Exception e) {
			logger.info("签到服务器异常, 将数据库的数据写到缓存或者从缓存删除数据; Message:{}, Cause:{}, StackTrace:{}", new Object[]{e.getMessage(), e.getCause(), e.getStackTrace()});

	        Map<String, Object> result = new HashMap<>();
			result.put("isSuccess", false);
			result.put("message", "获取签到列表失败 ");
			return result;
		}
	}
	
	private void setSignCache(String account, int serverId, String batch, Date date) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String cacheTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		String cacheKey = account + "_" + serverId + "_" + batch;
		String cachetoDayKey = account + "_" + serverId + "_" + batch + "_" + today;
		logger.info("Start delete the key '{}'", cacheKey);
		boolean deleteFlag = memcache.delete(cacheKey);
		logger.info("The cache key '{}' will be removed form memcache. [Time:{},DeleteFlag:{}]", new Object[]{cacheKey, cacheTime, deleteFlag});
		Map<String,Boolean> todayIsSignMap = new HashMap<String,Boolean>();
		todayIsSignMap.put("isSign", true);
		memcache.set(cachetoDayKey, getTodayCacheExpire(date) , todayIsSignMap);
	}
	
	private boolean isTodaySign(String account, int serverId, String batch, Date date) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		String cacheKey = account + "_" + serverId + "_" + batch + "_" + today;
		boolean isTodaySign = false;
		
		Map<String,Boolean> todayIsSignMapCache = memcache.get(cacheKey, Map.class);
		
		if(todayIsSignMapCache != null) {
			isTodaySign = true;
		} else {
			WdAppSignedLogBean todayLog = wdAppSignBll.getTodaySignLog(account, serverId, batch,today);
			logger.info("Today {} sign data:{}", today, todayLog);
			
			Map<String,Boolean> todayIsSignMap = new HashMap<String,Boolean>();
			
			if (todayLog != null) {
				isTodaySign = true;
				todayIsSignMap.put("isSign", true);
                memcache.set(cacheKey, getTodayCacheExpire(date) , todayIsSignMap);
			} else {
				isTodaySign = false;
				todayIsSignMap.put("isSign", false);
			}
		}
		
		return isTodaySign;
	}

    /**
     * 奖品是否发放
     * @return
     */
    private boolean isGitIsSend(String account, int serverId, String batch ){
    	
    	List<String> gifts = new ArrayList<>();
    	gifts.add(WdAppSignedConstantBean.serialThreeGift);
    	gifts.add(WdAppSignedConstantBean.serialFiveGift);
    	gifts.add(WdAppSignedConstantBean.totalEightGift);
    	gifts.add(WdAppSignedConstantBean.totalTwelveGift);
    	List<WdAppSignedSendPrizeBean> signSendPrizeByPrizes = iWdAppSignSendPrizeBll.getSignSendPrizeByPrizes(account, serverId, gifts, batch);
    	if(signSendPrizeByPrizes != null){
	    	for (WdAppSignedSendPrizeBean wdAppSignedSendPrizeBean : signSendPrizeByPrizes) {
	    		if(wdAppSignedSendPrizeBean.getStatus() == 0)
	    			return true;
			}
    	}
    	return false;
    }
		
    /**
     * 获取失效时间
     * @param batch
     * @return
     */
    private int getTodayCacheExpire(Date today){
        DateTime tomorrow = new DateTime(today).plusDays(1).withTimeAtStartOfDay();
        
        return (int) new Duration(new DateTime(today), tomorrow).getStandardSeconds();
    }
		
	/**
	 * url参数转map
	 * 
	 * @param paramsStr
	 * @return
	 */
	public Map<String, String> parseQueryParams(String paramsStr) {
		if (StringUtils.isEmpty(paramsStr)) {
			return null;
		}
		Map<String, String> params = new HashMap<>();
		if (paramsStr.contains("?") && paramsStr.split("\\?").length >= 2) {
			paramsStr = paramsStr.split("\\?")[1];
		}
		String[] splits = paramsStr.split("&");

		for (String param : splits) {
			String[] split = param.split("=");
			params.put(split[0], split[1]);
		}
		return params;
	}

	/**
	 * 获取 签到二维码
	 * 
	 * @return
	 */
	public String getQrCode() {
		RestTemplate restTemplate = new RestTemplate();
		String result = "";
		try {
			ResponseEntity<Map> response = restTemplate.getForEntity(WdAppSignedConstantBean.getQrUrl, Map.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody().toString();
				String data = response.getBody().get("Data").toString();
				String splite[] = data.split(",");
				int index = splite[1].indexOf("}");
				result = splite[1].substring(9, index);
				logger.info("获取密保二维码接口responseBody的date值{}", data);
			}
		} catch (Exception e) {
			logger.info("获取密保二维码出错{}", e.toString());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 验证签到二维码
	 * 
	 * @param qrId
	 * @param type
	 * @param userId
	 * @return
	 */
	public boolean validQrCode(String qrId, String type, String userId) {
		RestTemplate restTemplate = new RestTemplate();
		boolean result = false;
		ResponseEntity<Map> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			StringBuffer md5Para = new StringBuffer("/api/qr/validate?timestamp=");
			StringBuffer urlStr = new StringBuffer("http://interface.security.gyyx.cn/api/qr/validate");
			StringBuffer paraStr = new StringBuffer("timestamp=");
			long nowDate = System.currentTimeMillis() / 1000;
			String nowDateStr = String.valueOf(nowDate);
			String param = String.format("&qrId=%s&type=%s&userId=%s", qrId, type, userId);
			md5Para.append(nowDateStr).append(param).append("123456");
			String sign = MD5.encode(md5Para.toString());
			paraStr.append(nowDateStr).append(param).append("&sign_type=MD5").append("&sign=").append(sign);
			urlStr.append("?").append(paraStr.toString()).toString();
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
			response = restTemplate.postForEntity(urlStr.toString(), httpEntity, Map.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody().toString();
				logger.info("验证密保二维码接口responseBody{}", responseBody);
				if ("success".equals(response.getBody().get("Status"))) {
					result = true;
				}
			}
		} catch (HttpClientErrorException e) {
			e.getStatusCode();
			logger.info("reponse 400  {}", e.getResponseBodyAsString());

			String exception = e.getResponseBodyAsString();
			try {
				Map<String, Object> resultMap = new ObjectMapper().readValue(exception, Map.class);
				String message = (String) resultMap.get("Message");
				switch (message) {
				case WdAppSignedConstantBean.MATRIX:
					return true;
				case WdAppSignedConstantBean.NOTPHONE:
					return true;
				case WdAppSignedConstantBean.MATRIXANDKEY:
					return true;
				default:
					return false;
				}
			} catch (IOException e1) {
				logger.info("解析response异常{}", e1);
				e1.printStackTrace();
			}
			logger.info(exception);

		} catch (Exception e) {
			logger.info("验证密保二维码出错{}", e.toString());
			logger.info("repose", response.getBody().toString());
			e.printStackTrace();
		}
		return result;
	}

	private Integer getUserIdByAccount(String account) {
		RestTemplate restTemplate = new RestTemplate();
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String salt = "123456";
		String sign = getSignM(account, timestamp, salt);
		String url = "http://account.module.gyyx.cn/user?account=" + account + "&timestamp=" + timestamp + "&sign_type="
				+ sign_type + "&sign=" + sign;
		Integer userId = null;
		logger.info("调用获取用户基本信息接口的url：{}", url);
		try {
			ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
			logger.info("调用获取用户基本信息接口response：{}", response.getBody().toString());
			if (response.getStatusCode() == HttpStatus.OK) {
				userId = (int) response.getBody().get("UserId");
				logger.info("获取userId{}", userId);
			}
		} catch (HttpClientErrorException e) {
			e.getStatusCode();
			logger.info("reponse 400  {}", e.getResponseBodyAsString());
		} catch (Exception e) {
			logger.info("reponse 400  {}", e.toString());
		}
		return userId;
	}

	/**
	 * 获取签名
	 * 
	 * @param userCode
	 * @param timestamp
	 * @param salt
	 * @return
	 */
	private static String getSignM(String account, long timestamp, String salt) {
		String string = "/user?account=" + account + "&timestamp=" + timestamp + salt;
		logger.info(string);
		return MD5.encode(string);
	}

	/**
	 * 根据serverName获取ServerId
	 * 
	 * @return
	 */
	public int getRegion(String serverName) {
		String cache = "region_list_game_2_server_"+serverName;
		String mapKey = "serviceId";
		Map<String, Integer> services = memcache.get(cache,Map.class);
		if(services != null){
			Integer serverId = services.get(mapKey);
			return serverId;
		}
		StringBuffer md5Para = new StringBuffer("/v2/game/2/server?timestamp=");
		StringBuffer urlStr = new StringBuffer("http://game.module.gyyx.cn/v2/game/2/server");
		StringBuffer paraStr = new StringBuffer("timestamp=");
		long nowDate = System.currentTimeMillis() / 1000;
		String nowDateStr = String.valueOf(nowDate);
		md5Para.append(nowDateStr).append("123456");
		String sign = MD5.encode(md5Para.toString());
		paraStr.append(nowDateStr).append("&sign_type=MD5").append("&sign=").append(sign);
		urlStr.append("?").append(paraStr.toString()).toString();
		Integer result = requestGet(urlStr.toString(), serverName);
		Map<String, Integer> data = new HashMap<>();
		data.put(mapKey, result);
		memcache.set(cache,data);
		return result;
	}

	private int requestGet(String urlStr, String serverName) {
		RestTemplate restTemplate = new RestTemplate();
		int serverId = 0;
		List<Map> list = null;
		try {
			ResponseEntity<Map> response = restTemplate.getForEntity(urlStr, Map.class);
			logger.info("调用获取问道区服response：{}", response.getBody().toString());
			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody().toString();
				list = (List<Map>) response.getBody().get("data");
				for (Map<String, Object> map : list) {
					if (serverName.equals(map.get("ServerName"))) {
						serverId = Integer.parseInt(map.get("Code") + "");
						return serverId;
					}
				}
			}
		} catch (Exception e) {
			logger.info("调用获取问道区服{}", e.toString());
		}
		return serverId;
	}

	@Override
	public List<String> sendGift(final int serverId, final String account) {
		// --------------获取batch
		String batch = memcache.get(WdAppSignedConstantBean.MEM_KEY_PREFIX, String.class);
		// String batch = "2016-11-11";
		List<String> resultList = null;
		String dataBatch = "";
		if (StringUtils.isEmpty(batch) || "null".equals(batch)) {
			SameDataBean sameDataBean = sameDataBll.getSameDataBean(WdAppSignedConstantBean.MEM_KEY_PREFIX,
					WdAppSignedConstantBean.ACTIVITY_CODE);
			if (sameDataBean == null) {
				return null;
			} else {
				dataBatch = sameDataBean.getContent();
				if (StringUtils.isEmpty(dataBatch) || "null".equals(dataBatch)) {
					return null;
				}
				batch = batch == null ? dataBatch : batch;
				if (activeTime(dataBatch)) {
					memcache.set(WdAppSignedConstantBean.MEM_KEY_PREFIX, 3600 * 30 * 24, dataBatch);
					return null;
				}
			}
		} else {
			if (activeTime(batch)) {
				return null;
			}
		}
		// 获取状态为0 可以发送礼品
		try (DistributedLock lock = new DistributedLock(WdAppSignedConstantBean.MEM_KEY_PREFIX + "enroll_" + account)) {
			lock.weakLock(30, 11);
			List<WdAppSignedSendPrizeBean> sendPrize = iWdAppSignSendPrizeBll.getSignSendPrize(account, serverId, batch,
					0);
			resultList = new ArrayList<>();
			// 遍历发送发送礼品
			if (sendPrize == null || sendPrize.size() == 0) {
				return null;
			}
			for (WdAppSignedSendPrizeBean prize : sendPrize) { 
				switch (prize.getGift()) {
				case WdAppSignedConstantBean.serialThreeGift:
					int result = iWdAppSignSendPrizeBll.modifyPrizeStatus(account, serverId,
							WdAppSignedConstantBean.serialThreeGift, batch, 1, new Date());
					if (result == 0) {
						logger.warn("账号:{} ,礼品:{}由于数据库原因修改失败", account + "区服:" + serverId,
								WdAppSignedConstantBean.serialThreeGift);
					} else {
						final String giftPackage3 = WdAppSignedConstantBean.getGiftCodeBySignCount(3);
						if (giftPackage3 != null) {
							// 发放到游戏
							new Thread(new Runnable() {
								@Override
								public void run() {
									ServerInfoBean serverInfo = callWebApiAgent.getServerStatusFromWebApi(serverId);
									CallWebServiceAgent.givePresents(2, account, giftPackage3, getFormateTime(),
											WdAppSignedConstantBean.HD_NAME + "_sign", serverInfo);
								}
							}).start();
						}
						logger.warn("账号:{} ,礼品:{}  发送成功", account + "区服:" + serverId,
								WdAppSignedConstantBean.serialThreeGift);
						resultList.add(WdAppSignedConstantBean.serialThreeGift);
						cacheIsGetGiftAfterSendGit(serverId, account, batch);
					}
					break;
				case WdAppSignedConstantBean.serialFiveGift:
					result = iWdAppSignSendPrizeBll.modifyPrizeStatus(account, serverId,
							WdAppSignedConstantBean.serialFiveGift, batch, 1, new Date());
					if (result == 0) {
						logger.warn("账号:{} ,礼品:{} 由于数据库原因修改失败", account + "区服:" + serverId,
								WdAppSignedConstantBean.serialFiveGift);
					} else {
						final String giftPackage5 = WdAppSignedConstantBean.getGiftCodeBySignCount(5);
						if (giftPackage5 != null) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									ServerInfoBean serverInfo = callWebApiAgent.getServerStatusFromWebApi(serverId);
									CallWebServiceAgent.givePresents(2, account, giftPackage5, getFormateTime(),
											WdAppSignedConstantBean.HD_NAME + "_sign", serverInfo);
								}
							}).start();
						}
						logger.warn("账号{} ,礼品{}发送成功", account + "区服:" + serverId,
								WdAppSignedConstantBean.serialFiveGift);
						resultList.add(WdAppSignedConstantBean.serialFiveGift);
						cacheIsGetGiftAfterSendGit(serverId, account, batch);
					}
					break;
				case WdAppSignedConstantBean.totalEightGift:
					result = iWdAppSignSendPrizeBll.modifyPrizeStatus(account, serverId,
							WdAppSignedConstantBean.totalEightGift, batch, 1, new Date());
					if (result == 0) {
						logger.warn("账号:{} ,礼品:{} 由于数据库原因修改失败", account + "区服:" + serverId,
								WdAppSignedConstantBean.totalEightGift);
					} else {
						final String giftPackage8 = WdAppSignedConstantBean.getGiftCodeBySignCount(8);
						if (giftPackage8 != null) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									ServerInfoBean serverInfo = callWebApiAgent.getServerStatusFromWebApi(serverId);
									CallWebServiceAgent.givePresents(2, account, giftPackage8, getFormateTime(),
											WdAppSignedConstantBean.HD_NAME + "_sign", serverInfo);
								}
							}).start();
						}
						logger.warn("账号{} ,礼品{}发送成功", account + "区服:" + serverId,
								WdAppSignedConstantBean.totalEightGift);
						resultList.add(WdAppSignedConstantBean.totalEightGift);
						cacheIsGetGiftAfterSendGit(serverId, account, batch);
					}
					break;
				case WdAppSignedConstantBean.totalTwelveGift:
					result = iWdAppSignSendPrizeBll.modifyPrizeStatus(account, serverId,
							WdAppSignedConstantBean.totalTwelveGift, batch, 1, new Date());
					if (result == 0) {
						logger.warn("账号:{} ,礼品:{} 由于数据库原因修改失败", account + "区服:" + serverId,
								WdAppSignedConstantBean.totalTwelveGift);
					} else {
						final String giftPackage12 = WdAppSignedConstantBean.getGiftCodeBySignCount(12);
						if (giftPackage12 != null) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									ServerInfoBean serverInfo = callWebApiAgent.getServerStatusFromWebApi(serverId);
									CallWebServiceAgent.givePresents(2, account, giftPackage12, getFormateTime(),
											WdAppSignedConstantBean.HD_NAME + "_sign", serverInfo);
								}
							}).start();
						}
						logger.warn("账号:{} ,礼品:{} 发送成功", account + "区服:" + serverId,
								WdAppSignedConstantBean.totalTwelveGift);
						resultList.add(WdAppSignedConstantBean.totalTwelveGift);
						cacheIsGetGiftAfterSendGit(serverId, account, batch);
					}
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			logger.info("发送奖品失败:{}", e.toString());
		}

		return resultList;
	}

	/**
	 *在完成发放完奖品后，将缓存标识置成已发放
	 * @param serverId
	 * @param account
	 * @param batch
	 */
	private void cacheIsGetGiftAfterSendGit(final int serverId, final String account, String batch) {
		String cacheKey = account + "_" + serverId + "_" + batch;
		String cacheTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		logger.info("Update IsGetGift Flag: '{}'", cacheKey);
		boolean deleteFlag = memcache.delete(cacheKey);
		logger.info("Delete the cache  '{}' after send gift. [Time:{},DeleteFlag:{}]", new Object[]{cacheKey, cacheTime, deleteFlag});
	}

	private String getFormateTime() {
		Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		c.add(Calendar.YEAR, 1);
		java.util.Date newdate = c.getTime();
		return formatter.format(newdate);
	}

	private boolean activeTime(String batch) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar eCalendar = Calendar.getInstance();
		try {
			eCalendar.setTime(sdf.parse(batch));
		} catch (ParseException e) {
			e.printStackTrace();
			return true;
		}
		eCalendar.add(Calendar.DATE, 14);
		String endTime = sdf.format(eCalendar.getTime());
		String today = sdf.format(new Date());
		return today.compareTo(endTime) >= 0 || batch.compareTo(today) > 0;
	}

	public void setCache(String account, int serverId, String batch) {
		Map<String, Object> result = memcache.get(account + "_" + serverId + "_" + batch, Map.class);
		if (result != null) {
			result.put("isConnect", false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sdf.format(new Date());
			long m;
			int signday;
			try {
				m = sdf.parse(today).getTime() - sdf.parse(batch).getTime();
				signday = (int) (m / (1000 * 60 * 60 * 24));
				List<Integer> signDayList = (List<Integer>) result.get("signDay");

				List<Integer> unsignDay = new ArrayList<>();
				if(!signDayList.contains(signday)){
					signDayList.add(signday);
				}
				for (int i = 0; i < signday; i++) {
					if (!signDayList.contains(i)) {
						unsignDay.add(i);
					}
				}
				result.put("message", "获取签到列表成功");
				if (signDayList.size() == 0) {
					result.put("serialDay", 0);
				}

				int signDayTemp = signday;
				int signDayLen = signDayList.size();
				int signDaySum = 1;
				for (int j = signDayLen - 2; j >= 0; j--) {
					Integer currentDay = signDayList.get(j);
					if (signDayTemp - currentDay == 1) {
						signDayTemp = currentDay;
						signDaySum++;
					} else if (signDayTemp - currentDay > 1) {
						break;
					}
				}
				result.put("serialDay", signDaySum);
				result.put("totalDay", signDayList.size());
				result.put("isSuccess", true);
				memcache.set(account + "_" + serverId + "_" + batch, result);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		}

	}

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
}