/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年7月14日下午4:49:56
 * 版本号：v1.0
 * 本类主要用途叙述：关于问道保险计划的webService
 */

package cn.gyyx.action.service.agent;

import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.LastLoginBean;
import cn.gyyx.action.bll.insurance.GyyxGoPayBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

import com.ibm.icu.text.SimpleDateFormat;

public class CallWebServiceInsuranceAgent {
	// 签名
	private static int sign = 123456;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebServiceInsuranceAgent.class);

	/**
	 * 获取乾坤锁开始时间
	 * 
	 * @param userCode
	 * @return
	 */
	public static Date getEkeyInformationBean(int userCode) {
		logger.debug("userCode", userCode);
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String preSign = "/" + userCode + "?timestamp=" + timestamp + "" + sign;
		String sign = MD5.encode(preSign);
		// 请求地址
		String url = "http://ekey.module.gyyx.cn/" + userCode + "?timestamp="
				+ timestamp + "&sign_type=" + sign_type + "&sign=" + sign;
		RestClient client = new RestClient();
		String date = "";
		Date datetime = new Date();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			result = result.substring(1, result.length() - 1);
			JSONObject jsonobject = JSONObject.fromObject(result);
			// 获取乾坤锁开始时间
			date = jsonobject.getString("BindTime");
			String date1 = date.substring(0, 9);
			String date2 = date.substring(11, 19);
			date = date1 + " " + date2;
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getEkeyInformationBean web service error"
					+ e.getMessage());
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// TODO: handle exception
		try {
			datetime = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warn(" getEkeyInformationBean date  web service error"
					+ e.getMessage());
		}
		return datetime;

	}

	/**
	 * 获取账户的认证手机号
	 * 
	 * @param userCode
	 * @return
	 */
	public static String getUserPhone(int userCode) {
		logger.debug("userCode", userCode);
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_type = "MD5";
		 
		String preSign = "/authphone?timestamp=" + timestamp
				+ "&userId="+ userCode
				+ "123456" ;
		String sign = MD5.encode(preSign);
		RestClient client = new RestClient();
	 
		String url = "http://authphone.module.gyyx.cn/authphone?timestamp=" + timestamp
				+ "&userId="+ userCode
				+ "&sign=" + sign
				+ "&sign_type="+ sign_type;
		org.apache.wink.client.Resource resource = client.resource(url).accept(
				"application/json");
		String endString = "";
		try {
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			// 转换成标准字符串
			//result = result.substring(1, result.length() - 1);
			JSONObject jsonobject = JSONObject.fromObject(result);
			JSONObject object = jsonobject.getJSONObject("Data");
			endString = object.getString("RealPhoneNumber");

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserPhone web service error" + e.getMessage());
		}

		logger.debug("getUserPhone result" + endString);
		return endString;
	}

	/**
	 * 获取账户手机认证的时间
	 * 
	 * @param userCode
	 * @return
	 */
	public static Date getUserPhoneValidateTime(int userCode) {
		logger.debug("userCode", userCode);
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_type = "MD5";
		 
		String preSign = "/authphone?timestamp=" + timestamp
				+ "&userId="+ userCode
				+ "123456" ;
		String sign = MD5.encode(preSign);
		RestClient client = new RestClient();
	 
		String url = "http://authphone.module.gyyx.cn/authphone?timestamp=" + timestamp
				+ "&userId="+ userCode
				+ "&sign=" + sign
				+ "&sign_type="+ sign_type;
		org.apache.wink.client.Resource resource = client.resource(url).accept(
				"application/json");
		String date = "";
		Date dateEndDate = new Date();
		try {
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			// 转换成标准字符串
			JSONObject jsonobject = JSONObject.fromObject(result);
			JSONObject object = jsonobject.getJSONObject("Data");
			date = object.getString("AuthTime");
			String date1 = date.substring(0, 10);
			String date2 = date.substring(11, 19);
			date = date1 + " " + date2;

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserPhoneValidateTime web service error"
					+ e.getMessage());
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dateEndDate = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warn(" getUserPhoneValidateTime date  web service error",
					e.getMessage());
		}
		logger.debug("getUserPhoneValidateTime result" + dateEndDate);
		return dateEndDate;
	}

	/***
	 * 获取手机发送验证码
	 * 
	 * @param phone
	 * @return
	 */
	public static String sendUserPhoneverifyCode(String phone,
			String userAccount) {
		logger.debug("sendUserPhoneverifyCode input ", phone);
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String messageString = userAccount;
		String preSign = "/v1/Send/VerifyCode/Phone/?phone=" + phone
				+ "&sourceType=PayVirtualSecure&messageParam=" + messageString
				+ "&timestamp=" + timestamp + "123456";
		String sign = MD5.encode(preSign);
		String url = "http://interface.message.gyyx.cn/v1/Send/VerifyCode/Phone?phone="
				+ phone
				+ "&sourceType=PayVirtualSecure&messageParam="
				+ messageString
				+ "&timestamp="
				+ timestamp
				+ "&sign_type="
				+ sign_type + "&sign=" + sign;
		RestClient client = new RestClient();
		String result = "";
		try {
			org.apache.wink.client.Resource resource = client.resource(url)
					.accept("application/json");
			ClientResponse response = (resource).post(url);
			// 接收返回响应体
			result = response.getEntity(String.class);

		} catch (Exception e) {
			logger.warn(" sendUserPhoneverifyCode   web service error"
					+ e.getMessage());
		}
		logger.debug("sendUserPhoneverifyCode output " + result);
		return result;
	}

	/**
	 * 验证手机验证码
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	public static String getUserPhoneverifyCode(String phone, String verifyCode) {
		logger.debug("getUserPhoneverifyCode input ", "phone" + phone
				+ "verifyCode" + verifyCode);
		String sign_type = "MD5";
		String status = "";
		long timestamp = System.currentTimeMillis() / 1000;
		String preSign = "/v1/Valid/VerifyCode/Phone/?phone=" + phone
				+ "&sourceType=PayVirtualSecure&timestamp=" + timestamp
				+ "&verifyCode=" + verifyCode + "123456";
		String sign = MD5.encode(preSign);
		String url = "http://interface.message.gyyx.cn/v1/Valid/VerifyCode/Phone?phone="
				+ phone
				+ "&sourceType=PayVirtualSecure&timestamp="
				+ timestamp
				+ "&verifyCode="
				+ verifyCode
				+ "&sign_type="
				+ sign_type
				+ "&sign=" + sign;
		RestClient client = new RestClient();
		String result = "";
		try {
			org.apache.wink.client.Resource resource = client.resource(url)
					.accept("application/json");
			ClientResponse response = (resource).post(url);
			// 接收返回响应体
			result = response.getEntity(String.class);
			JSONObject jsonobject = JSONObject.fromObject(result);
			// 获取乾坤锁开始时间
			status = jsonobject.getString("Status");

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(" getUserPhoneverifyCode   web service error"
					+ e.getMessage());
		}
		logger.debug("getUserPhoneverifyCode output " + result);
		return status;
	}

	/**
	 * 获取乾坤锁验证码
	 * 
	 * @param userName
	 * @param userId
	 * @param Passpod
	 * @param gameId
	 * @param PasspodType
	 * @return
	 */
	public static String getUserEkeyverifyCode(String passpod, String userName,
			int userId, int gameId, String PasspodType) {
		logger.debug("getUserEkeyverifyCode Passpod" + passpod);
		logger.debug("PasspodType"+PasspodType);
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String preSign = "/api/EKeyVerifi?Passpod=" + passpod + "&PasspodType="
				+ PasspodType + "&UserId=" + userId + "&UserName=" + userName
				+ "&gameId=2&timestamp=" + timestamp + "123456";
		String sign = MD5.encode(preSign);
		String url = "http://interface.security.gyyx.cn/api/EKeyVerifi?UserName="
				+ userName + "&UserId=" + userId + "&Passpod=" + passpod
				+ "&gameId=" + gameId + "&PasspodType=" + PasspodType+ "&timestamp="
						+ timestamp+"&sign_type="+ sign_type+ "&sign=" + sign;
	   
		logger.debug("getUserEkeyverifyCode url"+url);
		RestClient client = new RestClient();
		String result = "";
		try {
			org.apache.wink.client.Resource resource = client.resource(url)
					.accept("application/json");
			ClientResponse response = (resource).post(url);
			// 接收返回响应体
			result = response.getEntity(String.class);

		} catch (Exception e) {
			// TODO: handle excepti);
			logger.warn("getUserEkeyverifyCode web server error"
					+ e.getMessage());
		}
		logger.debug("getUserEkeyverifyCode output " + result);
		return result;
	}

	/**
	 * 获取账户角色信息
	 * 
	 * @param userId
	 * @param serverId
	 * @return
	 */

	public static String getRoleInfo(String account, int serverId) {
		logger.debug("getUserEkeyverifyCode input", "account" + account
				+ "serverId" + serverId);
		long timestamp = System.currentTimeMillis() / 1000;
		String rolekey = "";
		GyyxGoPayBLL gpayBLL = new GyyxGoPayBLL();
		rolekey = gpayBLL.getInsureKey("role_key");
		if (rolekey == "") {
			logger.error("查询任务信息秘钥出错");
		}
		String preSign = "/Role/GetRoleList" + "?account=" + account
				+ "&serverId=" + serverId + "&timestamp=" + timestamp + rolekey;
		String sign = MD5.encode(preSign);
		String url = "http://interface.qibao.gyyx.cn/Role/GetRoleList"
				+ "?account=" + account + "&serverId=" + serverId
				+ "&timestamp=" + timestamp + "&sign_type=MD5&sign=" + sign;
		RestClient client = new RestClient();
		String result = "";
		try {
			logger.info("CallWebServiceInsuranceAgent->getRoleInfo->url=" + url);
			org.apache.wink.client.Resource resource = client.resource(url)
					.accept("application/json");
			ClientResponse response = (resource).get();
			// 接收返回响应体
			result = response.getEntity(String.class);

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserEkeyverifyCode web server error"
					+ e.getMessage());
		}
		logger.debug("getUserEkeyverifyCode output " + result);
		return result;
	}

	/**
	 * 得到上次登录的信息
	 * 
	 * @param userCode
	 * @return
	 */
	public static LastLoginBean getLastLoginInfo(int userCode) {
		LastLoginBean lastLoginBean = new LastLoginBean();
		logger.debug("getLastLoginInfo input", userCode);
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_type = "MD5";
		String preSign = "/user/" + userCode + "/log/login/last?timestamp="
				+ timestamp + "123456";
		String sign = MD5.encode(preSign);
		RestClient client = new RestClient();
		String url = "http://account.module.gyyx.cn/user/" + userCode
				+ "/log/login/last" + "?timestamp=" + timestamp + "&sign="
				+ sign + "&sign_type=" + sign_type;
		org.apache.wink.client.Resource resource = client.resource(url).accept(
				"application/json");
		String result = "";
		String endString = "";
		try {
			ClientResponse response = (resource).get();
			// 接收返回响应体
			result = response.getEntity(String.class);
			// 转换成标准字符串
			JSONObject jsonobject = JSONObject.fromObject(result);
			String success = jsonobject.getString("Success");
			// 获得ip和时间
			if (success.equals("true")) {
				String date = jsonobject.getString("OperateTime");
				String date1 = date.substring(0, 10);
				String date2 = date.substring(11, 19);
				date = date1 + " " + date2;
				String ip = jsonobject.getString("LoginIp");
				lastLoginBean.setIp(ip);
				lastLoginBean.setTime(date);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getLastLoginInfo web service error" + e.toString());
		}

		logger.debug("getLastLoginInfo result" + endString);
		return lastLoginBean;
	}
/**
 * 获取手机认证状态
        /// 未绑定Unbind,0	
        /// 已绑定Bind,1	
        /// 已绑定,认证中Authing,2
        /// 已认证AuthFinish3
 * @param userCode
 * @return
 */
	public static String getUserPhoneStatus(int userCode) {
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_type = "MD5";
		 
		String preSign = "/authphone?timestamp=" + timestamp
				+ "&userId="+ userCode
				+ "123456" ;
		String sign = MD5.encode(preSign);
		RestClient client = new RestClient();
	 
		String url = "http://authphone.module.gyyx.cn/authphone?timestamp=" + timestamp
				+ "&userId="+ userCode
				+ "&sign=" + sign
				+ "&sign_type="+ sign_type;
		org.apache.wink.client.Resource resource = client.resource(url).accept(
				"application/json");
		String status = "";
		try {
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			// 转换成标准字符串
			//result = result.substring(1, result.length() - 1);
			JSONObject jsonobject = JSONObject.fromObject(result);
			JSONObject object = jsonobject.getJSONObject("Data");
			status = object.getString("Status");

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserPhoneStatus web service error" ,e);
		}

		logger.debug("getUserPhoneStatus userCode={} and status={}", userCode , status);
		return status;
	}
}
