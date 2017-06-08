package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WanwdManhuaBook {
	private Integer code;

	private Integer manhuaCode;

	private String bookName;

	private String bookNum;

	private String reviewStatus;

	private Date reviewTime;

	private Date createTime;

	private Date updateTime;
	private String newly;

	private Integer isDelete;
	private Integer netTypeCode;
	private Integer areaCode;
	private String areaName;

	private Integer userId;
	private String account;
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getManhuaCode() {
		return manhuaCode;
	}

	public void setManhuaCode(Integer manhuaCode) {
		this.manhuaCode = manhuaCode;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName == null ? null : bookName.trim();
	}

	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum == null ? null : bookNum.trim();
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus == null ? null : reviewStatus.trim();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getNewly() {
		return newly;
	}

	public void setNewly(String newly) {
		this.newly = newly;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getNetTypeCode() {
		return netTypeCode;
	}

	public void setNetTypeCode(Integer netTypeCode) {
		this.netTypeCode = netTypeCode;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}