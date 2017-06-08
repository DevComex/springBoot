
package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class ChallenterTeamUserBean {
	// 队伍id
	private int teamId;
	// 用户id
	private int userId;
	// 创建时间
	private Date createTime;
	/**
	 * @return the teamId
	 */
	public int getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
