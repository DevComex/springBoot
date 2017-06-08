package cn.gyyx.action.beans.lottery;

import java.util.Date;

public class ContrParmBean {
	private int code; //活动code
	private String activityName;//活动名称
	private Date activityStart;//活动开始时间
	private Date activityEnd;//活动结束时间
	private boolean isDelete;//是否删除
	private int gameId;   //游戏Code
	private String paras;//参数
	private String activityLink; //链接


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getActivityStart() {
		return activityStart;
	}

	public void setActivityStart(Date activityStart) {
		this.activityStart = activityStart;
	}

	public Date getActivityEnd() {
		return activityEnd;
	}

	public void setActivityEnd(Date activityEnd) {
		this.activityEnd = activityEnd;
	}


	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getParas() {
		return paras;
	}

	public void setParas(String paras) {
		this.paras = paras;
	}

	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}
}
