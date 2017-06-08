package cn.gyyx.action.beans.exchange.po;

import java.util.Date;

public class ActionExchangePO {

	private Long code;
	
	private Integer activityId;
	
	private String activityType;
	
	private String itemsId;
	
	private String itemsName;
	
	private String itemsType;
	
	private Integer exchangeCondition;
	
	private Integer itemsQuantity;
	
	private String servicePrizesCode;
	
	private Integer itemsSort;
	
	private String remark;

	private Date modifyAt;
	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

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

	public Integer getExchangeCondition() {
		return exchangeCondition;
	}

	public void setExchangeCondition(Integer exchangeCondition) {
		this.exchangeCondition = exchangeCondition;
	}

	public Integer getItemsQuantity() {
		return itemsQuantity;
	}

	public void setItemsQuantity(Integer itemsQuantity) {
		this.itemsQuantity = itemsQuantity;
	}

	public String getServicePrizesCode() {
		return servicePrizesCode;
	}

	public void setServicePrizesCode(String servicePrizesCode) {
		this.servicePrizesCode = servicePrizesCode;
	}

	public Integer getItemsSort() {
		return itemsSort;
	}

	public void setItemsSort(Integer itemsSort) {
		this.itemsSort = itemsSort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}
	
}
