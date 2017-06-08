/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月30日下午1:37:15
 * 版本号：v1.0
 * 本类主要用途叙述：提名展示的实体
 */

package cn.gyyx.action.beans.wdtenyearfans;

public class WdNominationShowBean {
	// 提名主键
	private int nominationCode;
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
	// 投票类型
	private int voteType;
	// 简介
	private String nominatedContent;
	// 区组名
	private String serverName;

	public int getVoteType() {
		return voteType;
	}

	public void setVoteType(int voteType) {
		this.voteType = voteType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

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

	public WdNominationShowBean() {
		super();
	}

	public int getTypeCode() {
		return voteType;
	}

	public void setTypeCode(int typeCode) {
		this.voteType = typeCode;
	}

	public String getNominatedContent() {
		return nominatedContent;
	}

	public void setNominatedContent(String nominatedContent) {
		this.nominatedContent = nominatedContent;
	}

	/*
	 * public WdNominationShowBean(int nominationCode, int whiteNum, int
	 * whiteRanking, int blackNum, int blackRanking, String roleName, String
	 * picNum, int pageNum, int voteType, String nominatedContent) {
	 * this.nominationCode = nominationCode; this.whiteNum = whiteNum;
	 * this.whiteRanking = whiteRanking; this.blackNum = blackNum;
	 * this.blackRanking = blackRanking; this.roleName = roleName; this.picNum =
	 * picNum; this.voteType = voteType; this.nominatedContent =
	 * nominatedContent; }
	 */

	@Override
	public String toString() {
		return "WdNominationShowBean [nominationCode=" + nominationCode
				+ ", whiteNum=" + whiteNum + ", whiteRanking=" + whiteRanking
				+ ", blackNum=" + blackNum + ", blackRanking=" + blackRanking
				+ ", roleName=" + roleName + ", picNum=" + picNum
				+ ", voteType=" + voteType + ", nominatedContent="
				+ nominatedContent + "]";
	}

}
