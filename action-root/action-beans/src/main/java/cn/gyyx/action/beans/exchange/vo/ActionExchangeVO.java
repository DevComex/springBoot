package cn.gyyx.action.beans.exchange.vo;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;

public class ActionExchangeVO extends ActionExchangePO {

	private String userId;
	
	private Integer userPoint;
	
	private String ip;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserPoint() {
		return userPoint;
	}

	public void setUserPoint(Integer userPoint) {
		this.userPoint = userPoint;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
