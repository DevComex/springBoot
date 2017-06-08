/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：chenpeilan 
 * 联系方式：chenpeilan@gyyx.cn 
 * 创建时间：2016年4月12日下午2:06:18
 * 版本号：v1.0
 * 本类主要用途叙述：投票后台信息
 */

package cn.gyyx.action.beans.wdtenyearfans;

public class AccountVoteScoreBean {
	// 账号
	private String accountName;
	// 积分
	private Integer score;
	// 投票次数
	private Integer voteNum;

	
	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public Integer getVoteNum() {
		return voteNum;
	}


	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}


	@Override
	public String toString() {
		return "AccountVoteScoreBean [accountName=" + accountName + ", score="
				+ score + ", voteNum=" + voteNum + "]";
	}

}
