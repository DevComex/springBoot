/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：guohai
 * 联系方式：guohai@gyyx.cn
 * 创建时间： 2014年12月8日
 * 版本号：v1.0
 * 本类主要用途描述：
 * 手动激活游戏
-------------------------------------------------------------------------*/
package cn.gyyx.action.service.agent;

import java.rmi.RemoteException;

import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.sdk.module.action.ActionModuleSDKResult;
import cn.gyyx.sdk.module.action.ClientGame;

public class CallWebServiceAgent {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebServiceAgent.class);

	/**
	 * 问道手动激活
	 * 
	 * @param accountName
	 *            用户账号
	 * @param password
	 *            MD5后密码
	 * @param serverId
	 *            服务器编号
	 * @param userIp
	 *            用户IP
	 * @return 接口返回状态 </br> badRequest 错误的返回值（未知的接口返回值）</br> error
	 *         错误的请求（请求链接失败）</br> false 激活失败</br> true 激活成功</br> default
	 *         默认状态（执行激活出现问题）</br>
	 */
	public static ResultBean<String> wenDaoManualActivate(
			NoviceParameter noviceParameter, String ip) {
		logger.debug("start activate !");
		ResultBean<String> result = new ResultBean<String>();
		if (null == ip || "".equals(ip)) {
			ip = "127.0.0.3";
		}
		result = new CallWebApiActivateAccount(String.valueOf(noviceParameter
				.getGameId()), String.valueOf(noviceParameter.getServerId()),
				noviceParameter.getAccount(), noviceParameter.getPassWord(), ip)
				.executeActivate();
		logger.debug("finished activate !");
		return result;
	}

	/**
	 * @日期：2014年12月12日
	 * @Title: givePresents
	 * @Description: TODO 发送物品
	 * @param gameId
	 * @param account
	 * @param giftPackage
	 * @param expiredTime
	 * @param memo
	 * @param serverInfo
	 * @return ProcessResult
	 * @throws RemoteException
	 */
	public static ProcessResult givePresents(int gameId, String account,
			String giftPackage, String expiredTime, String memo,
			ServerInfoBean serverInfo) {
		if(memo == null || memo.length() == 0) {
			memo = "DefaultMemo";
		}
		ProcessResult processResult = null;
		ClientGame clientGame = new ClientGame();
		ActionModuleSDKResult response = clientGame.clientGameViaAccount(
				account, giftPackage, expiredTime, memo, gameId, serverInfo
						.getValue().getServerIp(), serverInfo.getValue()
						.getServerPort());
		if (response != null) {
			processResult = new ProcessResult();
			processResult.setErrorCode(response.isSuccess() ? 0
					: Integer.MIN_VALUE);
			processResult.setDescription(response.getMessage());
		}
		return processResult;
	}
	
	/**
	 * @日期：2014年12月12日
	 * @Title: givePresents
	 * @Description: 根据订单号orderid发送物品
	 * @param gameId
	 * @param account
	 * @param giftPackage
	 * @param expiredTime
	 * @param memo
	 * @param serverInfo
	 * @return ProcessResult
	 * @throws RemoteException
	 */
	public static ProcessResult givePresents(int gameId, String account,
			String giftPackage, String expiredTime, String memo,
			ServerInfoBean serverInfo, String orderId) {
		if(memo == null || memo.length() == 0) {
			memo = "DefaultMemo";
		}
		ProcessResult processResult = new ProcessResult();
		ClientGame clientGame = new ClientGame();
		
		ActionModuleSDKResult response = clientGame.clientGameViaAccount(orderId, "defaultSource",
				account, giftPackage, expiredTime, memo, gameId, serverInfo
				.getValue().getServerIp(), serverInfo.getValue()
				.getServerPort());
		if (response != null) {
			processResult.setErrorCode(response.isSuccess() ? 0
					: Integer.MIN_VALUE);
			processResult.setDescription(response.getMessage());
		}else{
			processResult.setErrorCode(Integer.MIN_VALUE);
			processResult.setDescription("");
		}
		return processResult;
	}

	/**
	 * @Title: getAccountRegTime
	 * 
	 * @Description: TODO 获取账户注册时间
	 * 
	 * @return ClientResponse
	 * 
	 */
	public static String getAccountRegTime(int userCode) {
		long timestamp = System.currentTimeMillis() / 1000;
		logger.debug("userCode", userCode);
		String sign_type = "MD5";
		String sign = MD5.encode("/user/" + userCode
				+ "?timestamp=" + timestamp + "123456");
		String url = "http://account.module.gyyx.cn/user/" + userCode
				+ "?timestamp=" + timestamp + "&sign_type=" + sign_type
				+ "&sign=" + sign;
		logger.debug("sign", sign);
		logger.debug("url", url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = (resource).get();
		// 接收返回响应体
		String result = response.getEntity(String.class);
		JSONObject obj = JSONObject.fromObject(result);
		return obj.getString("RegTime");
	}

}
