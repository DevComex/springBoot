package cn.gyyx.action.service.wdnovicecard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ErWeiMaBean;
import cn.gyyx.action.beans.novicecard.NoviceCardBean;
import cn.gyyx.action.beans.novicecard.NoviceCardMark;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.bll.novicecard.ErWeiMaBLL;
import cn.gyyx.action.bll.novicecard.GetMD5PasswordBLL;
import cn.gyyx.action.bll.novicecard.NoviceCardBLL;
import cn.gyyx.action.bll.novicecard.ReceiveLogBll;
import cn.gyyx.action.bll.wd9yearnovicebag.BagReceiveLogBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.core.Ip;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.security.Aes;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.message.MessageClient;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月13日 上午11:02:38
 * 描        述：
 */
public class WDNoviceCommonUtil {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WDNoviceCommonUtil.class);
	public static final boolean isRelease = true;
	
	public static final String GYYX_WAP_BAG_REG = "GYYX_WAP_BAG_REG";
	public static final String GYYX_WAP_CARD_REG_INFO = "GYYX_WAP_CARD_USER_INFO";
	public static final String GYYX_WAP_CARD_REG_PWD = "GYYX_WAP_CARD_USER_PWD";
    
	public static final String GYYX_WAP_WX_REG_INFO = "GYYX_WAP_WX_USER_INFO";
	public static final String GYYX_WAP_WX_REG_PWD = "GYYX_WAP_WX_USER_PWD";
    
	public static final String SKEY = "asdfghjklzxcvbnm";
	public static final String SIV = "qwertyuiasdfghjk";
	public static final int WDYY_ActionCode=433;
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private GetMD5PasswordBLL getMD5PasswordBll = new GetMD5PasswordBLL();
	private ErWeiMaBLL erWeiMaBll = new ErWeiMaBLL();
	private NoviceCardBLL noviceCardBll = new NoviceCardBLL();
	private BagReceiveLogBll bagReceiveLogBll = new BagReceiveLogBll();
	private ReceiveLogBll receiveLogBll = new ReceiveLogBll();
	
	//新手注册来源类型
	public static final String REGIST_SOURCE_M_BUSINESS = "m_business";//手机商务
	public static final String REGIST_SOURCE_M_WEIXIN = "m_weixin";//手机微信
	public static final String REGIST_SOURCE_M_GW = "m_gw";//手机官网
	public static final String REGIST_SOURCE_M_MT = "m_mt";//手机媒体
	private MessageClient redisClient = new MessageClient("redis.queue.gift.send");
	
	/**
	 * 发送新手礼包
	 */
	public ResultBean<String> receiveBagOperate(NoviceParameter parameter, String uuid) {
		logger.info(String.format("编号[%s]：领取新手礼包：活动发送新手礼包,account:%s",uuid,parameter.getAccount()));
		ResultBean<String> result = null;
		try{
			result = bagReceiveLogBll.setReceiveLog(parameter);
		}catch(Exception e){
			logger.error(String.format("编号[%s]：活动发送新手礼包异常,account:%s,错误堆栈：%s",uuid,parameter.getAccount(),Throwables.getStackTraceAsString(e)));
			return new ResultBean<>(false, "活动发送新手礼包异常","");
		}
		if (result == null || !result.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手礼包：活动发送新手礼包失败,返回结果：%s",uuid,result));
			return result;
		}
		ResultBean<Integer> sendResult = this.sendItem(parameter,uuid);
		if (!sendResult.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手礼包：发送奖励到游戏失败,返回结果：%s",uuid,sendResult));
			return new ResultBean<>(false, sendResult.getMessage(),"");
		}
		result.setProperties(true, parameter.getVirtualItemName(),"");
		return result;
	}

	/**
	 * 发送新手卡
	 */
	public ResultBean<String> receiveCardOperate(NoviceParameter parameter,String uuid) {
		logger.info(String.format("编号[%s]：领取新手卡：活动发送新手卡,account:%s",uuid,parameter.getAccount()));
		ResultBean<String> result = null;
		try{
			result = receiveLogBll.setReceiveLog(parameter,parameter.getVirtualItemCode(), parameter.getTypeId());
		}catch(Exception e){
			logger.error(String.format("编号[%s]：活动发送新手卡异常,account:%s,错误堆栈：%s",uuid,parameter.getAccount(),Throwables.getStackTraceAsString(e)));
			return new ResultBean<>(false, "活动发送新手卡异常","");
		}
		if (result == null || !result.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手卡：活动发送新手卡失败,返回结果：%s",uuid,result));
			return result;
		}
		ResultBean<Integer> sendResult = this.sendItem(parameter,uuid);
		if (!sendResult.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手礼包：发送奖励到游戏失败,返回结果：%s",uuid,sendResult));
			return new ResultBean<>(false, sendResult.getMessage(),"");
		}
		result.setProperties(true, "恭喜您获得了" + parameter.getVirtualItemName(),"恭喜您获得了" + parameter.getVirtualItemName());
		return result;
	}
    
	/**
     * 获取访问统计key
     */
    public String [] getPageVisitKeyFromCookie(HttpServletRequest request){
    	String [] result = new String[]{"default","default","default"};
    	Iterator<String> it = Splitter.on("; ").split(request.getHeader("Cookie")).iterator();
		while(it.hasNext()){
			String [] tempa = it.next().split("=");
			if(tempa[0].contains("ADVisitTemp")){
				result[0]=tempa[1];
			}else if(tempa[0].contains("ADVisitForEver")){
				result[1]=tempa[1];
			}else if(tempa[0].contains("PageVisitGuid")){
				result[2]=tempa[1];
			}
		}
		return result;
    }
    
    /**
     * 获取cookie
     */
    public String getCookieByName(HttpServletRequest request,String name){
    	Cookie [] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
    		for(Cookie co:cookies){
    			if(co.getName().equals(name)){
    				return co.getValue();
    			}
    		}
    	}
		return "";
    }
    
    /**
     * wap 新手卡 cookie设置用户信息
     */
    public void setUserInfoFromWapNoviceCard(HttpServletRequest request,NoviceParameter parameter){
		UserInfo us = null;
		Cookie [] cookies = request.getCookies();
    	if(cookies!=null&&cookies.length>0){
    		for(Cookie co:cookies){
    			if(co.getName().equals(GYYX_WAP_CARD_REG_INFO)){
					String aesResult = Aes.decrypt(co.getValue(), SKEY, SIV);
				    String [] paramM = aesResult.split(";"); 
				    us = new UserInfo();
				    us.setAccount(paramM[1]);
				    us.setUserId(Integer.parseInt(paramM[0]));
				    parameter.setAccount(paramM[1]);
    			}
    			if(co.getName().equals(GYYX_WAP_CARD_REG_PWD)){
    				String aesResult = Aes.decrypt(co.getValue(), SKEY, SIV);
    				parameter.setPassWord(aesResult);
    			}
    		}
    	}
    	parameter.setUserInfo(us);
	}
    
    /**
     * wap 微信新手礼包 cookie设置用户信息
     */
    public void setUserInfoFromWapWeiWinBag(HttpServletRequest request,NoviceParameter parameter){
		UserInfo us = null;
		Cookie [] cookies = request.getCookies();
    	if(cookies!=null&&cookies.length>0){
    		for(Cookie co:cookies){
    			if(co.getName().equals(GYYX_WAP_WX_REG_INFO)){
					String aesResult = Aes.decrypt(co.getValue(), SKEY, SIV);
				    String [] paramM = aesResult.split(";"); 
				    us = new UserInfo();
				    us.setAccount(paramM[1]);
				    us.setUserId(Integer.parseInt(paramM[0]));
				    parameter.setAccount(paramM[1]);
    			}
    			if(co.getName().equals(GYYX_WAP_WX_REG_PWD)){
    				String aesResult = Aes.decrypt(co.getValue(), SKEY, SIV);
    				parameter.setPassWord(aesResult);
    			}
    		}
    	}
    	parameter.setUserInfo(us);
	}
    
    /**
	 * 检测服务器信息
	 */
	public ResultBean<ServerInfoBean> checkSeverInfo(NoviceParameter noviceParameter,
			String uuid) {
		ServerInfoBean serverInfo = new ServerInfoBean();
		try {
			serverInfo = callWebApiAgent.getServerStatusFromWebApi(noviceParameter.getServerId());
			if (serverInfo == null) {
				logger.info(String.format("编号[%s]：领取新手礼包：检查服务器信息,获取服务失败,返回结果:%s",uuid,serverInfo));
				return new ResultBean<>(false, "服务器无效!",null);
			}
			if(serverInfo.getErrorMessage() != null && !"".equals(serverInfo.getErrorMessage())){
				logger.info(String.format("编号[%s]：领取新手礼包：检查服务器信息,获取服务失败,返回结果:%s",uuid,serverInfo));
				return new ResultBean<>(false, serverInfo.getErrorMessage(),null);
			}
		} catch (Exception e) {
			logger.error(String.format("编号[%s]：领取新手礼包：检查服务器信息,获取服务接口异常,错误堆栈：%s",uuid,Throwables.getStackTraceAsString(e)));
			return new ResultBean<>(false, "检查服务器信息接口异常",null);
		}
		return new ResultBean<>(true, "服务器信息正确",serverInfo);
	}
	
	/**
	 * 发奖到游戏
	 */
	public ResultBean<Integer> sendItem(NoviceParameter parameter,String uuid) {
		logger.info(String.format("编号[%s]：发奖到游戏,parameter:%s",uuid,parameter));
		boolean flag = false;
		String orderId = OrderIdBuilder.buildOrderId(parameter.getAccount());//生成订单ID
		Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(),Locale.CHINA);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		c.add(Calendar.YEAR, 1);
		java.util.Date newdate = c.getTime();
		String expiredTime = formatter.format(newdate);
		try {
			logger.info(String.format("编号[%s]：调用发奖到游戏接口,parameter:%s",uuid,parameter));
			ProcessResult processResult = CallWebServiceAgent.givePresents(
					parameter.getGameId(), parameter.getAccount(),
					parameter.getVirtualItemType(), expiredTime, parameter.getActivityId()+"",
					parameter.getServerInfo(),orderId);
			logger.info(String.format("编号[%s]：调用发奖到游戏接口,返回结果：%s",uuid,processResult));
			if(processResult != null && processResult.getErrorCode() == 0){
				flag = true;
			}
		} catch (Exception e) {
			logger.error(String.format("编号[%s]：调用发奖到游戏接口异常,account:%s,错误堆栈：%s",uuid,parameter.getAccount(),Throwables.getStackTraceAsString(e)));
		} finally {
			if(!flag){
				logger.info(String.format("编号[%s]：调用发奖到游戏接口异常,调用redis重新发送,account:%s,订单ID：%s",uuid,parameter.getAccount(),orderId));
				
		        try {
		        	//推送到redis异步发送
			        NoviceRedisSendBean noviceRedisSendBean = new NoviceRedisSendBean();
			        noviceRedisSendBean.setOrderId(orderId);
			        noviceRedisSendBean.setAccount(parameter.getAccount());
			        noviceRedisSendBean.setDescription(parameter.getActivityId()+"");
			        noviceRedisSendBean.setExpiredTime(expiredTime);
			        noviceRedisSendBean.setGameId(parameter.getGameId());
			        noviceRedisSendBean.setGiftPackage(parameter.getVirtualItemType());
			        noviceRedisSendBean.setServerIp(parameter.getServerInfo().getValue().getServerIp());
			        noviceRedisSendBean.setServerName(parameter.getServerInfo().getValue().getServerName());
			        noviceRedisSendBean.setServerPort(parameter.getServerInfo().getValue().getServerPort());
			        noviceRedisSendBean.setSourceType("novice-bag-retry");
			        
		            redisClient.send(JSONObject.toJSONString(noviceRedisSendBean).getBytes("UTF-8"));
		        } catch (Exception e) {
		        	logger.error(String.format("编号[%s]：调用redis异步重新发送礼包异常,account:%s,订单ID：%s,错误堆栈：%s",uuid,parameter.getAccount(),orderId,Throwables.getStackTraceAsString(e)));
		        }
			}
		}
		return new ResultBean<>(true, "发送成功",0);
	}
	
	/**
	 * 检查卡号是否合法
	 */
	public ResultBean<String> checkCardIsLegal(NoviceParameter parameter) {
		ResultBean<String> resultOfFunction = new ResultBean<String>(false, "判断新手卡条件未知错误", null);
		
		// 判断二维码与新手卡
		if (parameter.getCard().toCharArray().length == 14) {
			parameter.setMark(NoviceCardMark.erWeiMa);
			ErWeiMaBean erWeiMa = new ErWeiMaBean(parameter.getCard(),parameter.getBatchNo(), parameter.getGameId());
			ResultBean<ErWeiMaBean> resultErWeiMaNovice = erWeiMaBll.CheckErWeiCardNoInfoIsValid(erWeiMa);
			if (!resultErWeiMaNovice.getIsSuccess()) {
				resultOfFunction.setMessage(resultErWeiMaNovice.getMessage());
				return resultOfFunction;
			}
			// 设置给玩家发放的物品编号
			parameter.setLimitItemId(resultErWeiMaNovice.getData().getItemId());
			parameter.setTypeId(resultErWeiMaNovice.getData().getTypeId());
		} else if (parameter.getCard().toCharArray().length == 12) {
			parameter.setMark(NoviceCardMark.noviceCard);
			ResultBean<NoviceCardBean> resultNovice = noviceCardBll.CheckCardNoInfoIsValid(parameter.getCard(), parameter.getGameId(),parameter.getBatchNo());
			if (!resultNovice.getIsSuccess()) {
				resultOfFunction.setMessage(resultNovice.getMessage());
				logger.debug("resultOfFunction", resultOfFunction);
				return resultOfFunction;
			}
			// 设置给玩家发放的物品编号
			parameter.setLimitItemId(resultNovice.getData().getItemId());
			parameter.setTypeId(resultNovice.getData().getTypeId());
		} else {
			resultOfFunction.setMessage("卡号格式不正确");
			return resultOfFunction;
		}
		resultOfFunction = new ResultBean<String>(true, "卡号合法", null);
		return resultOfFunction;
	}
	
	/**
	 * 激活游戏
	 */
	public ResultBean<String> activateAccountInWdGame(NoviceParameter parameter,
			HttpServletRequest request, String uuid) {
		ResultBean<String> result;
		try {
			cn.gyyx.action.beans.ResultBean<String> activatedResult = new CallWebApiAgent().accountIsActivated(parameter.getUserInfo().getAccount()
					,String.valueOf(parameter.getServerId()));
			if(activatedResult.getIsSuccess()){
				if(activatedResult.getMessage().equals("already")){
					Date activateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(activatedResult.getData());
					Date nowDate = new Date();
					long tempTime = (nowDate.getTime()-activateDate.getTime())/1000;
					if(tempTime>30*24*60*60){  //激活超过30天
						logger.info(String.format("编号[%s]：查询激活接口返回激活时间大于30天,serverId:%s,account:%s",uuid,parameter.getServerId(),parameter.getAccount()));
						return new ResultBean<String>(false, "只有激活时间小于30天才可以领取礼包!","只有激活时间小于30天才可以领取礼包!");
					}
				}else{
					if(!StringUtils.isBlank(parameter.getUserInfo().getLoginID())){
						logger.info(String.format("编号[%s]：手动激活获取MD5密码,serverId:%s,account:%s",uuid,parameter.getServerId(),parameter.getAccount()));
						ResultBean<String> resultpass = getMD5PasswordBll.getPassword(parameter.getUserInfo().getLoginID());
						if (!resultpass.getIsSuccess()) {
							logger.info(String.format("编号[%s]：激活获取用户MD5密码接口失败,serverId:%s,account:%s,返回结果：%s",uuid,parameter.getServerId(),parameter.getAccount(),resultpass));
							return new ResultBean<String>(false, "请使用账号密码登录领取礼包","");
						} 
						parameter.setPassWord(resultpass.getData());
					}
					
					logger.info(String.format("编号[%s]：手动激活调用接口,serverId:%s,account:%s",uuid,parameter.getServerId(),parameter.getAccount()));
					result = CallWebServiceAgent.wenDaoManualActivate(parameter,Ip.getCurrent(request).asString());
					if (!result.getIsSuccess()) {
						logger.info(String.format("编号[%s]：手动激活接口失败,serverId:%s,account:%s,返回结果：%s",uuid,parameter.getServerId(),parameter.getAccount(),result));
						return new ResultBean<String>(false, "调用手动激活接口失败","");
					}
				}
			}else{
				logger.info(String.format("编号[%s]：查询激活接口返回false,serverId:%s,account:%s,返回结果：%s",uuid,parameter.getServerId(),parameter.getAccount(),activatedResult));
				return new ResultBean<String>(false, "调用社区激活接口失败","");
			}
		} catch (Exception e) {
			logger.error(String.format("编号[%s]：设置激活接口异常,serverId:%s,account:%s,错误堆栈：%s",uuid,parameter.getServerId(),parameter.getAccount(),Throwables.getStackTraceAsString(e)));
			return new ResultBean<String>(false, "活动激活异常","");
		}
		return new ResultBean<String>(true, "激活成功","");
	}
}
