/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午3:20:02
 * 版本号：v1.0
 * 本类主要用途叙述：用户的中奖信息bean
 */



package cn.gyyx.action.beans.lottery;

import java.util.Date;

public class UserInfoBean {
	/**
	 * 主键
	 */
	private  int code;
	/**
	 * 活动主键
	 */
	private int actionCode;
	/**
	 * 资源信息
	 */
	private String source;
	/**
	 * 信息主键
	 */
	private int sourceCode;
	/**
	 * 账户
	 */
	private String  account;
	/**
	 * 游戏主键
	 */
	private int gameCode;
	/**
	 * 服务器主键
	 */
	private int serverCode;
	/**
	 * 服务器信息
	 */
	private String serverName;
	/**
	 * 奖品种类
	 */
	private String presentType;
	/**
	 * 奖品名称
	 */
	private String presentName;
	/**
	 * 中奖时间
	 */
    private Date   time;
    
    /**
	 * 中奖时间
	 */
    
    private String cardCode;
    
    private String timeStr;
    /**
     * 中奖ip
     */
    private 	String ip;
    private int available;
    
    /**
     * 扩展列 yangteng
     */
    private String remark;
    
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getGameCode() {
		return gameCode;
	}
	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}
	public int getServerCode() {
		return serverCode;
	}
	public void setServerCode(int serverCode) {
		this.serverCode = serverCode;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getPresentType() {
		return presentType;
	}
	public void setPresentType(String presentType) {
		this.presentType = presentType;
	}
	public String getPresentName() {
		return presentName;
	}
	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    
}
