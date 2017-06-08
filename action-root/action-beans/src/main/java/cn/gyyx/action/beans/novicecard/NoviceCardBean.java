/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月9日 上午9:23:39
 * @版本号：
 * @本类主要用途描述：新手卡实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.novicecard;

import java.util.Date;


public class NoviceCardBean {

	//新手卡主键
	private int code;
	//
	private String cardNo;
	//新手卡是否使用
	private Integer isUse;
	//游戏ID
	private Integer gameId;
	//新手卡物品描述
	private String description;
	//新手卡物品ID
	private Integer itemId;
	//新手卡有效起始时间
	private Date startTime;
	//新手卡有效结束时间
	private Date endTime;
	//新手卡是否受有效时间限制
	private boolean isLimit;
	//批次编号
	private Integer batchNo;
	//新手卡类别编号
	private Integer typeId;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	public boolean isLimit() {
		return isLimit;
	}
	public void setLimit(boolean isLimit) {
		this.isLimit = isLimit;
	}
	public Integer getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
}
