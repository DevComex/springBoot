package cn.gyyx.action.beans.address.vo;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;

public class ActionPrizesAddressOfExchangeVO extends ActionPrizesAddressPO {
	
	private String itemsId;
	
	private String itemsName;
	
	private String itemsType;

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

	public String getItemsName() {
		return itemsName;
	}

	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}

	public String getItemsType() {
		return itemsType;
	}

	public void setItemsType(String itemsType) {
		this.itemsType = itemsType;
	}
}
