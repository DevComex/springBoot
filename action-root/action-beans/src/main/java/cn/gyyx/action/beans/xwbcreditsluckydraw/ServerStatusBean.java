/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2016年-02月-18日
       Note:服务器关闭状态Bean
 ************************************************/
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class ServerStatusBean {
	// 主键
	private int code;
	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;
	// 备注
	private String remarks;
	// 状态
	private boolean closeStatus;

	public ServerStatusBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServerStatusBean(Date startTime, Date endTime, String remarks,
			boolean closeStatus) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.remarks = remarks;
		this.closeStatus = closeStatus;
	}
	
	
	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(boolean closeStatus) {
		this.closeStatus = closeStatus;
	}

	@Override
	public String toString() {
		return "ServerStatusBean [code=" + code + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", remarks=" + remarks
				+ ", closeStatus=" + closeStatus + "]";
	}

}
