/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月14日下午4:45:10
 * 版本号：v1.0
 * 本类主要用途叙述：分数后台展示的实体
 */

package cn.gyyx.action.beans.tenyearpicture;

import java.util.List;

public class ScoreOaBean {
	// 账户
	private String account;
	// 分数
	private int score;
	// 活动分数
	private List<Integer> hdScore;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Integer> getHdScore() {
		return hdScore;
	}

	public void setHdScore(List<Integer> hdScore) {
		this.hdScore = hdScore;
	}

	public ScoreOaBean() {
	}

	public ScoreOaBean(String account, int score, List<Integer> hdScore) {
		this.account = account;
		this.score = score;
		this.hdScore = hdScore;
	}

	@Override
	public String toString() {
		return "ScoreOaBean [account=" + account + ", score=" + score
				+ ", hdScore=" + hdScore + "]";
	}

}
