/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:37:06
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡的bean
 */

package cn.gyyx.action.beans.wechat;

public class WeiXinRechargeBean {
	// 主键
	private int code;
	// 卡号
	private String cardCode;
	// 微信用户标示
	private String openId;
	// 类型
	private String type;
	// 活动主键
	private int actionCode;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public WeiXinRechargeBean(int code, String cardCode, String openId,
			String type, int actionCode) {
		super();
		this.code = code;
		this.cardCode = cardCode;
		this.openId = openId;
		this.type = type;
		this.actionCode = actionCode;
	}

	public WeiXinRechargeBean() {
	}

}
