package cn.gyyx.action.beans.wd9yeardatavideo;

import java.util.Date;


import cn.gyyx.action.beans.novicecard.ServerInfoBean;

public class ParaDataVideoSendGift {
	private int gameID;
	private String account;
	private Date presentStartTime;
	private String presentStartTimeStr;
	private String virtualItemCode;
	private ServerInfoBean serverInfoBean;
	public ParaDataVideoSendGift(int gameID,String account,Date presentStartTime,ServerInfoBean serverInfoBean){
		this.gameID = gameID;
		this.account = account;
		this.presentStartTime = presentStartTime;
		this.serverInfoBean = serverInfoBean;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getPresentStartTime() {
		return presentStartTime;
	}
	public void setPresentStartTime(Date presentStartTime) {
		this.presentStartTime = presentStartTime;
	}
	public String getPresentStartTimeStr() {
		return presentStartTimeStr;
	}
	public void setPresentStartTimeStr(String presentStartTimeStr) {
		this.presentStartTimeStr = presentStartTimeStr;
	}
	public String getVirtualItemCode() {
		return virtualItemCode;
	}
	public void setVirtualItemCode(String virtualItemCode) {
		this.virtualItemCode = virtualItemCode;
	}
	public ServerInfoBean getServerInfoBean() {
		return serverInfoBean;
	}
	public void setServerInfoBean(ServerInfoBean serverInfoBean) {
		this.serverInfoBean = serverInfoBean;
	}
}
