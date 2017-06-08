package cn.gyyx.action.beans.wd161;

import java.util.Date;

/**
 * 问道161道友角色信息绑定实体
 */
public class Wd161RoleAccountBean {

	//主键
	private int code;
	//用户标识
	private int userCode;
	//用户名
	private String userName;
	//服务器标识
	private int serverCode;
	//服务器名字
	private String serverName;
	//玩家GID
	private String gid;
	//角色名称
	private String roleName;
	//活动编号
	private int actionCode;
	//用户绑定角色时间
	private Date createTime;
	//ip
	private String ip;
	//图品url
	private String picUrl;
	//审核状态
	private String auditStatus;
	//审核人
	private String auditPerson;
	//审核时间
	private Date auditTime;
	//票数
	private int votes;
	//排名
	private int rank;
	//更新时间
	private Date updateTime;
	
	
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getServerCode() {
		return serverCode;
	}
	public void setServerCode(int serverCode) {
		this.serverCode = serverCode;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	public Date getAuditDate() {
		return auditTime;
	}
	public void setAuditDate(Date auditDate) {
		this.auditTime = auditDate;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
	
	
}
