/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei && zhouzhongkai
 * @联系方式：wanglei@gyyx.cn  zhouzhongkai@gyyx.cn
 * @创建时间： 2015年3月23日 
 * @版本号：
 * @本类主要用途描述：新手礼包日志Bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wd9yearnovicebag;

import java.util.Date;

public class HdSendPresentLogBean {
	private Integer code;
	//活动ID
	private Integer activityId;
	//活动名称
	private String source;
	//
	private Integer sourceCode;
	//用户名
	private String account;
	//游戏编号
	private Integer gameId;
	//服务器Id
	private Integer serverId;
	//服务器名称
	private String serverName;
	//礼包类型
	private String presentType;
	//礼包名称
	private String presentName;
	//领取时间
	private Date drawTime;
	//领取IP
	private String drawIp;
	// 错误ID
	private Integer errorCode;
	// 错误描述
	private String errorDesc;
	 private int available;
	 
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(Integer sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
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
	public Date getDrawTime() {
		return drawTime;
	}
	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}
	public String getDrawIp() {
		return drawIp;
	}
	public void setDrawIp(String drawIp) {
		this.drawIp = drawIp;
	}
	public HdSendPresentLogBean( Integer activityId,
			String source, Integer sourceCode, String account, Integer gameId,
			Integer serverId, String serverName, String presentType,
			String presentName, Date drawTime, String drawIp) {
		super();
		this.activityId = activityId;
		this.source = source;
		this.sourceCode = sourceCode;
		this.account = account;
		this.gameId = gameId;
		this.serverId = serverId;
		this.serverName = serverName;
		this.presentType = presentType;
		this.presentName = presentName;
		this.drawTime = drawTime;
		this.drawIp = drawIp;
	}
	public HdSendPresentLogBean(){}
	public HdSendPresentLogBean(Integer activityId, String account,
			Integer gameId) {
		super();
		this.activityId = activityId;
		this.account = account;
		this.gameId = gameId;
	}
	
}
