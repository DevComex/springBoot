/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年8月11日 
 * @版本号：v1.211
 * @本类主要用途描述：问道康师傅抽奖活动项目调用api.activityresult.gyyx.cn接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.agent;

import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdmaster.AgentResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class CallWebApiActivityResultAgent {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebApiAgent.class);
	private static String key = "sicent@gywd";
	/**
	 * 获取用户是否到达15级
	 * @param userId
	 * @return
	 */
	public AgentResultBean getIsFifteenFromWebApi(Integer userId) {

		long timestamp = System.currentTimeMillis() / 1000;
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的接口开始，参数userId：", userId);
		String sign_type = "MD5";
		String sign = getMD5ByAccountAndGameAndTimestamp(userId,timestamp);
		String url = "http://api.activityresult.gyyx.cn/activity/ActivityStateByUserId?userId=" + userId+
		"&timestamp="+timestamp+ "&sign_type=" + sign_type + "&sign=" + sign;
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的接口，URL为："+ url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = (resource).get();
		
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的接口，response为："+ response);
		// 接收返回响应体
		String result = response.getEntity(String.class);
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的接口，result为："+result);
		AgentResultBean activityResultAgentResultBean = new AgentResultBean();
		JSONObject jsonobject = JSONObject.fromObject(result);
		activityResultAgentResultBean.setMsg(jsonobject.getString("msg"));
		activityResultAgentResultBean.setSuccess(new Boolean(jsonobject.getString("success")));
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的接口，activityResultAgentResultBean为："+ activityResultAgentResultBean);
		return activityResultAgentResultBean;
	}


	/**
	 * 根据参数计算sign的值
	 * @param gameaccount
	 * @param timestamp
	 * @return sign
	 */
	private String getMD5ByAccountAndGameAndTimestamp(Integer userId,
			long timestamp) {
		logger.info("问道康师傅暑期推广活动V1.211___计算sign的值。参数gameaccount:"+userId+"  &timestamp:"+timestamp);
		String integtationURL = "/activity/ActivityStateByUserId?timestamp="+timestamp+
		"&userId=" + userId + key;
		String result = MD5.encode(integtationURL);
		logger.info("问道康师傅暑期推广活动V1.211___计算sign的值。计算结果为："+result);
		return result;
	}
	/**
	 * 增加用户
	 * @param activityCode
	 * @param adSpaceCode
	 * @param gameId
	 * @param userId
	 * @return
	 */
	public AgentResultBean addUserFromWebApi(int activityCode,int adSpaceCode,int gameId,Integer userId) {

		long timestamp = System.currentTimeMillis() / 1000;
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的增加用户接口开始，参数userId："+ userId+"   adSpaceCode"+adSpaceCode+"   gameId"+gameId+"  userId"+userId);
		String sign_type = "MD5";
		String sign = getMD5ByAccountAndGameAndTimestamp(activityCode,adSpaceCode,gameId,userId,timestamp);
		String url = "http://api.activityresult.gyyx.cn/activity/ActivityUser?activityCode=" + activityCode+
				"&adSpaceCode="+adSpaceCode +"&gameId="+gameId +"&userId="+userId +
				"&timestamp="+timestamp+ "&sign_type=" + sign_type + "&sign=" + sign;
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的增加用户接口，URL为："+ url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = (resource).post(url);
		
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的增加用户接口，response为："+response);
		// 接收返回响应体
		String result = response.getEntity(String.class);
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的接口，result为："+result);
		AgentResultBean activityResultAgentResultBean = new AgentResultBean();
		JSONObject jsonobject = JSONObject.fromObject(result);
		activityResultAgentResultBean.setMsg(jsonobject.getString("msg"));
		activityResultAgentResultBean.setSuccess(new Boolean(jsonobject.getString("success")));
		logger.info("问道康师傅暑期推广活动V1.211___调取api.activityresult.gyyx.cn域名的增加用户接口，activityResultAgentResultBean为："+activityResultAgentResultBean);
		return activityResultAgentResultBean;
	}


	/**
	 * 根据参数计算sign的值
	 * @param gameaccount
	 * @param timestamp
	 * @return sign
	 */
	private String getMD5ByAccountAndGameAndTimestamp(int activityCode,int adSpaceCode,int gameId,Integer userId,
			long timestamp) {
		logger.info("问道康师傅暑期推广活动V1.211___计算sign的值。参数gameaccount:"+userId+"  &timestamp:"+timestamp);
		String integtationURL = "/activity/ActivityUser?activityCode=" + activityCode+
				"&adSpaceCode="+adSpaceCode +"&gameId="+gameId + "&timestamp="+timestamp +"&userId="+userId +
				 key;
		String result = MD5.encode(integtationURL);
		logger.info("问道康师傅暑期推广活动V1.211___计算sign的值。计算结果为："+result);
		return result;
	}
}
