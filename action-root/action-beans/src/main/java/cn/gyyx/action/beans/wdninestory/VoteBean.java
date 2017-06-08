/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wdninestory
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月11日 上午10:31:58
 * @版本号：
 * @本类主要用途描述：投票记录实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdninestory;

import java.util.Date;

public class VoteBean {
	private int code;
	private int userCode;
	private String userName;
	private int writingCode;
	private String userIp;
	private Date voteTime;
	private String type;

	public VoteBean() {

	}

	public VoteBean(int userCode, int writingCode, String userName,
			String userIp, String type, Date voteTime) {
		this.userCode = userCode;
		this.writingCode = writingCode;
		this.userName = userName;
		this.userIp = userIp;
		this.type = type;
		this.voteTime = voteTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getWritingCode() {
		return writingCode;
	}

	public void setWritingCode(int writingCode) {
		this.writingCode = writingCode;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
