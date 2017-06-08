package cn.gyyx.action.service.mobile.website;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.mobile.website.InterfaceReturnBean;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class CallApi {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(CallApi.class);
	/**
	 * 注册接口(参数在url里)
	 * pwd 明文密码
	 * post
	 */
	public ResultBean<String> register(String [] reg,String phone,String pwd,HttpServletRequest request,String userType){
		LOG.debug("start register interface！");
		ResultBean<String> resultBean = new ResultBean<>();
		CallWebAgentMobile callWeb = new CallWebAgentMobile();
		String domain = "http://account.module.gyyx.cn";
		String projectName = "/user/adregister/";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("accounttype", userType);
		map.put("username", phone);
		map.put("md5pwd", MD5.encode(pwd));
		map.put("regtype", "1");
		map.put("regip", Ip.getCurrent(request).asString());
		map.put("regentry", "phone");
		map.put("gametype", "2");
		map.put("isfromad", "false");
		map.put("adreguserinfotemp", reg[0]);
		map.put("adreguserinfoforever", reg[1]);
		map.put("pagevisitguid", reg[2]);
		map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		ResultBean<InterfaceReturnBean> result = callWeb.post(callWeb.getSign(domain, projectName,  "123456", map));
		JSONObject json = JSONObject.fromObject(result.getData().getMessage());
		try{
			if(result.getIsSuccess()){
				resultBean.setProperties(true, "注册成功!", json.getString("UserId"));
			}else{
				resultBean.setProperties(false,json.getString("Message"), "");
			}
		}catch(Exception e){
			resultBean.setProperties(false,"网络异常请稍后重试!", "");
		}
		LOG.debug("register message:"+result.getData().getMessage());
		LOG.debug("finished register interface！"+resultBean.getIsSuccess()+"--"+resultBean.getMessage()+"--"+resultBean.getData());
		return resultBean;
	}
	/**
	 * TODO
	 * 发送验证码
	 * post
	 */
	public ResultBean<String> getCAPTCHA(String phone){
		LOG.debug("start CAPTCHA :");
		ResultBean<String> resultBean = new ResultBean<>();
		CallWebAgentMobile callWeb = new CallWebAgentMobile();
		String domain = "http://interface.message.gyyx.cn";
		String projectName = "/v1/Send/VerifyCode/Phone/";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("phone", phone);map.put("sourceType", "MobilePhoneStageReg");map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		ResultBean<InterfaceReturnBean> result = callWeb.post(callWeb.getSign(domain, projectName, "123456",map),map);
		JSONObject json = JSONObject.fromObject(result.getData().getMessage());
		if(result.getIsSuccess()){
			resultBean.setProperties(true, json.getString("Message"), json.getString("Status"));
		}else{
			resultBean.setProperties(false, json.getString("Message"), json.getString("Status"));
		}
		LOG.debug("CAPTCHA message:"+result.getData().getMessage());
		LOG.debug("finished CAPTCHA ! "+resultBean.getIsSuccess()+"--"+resultBean.getMessage()+"--"+resultBean.getData());
		return resultBean;
	}
	/**
	 * TODO
	 * 检验验证码  
	 * post
	 */
	public ResultBean<String> examineCAPTCHA(String phone,String verifyCode){
		LOG.debug("start examineCAPTCHA");
		ResultBean<String> resultBean = new ResultBean<>();
		CallWebAgentMobile callWeb = new CallWebAgentMobile();
		String domain = "http://interface.message.gyyx.cn";
		String projectName = "/v1/Valid/VerifyCode/Phone";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("phone", phone);map.put("verifyCode", verifyCode);map.put("sourceType", "MobilePhoneStageReg");map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		ResultBean<InterfaceReturnBean> result = callWeb.post(callWeb.getSign(domain, projectName, "123456",map),map);
		JSONObject json = JSONObject.fromObject(result.getData().getMessage());
		if(result.getIsSuccess()){
			resultBean.setProperties(true, json.getString("Message"), json.getString("Status"));
		}else{
			resultBean.setProperties(false, json.getString("Message"), json.getString("Status"));
			LOG.debug("examineCAPTCHA connect error！");
		}
		LOG.debug("examineCAPTCHA message:"+result.getData().getMessage());
		LOG.debug("finished examineCAPTCHA"+resultBean.getIsSuccess()+"--"+resultBean.getMessage()+"--"+resultBean.getData());
		return resultBean;
	}
	
}
