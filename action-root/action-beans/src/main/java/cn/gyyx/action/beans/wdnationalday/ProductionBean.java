package cn.gyyx.action.beans.wdnationalday;

import java.util.Date;

/**
 * 晒一晒
 * @ClassName: ProductionBean
 * @description ProductionBean
 * @author luozhenyu
 * @date 2016年9月22日
 */
public class ProductionBean {
	/**
	 * 主键
	 */
	private int code;
	/**
	 * 账号id
	 */
	private int userId;
	/**
	 * 图片链接
	 */
	private String imageUrl;
	/**
	 * 文章
	 */
	private String Diary;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 天气
	 */
	private String weather;
	/**
	 * 上传时间
	 */
	private Date upTime;
	/**
	 * 作品审核状态
	 */
	private int status;

	/**
	 * 角色名
	 */
	private String roalName;
	/**
	 * 区组
	 */
	private String serverName;
	private String upTimeStr;
	public String getUpTimeStr() {
		return upTimeStr;
	}
	public void setUpTimeStr(String upTimeStr) {
		this.upTimeStr = upTimeStr;
	}
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the diary
	 */
	public String getDiary() {
		return Diary;
	}
	/**
	 * @param diary the diary to set
	 */
	public void setDiary(String diary) {
		Diary = diary;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the weather
	 */
	public String getWeather() {
		return weather;
	}
	/**
	 * @param weather the weather to set
	 */
	public void setWeather(String weather) {
		this.weather = weather;
	}
	/**
	 * @return the upTime
	 */
	public Date getUpTime() {
		return upTime;
	}
	/**
	 * @param upTime the upTime to set
	 */
	public void setUpTime(Date upTime) {
		this.upTime = upTime;
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
	 * @return the roalName
	 */
	public String getRoalName() {
		return roalName;
	}
	/**
	 * @param roalName the roalName to set
	 */
	public void setRoalName(String roalName) {
		this.roalName = roalName;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Diary == null) ? 0 : Diary.hashCode());
		result = prime * result + code;
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((roalName == null) ? 0 : roalName.hashCode());
		result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
		result = prime * result + status;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((upTime == null) ? 0 : upTime.hashCode());
		result = prime * result + userId;
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
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
		ProductionBean other = (ProductionBean) obj;
		if (Diary == null) {
			if (other.Diary != null)
				return false;
		} else if (!Diary.equals(other.Diary))
			return false;
		if (code != other.code)
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (roalName == null) {
			if (other.roalName != null)
				return false;
		} else if (!roalName.equals(other.roalName))
			return false;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (upTime == null) {
			if (other.upTime != null)
				return false;
		} else if (!upTime.equals(other.upTime))
			return false;
		if (userId != other.userId)
			return false;
		if (weather == null) {
			if (other.weather != null)
				return false;
		} else if (!weather.equals(other.weather))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductionBean [code=" + code + ", userId=" + userId + ", imageUrl=" + imageUrl + ", Diary=" + Diary
				+ ", title=" + title + ", weather=" + weather + ", upTime=" + upTime + ", status=" + status
				+ ", roalName=" + roalName + ", serverName=" + serverName + "]";
	}
	
}
