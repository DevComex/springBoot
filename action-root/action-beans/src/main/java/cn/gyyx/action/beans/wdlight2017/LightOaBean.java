package cn.gyyx.action.beans.wdlight2017;

import java.util.Date;

public class LightOaBean {
    private Integer code;

    private Integer actionCode;

    private Integer level;

    private Integer limitNum;

    private Integer lightType;

    private Date updateTime;

    private  int peopleNum;

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the actionCode
	 */
	public Integer getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return the limitNum
	 */
	public Integer getLimitNum() {
		return limitNum;
	}

	/**
	 * @param limitNum the limitNum to set
	 */
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	/**
	 * @return the lightType
	 */
	public Integer getLightType() {
		return lightType;
	}

	/**
	 * @param lightType the lightType to set
	 */
	public void setLightType(Integer lightType) {
		this.lightType = lightType;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the peopleNum
	 */
	public int getPeopleNum() {
		return peopleNum;
	}

	/**
	 * @param peopleNum the peopleNum to set
	 */
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionCode == null) ? 0 : actionCode.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((lightType == null) ? 0 : lightType.hashCode());
		result = prime * result + ((limitNum == null) ? 0 : limitNum.hashCode());
		result = prime * result + peopleNum;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		LightOaBean other = (LightOaBean) obj;
		if (actionCode == null) {
			if (other.actionCode != null)
				return false;
		} else if (!actionCode.equals(other.actionCode))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (lightType == null) {
			if (other.lightType != null)
				return false;
		} else if (!lightType.equals(other.lightType))
			return false;
		if (limitNum == null) {
			if (other.limitNum != null)
				return false;
		} else if (!limitNum.equals(other.limitNum))
			return false;
		if (peopleNum != other.peopleNum)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LightOaBean [code=" + code + ", actionCode=" + actionCode + ", level=" + level + ", limitNum="
				+ limitNum + ", lightType=" + lightType + ", updateTime=" + updateTime + ", peopleNum=" + peopleNum
				+ "]";
	}


}