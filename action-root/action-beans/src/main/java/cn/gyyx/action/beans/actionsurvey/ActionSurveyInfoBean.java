package cn.gyyx.action.beans.actionsurvey;

import java.util.Date;

public class ActionSurveyInfoBean {

	/**
	 * 主键
	 */
	private int code;
	/**
	 * 活动ID
	 */

	private int actionCode;

	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 得知游戏的渠道
	 */
	private String source;
	/**
	 * 看重游戏的品质
	 */
	private String quality;
	/**
	 * 每天游戏时间
	 */
	private String time;
	/**
	 * 最近玩的游戏
	 */
	private String recentlyGame;
	/**
	 * 游戏流失原因
	 */
	private String lossReason;
	/**
	 * 手机号码
	 */
	private String phoneNum;
	/**
	 * 提交时间
	 */
	private Date createTime;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRecentlyGame() {
		return recentlyGame;
	}
	public void setRecentlyGame(String recentlyGame) {
		this.recentlyGame = recentlyGame;
	}
	public String getLossReason() {
		return lossReason;
	}
	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "ActionSurveyInfoBean [code=" + code + ", actionCode=" + actionCode + ", sex=" + sex + ", birthday="
				+ birthday + ", city=" + city + ", source=" + source + ", quality=" + quality + ", time=" + time
				+ ", recentlyGame=" + recentlyGame + ", lossReason=" + lossReason + ", phoneNum=" + phoneNum
				+ ", createTime=" + createTime + "]";
	}
	
	
}
