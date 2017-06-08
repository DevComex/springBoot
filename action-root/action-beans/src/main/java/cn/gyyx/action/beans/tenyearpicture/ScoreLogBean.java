/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日上午10:46:01
 * 版本号：v1.0
 * 本类主要用途叙述：问道十年的积分日志表的实体
 */

package cn.gyyx.action.beans.tenyearpicture;

import java.util.Date;

public class ScoreLogBean {
	// 主键
	private int code;
	// 进出分数
	private int score;
	// 用户主键
	private int userCode;
	// 账户
	private String account;
	// 活动编号
	private int hdCode;
	// 活动号
	private String hdName;
	// 建立时间
	private Date creatTime;

	public ScoreLogBean() {
	}

	public ScoreLogBean(int code, int score, int userCode, String account,
			int hdCode, String hdName, Date crratTime) {
		this.code = code;
		this.score = score;
		this.userCode = userCode;
		this.account = account;
		this.hdCode = hdCode;
		this.hdName = hdName;
		this.creatTime = crratTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getHdCode() {
		return hdCode;
	}

	public void setHdCode(int hdCode) {
		this.hdCode = hdCode;
	}

	public String getHdName() {
		return hdName;
	}

	public void setHdName(String hdName) {
		this.hdName = hdName;
	}

	public Date getCrratTime() {
		return creatTime;
	}

	public void setCrratTime(Date crratTime) {
		this.creatTime = crratTime;
	}

	@Override
	public String toString() {
		return "ScoreLogBean [code=" + code + ", score=" + score
				+ ", userCode=" + userCode + ", account=" + account
				+ ", hdCode=" + hdCode + ", hdName=" + hdName + ", crratTime="
				+ creatTime + "]";
	}

}
