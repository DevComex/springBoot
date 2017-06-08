package cn.gyyx.action.beans.wdsigned;

import java.util.Date;

public class WdAppSignedSendPrizeBean {

	private int code;
	private String account;

	private int serverId;

	private String gift;

	private int status;

	private String batch;

	private Date createTime;

	private Date sendTime;

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
	 * @return the gift
	 */
	public String getGift() {
		return gift;
	}

	/**
	 * @param gift the gift to set
	 */
	public void setGift(String gift) {
		this.gift = gift;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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
		result = prime * result + ((gift == null) ? 0 : gift.hashCode());
		result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
		result = prime * result + serverId;
		result = prime * result + status;
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
		WdAppSignedSendPrizeBean other = (WdAppSignedSendPrizeBean) obj;
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
		if (gift == null) {
			if (other.gift != null)
				return false;
		} else if (!gift.equals(other.gift))
			return false;
		if (sendTime == null) {
			if (other.sendTime != null)
				return false;
		} else if (!sendTime.equals(other.sendTime))
			return false;
		if (serverId != other.serverId)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WdAppSignedSendPrizeBean [code=" + code + ", account=" + account + ", serverId=" + serverId + ", gift="
				+ gift + ", status=" + status + ", batch=" + batch + ", createTime=" + createTime + ", sendTime="
				+ sendTime + "]";
	}

	
}
