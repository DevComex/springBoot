/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：chenpeilan
 * 联系方式：chenpeilan@gyyx.cn 
 * 创建时间：2016年4月1日上午11:37:15
 * 版本号：v1.0
 * 本类主要用途叙述：提名展示查看状态的实体
 */

package cn.gyyx.action.beans.wdtenyearfans;

public class WdNominationCheckBean {
	// 提名主键
	private int nominationCode;
	// 审核状态
	private int status;
	// 大区
	private int area;
	// 区服ID
	private int serverId;
	// 区服名称
	private String serverName;
	// 粉丝数
	private int whiteNum;
	// 粉丝排名
	private int whiteRanking;
	// 黑粉数
	private int blackNum;
	// 黑粉排名
	private int blackRanking;
	// 角色名称
	private String roleName;
	// 图片的值
	private String picNum;

	public int getNominationCode() {
		return nominationCode;
	}

	public void setNominationCode(int nominationCode) {
		this.nominationCode = nominationCode;
	}

	public int getWhiteNum() {
		return whiteNum;
	}

	public void setWhiteNum(int whiteNum) {
		this.whiteNum = whiteNum;
	}

	public int getWhiteRanking() {
		return whiteRanking;
	}

	public void setWhiteRanking(int whiteRanking) {
		this.whiteRanking = whiteRanking;
	}

	public int getBlackNum() {
		return blackNum;
	}

	public void setBlackNum(int blackNum) {
		this.blackNum = blackNum;
	}

	public int getBlackRanking() {
		return blackRanking;
	}

	public void setBlackRanking(int blackRanking) {
		this.blackRanking = blackRanking;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public WdNominationCheckBean() {
		super();
	}

	public WdNominationCheckBean(int nominationCode, int whiteNum, int status,
			int area, int serverId, String serverName, int whiteRanking,
			int blackNum, int blackRanking, String roleName, String picNum) {
		this.nominationCode = nominationCode;
		this.status = status;
		this.area = area;
		this.serverId = serverId;
		this.serverName = serverName;
		this.whiteNum = whiteNum;
		this.whiteRanking = whiteRanking;
		this.blackNum = blackNum;
		this.blackRanking = blackRanking;
		this.roleName = roleName;
		this.picNum = picNum;
	}

	@Override
	public String toString() {
		return "WdNominationCheckBean [nominationCode=" + nominationCode
				+ ", status=" + status + ", area=" + area + ", serverId="
				+ serverId + ", serverName=" + serverName + ", whiteNum="
				+ whiteNum + ", whiteRanking=" + whiteRanking + ", blackNum="
				+ blackNum + ", blackRanking=" + blackRanking + ", roleName="
				+ roleName + ", picNum=" + picNum + "]";
	}


}
