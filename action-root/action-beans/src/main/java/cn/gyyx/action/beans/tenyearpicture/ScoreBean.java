/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日上午10:41:06
 * 版本号：v1.0
 * 本类主要用途叙述：问道10年积分总表的实体
 */

package cn.gyyx.action.beans.tenyearpicture;

import java.util.Date;

public class ScoreBean {
	// 主键
	private int code;
	// 账户
	private String account;
	// 用户主键
	private int userCode;
	// 用户ip
	private String ip;
	// 分数
	private int score;
	// 建立时间
	private Date creatTime;

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

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public ScoreBean(int code, String account, int userCode, String ip,
			int score, Date creatTime) {
		this.code = code;
		this.account = account;
		this.userCode = userCode;
		this.ip = ip;
		this.score = score;
		this.creatTime = creatTime;
	}

	public ScoreBean() {
	}

	@Override
	public String toString() {
		return "ScoreBean [code=" + code + ", account=" + account
				+ ", userCode=" + userCode + ", ip=" + ip + ", score=" + score
				+ ", creatTime=" + creatTime + "]";
	}
	
}
