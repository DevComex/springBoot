package cn.gyyx.action.beans.config.po;

import java.util.Date;

public class ActionConfigPO {
	private Integer code;

	private String hdName;
	
	private Date hdStart;
	
	private Date hdEnd;
	
	private Boolean isDelete;
	
	private Integer gameId;
	
	private String paras;
	
	private String hdLink;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getHdName() {
		return hdName;
	}

	public void setHdName(String hdName) {
		this.hdName = hdName;
	}

	public Date getHdStart() {
		return hdStart;
	}

	public void setHdStart(Date hdStart) {
		this.hdStart = hdStart;
	}

	public Date getHdEnd() {
		return hdEnd;
	}

	public void setHdEnd(Date hdEnd) {
		this.hdEnd = hdEnd;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
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
}
