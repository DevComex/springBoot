package cn.gyyx.action.beans.wdtombthreeteam;

import java.util.Date;

/**
 *  投票信息实体
 * @author lizhihai
 *
 */
public class TombVoteInfoBean {

	 //主键编号
	 private int code;   
	 //活动编号
	 private int actionCode; 
	 //积分
	 private int score;
	 //投票人电话
	 private String votterPhone;
	 //投票给谁
	 private String voteWho;
	 //投票时间
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
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getVotterPhone() {
		return votterPhone;
	}
	public void setVotterPhone(String votterPhone) {
		this.votterPhone = votterPhone;
	}
	public String getVoteWho() {
		return voteWho;
	}
	public void setVoteWho(String voteWho) {
		this.voteWho = voteWho;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	 
	 
	 
	 
}
