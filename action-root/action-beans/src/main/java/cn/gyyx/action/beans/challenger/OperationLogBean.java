/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午7:47:50
 * 版本号：v1.0
 * 本类主要用途叙述：后台操作日志表
 */package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class OperationLogBean {

	private int code;
	private int tid;

	// 类型
	// userInfoCheck 报名信息审核
	// challengeInfoCheck 挑战信息审核
	// liveInfoCheck 直播信息审核
	private String type;
	private int actionCode;
	private int userid;
	private String userName;
	private String description;
	private Date createTime;
	private String account;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "OperationLogBean [code=" + code + ", tid=" + tid + ", type="
				+ type + ", actionCode=" + actionCode + ", userid=" + userid
				+ ", userName=" + userName + ", description=" + description
				+ ", createTime=" + createTime + "]";
	}

}


