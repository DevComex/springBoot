package cn.gyyx.action.beans.address.vo;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;

public class ActionPrizesAddressOfLotteryVO extends ActionPrizesAddressPO {
	
	private String prizeType;
	
	private Integer prizeCode;
	
	private String prizeName;
	
	private Integer prizeNum;
	
	private String cardCode;

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public Integer getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(Integer prizeCode) {
		this.prizeCode = prizeCode;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getPrizeNum() {
		return prizeNum;
	}

	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
}
