package cn.gyyx.action.beans.userinfo.po;

import java.util.Date;

public class ActionGameInterimPO {

	private Integer code;
	
	private Integer userCode;
	
	private String userId;
	
	private String userNick;
	
	private Integer serverId;
	
	private String serverName;
	
	private String roleId;
	
	private String roleName;
	
	private Integer roleLevel;
	
	private Integer roleProficiency;
	
	private Date createAt;
	
	private Date modifyAt;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Integer getRoleProficiency() {
		return roleProficiency;
	}

	public void setRoleProficiency(Integer roleProficiency) {
		this.roleProficiency = roleProficiency;
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
}
