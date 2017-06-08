/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.wdqingchengshan;

import java.util.Date;

/** 
      问道青城山签到功能-用户积分表
 */
public class UserScoreBean {
	private Integer code;//主键
	private String account;//用户名
	private Integer totalScore;//当前总积分
	private Integer scoreSource;//积分来源（0代表签到所得，1代表微博分享，2代表道行转化）
	private Integer usedScore;//被用掉的积分
	private Date lastUpdateTime;//最后更新积分时间
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getScoreSource() {
		return scoreSource;
	}
	public void setScoreSource(Integer scoreSource) {
		this.scoreSource = scoreSource;
	}
	public Integer getUsedScore() {
		return usedScore;
	}
	public void setUsedScore(Integer usedScore) {
		this.usedScore = usedScore;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
