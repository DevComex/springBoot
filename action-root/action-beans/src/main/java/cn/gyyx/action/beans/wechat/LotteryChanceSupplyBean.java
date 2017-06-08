/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:奖品补充Bean
 ************************************************/
package cn.gyyx.action.beans.wechat;

import java.util.Date;

public class LotteryChanceSupplyBean {
	// 主键
	private int code;
	// 概率编号
	private int chanceCode;
	// 补充数量
	private int supplyNum;
	//
	private int actionCode;
	// 补充时间
	private Date supplyDate;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getChanceCode() {
		return chanceCode;
	}

	public void setChanceCode(int chanceCode) {
		this.chanceCode = chanceCode;
	}

	public int getSupplyNum() {
		return supplyNum;
	}

	public void setSupplyNum(int supplyNum) {
		this.supplyNum = supplyNum;
	}

	public Date getSupplyDate() {
		return supplyDate;
	}

	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public LotteryChanceSupplyBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LotteryChanceSupplyBean(int chanceCode, int supplyNum,
			int actionCode, Date supplyDate) {
		super();
		this.chanceCode = chanceCode;
		this.supplyNum = supplyNum;
		this.actionCode = actionCode;
		this.supplyDate = supplyDate;
	}

	@Override
	public String toString() {
		return "LotteryChanceSupplyBean [chanceCode=" + chanceCode
				+ ", supplyNum=" + supplyNum + ", actionCode=" + actionCode
				+ ", supplyDate=" + supplyDate + "]";
	}

}
