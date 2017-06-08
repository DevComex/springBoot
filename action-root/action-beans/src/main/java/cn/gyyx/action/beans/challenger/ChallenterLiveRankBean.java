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


public class ChallenterLiveRankBean {
	//玩家
	private String player;
	//直播场数
	private String liveCount;
	//频道
	private String channel;
	//胜出场数
	private String winCount;
	
	@JsonIgnore  
	private String[] playerString;
	@JsonIgnore  
	private String[] liveCountString;
	@JsonIgnore  
	private String[] channelString;
	@JsonIgnore  
	private String[] winCountString;
	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(String player) {
		this.player = player;
	}
	/**
	 * @return the liveCount
	 */
	public String getLiveCount() {
		return liveCount;
	}
	/**
	 * @param liveCount the liveCount to set
	 */
	public void setLiveCount(String liveCount) {
		this.liveCount = liveCount;
	}
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * @return the winCount
	 */
	public String getWinCount() {
		return winCount;
	}
	/**
	 * @param winCount the winCount to set
	 */
	public void setWinCount(String winCount) {
		this.winCount = winCount;
	}
	/**
	 * @return the playerString
	 */
	public String[] getPlayerString() {
		return playerString;
	}
	/**
	 * @param playerString the playerString to set
	 */
	public void setPlayerString(String[] playerString) {
		this.playerString = playerString;
	}
	/**
	 * @return the liveCountString
	 */
	public String[] getLiveCountString() {
		return liveCountString;
	}
	/**
	 * @param liveCountString the liveCountString to set
	 */
	public void setLiveCountString(String[] liveCountString) {
		this.liveCountString = liveCountString;
	}
	/**
	 * @return the channelString
	 */
	public String[] getChannelString() {
		return channelString;
	}
	/**
	 * @param channelString the channelString to set
	 */
	public void setChannelString(String[] channelString) {
		this.channelString = channelString;
	}
	/**
	 * @return the winCountString
	 */
	public String[] getWinCountString() {
		return winCountString;
	}
	/**
	 * @param winCountString the winCountString to set
	 */
	public void setWinCountString(String[] winCountString) {
		this.winCountString = winCountString;
	}
	
}
