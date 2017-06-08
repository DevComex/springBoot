package cn.gyyx.action.beans.wdpkforecast;

import java.util.Date;

public class WdVoteOpportunityBean {
    private int code;
    private int actionCode;
    private int userId;
    private int type;
    private int votetimes;
    private Date createAt ;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getVotetimes() {
		return votetimes;
	}
	public void setVotetimes(int votetimes) {
		this.votetimes = votetimes;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
    
    
}
