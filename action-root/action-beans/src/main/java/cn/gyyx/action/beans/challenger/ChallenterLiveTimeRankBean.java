/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午2:47:42
 * 版本号：v1.0
 * 本类主要用途叙述：直播的实体
 */

package cn.gyyx.action.beans.challenger;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ChallenterLiveTimeRankBean {
	//玩家
	private String playerA;
	private String playerB;
	//直播时间
	private String liveTime;
	//直播频道
	private String liveRadio;
	@JsonIgnore  
	private String[] playerAString;
	@JsonIgnore  
	private String[] playerBString;
	@JsonIgnore  
	private String[] liveTimeString;
	@JsonIgnore  
	private String[] liveRadioString;
	/**
	 * @return the playerA
	 */
	public String getPlayerA() {
		return playerA;
	}
	/**
	 * @param playerA the playerA to set
	 */
	public void setPlayerA(String playerA) {
		this.playerA = playerA;
	}
	/**
	 * @return the playerB
	 */
	public String getPlayerB() {
		return playerB;
	}
	/**
	 * @param playerB the playerB to set
	 */
	public void setPlayerB(String playerB) {
		this.playerB = playerB;
	}
	/**
	 * @return the liveTime
	 */
	public String getLiveTime() {
		return liveTime;
	}
	/**
	 * @param liveTime the liveTime to set
	 */
	public void setLiveTime(String liveTime) {
		this.liveTime = liveTime;
	}
	/**
	 * @return the liveRadio
	 */
	public String getLiveRadio() {
		return liveRadio;
	}
	/**
	 * @param liveRadio the liveRadio to set
	 */
	public void setLiveRadio(String liveRadio) {
		this.liveRadio = liveRadio;
	}
	/**
	 * @return the playerAString
	 */
	public String[] getPlayerAString() {
		return playerAString;
	}
	/**
	 * @param playerAString the playerAString to set
	 */
	public void setPlayerAString(String[] playerAString) {
		this.playerAString = playerAString;
	}
	/**
	 * @return the playerBString
	 */
	public String[] getPlayerBString() {
		return playerBString;
	}
	/**
	 * @param playerBString the playerBString to set
	 */
	public void setPlayerBString(String[] playerBString) {
		this.playerBString = playerBString;
	}
	/**
	 * @return the liveTimeString
	 */
	public String[] getLiveTimeString() {
		return liveTimeString;
	}
	/**
	 * @param liveTimeString the liveTimeString to set
	 */
	public void setLiveTimeString(String[] liveTimeString) {
		this.liveTimeString = liveTimeString;
	}
	/**
	 * @return the liveRadioString
	 */
	public String[] getLiveRadioString() {
		return liveRadioString;
	}
	/**
	 * @param liveRadioString the liveRadioString to set
	 */
	public void setLiveRadioString(String[] liveRadioString) {
		this.liveRadioString = liveRadioString;
	}
	
}
