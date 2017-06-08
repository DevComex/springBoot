package cn.gyyx.action.beans.lottery.po;

import java.util.Date;

public class ActionQualificationPO {

	private Long code;
	
	private String userId;
	
	private Integer activityId;
	
	private Integer lotteryCount;
	
	private Integer mustWinCount;
	
	private Integer lotteryScore;
	
	private Date createAt;
	
	private Date modifyAt;
	
	private String remark;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getLotteryCount() {
		return lotteryCount;
	}

	public void setLotteryCount(Integer lotteryCount) {
		this.lotteryCount = lotteryCount;
	}

	public Integer getMustWinCount() {
		return mustWinCount;
	}

	public void setMustWinCount(Integer mustWinCount) {
		this.mustWinCount = mustWinCount;
	}

	public Integer getLotteryScore() {
		return lotteryScore;
	}

	public void setLotteryScore(Integer lotteryScore) {
		this.lotteryScore = lotteryScore;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ActionQualificationPO [code=" + code + ", userId=" + userId
                + ", activityId=" + activityId + ", lotteryCount="
                + lotteryCount + ", mustWinCount=" + mustWinCount
                + ", lotteryScore=" + lotteryScore + ", createAt=" + createAt
                + ", modifyAt=" + modifyAt + ", remark=" + remark + "]";
    }
	
}
