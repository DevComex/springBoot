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

public class ChallenterUserInfoBean {
	// 主键
	private int code;
	// 用户主键
	private int userId;
	// 用户账户
	private String account;
	// 角色名
	private String roleName;
	// 联系类型
	private String contactType;
	// 联系方式
	private String contact;
	// 生成时间
	private Date createTime;
	// 状态
	// oncheck pass unpass
	private String state;
	//审核时间
	private Date reviewTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	@Override
	public String toString() {
		return "UserInfoBean [userId=" + userId + ", account=" + account
				+ ", roleName=" + roleName + ", contactType=" + contactType
				+ ", contact=" + contact + ", createTime=" + createTime
				+ ", state=" + state + "]";
	}

}
