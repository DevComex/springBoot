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


public class ChallenterDeathLifeRankBean {
	//玩家
	private String player;
	//参与场数
	private String attendCount;
	//获胜场数
	private String winCount;
	@JsonIgnore  
	private String[] playerString;
	@JsonIgnore  
	private String[] attendCountString;
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
	 * @return the attendCount
	 */
	public String getAttendCount() {
		return attendCount;
	}
	/**
	 * @param attendCount the attendCount to set
	 */
	public void setAttendCount(String attendCount) {
		this.attendCount = attendCount;
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
	 * @return the attendCountString
	 */
	public String[] getAttendCountString() {
		return attendCountString;
	}
	/**
	 * @param attendCountString the attendCountString to set
	 */
	public void setAttendCountString(String[] attendCountString) {
		this.attendCountString = attendCountString;
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
