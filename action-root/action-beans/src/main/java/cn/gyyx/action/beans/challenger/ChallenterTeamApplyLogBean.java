/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:33:31
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的实体
 */

package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class ChallenterTeamApplyLogBean {
	// 用户主键
	private int code;
	// 队伍id
	private int teamId;
	// 用户id
	private int userId;
	// 创建时间
	private Date createTime;
	// 状态（oncheck 待审核 pass 通过  unpass 忽略）
	private String state;
	private Date reviewTime;//审核时间
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
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
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the reviewTime
	 */
	public Date getReviewTime() {
		return reviewTime;
	}
	/**
	 * @param reviewTime the reviewTime to set
	 */
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
}
