package cn.gyyx.action.oa.noviceoa.ViewModel;

public class NoviceGiftGenerateViewBean {
	private Integer code;

	private Integer batchId;

	private Integer giftId;

	private Integer count;

	private Long beginDate;

	private Long endDate;

	private String channel;

	private Boolean isDelete;

	private String description;

	public NoviceGiftGenerateViewBean() {
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
		this.giftId=giftId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Long beginDate) {
		this.beginDate = beginDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
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
