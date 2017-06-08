package cn.gyyx.action.beans.lottery.po;

import java.util.Date;

public class ActionLotteryLogPO {
	
	private Long code;
	
	private String activityType;
	
	private Integer activityId;
	
	private String userId;
	
	private String gameId;
	
	private String serverId;
	
	private String prizeType;
	
	private Integer prizeCode;
	
	private String prizeName;
	
	private Integer prizeNum;
	
	private String cardCode;
	
	private Date winningAt;
	
	private String winningIp;
	
	private Integer isAvailable;
	
	private String remark;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public Integer getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(Integer prizeCode) {
		this.prizeCode = prizeCode;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getPrizeNum() {
		return prizeNum;
	}

	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Date getWinningAt() {
		return winningAt;
	}

	public void setWinningAt(Date winningAt) {
		this.winningAt = winningAt;
	}

	public String getWinningIp() {
		return winningIp;
	}

	public void setWinningIp(String winningIp) {
		this.winningIp = winningIp;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
