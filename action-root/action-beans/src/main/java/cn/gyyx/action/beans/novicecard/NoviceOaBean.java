package cn.gyyx.action.beans.novicecard;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月3日 下午3:42:26
 * 描        述：
 */
public class NoviceOaBean {
	private int code;
	
	private String account;
	private String serverName;
	private String giftPackageName;
	private String cardNo;
	private String type;//媒体还是官网
	private String receiveTime;
	private String sendToGameTime;
	private String sendToGameResponse;
	
	private int serverId;
	private int activityId;
	private int batchNo;
	private String areaName;
	private int areaCode;
	private String hdType;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getGiftPackageName() {
		return giftPackageName;
	}
	public void setGiftPackageName(String giftPackageName) {
		this.giftPackageName = giftPackageName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getSendToGameTime() {
		return sendToGameTime;
	}
	public void setSendToGameTime(String sendToGameTime) {
		this.sendToGameTime = sendToGameTime;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSendToGameResponse() {
		return sendToGameResponse;
	}
	public void setSendToGameResponse(String sendToGameResponse) {
		this.sendToGameResponse = sendToGameResponse;
	}
	public String getHdType() {
		return hdType;
	}
	public void setHdType(String hdType) {
		this.hdType = hdType;
	}
}
