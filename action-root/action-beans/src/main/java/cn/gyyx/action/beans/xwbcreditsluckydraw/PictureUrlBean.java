package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class PictureUrlBean {
	private int code;
	private int prizeCode;
	private String pictureUrl;
	private int prizeFlag;
	private String prizeYard;
	// 中文名
	private String chinese;
	// 奖品英文名
	private String english;
	// 奖品的code
	private int actionCode;
	// 是否是虚拟
	private String isReal;
	// 与前台互动的代号
	private int num;
	private double probability;
	//邮件内容
	private String mailContent;

	/**
	 * 限定个数
	 */
	private int number;

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPrizeYard() {
		return prizeYard;
	}

	public void setPrizeYard(String prizeYard) {
		this.prizeYard = prizeYard;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public int getPrizeFlag() {
		return prizeFlag;
	}

	public void setPrizeFlag(int prizeFlag) {
		this.prizeFlag = prizeFlag;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
}
