
package cn.gyyx.action.service.wdsigned;


import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.ResultBean;
//import cn.gyyx.message.MessageClient;

public interface IWdAppSignLogService {
	
	/**
	 * 签到
	 * @param gameAccount
	 * @param signedLogBean
	 * @return
	 */
	ResultBean<String> sign(String qrCodeContent, String account);
	
	
	/**
	 * 获取签到列表
	 * @return
	 */
	Map<String, Object> signList(String account,int serverId);
	
	/**
	 * 获取签到二维码
	 * @return
	 */
	String getQrCode();

	/**
	 * 验证二维码
	 * @return
	 */
	boolean validQrCode(String qrId, String type, String userId);
	
	/**
	 * 发送签到礼品
	 * @param serverId
	 * @param account
	 * @return
	 */
	List<String>  sendGift(int serverId,String account);
	
	
	int getRegion(String serverName);
	
	//void gameLoginSign(MessageClient client);
}
