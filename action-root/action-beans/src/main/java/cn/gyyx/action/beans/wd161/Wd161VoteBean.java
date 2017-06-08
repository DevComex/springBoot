package cn.gyyx.action.beans.wd161;

import java.util.Date;

public class Wd161VoteBean {
	//主键
	private int code;
	//用户Id
	private int  userCode;
	//用户名
	private String  userName;
	//服务器标识
	private int serverCode;
	//服务器名字
	private String serverName;
	//角色名
	private String roleName;
	//投票人id
	private int votterId;
	//投票人name
	private String votterName;
	//投票人ip
	private String votterIp;
	//投票时间
	private Date createTime;
	// 冗余，投票数
	private int votesCount;
	
	
public int getVotesCount() {
		return votesCount;
	}
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}
	//	//昨日活跃度
//	private String activeDegree;
//	//昨日战力级别
//	private String powerLevel;
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public int getVotterId() {
		return votterId;
	}
	public void setVotterId(int votterId) {
		this.votterId = votterId;
	}
	public String getVotterName() {
		return votterName;
	}
	public void setVotterName(String votterName) {
		this.votterName = votterName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getVotterIp() {
		return votterIp;
	}
	public void setVotterIp(String votterIp) {
		this.votterIp = votterIp;
	}
	
	
	
}
