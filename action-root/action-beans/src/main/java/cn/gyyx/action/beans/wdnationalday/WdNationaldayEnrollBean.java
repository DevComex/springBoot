package cn.gyyx.action.beans.wdnationalday;

import java.util.Date;

public class WdNationaldayEnrollBean {
	private int code;
	private int userId;
	private String account;
	private int serverId;
	private String serverName;
	private String roleId;
	private String roleName;
	private int totalScore;
	private Date createTime;
	private String ip;
	private String openId;
	private String nickName;
	
	private int signCount;
	private int diaryCount;
	private int picCount;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getSignCount() {
		return signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}

	public int getDiaryCount() {
		return diaryCount;
	}

	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}

	public int getPicCount() {
		return picCount;
	}

	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
