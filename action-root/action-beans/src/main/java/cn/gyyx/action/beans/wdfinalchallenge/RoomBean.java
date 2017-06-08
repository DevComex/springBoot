package cn.gyyx.action.beans.wdfinalchallenge;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class RoomBean {

	private int code;
	private String title;
	private boolean onAir;
	private int onlineCount;
	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date liveStartTime;
	private Map<String, String> livePullUrls;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isOnAir() {
		return onAir;
	}
	public void setOnAir(boolean onAir) {
		this.onAir = onAir;
	}
	public int getOnlineCount() {
		return onlineCount;
	}
	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}
	public Date getLiveStartTime() {
		return liveStartTime;
	}
	public void setLiveStartTime(Date liveStartTime) {
		this.liveStartTime = liveStartTime;
	}
	public Map<String, String> getLivePullUrls() {
		return livePullUrls;
	}
	public void setLivePullUrls(Map<String, String> livePullUrls) {
		this.livePullUrls = livePullUrls;
	}
}
