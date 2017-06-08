package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

public class NoviceGiftGenerateBean {

	private Integer code;

	private Integer batchId;

	private Integer giftId;

	private Integer count;

	private Date beginDate;

	private Date endDate;

	private String channel;

	private Boolean isDelete;

	private String description;

	public NoviceGiftGenerateBean(Integer code, Integer batchId, Integer giftId, Integer count, Date beginDate,
			Date endDate, String channel, Boolean isDelete, String description) {
		this.code = code;
		this.batchId = batchId;
		this.giftId = giftId;
		this.count = count;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.channel = channel;
		this.isDelete = isDelete;
		this.description = description;
	}

	public NoviceGiftGenerateBean() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId =giftId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
}
