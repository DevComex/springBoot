package cn.gyyx.action.beans.config.po;

public class ActionPrizePO {
	
	private Integer code;
	
	private String chinese;
	
	private String english;
	
	private Integer actionCode;
	
	private String isReal;
	
	private Integer num;
	
	private Integer isAvailable;

	private String servicePrizesCode;
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}
	
	public void setAction_code(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getServicePrizesCode() {
		return servicePrizesCode;
	}

	public void setServicePrizesCode(String servicePrizesCode) {
		this.servicePrizesCode = servicePrizesCode;
	}
}
