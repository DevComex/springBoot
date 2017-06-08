package cn.gyyx.action.beans.noviceoa;

public class NoviceGiftPutBean {
	private Integer code;

	private String putName;

	private Integer putType;

	private String putUrl;

	private String description;

	public NoviceGiftPutBean(Integer code, String putName, Integer putType, String putUrl, String description) {
		this.code = code;
		this.putName = putName;
		this.putType = putType;
		this.putUrl = putUrl;
		this.description = description;
	}

	public NoviceGiftPutBean() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getPutName() {
		return putName;
	}

	public void setPutName(String putName) {
		this.putName = putName;
	}

	public Integer getPutType() {
		return putType;
	}

	public void setPutType(Integer putType) {
		this.putType = putType;
	}

	public String getPutUrl() {
		return putUrl;
	}

	public void setPutUrl(String putUrl) {
		this.putUrl = putUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}