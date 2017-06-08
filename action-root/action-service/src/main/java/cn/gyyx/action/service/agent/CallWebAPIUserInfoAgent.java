/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月16日 下午3:38:03
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 用户账户信息网络组件
-------------------------------------------------------------------------*/
package cn.gyyx.action.service.agent;

import org.apache.commons.lang.StringUtils;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.json.JSONObject;
import org.slf4j.Logger;

import cn.gyyx.action.service.common.SignTools;
import cn.gyyx.core.prop.SimpleProperties;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;


/**
 * 用户账户信息网络组件
 * 
 * @author Chen
 *
 */
public class CallWebAPIUserInfoAgent {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebAPIUserInfoAgent.class);

	/**
	 * 获取用户基本信息(根据UserId)
	 * 
	 * @param userCode
	 * @return
	 */
	public static JSONObject getUserBasicInfoByUserId(Integer userCode) {
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String salt = SimpleProperties.getInstance().getStringProperty("account.salt");
		String sign = getSign(userCode, timestamp, salt);
		String url = "http://account.module.gyyx.cn/user/" + userCode
				+ "?timestamp=" + timestamp + "&sign_type=" + sign_type
				+ "&sign=" + sign;
		RestClient client = new RestClient();
		logger.debug(url);
		org.apache.wink.client.Resource resource = client.resource(url).accept(
				"application/json");
		ClientResponse response = (resource).get();
		// 接收返回响应体
		String result = response.getEntity(String.class);
		org.json.JSONObject obj = new org.json.JSONObject(result);
		logger.debug(obj.toString());
		return obj;
	}

	/**
	 * 获取签名
	 * 
	 * @param userCode
	 * @param timestamp
	 * @param salt
	 * @return
	 */
	private static String getSign(Integer userCode, long timestamp, String salt) {
		String string = "/user/" + userCode + "?timestamp=" + timestamp + salt;
		logger.info(string);
		return MD5.encode(string);
	}
	/**
	 * 获取用户基本信息(根据account)
	 * 
	 * @param userCode
	 * @return
	 */
	public  Integer getUserBasicInfoByAccount(String account) {
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		//String salt = SimpleProperties.getInstance().getStringProperty("account.salt");
		String salt = "123456";
		String sign = getSignM(account, timestamp, salt);
		String url = "http://account.module.gyyx.cn/user?account=" + account
				+ "&timestamp=" + timestamp + "&sign_type=" + sign_type
				+ "&sign=" + sign;
		RestClient client = new RestClient();
		logger.info("问道康师傅暑期推广活动V1.211___调用获取用户基本信息接口的url："+url);
		org.apache.wink.client.Resource resource = client.resource(url).accept(
				"application/json");
		ClientResponse response = (resource).get();
		// 接收返回响应体
		String result = response.getEntity(String.class);
		logger.info("问道康师傅暑期推广活动V1.211___调取account.module.gyyx.cn域名的接口，result为："+result);
		result = result.substring(1, result.length()-1);
		logger.info("问道康师傅暑期推广活动V1.211___调取account.module.gyyx.cn域名的接口，result为："+result);
		//System.out.println("--___________________"+result);
		net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(result);
		String userId = jsonobject.getString("UserId");
		logger.info("问道康师傅暑期推广活动V1.211___调用获取用户基本信息接口的结果userId："+userId);
		return Integer.valueOf(userId);
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
	 * 验证用户名和密码
	 */
	public static String validUserAccountAndpwd(String account,String md5Pwd) {
		RestClient client = new RestClient();
		String returnMessage = "验证用户名和密码失败";
		try{
			String url = "http://account.module.gyyx.cn/user/"+account+"/pwdchecker/?md5pwd=" + md5Pwd;
			logger.info("调用社区接口验证用户名密码：原始请求,{}",url);
			url = SignTools.getSignUrl(url);
			logger.info("调用社区接口验证用户名密码：签名请求,{}",url);
			
			Resource resource = client.resource(url).accept("application/json");
			ClientResponse response = (resource).post(url);
			String result = response.getEntity(String.class);
			net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(result);
			String message = jsonobject.getString("Message");
			logger.info("调用社区接口验证用户名密码：参数[account:{},md5Pwd:{}]接口返回：{}",new Object[]{account,md5Pwd,result});
			if(StringUtils.isNotBlank(message)){
				if(message.contains("Success")){
					returnMessage = "valid";
					return returnMessage;
				}
				if(message.contains("用户不存在")){
					returnMessage = "账号不存在,请重新输入";
					return returnMessage;
				}
				returnMessage = message;
			}else{
				throw new RuntimeException("验证用户名和密码失败,返回Message结果为null");
			}
			return returnMessage;
		}catch(Exception e){
			logger.error("调用社区接口验证用户名密码：获取接口代码失败,错误堆栈：{}",Throwables.getStackTraceAsString(e));
			return returnMessage;
		}
	}
	
	public static void main(String[] args) {
		CallWebAPIUserInfoAgent.validUserAccountAndpwd("javacheng711111", "523c43860bbadbb61ed669816c0f95de1s");
	}
}
