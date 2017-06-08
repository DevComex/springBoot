package cn.gyyx.action.beans.oa.lhzs.activityCode;

public class InsertActivtionCodeBean {
	private int num;
	private int channel;
	private String channelStr;
	private String cardType;
	private int status;
	private int gameId;
	private String desc;
	private int actionCode;
	private String errorMes;
	public InsertActivtionCodeBean() {

	}
	public InsertActivtionCodeBean(int num, int channel, String channelStr,String cardType, int status, int fameId, String desc,
			int actionCode, String errorMes) {
		this.num = num;
		this.channel = channel;
		this.channelStr = channelStr;
		this.cardType = cardType;
		this.status = status;
		this.gameId = fameId;
		this.desc = desc;
		this.actionCode = actionCode;
		this.errorMes = errorMes;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getChannelStr() {
		return channelStr;
	}
	public void setChannelStr(String channelStr) {
		this.channelStr = channelStr;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int fameId) {
		this.gameId = fameId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public String getErrorMes() {
		return errorMes;
	}
	public void setErrorMes(String errorMes) {
		this.errorMes = errorMes;
	}
	
}
