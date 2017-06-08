/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:37:06
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡的bean
 */



package cn.gyyx.action.beans.lottery;

public class RechargeBean {
	//主键
	private int code;
	//卡号
	private String cardCode;
     //用户主键
	private int userCode;
	//类型
	private String type;
	//活动主键
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
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
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
	public RechargeBean(int code, String cardCode, int userCode, String type,
			int actionCode) {
		this.code = code;
		this.cardCode = cardCode;
		this.userCode = userCode;
		this.type = type;
		this.actionCode = actionCode;
	}
	public RechargeBean() {
	}
	
	

}
