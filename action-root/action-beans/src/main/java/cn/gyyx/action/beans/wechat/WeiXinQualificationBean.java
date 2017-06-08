/*************************************************
       Copyright ©, 2015, GY Game
       Author: 王雷
       Created: 2015年-12月-21日
       Note: 微信端用户抽奖资格实体类
************************************************/
package cn.gyyx.action.beans.wechat;

public class WeiXinQualificationBean {
	//主键
	private int code;
	//微信用户标示
	private String openId;
	//抽奖次数
	private int lotteryTime;
	//活动编号
	private int actionCode;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(int lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public WeiXinQualificationBean(String openId, int lotteryTime, int actionCode) {
		super();
		this.openId = openId;
		this.lotteryTime = lotteryTime;
		this.actionCode = actionCode;
	}

	public WeiXinQualificationBean() {
		super();
	}
}
