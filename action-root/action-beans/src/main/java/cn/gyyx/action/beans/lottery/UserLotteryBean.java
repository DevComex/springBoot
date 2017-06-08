package cn.gyyx.action.beans.lottery;

public class UserLotteryBean {
	private int code;
	//用户code
	private int userCode;
	//奖品code
	private int prizeCode;
	//奖品中文名
	private String prizeChinese;
	//奖品英文名
	private String prizeEnglish;
	//前台需配置的数
	private int configCode;
	//是否虚拟
	private String isReal;
	//卡号
	private String cardCode;
	//图片链接
	private String url;
	//日志code
	private int presentLogCode;
	
	//剩余抽奖次数
	private int lotterytime;
	
	//是否有效奖品
	private int isAvailable;
	public int getLotterytime() {
		return lotterytime;
	}
	public void setLotterytime(int lotterytime) {
		this.lotterytime = lotterytime;
	}
	public int getPresentLogCode() {
		return presentLogCode;
	}
	public void setPresentLogCode(int presentLogCode) {
		this.presentLogCode = presentLogCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getIsReal() {
		return isReal;
	}
	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public int getPrizeCode() {
		return prizeCode;
	}
	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}
	public String getPrizeChinese() {
		return prizeChinese;
	}
	public void setPrizeChinese(String prizeChinese) {
		this.prizeChinese = prizeChinese;
	}
	public String getPrizeEnglish() {
		return prizeEnglish;
	}
	public void setPrizeEnglish(String prizeEnglish) {
		this.prizeEnglish = prizeEnglish;
	}
	public int getConfigCode() {
		return configCode;
	}
	public void setConfigCode(int configCode) {
		this.configCode = configCode;
	}
	
	public int getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	public int getCode() {
		return code;
	}
	
	
	public void setCode(int code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "UserLotteryBean [code=" + code + ", userCode=" + userCode + ", prizeCode=" + prizeCode
				+ ", prizeChinese=" + prizeChinese + ", prizeEnglish=" + prizeEnglish + ", configCode=" + configCode
				+ ", isReal=" + isReal + ", cardCode=" + cardCode + ", url=" + url + ", presentLogCode="
				+ presentLogCode + ", lotterytime=" + lotterytime + ", isAvailable=" + isAvailable + "]";
	}
}
