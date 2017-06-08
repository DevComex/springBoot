package cn.gyyx.action.beans.lottery.po;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ActionPrizesAddressPO {

	private Long code;
	
	private String activityType;
	
	@NotNull(message="活动ID不能为空！")
	private Integer activityId;
	
	private Integer userCode;
	
	private String userId;
	
	private String userName;
	
	private String userPhone;
	
	private String userEmail;
	
	private String userAddress;
	
	private String remark;
	
	private Date createAt;
	
	private Date modifyAt;
	
	private Date winningAt;
	
	private String qq;
	
	private String source;

	private String zipCode;
	
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

	public Integer getUserCode() {
		return userCode;
	}

	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getWinningAt() {
		return winningAt;
	}

	public void setWinningAt(Date winningAt) {
		this.winningAt = winningAt;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
