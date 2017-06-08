package cn.gyyx.action.beans.wd161;

import java.util.Date;

public class Wd161CommentsBean {
	//主键
	private int code;
	//昵称
	private String nickName;
	//评论信息
	private String comments;
	//发布时间
	private Date createTime;
	//审核状态
	private String auditStatus;
	//审核人
	private String auditPerson;
	//审核时间
	private Date auditTime;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditPerson() {
		return auditPerson;
	}
	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	
}
