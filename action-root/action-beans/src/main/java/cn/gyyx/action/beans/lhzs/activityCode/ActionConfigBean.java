package cn.gyyx.action.beans.lhzs.activityCode;

import java.util.Date;

public class ActionConfigBean {
	private int code=0;
	private String hdName;
	private String hdStart;    //开始时间
	private Date hdStartD;
	private String hdEnd;    //结束时间
	private Date hdEndD;
	private String isDelete;
	private String gameId;
	private String paras;
	private String hdLink;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getHdName() {
		return hdName;
	}
	public void setHdName(String hdName) {
		this.hdName = hdName;
	}
	public String getHdStart() {
		return hdStart;
	}
	public void setHdStart(String hdStart) {
		this.hdStart = hdStart;
	}
	public String getHdEnd() {
		return hdEnd;
	}
	public void setHdEnd(String hdEnd) {
		this.hdEnd = hdEnd;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getParas() {
		return paras;
	}
	public void setParas(String paras) {
		this.paras = paras;
	}
	public String getHdLink() {
		return hdLink;
	}
	public void setHdLink(String hdLink) {
		this.hdLink = hdLink;
	}
	
	public Date getHdStartD() {
		return hdStartD;
	}
	public void setHdStartD(Date hdStartD) {
		this.hdStartD = hdStartD;
	}
	public Date getHdEndD() {
		return hdEndD;
	}
	public void setHdEndD(Date hdEndD) {
		this.hdEndD = hdEndD;
	}
	public boolean isNull(){
		boolean result = true;
		if(null == this.hdName|| "".equals(this.hdName)){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
}
