package cn.gyyx.action.beans.wechat;

import java.util.Date;

public class WechatPresentLogBean {
	// 主键
	private int code;
	// 活动号
	private int activityId;
	// 活动名
	private String source;
	// 活动号
	private int sourceCode;
	// 账号
	private String account;
	// 游戏
	private int gameId;
	// 服务器
	private int serverId;
	// 服务器
	private String serverName;
	// 奖品类型
	private String presentType;
	// 奖品名
	private String presentName;
	// 时间
	private Date drawTime;
	// IP
	private String drawIp;
	//
	private int available;
	//
	private String remark;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(int sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPresentType() {
		return presentType;
	}

	public void setPresentType(String presentType) {
		this.presentType = presentType;
	}

	public String getPresentName() {
		return presentName;
	}

	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public String getDrawIp() {
		return drawIp;
	}

	public void setDrawIp(String drawIp) {
		this.drawIp = drawIp;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WechatPresentLogBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WechatPresentLogBean(int activityId, String source, int sourceCode,
			String account, int gameId, int serverId, String serverName,
			String presentType, String presentName, Date drawTime,
			String drawIp, int available, String remark) {
		super();
		this.activityId = activityId;
		this.source = source;
		this.sourceCode = sourceCode;
		this.account = account;
		this.gameId = gameId;
		this.serverId = serverId;
		this.serverName = serverName;
		this.presentType = presentType;
		this.presentName = presentName;
		this.drawTime = drawTime;
		this.drawIp = drawIp;
		this.available = available;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "WechatPresentLogBean [activityId=" + activityId + ", source="
				+ source + ", sourceCode=" + sourceCode + ", account="
				+ account + ", gameId=" + gameId + ", serverId=" + serverId
				+ ", serverName=" + serverName + ", presentType=" + presentType
				+ ", presentName=" + presentName + ", drawTime=" + drawTime
				+ ", drawIp=" + drawIp + ", available=" + available
				+ ", remark=" + remark + "]";
	}

}
