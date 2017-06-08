package cn.gyyx.action.beans.wd9yeardatavideo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PresentLogBean {
	private int code; 
	private int activityId;
	private String account;    //账号
	private int serverID=0;    //服务器ID
	private String source;    //活动名称
	private int sourceCode=0;
	private int gameId=0;
	private String presentType;
	private String serverName="";    //服务器名字
	private String presentName;    //称号名称
	private Date drawTime;    //用户获得称号时间
	private String drawTimeStr="0001-01-01";    
	private String drawIP;    //用户登录IP
	private String fraction;
	private int available;
	public PresentLogBean() {
	}
	public PresentLogBean(int activityId, String account
			,String source,int sourceCode
			,String presentType, String presentName
			,String drawTimeStr
			,String drawIP) {
		this.activityId = activityId;
		this.account = account;
		this.source = source;
		this.sourceCode = sourceCode;
		this.presentType = presentType;
		this.presentName = presentName;
		this.drawTimeStr = drawTimeStr;
		this.drawIP = drawIP;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getServerID() {
		return serverID;
	}
	public void setServerID(int serverID) {
		this.serverID = serverID;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getPresentName() {
		return presentName;
	}
	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}
	public Date getDrawTime() {
		return drawTime;
	}
	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getPresentType() {
		return presentType;
	}
	public void setPresentType(String presentType) {
		this.presentType = presentType;
	}
	public String getDrawTimeStr() {
		return drawTimeStr;
	}
	public void setDrawTimeStr(String drawTimeStr) {
		this.drawTimeStr = drawTimeStr;
	}
	public String getDrawIP() {
		return drawIP;
	}
	public void setDrawIP(String drawIP) {
		this.drawIP = drawIP;
	}
	public String getFraction() {
		return fraction;
	}
	public void setFraction(String fraction) {
		this.fraction = fraction;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(int sourceCode) {
		this.sourceCode = sourceCode;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	/**
	 * 格式化时间
	 * @param date 待格式化的时间
	 * @return 格式化好的时间
	 */
	public String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		return format.format(date);
	}
	/**
	 * 获取当天时间的10点
	 * @return 返回Date类型
	 */
	public Date getTodayTen() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY,10);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取当天时间的10点
	 * @return 返回String类型
	 */
	public String getTodayTenStr() {
		return formatDate(getTodayTen());
	}
}
