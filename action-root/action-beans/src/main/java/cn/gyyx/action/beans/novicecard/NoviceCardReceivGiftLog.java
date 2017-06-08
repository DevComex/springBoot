/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月17日 下午3:54:24 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 激活日志实体类
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.novicecard;

import java.util.Date;

public class NoviceCardReceivGiftLog {
	private int code;
	private int userId;
	private String accountName;
	private int gameId;
	private int serverId;
	private String serverName;
	private int activityId;
	private String activityName;
	private int batchNo;
	private Date createDate;
	private int flag;//0 pc 1 移动
	private String cardNo;//卡号
	private String responseResult;//发奖到游戏结果
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}
	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	/**
	 * @return the serverId
	 */
	public int getServerId() {
		return serverId;
	}
	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	/**
	 * @return the activityId
	 */
	public int getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}
	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	/**
	 * @return the batchNo
	 */
	public int getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the responseResult
	 */
	public String getResponseResult() {
		return responseResult;
	}
	/**
	 * @param responseResult the responseResult to set
	 */
	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}

}
