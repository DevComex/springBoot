package cn.gyyx.action.beans.wdsigned;

import java.util.Date;

public class WdAppSignedBean {

	private int code;
	private String account;
	private String serverName;
	private int serverId;
	private int serialDay;
	private int totalDay;
	private String batch;
	private Date createTime;
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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	/**
	 * @return the serverId
	 */
	public int getServerId() {
		return serverId;
	}
	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	/**
	 * @return the serialDay
	 */
	public int getSerialDay() {
		return serialDay;
	}
	/**
	 * @param serialDay the serialDay to set
	 */
	public void setSerialDay(int serialDay) {
		this.serialDay = serialDay;
	}
	/**
	 * @return the totalDay
	 */
	public int getTotalDay() {
		return totalDay;
	}
	/**
	 * @param totalDay the totalDay to set
	 */
	public void setTotalDay(int totalDay) {
		this.totalDay = totalDay;
	}
	/**
	 * @return the batch
	 */
	public String getBatch() {
		return batch;
	}
	/**
	 * @param batch the batch to set
	 */
	public void setBatch(String batch) {
		this.batch = batch;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((batch == null) ? 0 : batch.hashCode());
		result = prime * result + code;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + serialDay;
		result = prime * result + serverId;
		result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
		result = prime * result + totalDay;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WdAppSignedBean other = (WdAppSignedBean) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (batch == null) {
			if (other.batch != null)
				return false;
		} else if (!batch.equals(other.batch))
			return false;
		if (code != other.code)
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (serialDay != other.serialDay)
			return false;
		if (serverId != other.serverId)
			return false;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		if (totalDay != other.totalDay)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WdAppSignedBean [code=" + code + ", account=" + account + ", serverName=" + serverName + ", serverId="
				+ serverId + ", serialDay=" + serialDay + ", totalDay=" + totalDay + ", batch=" + batch
				+ ", createTime=" + createTime + "]";
	}
	
}
