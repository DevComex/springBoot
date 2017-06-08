package cn.gyyx.action.beans.lottery.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class LotteryPrizesVO {

	@NotNull(message="活动ID不能为空！")
	private Integer activityId;
	
	private Integer userCode;
	
	private String userId;  // uerId 和 openId 是同一个值
	
	private String openId;  // uerId 和 openId 是同一个值
	
	private String prizeType;
	
	private Integer prizeCode;
	
	private String prizeName;
	
	private String prizeEnglish;
	
	private Integer prizeNum;
	
	private String cardCode;
	
	private Integer isAvailable;
	
	@NotBlank(message="请填写“姓名”！")
	private String userName;
	
	@NotBlank(message="请填写“联系方式”！")
	private String userPhone;
	
	@NotBlank(message="请填写“地址”！")
	private String userAddress;

	private String remark;
	
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
		this.openId = userId;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
		this.userId = openId;
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

	public String getPrizeEnglish() {
		return prizeEnglish;
	}

	public void setPrizeEnglish(String prizeEnglish) {
		this.prizeEnglish = prizeEnglish;
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

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
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
	
}
