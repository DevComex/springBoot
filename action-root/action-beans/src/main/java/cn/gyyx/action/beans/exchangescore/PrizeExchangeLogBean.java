/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日上午11:48:29
 * 版本号：v1.0
 * 本类主要用途叙述：奖品兑换日志
 */

package cn.gyyx.action.beans.exchangescore;

import java.util.Date;

public class PrizeExchangeLogBean {
	// 主键
	private int code;
	// 用户主键
	private int userCode;
	// 用户账户名
	private String userName;
	// 奖品主键
	private int prizeCode;
	// 兑换前积分
	private int beforeScore;
	// 兑换后积分
	private int afterScore;
	// 兑换时间
	private Date creatTime;
	// 兑换奖品名称
	private String prizeName;
	// 住址
	private String address;
	// 电话
	private String phone;
	// 账户
	private String account;
	//活动号
	private int actionCode;
	

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public int getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}

	public int getBeforeScore() {
		return beforeScore;
	}

	public void setBeforeScore(int beforeScore) {
		this.beforeScore = beforeScore;
	}

	public int getAfterScore() {
		return afterScore;
	}

	public void setAfterScore(int afterScore) {
		this.afterScore = afterScore;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "PrizeExchangeLog [code=" + code + ", userCode=" + userCode
				+ ", userName=" + userName + ", prizeCode=" + prizeCode
				+ ", beforeScore=" + beforeScore + ", afterScore=" + afterScore
				+ ", creatTime=" + creatTime + ", prizeName=" + prizeName
				+ ", address=" + address + ", phone=" + phone + "]";
	}

}
