package cn.gyyx.action.beans.wdpkforecast;

import java.sql.Date;

public class WdPkVoteTeamsBean {
	private int code;
	private  int actionCode;
	private int netCode;
	private int type;
	private String teams;
	private String teamLeader;
	private int voteTimes;	
	private Date createAt;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	
	public int getNetCode() {
		return netCode;
	}
	public void setNetCode(int netCode) {
		this.netCode = netCode;
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
