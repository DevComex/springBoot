package cn.gyyx.action.service.lhzs.activityCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lhzs.activityCode.ActionConfigBean;
import cn.gyyx.action.beans.lhzs.activityCode.LHZSConfig;
import cn.gyyx.action.beans.lhzs.activityCode.LHZSConfig.PRESENT;
import cn.gyyx.action.beans.lhzs.activityCode.LHZSConfig.PRESENTTYPE;
import cn.gyyx.action.beans.lhzs.activityCode.LHZSConfig.RESULTTYPE;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.bll.lhzs.activityCode.LHZSPresentLogBLL;
import cn.gyyx.action.bll.lottery.RechargeBll;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.service.lottery.LotteryService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.security.MD5;

public class LHZSActivityCodeService {
	private static final String SERVLET_POST = "POST";
	private LHZSPresentLogBLL presentLogBLL = new LHZSPresentLogBLL();
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private long day = 24 * 3600 * 1000;
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(LHZSActivityCodeService.class);
	/**
	 * 第一步
	 * @param userInfo
	 * @param actionCode
	 * @param lotteryType
	 * @return
	 */
	public Map<String, Object> stepOne(UserInfo userInfo, int actionCode,
			String lotteryType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResultBean<String> result = new ResultBean<String>();
		ContrParmBean controParm = new UserLotteryBll()
				.getContrParm(actionCode); // 传入活动编号活动编号
		Date nowDate = new Date();
		if (nowDate.getTime() < controParm.getActivityStart().getTime()) { // 判断活动是否过期，没过期进入以下操作
			result.setProperties(false, "notstart", "活动还未开始！！");
			resultMap.put("resultBean", result);
			resultMap.put("lotteryNum", "default");
			return resultMap;
		} else if (nowDate.getTime() > controParm.getActivityEnd().getTime()) {
			result.setProperties(false, "overdue", "活动已结束！！");
			resultMap.put("resultBean", result);
			resultMap.put("lotteryNum", "default");
			return resultMap;
		} else {
			int isQualification = lotteryNum(actionCode,userInfo);
			if (isQualification > 0) { // 判断是否有抽奖资格..(是否每天抽了多于三次)
				PresentLogBean prizeLog = presentLogBLL.hasPrize(actionCode,
						userInfo.getAccount(), PRESENTTYPE.noRealPrize.name());
				if (prizeLog == null) { // 判断是否已经中奖
					// 抽奖
					ResultBean<UserLotteryBean> userLotter = new LotteryService()
							.lotteryByDataBase(actionCode, userInfo.getUserId(),
									lotteryType, userInfo.getAccount(),
									"default", 0, userInfo.getLoginIP(), null);
					if (!userLotter.getData().getPrizeEnglish()
							.equals(PRESENT.prizeEnglish.getValue())) {
						// 获取中奖所有的激活码
						String cardCodeString = new RechargeBll().getCardCode(
								actionCode, userLotter.getData().getPrizeEnglish(),
								userInfo.getUserId());
						if(cardCodeString==null){
							result.setProperties(false, RESULTTYPE.noActivityCode.name(),cardCodeString);
						}else{
							presentLogBLL.updateGameActivityCode(16,cardCodeString);
							result.setProperties(true, "3",cardCodeString);
							isQualification -=1;
						}
					} else {
						result.setProperties(true, "1","谢谢参与！！");
						isQualification -=1;
					}
				} else {
					String cardCodeString = presentLogBLL.getCardCode(userInfo.getUserId(), actionCode, PRESENT.activityCodeType.getValue());
					result.setProperties(false, RESULTTYPE.alreadyHas.name(),
							cardCodeString);
				}
			} else {
				PresentLogBean prizeLog = presentLogBLL.hasPrize(actionCode,
						userInfo.getAccount(), PRESENTTYPE.noRealPrize.name());
				if(prizeLog == null){  //没有中奖
					result.setProperties(false, RESULTTYPE.noChance.name(),"每天只能抽3次哟！");
				}else{
					String cardCodeString = presentLogBLL.getCardCode(userInfo.getUserId(), actionCode, PRESENT.activityCodeType.getValue());
					result.setProperties(false, RESULTTYPE.alreadyHas.name(),cardCodeString);
				}
			}
			resultMap.put("resultBean", result);
			resultMap.put("lotteryNum", isQualification);
			resultMap.put("account", userInfo.getAccount());
			return resultMap;
		}
	}
	
	public int lotteryNum(int actionCode,UserInfo userInfo){  //剩余抽奖次数
		Date nowDate = new Date(); 
		int isQualification = presentLogBLL.hasQualification(actionCode,
				userInfo.getAccount(),
				format.format(new Date(getDayEnd(nowDate))),
				format.format(new Date(getDayBegin(nowDate))));
		if(isQualification<0){
			return 0;
		}else{
			int result = 3-isQualification;
			if(result<0){
				return 0;
			}else{
				return result;
			}
		}
		
	}
	public boolean actionTime(int actionCode){
		ContrParmBean controParm = new UserLotteryBll().getContrParm(actionCode);
		Date nowDate = new Date();
		if(nowDate.after(controParm.getActivityStart())&&nowDate.before(controParm.getActivityEnd())){
			return true;
		}
		return false;
	}
	/**
	 *获取灵魂战神区服
	 * @return
	 */
	public String getRegion(){
		StringBuffer md5Para = new StringBuffer("/v2/game/16/server?timestamp=");
		StringBuffer urlStr = new StringBuffer("http://game.module.gyyx.cn/v2/game/16/server");
		StringBuffer paraStr = new StringBuffer("timestamp=");
		long nowDate = System.currentTimeMillis()/1000;
		String nowDateStr = String.valueOf(nowDate);
		md5Para.append(nowDateStr).append("123456");
		String sign = MD5.encode(md5Para.toString());
		paraStr.append(nowDateStr).append("&sign_type=MD5").append("&sign=").append(sign);
		urlStr.append("?").append(paraStr.toString()).toString();
		String result = requestGet(urlStr.toString(),"UTF-8");
		return result;
	}
	/**
	 * 第二步   0未领取 1已领取  2已激活 
	 * @param userInfo
	 * @param actionCode
	 * @param serverID
	 * @param activeCode 激活码
	 * @return
	 */
	public ResultBean<String> stepTwo(UserInfo userInfo, int actionCode,
			String serverID, String serverName,String activeCode,String userPsw) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(presentLogBLL.isExistActiveCode(activeCode)){    //已经存在
			resultBean.setProperties(false,  RESULTTYPE.error.name(), "该激活码已经被使用！");
		}else{
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("Account", userInfo.getAccount());
//			paraMap.put("Account", "dceshiyonghu99");
			paraMap.put("Ip", userInfo.getLoginIP());
			paraMap.put("GameId", LHZSConfig.GAMEID);
			paraMap.put("ServerId", serverID);
			paraMap.put("GamePwd", userPsw); // 暂时不知道应该怎么输入
			paraMap.put("IsNeedActiveCode", "1");    //需要再问一下 TODO
			paraMap.put("ActiveCode", activeCode);
			paraMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
			String resultStr = post("http://interface.account.gyyx.cn/game/client/register",paraMap, "123456");
			String isSuccess = "",JSONMes="";
			try{
				JSONObject jsonObj = JSONObject.fromObject(resultStr); 
				isSuccess = jsonObj.getString("IsSuccess");
				JSONMes = jsonObj.getString("Message");
			}catch(Exception e){
				LOG.error("激活返回信息出错！！",e);
				resultBean.setProperties(false, RESULTTYPE.error.name(), "激活失败！！");
				return resultBean;
			}
			if(isSuccess.equals("")){
				resultBean.setProperties(false, RESULTTYPE.error.name(), "激活失败！！");
			}if(isSuccess.equals("true")){
				boolean resultBool = presentLogBLL.isExist(actionCode, userInfo.getAccount(), PRESENTTYPE.noRealPrize.name());
				if(resultBool){    //存在
					presentLogBLL.updateActivation(actionCode, userInfo.getAccount(), PRESENTTYPE.noRealPrize.name(), serverID, serverName,activeCode);
				}else{    //不存在
					presentLogBLL.insertPrizeLog(userInfo.getAccount(), Integer.parseInt(serverID), serverName, activeCode, format.format(new Date()), userInfo.getUserId().toString());
				}
				resultBean.setProperties(true, RESULTTYPE.success.name(), "激活成功！！");
			}else{
				resultBean.setProperties(false, RESULTTYPE.error.name(), JSONMes);
			}
		}
		return resultBean;
	}
	/**
	 * 第三步
	 * @param actionCode
	 * @param account
	 * @return
	 */
	public ResultBean<String> stepThree(int gameId,int userId){
		ResultBean<String> result = new ResultBean<String>();
		Integer resultInt = presentLogBLL.isActivation(gameId, userId);
		if(resultInt==null){
			result.setProperties(true, RESULTTYPE.error.name(), "您还没有激活！！");
		}else if(resultInt>0){
			result.setProperties(false, RESULTTYPE.success.name(), "激活成功！！");
		}else{
			result.setProperties(false, RESULTTYPE.error.name(), "查询失败！！");
		}
		return result;
	}
	/**
	 * 获取一天开始时间
	 * 
	 * @return
	 */
	public long getDayBegin(Date date) {
		long ts = date.getTime();
		ts = ts - ts % (24 * 3600 * 1000) - 8*60*60*1000 ;
		return ts;
	}
	/**
	 * 获取一天结束时间
	 * 
	 * @return
	 */
	public long getDayEnd(Date date) {
		return getDayBegin(date) + (24 * 3600 * 1000);
	}
	/**
	 * post请求
	 * 
	 * @param urlStr
	 * @param paramMap
	 * @param key
	 * @return
	 */
	public String requestPost(String urlStr, Map<String, String> paramMap,
			String key) {
		URL url = null;
		HttpURLConnection conn = null;
		String result = "";
		String paramStr = prepareParam(paramMap, key);
		try {
			url = new URL(urlStr+"?"+paramStr);
			LOG.debug("begin connect " + urlStr);
			LOG.debug("connection type :" + SERVLET_POST);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			System.out.println("paraStr"+paramStr);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.toString().getBytes("utf-8"));
			os.close();
			InputStream a = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			LOG.debug("over connection ... begin response message ...");
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
			LOG.debug("get reponse message :" + result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("a");
		}
		return result;
	}
	public String post(String url, Map<String, String> paramMap,
			String key){
		StringBuffer response = new StringBuffer();
		String paramStr = prepareParam(paramMap, key);
		String paraUrl = url+"?"+paramStr;
		HttpClient client = new HttpClient();
//		HttpMethod method = new PostMethod(paraUrl);
		
		PostMethod method = new PostMethod(url);
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
        NameValuePair[] param = { 
        		new NameValuePair("Account", paramMap.get("Account")),
//        		paraMap.put("Account", "dceshiyonghu99");
        		new NameValuePair("Ip", paramMap.get("Ip")),
        		new NameValuePair("GameId", paramMap.get("GameId")),
        		new NameValuePair("ServerId", paramMap.get("ServerId")),
        		new NameValuePair("GamePwd", paramMap.get("GamePwd")), // 暂时不知道应该怎么输入
        		new NameValuePair("IsNeedActiveCode", "1"),    //需要再问一下 TODO
        		new NameValuePair("ActiveCode", paramMap.get("ActiveCode")),
        		new NameValuePair("timestamp", String.valueOf(System.currentTimeMillis()/1000))
        };
//        		
//        		new NameValuePair("Account",paramMap.get("Account")),
//                new NameValuePair("lastCity","沈阳"),
//                new NameValuePair("userID",""),
//                new NameValuePair("theDate","") } ;
        method.setRequestBody(param);
        method.releaseConnection();
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK||method.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),"utf-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}
	public String requestGet(String urlStr,String encode){
		RestClient client = new RestClient();
		String result = "";
		try {
			org.apache.wink.client.Resource resource = client.resource(urlStr)
					.accept("application/json");
			ClientResponse response = (resource).get();
			// 接收返回响应体
			result = response.getEntity(String.class);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.warn("获取机构列表失败" + e.getMessage());
		}
		return result;
	}
	/**
	 * 生成签名参数
	 * 
	 * @param paramMap
	 * @return
	 */
	private String prepareParam(Map<String, String> paramMap, String key) {
		StringBuffer paraBuffer = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer("/game/client/register");
		Object[] keyObj = paramMap.keySet().toArray();
		String[] keyArrayStr = new String[keyObj.length];
		for (int i = 0; i < paramMap.keySet().toArray().length; i++) {
			keyArrayStr[i] = (String) keyObj[i];
		}
		quickSort(keyArrayStr, 0, paramMap.size() - 1);
		for (int i = 0; i < keyArrayStr.length; i++) {
			if (i == 0) {
				paraBuffer.append(keyArrayStr[i]).append("=").append(paramMap.get(keyArrayStr[i]));
				urlBuffer.append("?").append(keyArrayStr[i]).append("=").append(paramMap.get(keyArrayStr[i]));
			} else {
				paraBuffer.append("&").append(keyArrayStr[i]).append("=")
						.append(paramMap.get(keyArrayStr[i]));
				urlBuffer.append("&").append(keyArrayStr[i]).append("=")
						.append(paramMap.get(keyArrayStr[i]));
			}
		}
		String sign = MD5.encode(urlBuffer.toString() + key);
		paraBuffer.append("&sign_type=MD5&sign=").append(sign);
		return paraBuffer.toString();
	}

	/**
	 * 快排
	 * 
	 * @param array
	 * @param first
	 * @param last
	 * @return
	 */
	public int executeCom(String[] array, int first, int last) {
		int i = first, j = last;
		String standard = array[i];
		while (i < j) {
			if (!(array[j] == null)) {
				while (i < j && array[j].compareTo(standard) >= 0) {
					j--;
				}
				if (i < j) {
					array[i] = array[j];
				}
				while (i < j && array[i].compareTo(standard) <= 0) {
					i++;
				}
				if (i < j) {
					array[j] = array[i];
				}
			} else {
				j--;
			}
		}
		array[i] = standard;
		return i;
	}

	public void quickSort(String[] array, int first, int last) {
		if (first < last) {
			int i = executeCom(array, first, last);
			quickSort(array, first, i - 1);
			quickSort(array, i + 1, last);
		}
	}
	
	
}
