/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2016年1月19日下午15:07:12
 * 版本号：
 * 本类主要用途叙述：绝世武神预约分享的service
 */
package cn.gyyx.action.service.jswsReserve;

import java.util.Date;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswsReserve.ReserveBean;
import cn.gyyx.action.bll.jswsReserve.ReserveBLL;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class ReserveService {
	
	// 签名
	private static String key = "123456";
	private static String message = 
			"亲爱的用户，恭喜您成功预约全球首款R式卡牌《绝世武神OL》。"
			+ "游戏开测前我们将会以短信形式通知您，请您注意查收。"; 
	private ReserveBLL reserveBLL = new ReserveBLL();
	private static final Logger logger = GYYXLoggerFactory
				.getLogger(CallWebServiceInsuranceAgent.class);
	/**
	 * 
	 * @Title: insertReserveInfo
	 * @Description: TODO 添加预约信息
	 * @param @param openId
 	 * @param @param phoneNum    
	 * @return void    
	 * @throws
	 */
	public void insertReserveInfo(String phoneNum){
		ReserveBean reserveBean = new ReserveBean();
		reserveBean.setPhone_num(phoneNum);
		reserveBean.setReserveTime(new Date());
		reserveBLL.insertReserveInfo(reserveBean);
	}
	
	/**
	 * @Title: sendUserPhoneverifyCode
	 * @Description: TODO 获取手机号码，发送预约成功短信
	 * @param @param phone
	 * @param @param userAccount
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	public String sendUserPhoneReserveSuccessMessage(String phoneNum) {
		logger.debug("sendUserPhoneReserveSuccessMessage input ", phoneNum);
		String result = "";
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String preSign = "/SMS/SendCustom?content=" + message
				+ "&isCustomSend=True"
				+ "&phone=" + phoneNum
				+ "&sourceType=Extend"
				+ "&timestamp=" + timestamp + key;
		String sign = MD5.encode(preSign);
		String url = "http://sms.module.gyyx.cn/SMS/SendCustom?content="
				+ message
				+ "&isCustomSend=True"
				+ "&phone=" + phoneNum
				+ "&sourceType=Extend"
				+ "&timestamp=" + timestamp
				+ "&sign=" + sign
				+ "&sign_type=" + sign_type;
		RestClient client = new RestClient();
		try {
			org.apache.wink.client.Resource resource = client.resource(url)
					.accept("application/json");
			ClientResponse response = (resource).post(url);
			// 接收返回响应体
			result = response.getEntity(String.class);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(" sendUserPhoneReserveSuccessMessage    web service error"
					+ e.getMessage());
		}
		logger.debug("sendUserPhoneReserveSuccessMessage output " + result);
		return result;
	}
}
