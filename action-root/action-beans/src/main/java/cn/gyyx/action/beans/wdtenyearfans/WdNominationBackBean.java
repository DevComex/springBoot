/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：chenpeilan 
 * 联系方式：chenpeilan@gyyx.cn 
 * 创建时间：2016年4月13日上午10:13:21
 * 版本号：v1.0
 * 本类主要用途叙述：提名后台信息
 */

package cn.gyyx.action.beans.wdtenyearfans;

public class WdNominationBackBean {
	// 被提名者区组
	private String nominatedServerName;
	// 被提名者角色
	private String nominatedRole;
	// 提名者账号
	private String accountName;
	//提名者区组
	private String accountServerName;
	//提名者角色
	private String accountRole;
	//当前粉丝数
	private Integer voteWhite;
	//粉丝数排名
	private Integer whiteRanking;
	//当前黑粉数
	private Integer voteBlack;
	//黑粉排名
	private Integer blackRanking;

	
	
	public String getNominatedServerName() {
		return nominatedServerName;
	}



	public void setNominatedServerName(String nominatedServerName) {
		this.nominatedServerName = nominatedServerName;
	}



	public String getNominatedRole() {
		return nominatedRole;
	}



	public void setNominatedRole(String nominatedRole) {
		this.nominatedRole = nominatedRole;
	}



	public String getAccountName() {
		return accountName;
	}



	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}



	public String getAccountServerName() {
		return accountServerName;
	}



	public void setAccountServerName(String accountServerName) {
		this.accountServerName = accountServerName;
	}



	public String getAccountRole() {
		return accountRole;
	}



	public void setAccountRole(String accountRole) {
		this.accountRole = accountRole;
	}



	public Integer getVoteWhite() {
		return voteWhite;
	}



	public void setVoteWhite(Integer voteWhite) {
		this.voteWhite = voteWhite;
	}



	public Integer getWhiteRanking() {
		return whiteRanking;
	}



	public void setWhiteRanking(Integer whiteRanking) {
		this.whiteRanking = whiteRanking;
	}



	public Integer getVoteBlack() {
		return voteBlack;
	}



	public void setVoteBlack(Integer voteBlack) {
		this.voteBlack = voteBlack;
	}



	public Integer getBlackRanking() {
		return blackRanking;
	}



	public void setBlackRanking(Integer blackRanking) {
		this.blackRanking = blackRanking;
	}



	@Override
	public String toString() {
		return "WdNominationBackBean [nominatedServerName="
				+ nominatedServerName + ", nominatedRole=" + nominatedRole
				+ ", accountName=" + accountName + ", accountServerName="
				+ accountServerName + ", accountRole=" + accountRole
				+ ", voteWhite=" + voteWhite + ", whiteRanking=" + whiteRanking
				+ ", voteBlack=" + voteBlack + ", blackRanking=" + blackRanking
				+ "]";
	}


}
