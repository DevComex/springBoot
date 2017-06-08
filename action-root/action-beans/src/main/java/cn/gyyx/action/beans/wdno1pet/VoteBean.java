/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午6:57:19
 * 版本号：v1.0
 * 本类主要用途叙述：投票的实体
 */

package cn.gyyx.action.beans.wdno1pet;

import java.sql.Timestamp;

public class VoteBean {
	/**
	 * 投票的主键
	 */
	private int voteCode;
	/**
	 * 用户主键
	 */
	private int userCode;
	/**
	 * 作品主键
	 */
	private int petCode;
	/**
	 * 投票用户IP
	 */
	private int voteIp;
	/**
	 * 投票的时间
	 */
	private Timestamp voteTime;

	/**
	 * 得到投票的主键
	 * 
	 * @return 投票的主键
	 */
	public int getVoteCode() {
		return voteCode;
	}

	/**
	 * 设定投票的主键
	 * 
	 * @param voteCode
	 */

	public void setVoteCode(int voteCode) {
		this.voteCode = voteCode;
	}

	/**
	 * 得到用户的主键
	 * 
	 * @return 用户的主键userCode
	 */
	public Integer getUserCode() {
		return userCode;
	}

	/**
	 * 设定用户的主键
	 * 
	 * @param userCode
	 */
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}

	/**
	 * 得到作品的主键
	 * 
	 * @return 作品主键 petCode
	 */
	public Integer getPetCode() {
		return petCode;
	}

	/**
	 * 设定作品的主键
	 * 
	 * @param petCode
	 */
	public void setPetCode(Integer petCode) {
		this.petCode = petCode;
	}

	/**
	 * 得到投票用户的IP
	 * 
	 * @return 用户IP voteIP
	 */
	public Integer getVoteIp() {
		return voteIp;
	}

	/**
	 * 设定用户的IP
	 * 
	 * @param voteIp
	 */
	public void setVoteIp(Integer voteIp) {
		this.voteIp = voteIp;
	}

	/**
	 * 得到用户投票的时间
	 * 
	 * @return 投票时间 voteTime
	 */
	public Timestamp getVoteTime() {
		return voteTime;
	}

	/**
	 * 设定用户投票的时间
	 * 
	 * @param voteTime
	 */
	public void setVoteTime(Timestamp voteTime) {
		this.voteTime = voteTime;
	}

}
