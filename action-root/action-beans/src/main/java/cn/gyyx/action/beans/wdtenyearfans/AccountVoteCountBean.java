/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：chenpeilan
 * 联系方式：chenpeilan@gyyx.cn 
 * 创建时间：2016年4月14日下午5:33:18
 * 版本号：v1.0
 * 本类主要用途叙述：账号每日关注和拉黑数量
 */

package cn.gyyx.action.beans.wdtenyearfans;

public class AccountVoteCountBean {
	// 账号
	private String accountName;
	// 关注数量
	private Integer whiteNum;
	// 拉黑数量
	private Integer blackNum;
	//每日参加为自己提名账号数
	private Integer nomaForMe;
	//每日参加为别人提名账号数
	private Integer nomaForHe;
	//每日参加关注账号数
	private Integer joinWhiteNum;
	//每日参加拉黑账号数
	private Integer joinBlackNum;
	//每日参加投票账号数
	private Integer joinVoteNum;

	
	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public Integer getWhiteNum() {
		return whiteNum;
	}


	public void setWhiteNum(Integer whiteNum) {
		this.whiteNum = whiteNum;
	}


	public Integer getBlackNum() {
		return blackNum;
	}


	public void setBlackNum(Integer blackNum) {
		this.blackNum = blackNum;
	}

	

	public Integer getNomaForMe() {
		return nomaForMe;
	}


	public void setNomaForMe(Integer nomaForMe) {
		this.nomaForMe = nomaForMe;
	}


	public Integer getNomaForHe() {
		return nomaForHe;
	}


	public void setNomaForHe(Integer nomaForHe) {
		this.nomaForHe = nomaForHe;
	}


	public Integer getJoinWhiteNum() {
		return joinWhiteNum;
	}


	public void setJoinWhiteNum(Integer joinWhiteNum) {
		this.joinWhiteNum = joinWhiteNum;
	}


	public Integer getJoinBlackNum() {
		return joinBlackNum;
	}


	public void setJoinBlackNum(Integer joinBlackNum) {
		this.joinBlackNum = joinBlackNum;
	}


	public Integer getJoinVoteNum() {
		return joinVoteNum;
	}


	public void setJoinVoteNum(Integer joinVoteNum) {
		this.joinVoteNum = joinVoteNum;
	}


	@Override
	public String toString() {
		return "AccountVoteCountBean [accountName=" + accountName
				+ ", whiteNum=" + whiteNum + ", blackNum=" + blackNum
				+ ", nomaForMe=" + nomaForMe + ", nomaForHe=" + nomaForHe
				+ ", joinWhiteNum=" + joinWhiteNum + ", joinBlackNum="
				+ joinBlackNum + ", joinVoteNum=" + joinVoteNum + "]";
	}


	


	

}
