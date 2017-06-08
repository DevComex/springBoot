/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 上午10:20:05
 * @版本号：
 * @本类主要用途描述：用户抽奖资格实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.lottery;

public class QualificationBean {

	private int code;
	private int userCode;
	private int lotteryTime;
	private int actionCode;

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

	public int getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(int lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public QualificationBean(int userCode, int lotteryTime, int actionCode) {
		super();
		this.userCode = userCode;
		this.lotteryTime = lotteryTime;
		this.actionCode = actionCode;
		
	}

	public QualificationBean() {
		super();
	}

	@Override
	public String toString() {
		return "QualificationBean{" +
				"code=" + code +
				", userCode=" + userCode +
				", lotteryTime=" + lotteryTime +
				", actionCode=" + actionCode +
				'}';
	}
}
