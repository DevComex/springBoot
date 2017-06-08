package cn.gyyx.action.beans.config.po;

import java.math.BigDecimal;

public class ActionLotteryChancePO {

	private Integer code;
	
	private Integer prizeCode;
	
	private Integer actionCode;
	
	private BigDecimal probability;
	
	private Integer number;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(Integer prizeCode) {
		this.prizeCode = prizeCode;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
