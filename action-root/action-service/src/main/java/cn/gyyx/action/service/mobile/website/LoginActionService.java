package cn.gyyx.action.service.mobile.website;


import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.userregistlog.UserRegistLogBean;
import cn.gyyx.action.bll.mobile.website.RegisteBLL;
import cn.gyyx.action.bll.userregistlog.UserRegistLogBLL;
import cn.gyyx.action.service.SimpleCaptcha;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.security.Aes;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Splitter;

public class LoginActionService {
    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(LoginActionService.class);
    private static final String REGULAR = "^1((3\\d)|(4[57])|(5[0-35-9])|(7[0678])|(8\\d))\\d{8}$";
    private static final String REGULAR_ACCOUNTNAME = "^[a-zA-Z][A-Za-z0-9]{5,15}$";
    
    private RegisteBLL registeBLL = new RegisteBLL();//未使用
    private UserRegistLogBLL userRegistLogBLL = new UserRegistLogBLL();
    private static final String SKEY = "asdfghjklzxcvbnm";
    private static final String SIV = "qwertyuiasdfghjk";
    /**
     * 注册
     * @return
     */
    public ResultBean<String> longinAction(String phone,String password,String verifyCode,HttpServletRequest request,HttpServletResponse response){
    	CallApi call = new CallApi();
        //验证手机验证码是否成功
        ResultBean<String> resultCAPTCHA = call.examineCAPTCHA(phone,verifyCode);
        if(resultCAPTCHA.getIsSuccess()){
        	//判断手机号是否符合
        	if(phone.matches(REGULAR)){
        		//判断是否注册过（自己的表）
        		if(registeBLL.isExist(phone).getIsSuccess()){
        			//已经注册过
        			resultCAPTCHA.setProperties(false, "该账号已注册过!", "");
        		}else{
        			//注册
        			String [] reg = getCookieValue(request);
        			ResultBean<String> result = new CallApi().register(reg,phone, password, request,"phone");
        			if(result.getIsSuccess()){
        				//回写cookies
        				String pwd = MD5.encode(password);
        				//0 pwd  1 id 2 phone
        				Cookie cookie = new Cookie("GYYX_RP", Aes.encrypt(pwd+";"+result.getData()+";"+phone, SKEY, SIV));
        				cookie.setMaxAge(60*60*24*15);
        				response.addCookie(cookie);
        				resultCAPTCHA.setProperties(true, "注册成功!", "");
        			}else{
        				resultCAPTCHA.setProperties(false, result.getMessage(), "");
        			}
        		}
        	}else{
        		resultCAPTCHA.setProperties(false, "请填写正确的手机号!", "");
        	}
        }else{
        	resultCAPTCHA.setProperties(false, resultCAPTCHA.getMessage(), "");
        }
        return resultCAPTCHA;
    }
    /**
     * 发送奖品
     * @return
     */
    public cn.gyyx.action.beans.novicecard.ResultBean<String> obtainPresent(NoviceParameter p,int serverCode,HttpServletRequest request){
    	cn.gyyx.action.beans.novicecard.ResultBean<String> activateResult = new cn.gyyx.action.beans.novicecard.ResultBean<String>();
    	NoviceParameter para = new NoviceParameter();
    	UserInfo userInfo = new UserInfo();
    	//获取cookie中的值 
    	String result = "";
    	Cookie [] cookies = request.getCookies();
    	if(cookies!=null&&cookies.length>0){
    		for(Cookie co:cookies){
    			if(co.getName().equals("GYYX_RP")){
    				result = co.getValue();
    				break;
    			}
    		}
    	}else{
    		activateResult.setProperties(false, "注册后请立即领取，您已经错过领取时间!!", "");
    		return activateResult;
    	}
        if("".equals(result)){
        	activateResult.setProperties(false, "注册后请立即领取，您已经错过领取时间!", "");
        	return activateResult;
        }
        String aesResult = Aes.decrypt(result, SKEY, SIV);
        LOG.debug("send cookies:"+aesResult);
        String [] paramM = aesResult.split(";");  //0 pwd  1userId  2 phone
        userInfo.setLoginID(paramM[1]);
        userInfo.setLoginID(paramM[1]);
        userInfo.setAccount(paramM[2]);
        para.setAccount(paramM[2]);
        para.setHdName(p.getHdName());
        para.setServerId(serverCode);
        para.setUserInfo(userInfo);
        para.setPassWord(paramM[0]);
        para.setGameId(2);
        para.setCheckType("ActivationCheck");
        para.setCellPhone("无");
        para.setActivityId(p.getActivityId());
        para.setVirtualItemName(p.getVirtualItemName());
        //激活发送物品
        activateResult = new ObtainPresent().sendNoviceBag(para,request);
        return activateResult;
    }
    /**
     * ADVisitTemp 0
     * ADVisitForEver 1
     * PageVisitGuid 2
     * @return
     */
    private String [] getCookieValue(HttpServletRequest request){
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
		LOG.debug("cookies :"+result[0]+"--"+result[1]+"--"+result[1]);
		return result;
    }
	/**
	 * 移动端新手卡注册 用户名注册和手机注册
	 */
	public cn.gyyx.action.beans.novicecard.ResultBean<String> registFromNoviceCard(
			String userType, String accountName, String password,
			String smsCode,String imgCode, HttpServletRequest request,
			HttpServletResponse response) {
		cn.gyyx.action.beans.novicecard.ResultBean<String> result = new cn.gyyx.action.beans.novicecard.ResultBean<String>();
		result.setProperties(false, "注册失败！", "");
		
		if (StringUtils.isBlank(userType)) {
			result.setMessage("用户类型不能为空！");
			return result;
		}
		if (!userType.equals("phone") && !userType.equals("custom")) {
			result.setMessage("用户类型不正确！");
			return result;
		}
		if (StringUtils.isBlank(accountName)) {
			if(userType.equals("phone")){
				result.setMessage("手机号不能为空！");
			}else{
				result.setMessage("用户名称不能为空！");
			}
			return result;
		}
		if (StringUtils.isBlank(accountName)) {
			if(userType.equals("phone")){
				result.setMessage("手机号不能为空！");
			}else{
				result.setMessage("用户名称不能为空！");
			}
			return result;
		}else{
			if(userType.equals("phone")){
				if(!accountName.matches(REGULAR)){
					result.setMessage("手机号格式不正确！");
					return result;
				}
			}else{
				if(!accountName.matches(REGULAR_ACCOUNTNAME)){
					result.setMessage("用户名格式不正确！");
					return result;
				}
			}
		}
		
		if(userType.equals("phone")){
			if(StringUtils.isBlank(smsCode)){
				result.setMessage("短信验证码不能为空！");
				return result;
			}
		}else{
			if(StringUtils.isBlank(imgCode)){
				result.setMessage("验证码不能为空！");
				return result;
			}
		}
		if (StringUtils.isBlank(password)) {
			result.setMessage("密码不能为空！");
			return result;
		}
		CallApi call = new CallApi();
		if(userType.equals("phone")){
			//验证手机验证码是否成功
	        ResultBean<String> resultCAPTCHA = call.examineCAPTCHA(accountName,smsCode);
	        if(!resultCAPTCHA.getIsSuccess()){
	        	result.setMessage("手机验证码填写错误！");
				return result;
	        }
		}else{
			if (!new Captcha(request, response).equals(imgCode)) {
				result.setProperties(false, "验证码填写错误", "");
				return result;
			}
		}
		
		//调用接口注册
		//注册
		String[] reg = getCookieValue(request);//百度统计
		ResultBean<String> resistResult = new CallApi().register(reg,accountName, password, request,userType);
		if(resistResult.getIsSuccess()){
			String pwd = MD5.encode(password);
			//0 id 1 accountName
			Cookie GYYX_RP_USER_INFO = new Cookie("GYYX_RP_USER_INFO", Aes.encrypt(resistResult.getData()+";"+accountName, SKEY, SIV));
			Cookie GYYX_RP_USER_PWD = new Cookie("GYYX_RP_USER_PWD", Aes.encrypt(pwd, SKEY, SIV));
			GYYX_RP_USER_INFO.setMaxAge(60*60*24*15);
			GYYX_RP_USER_PWD.setMaxAge(60*60*24*15);
			response.addCookie(GYYX_RP_USER_INFO);
			response.addCookie(GYYX_RP_USER_PWD);
			result.setProperties(true, "注册成功!", "");
		}else{
			result.setMessage(resistResult.getMessage());
			return result;
		}
		try{
			UserRegistLogBean ul = new UserRegistLogBean();
			ul.setAccountName(accountName);
			ul.setAccountType(userType);
			ul.setCreateDate(new Date());
			ul.setRegistSource("372");
			userRegistLogBLL.insertRegiste(ul);
		}catch(Exception e){
			LOG.error("问道新手卡活动注册用户"+accountName+"失败！",e);
		}
		
		return result;
	}
	
	public void setUserInfoFromNoviceCard(HttpServletRequest request,NoviceParameter parameter){
		UserInfo us = null;
		Cookie [] cookies = request.getCookies();
    	if(cookies!=null&&cookies.length>0){
    		for(Cookie co:cookies){
    			if(co.getName().equals("GYYX_RP_USER_INFO")){
					String aesResult = Aes.decrypt(co.getValue(), SKEY, SIV);
				    String [] paramM = aesResult.split(";"); 
				    us = new UserInfo();
				    us.setAccount(paramM[1]);
				    us.setUserId(Integer.parseInt(paramM[0]));
				    parameter.setAccount(paramM[1]);
    			}
    			if(co.getName().equals("GYYX_RP_USER_PWD")){
    				String aesResult = Aes.decrypt(co.getValue(), SKEY, SIV);
    				parameter.setPassWord(aesResult);
    			}
    		}
    	}
    	parameter.setUserInfo(us);
	}
}
