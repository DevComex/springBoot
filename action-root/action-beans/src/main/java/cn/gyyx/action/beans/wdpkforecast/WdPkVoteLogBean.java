package cn.gyyx.action.beans.wdpkforecast;

import java.util.Date;

public class WdPkVoteLogBean {

	private int code;
	private int actionCode;
	private String account ;
	private int userId;
	private int type;
	private String teams;
	private String teamLeader;
	private int voteTimes;
	private int win;
	private Date createAt;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTeams() {
		return teams;
	}
	public void setTeams(String teams) {
		this.teams = teams;
	}
	public String getTeamLeader() {
		return teamLeader;
	}
	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}
	public int getVoteTimes() {
		return voteTimes;
	}
	public void setVoteTimes(int voteTimes) {
		this.voteTimes = voteTimes;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
}
