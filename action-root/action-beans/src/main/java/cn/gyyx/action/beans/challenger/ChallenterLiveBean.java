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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChallenterLiveBean {
	// 主键
	private int code;
	// 对战玩家
	private String player;
	// 建立时间
	private Date createTime;
	// 直播频道
	private String liveRadio;
	// 直播时间
	private Date liveTime;
	// 用户主键
	private int userId;
	// 状态
	private String state;

	private String account;
	// 角色名
	private String roleName;

	private String liveTimeString;

	public String timeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(this.liveTime);
	}

	public String getLiveTimeString() {
		return liveTimeString;
	}

	public void setLiveTimeString(String liveTimeString) {
		this.liveTimeString = liveTimeString;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLiveRadio() {
		return liveRadio;
	}

	public void setLiveRadio(String liveRadio) {
		this.liveRadio = liveRadio;
	}

	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "ChallenterLiveBean [code=" + code + ", player=" + player
				+ ", createTime=" + createTime + ", liveRadio=" + liveRadio
				+ ", liveTime=" + liveTime + ", userId=" + userId + ", state="
				+ state + ", roleName=" + roleName + "]";
	}

}
