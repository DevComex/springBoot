
package cn.gyyx.action.service.wdnovicecard;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.NovicCardConfig.NoviceActionType;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.novicecard.VirtualItemBean;
import cn.gyyx.action.beans.userregistlog.UserRegistLogBean;
import cn.gyyx.action.beans.wd9yearnovicebag.BagActivityConfigBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.novicecard.VirtualItemBll;
import cn.gyyx.action.bll.userregistlog.UserRegistLogBLL;
import cn.gyyx.action.bll.wd9yearnovicebag.BagActivityConfigBll;
import cn.gyyx.action.service.mobile.website.CallApi;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.security.Aes;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.service.novicecard.NoviceCardMutex;

/**
 * 版 权：光宇游戏
 * 作 者：ChengLong 
 * 创建时间：2016年11月17日 下午12:49:24 
 * 描 述：新手礼包-公用
 */
public class WDNoviceCommonService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WDNoviceCommonService.class);
	
	private BagActivityConfigBll bagActivityConfigBll = new BagActivityConfigBll();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private WDNoviceCommonUtil noviceCommonUtil = new WDNoviceCommonUtil();
	
	private static final String REGULAR = "^1((3\\d)|(4[57])|(5[0-35-9])|(7[0678])|(8\\d))\\d{8}$";
    private static final String REGULAR_ACCOUNTNAME = "^[a-zA-Z][A-Za-z0-9]{5,15}$";
    
    //private RegisteBLL registeBLL = new RegisteBLL();//未使用
    private UserRegistLogBLL userRegistLogBLL = new UserRegistLogBLL();
	
	/**
	 * 领取礼包
	 */
	public ResultBean<String> receiveInPcOfBag(NoviceParameter noviceParameter,HttpServletRequest request,String uuid) {
		logger.info(String.format("编号[%s]：领取新手礼包：account:%s,hdName:%s,activityId:%s",uuid,noviceParameter.getAccount(),noviceParameter.getHdName(), noviceParameter.getActivityId()));
		
		// 检查活动配置信息
		logger.info(String.format("编号[%s]：领取新手礼包：检查活动配置信息,hdName:%s",uuid,noviceParameter.getHdName()));
		ResultBean<BagActivityConfigBean> resultParameter = bagActivityConfigBll.getConfigMessage(noviceParameter.getHdName());
		if (!resultParameter.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手礼包：检查活动配置信息,获取配置失败,返回结果:%s",uuid,resultParameter));
			return new ResultBean<String>(false, resultParameter.getMessage(),"");
		}
		noviceParameter.setActivityId(resultParameter.getData().getCode());
		noviceParameter.setVirtualItemType(resultParameter.getData().getPrize());
		noviceParameter.setVirtualItemCode("VirtualPresent");
		
		// 检查服务器信息
		logger.info(String.format("编号[%s]：领取新手礼包：检查服务器信息,serverId:%s",uuid,noviceParameter.getServerId()));
		ResultBean<ServerInfoBean> serverInfoResult = noviceCommonUtil.checkSeverInfo(noviceParameter, uuid);
		if(serverInfoResult == null || !serverInfoResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手礼包：检查服务器信息,获取配置失败,返回结果:%s",uuid,serverInfoResult));
			return new ResultBean<String>(false, serverInfoResult.getMessage(),"");
		}
		noviceParameter.setServerInfo(serverInfoResult.getData());
				
		// 判断互斥
		logger.info(String.format("编号[%s]：领取新手礼包：检查互斥",uuid));
		ResultBean<String> mutexResult = NoviceCardMutex.judgeMutex(noviceParameter, NoviceActionType.bag);
		if(!mutexResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手礼包：检查互斥结果,%s",uuid,mutexResult.getMessage()));
			return mutexResult;
		}
		// 激活账号（检查激活情况 并帮手动激活）
		logger.info(String.format("编号[%s]：领取新手礼包：检查激活,serverId:%s,account:%s",uuid,noviceParameter.getServerId(),noviceParameter.getAccount()));
		ResultBean<String> activateGameResult = noviceCommonUtil.activateAccountInWdGame(noviceParameter, request, uuid);
		if(activateGameResult == null || !activateGameResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手礼包：检查服务器信息,激活账号失败,返回结果：%s",uuid,activateGameResult));
			return activateGameResult;
		}
		
		return noviceCommonUtil.receiveBagOperate(noviceParameter,uuid);
	}

	/**
	 * 领取新手卡
	 */
	public ResultBean<String> receiveInPcOfCard(NoviceParameter parameter,
			HttpServletRequest request, String uuid) {
		logger.info(String.format("编号[%s]：领取新手卡：account:%s,card:%s,batchNo:%s,hdName:%s",uuid,parameter.getAccount(),parameter.getCard(), parameter.getBatchNo(), parameter.getHdName()));
		
		// 检查活动配置信息
		logger.info(String.format("编号[%s]：领取新手卡：检查活动配置信息,hdName:%s",uuid,parameter.getHdName()));
		ResultBean<ActivityConfigBean> resultParameter = activityConfigBll.getConfigMessage(parameter.getHdName());
		if (!resultParameter.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手卡：检查活动配置信息,获取配置失败",uuid));
			return new ResultBean<String>(false, resultParameter.getMessage(),"");
		}
		parameter.setActivityId(resultParameter.getData().getCode());
		// 检查服务器信息
		logger.info(String.format("编号[%s]：领取新手卡：检查服务器信息,serverId:%s",uuid,parameter.getServerId()));
		ResultBean<ServerInfoBean> serverInfoResult = noviceCommonUtil.checkSeverInfo(parameter, uuid);
		if(serverInfoResult == null || !serverInfoResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手卡：检查服务器信息,返回失败,serverId:%s,返回结果：%s",uuid,parameter.getServerId(),serverInfoResult));
			return new ResultBean<String>(false, serverInfoResult.getMessage(),"");
		}
		parameter.setServerInfo(serverInfoResult.getData());
				
		//判断新手卡存在与卡号是否有效
		logger.info(String.format("编号[%s]：领取新手卡：检查卡号是否合法,card:%s",uuid,parameter.getCard()));
		ResultBean<String> checkCardIsLegalResult = noviceCommonUtil.checkCardIsLegal(parameter);
		if(checkCardIsLegalResult == null || !checkCardIsLegalResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手卡：检查卡号是否合法,返回失败,card:%s,返回结果：%s",uuid,parameter.getCard(),checkCardIsLegalResult));
			return checkCardIsLegalResult;
		}
		
		// 判断互斥
		logger.info(String.format("编号[%s]：领取新手卡：检查互斥",uuid));
		ResultBean<String> mutexResult = NoviceCardMutex.judgeMutex(parameter, NoviceActionType.card);
		if(!mutexResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手卡：检查互斥结果,返回结果：%s",uuid,mutexResult));
			return mutexResult;
		}
		
		// 激活账号（检查激活情况 并帮手动激活）
		logger.info(String.format("编号[%s]：领取新手卡：检查激活,serverId:%s,account:%s",uuid,parameter.getServerId(),parameter.getAccount()));
		ResultBean<String> activateGameResult = noviceCommonUtil.activateAccountInWdGame(parameter, request, uuid);
		if(activateGameResult == null || !activateGameResult.getIsSuccess()){
			logger.info(String.format("编号[%s]：领取新手卡：检查激活失败,返回结果：%s",uuid,activateGameResult));
			return activateGameResult;
		}
		
		// 检查领取新手卡相关判断条件的正确性（帐号信息，卡号信息和服务器信息）
		ResultBean<VirtualItemBean> reusltVI = new VirtualItemBll().getVirtualItemByCode(parameter.getLimitItemId());
		if (!reusltVI.getIsSuccess()) {
			logger.info(String.format("编号[%s]：领取新手卡：检查领取新手卡相关判断条件的正确性失败,返回结果：%s",uuid,reusltVI));
			return new ResultBean<String>(false, "检查领取新手卡关联物品失败","");
		}
		parameter.setVirtualItemCode(reusltVI.getData().getItemName());
		parameter.setVirtualItemType(reusltVI.getData().getItemName());//存储过程type和name是反着的,所有代码也反着写
		parameter.setVirtualItemName(reusltVI.getData().getItemNote());
		return noviceCommonUtil.receiveCardOperate(parameter,uuid);
	}
	
	/**
     * 移动端官网注册
     */
    public ResultBean<String> wapBagRegist(String uuid,String phone,String password,String verifyCode,HttpServletRequest request,HttpServletResponse response){
    	ResultBean<String> r = new ResultBean<>(false, "注册失败","");
    	CallApi call = new CallApi();
    	logger.info(String.format("编号[%s]：移动端官网注册：phone:%s",uuid,phone));
        //验证手机验证码是否成功
    	cn.gyyx.action.beans.ResultBean<String> resultCAPTCHA = call.examineCAPTCHA(phone,verifyCode);
        if(WDNoviceCommonUtil.isRelease && !resultCAPTCHA.getIsSuccess()){
        	logger.info(String.format("编号[%s]：移动端官网注册,验证码错误：phone:%s",uuid,phone));
        	return new ResultBean<>(false, resultCAPTCHA.getMessage(),"");
        }
        //判断手机号是否符合
    	if(!phone.matches(REGULAR)){
    		return new ResultBean<>(false, "请填写正确的手机号","");
    	}
    	//调用接口注册
		String [] reg = noviceCommonUtil.getPageVisitKeyFromCookie(request);
		logger.info(String.format("编号[%s]：移动端官网注册,调用接口注册：phone:%s",uuid,phone));
		cn.gyyx.action.beans.ResultBean<String> result = new CallApi().register(reg,phone, password, request,"phone");
		if(!result.getIsSuccess()){
			logger.info(String.format("编号[%s]：移动端官网注册,调用接口注册,返回失败,phone:%s,返回结果:%s",uuid,phone,result));
        	return new ResultBean<>(false, result.getMessage(),"");
		}
		logger.info(String.format("编号[%s]：移动端官网注册,注册成功,设置cookie：phone:%s",uuid,phone));
		Cookie cookie = new Cookie(WDNoviceCommonUtil.GYYX_WAP_BAG_REG, Aes.encrypt(MD5.encode(password)+";"+result.getData()+";"+phone, WDNoviceCommonUtil.SKEY, WDNoviceCommonUtil.SIV));//pwd;id;phone
		cookie.setMaxAge(60*60*24*15);
		response.addCookie(cookie);
		r = new ResultBean<>(true, "注册成功","");
		return r;
    }
    
    /**
     * 移动端官网-领取礼包
     */
    public ResultBean<String> obtainPresent(String uuid,NoviceParameter p,int serverCode,HttpServletRequest request){
    	logger.info(String.format("编号[%s]：移动端官网领取礼包",uuid));
    	NoviceParameter para = new NoviceParameter();
    	UserInfo userInfo = new UserInfo();
    	logger.info(String.format("编号[%s]：移动端官网领取礼包-开始获取cookie",uuid));
    	//获取cookie中的值 
    	String cookieValue = noviceCommonUtil.getCookieByName(request,WDNoviceCommonUtil.GYYX_WAP_BAG_REG);
		if (StringUtils.isEmpty(cookieValue)) {
    		return new ResultBean<String>(false, "注册后请立即领取，您已经错过领取时间!", "");
    	}
		logger.info(String.format("编号[%s]：移动端官网领取礼包-获取cookie信息为：%s",uuid,cookieValue));
        String aesResult = Aes.decrypt(cookieValue, WDNoviceCommonUtil.SKEY, WDNoviceCommonUtil.SIV);
        String paramM[]  = aesResult.split(";");  //0 pwd  1userId  2 phone
        userInfo.setUserId(Integer.parseInt(paramM[1]));
        userInfo.setAccount(paramM[2]);
        para.setAccount(paramM[2]);
        para.setHdName(p.getHdName());
        para.setServerId(serverCode);
        para.setUserInfo(userInfo);
        para.setPassWord(paramM[0]);
        para.setGameId(2);
        para.setVirtualItemName(p.getVirtualItemName());
        //激活发送物品
        return receiveInPcOfBag(para,request,uuid);
    }
    
    /**
	 * 移动端新手卡媒体注册 用户名注册和手机注册
	 */
	public ResultBean<String> wapCardRegist(String uuid,String userType, String accountName, String password,String smsCode,String imgCode, HttpServletRequest request,
			HttpServletResponse response) {
		return wapCardRegistImpl(uuid,userType,accountName,password,smsCode,imgCode,request,response,WDNoviceCommonUtil.GYYX_WAP_CARD_REG_INFO,WDNoviceCommonUtil.GYYX_WAP_CARD_REG_PWD);
	}
	
	/**
	 * 移动端新手卡微信注册 用户名注册和手机注册
	 */
	public ResultBean<String> wapCardWeiXinRegist(String uuid,String userType, String accountName, String password,String smsCode,String imgCode, HttpServletRequest request,
			HttpServletResponse response) {
		return wapCardRegistImpl(uuid,userType,accountName,password,smsCode,imgCode,request,response,WDNoviceCommonUtil.GYYX_WAP_WX_REG_INFO,WDNoviceCommonUtil.GYYX_WAP_WX_REG_PWD);
	}
    
	private ResultBean<String> wapCardRegistImpl(String uuid, String userType,
			String accountName, String password, String smsCode,
			String imgCode, HttpServletRequest request,
			HttpServletResponse response, String cookieValue1,
			String cookieValue2) {
		ResultBean<String> result = new ResultBean<String>(false, "注册失败!", "");
		
		if (StringUtils.isBlank(userType)) {
			return new ResultBean<>(false, "用户类型不能为空!","");
		}
		if (!userType.equals("phone") && !userType.equals("custom")) {
			return new ResultBean<>(false, "用户类型不正确!","");
		}
		if (StringUtils.isBlank(accountName)) {
			if(userType.equals("phone")){
				result.setMessage("手机号不能为空!");
			}else{
				result.setMessage("用户名称不能为空!");
			}
			return new ResultBean<>(false, result.getMessage(),"");
		}
		if (StringUtils.isBlank(accountName)) {
			if(userType.equals("phone")){
				result.setMessage("手机号不能为空!");
			}else{
				result.setMessage("用户名称不能为空!");
			}
			return new ResultBean<>(false, result.getMessage(),"");
		}else{
			if(userType.equals("phone")){
				if(!accountName.matches(REGULAR)){
					return new ResultBean<>(false, "手机号格式不正确!","");
				}
			}else{
				if(!accountName.matches(REGULAR_ACCOUNTNAME)){
					return new ResultBean<>(false, "用户名格式不正确!","");
				}
			}
		}
		
		if(userType.equals("phone")){
			if(StringUtils.isBlank(smsCode)){
				return new ResultBean<>(false, "短信验证码不能为空!","");
			}
		}else{
			if(StringUtils.isBlank(imgCode)){
				return new ResultBean<>(false, "验证码不能为空!","");
			}
		}
		if (StringUtils.isBlank(password)) {
			return new ResultBean<>(false, "密码不能为空!","");
		}
		logger.info(String.format("编号[%s]：wap媒体或微信注册,发送验证码：account:%s",uuid,accountName));
		CallApi call = new CallApi();
		if(userType.equals("phone")){
			//验证手机验证码是否成功
			cn.gyyx.action.beans.ResultBean<String> resultCAPTCHA = call.examineCAPTCHA(accountName,smsCode);
	        if(WDNoviceCommonUtil.isRelease && !resultCAPTCHA.getIsSuccess()){
	        	return new ResultBean<>(false, "手机验证码填写错误!","");
	        }
		}else{
			if (WDNoviceCommonUtil.isRelease && !new Captcha(request, response).equals(imgCode)) {
				return new ResultBean<>(false, "验证码填写错误!","");
			}
		}
		logger.info(String.format("编号[%s]：wap媒体或微信注册,调用接口注册：account:%s",uuid,accountName));
		//调用接口注册
		//注册
		String[] reg = noviceCommonUtil.getPageVisitKeyFromCookie(request);
		cn.gyyx.action.beans.ResultBean<String> resistResult = new CallApi().register(reg,accountName, password, request,userType);
		if(resistResult.getIsSuccess()){
			String pwd = MD5.encode(password);
			//0 id 1 accountName
			Cookie GYYX_RP_USER_INFO = new Cookie(cookieValue1, Aes.encrypt(resistResult.getData()+";"+accountName, WDNoviceCommonUtil.SKEY, WDNoviceCommonUtil.SIV));
			Cookie GYYX_RP_USER_PWD = new Cookie(cookieValue2, Aes.encrypt(pwd, WDNoviceCommonUtil.SKEY, WDNoviceCommonUtil.SIV));
			GYYX_RP_USER_INFO.setMaxAge(60*60*24*15);
			GYYX_RP_USER_PWD.setMaxAge(60*60*24*15);
			response.addCookie(GYYX_RP_USER_INFO);
			response.addCookie(GYYX_RP_USER_PWD);
			
			try{
				UserRegistLogBean ul = new UserRegistLogBean();
				ul.setAccountName(accountName);
				ul.setAccountType(userType);
				ul.setCreateDate(new Date());
				ul.setRegistSource((String)request.getAttribute("registSource"));
				userRegistLogBLL.insertRegiste(ul);
			}catch(Exception e){
				logger.error("问道新手礼包活动新增注册用户【"+accountName+"】失败！",e);
			}
			logger.info(String.format("编号[%s]：wap媒体或微信注册,调用接口注册：account:%s,注册成功",uuid,accountName));
			return new ResultBean<>(true, "注册成功!", "");
		}else{
			logger.info(String.format("编号[%s]：wap媒体或微信注册,调用接口注册失败：account:%s,返回结果：%s",uuid,accountName,resistResult));
			return new ResultBean<>(false, resistResult.getMessage(),"");
		}
	}

}