package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.List;

public class ResultSignBasicInfo {
	//周签到天数
	Integer weekSignDays;
	//总签到天数
	Integer signDays;
	//总积分
	Integer credits;
	//性别
	String sex;
	//今天是否签到
	Boolean isSign;
	//服务器名
	String serverName;
	//服务器ID
	Integer serverId;
	
	//签到两天奖励
	Boolean twoDaysReward;
	//签到五天奖励
	Boolean fiveDaysReward;
	//签到十天奖励
	Boolean tenDaysReward;
	//签到十五天奖励
	Boolean fifteenDaysReward;
	//签到满勤奖励
	Boolean allDaysReward;
	
	List<RewardReceiveLogBean> receiveList;
	
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public List<RewardReceiveLogBean> getReceiveList() {
		return receiveList;
	}
	public void setReceiveList(List<RewardReceiveLogBean> receiveList) {
		this.receiveList = receiveList;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Boolean getIsSign() {
		return isSign;
	}
	public void setIsSign(Boolean isSign) {
		this.isSign = isSign;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Integer getWeekSignDays() {
		return weekSignDays;
	}
	public void setWeekSignDays(Integer weekSignDays) {
		this.weekSignDays = weekSignDays;
	}
	public Integer getSignDays() {
		return signDays;
	}
	public void setSignDays(Integer signDays) {
		this.signDays = signDays;
	}
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	public Boolean getTwoDaysReward() {
		return twoDaysReward;
	}
	public void setTwoDaysReward(Boolean twoDaysReward) {
		this.twoDaysReward = twoDaysReward;
	}
	public Boolean getFiveDaysReward() {
		return fiveDaysReward;
	}
	public void setFiveDaysReward(Boolean fiveDaysReward) {
		this.fiveDaysReward = fiveDaysReward;
	}
	public Boolean getTenDaysReward() {
		return tenDaysReward;
	}
	public void setTenDaysReward(Boolean tenDaysReward) {
		this.tenDaysReward = tenDaysReward;
	}
	public Boolean getFifteenDaysReward() {
		return fifteenDaysReward;
	}
	public void setFifteenDaysReward(Boolean fifteenDaysReward) {
		this.fifteenDaysReward = fifteenDaysReward;
	}
	public Boolean getAllDaysReward() {
		return allDaysReward;
	}
	public void setAllDaysReward(Boolean allDaysReward) {
		this.allDaysReward = allDaysReward;
	}
	public void SetProperties(Integer weekSignDays, Integer signDays,
			Integer credits, String sex, Boolean isSign,String serverName,Integer serverId) {
		this.weekSignDays = weekSignDays;
		this.signDays = signDays;
		this.credits = credits;
		this.sex = sex;
		this.isSign = isSign;
		this.serverName = serverName;
		this.serverId = serverId;
	}
	public void setRewardProperties(Boolean twoDaysReward, Boolean fiveDaysReward,
			Boolean tenDaysReward, Boolean fifteenDaysReward,
			Boolean allDaysReward,List<RewardReceiveLogBean> receiveList) {
		this.twoDaysReward = twoDaysReward;
		this.fiveDaysReward = fiveDaysReward;
		this.tenDaysReward = tenDaysReward;
		this.fifteenDaysReward = fifteenDaysReward;
		this.allDaysReward = allDaysReward;
		this.receiveList = receiveList;
	}
	
	
}
