/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月11日 上午10:28:44
 * @版本号：
 * @本类主要用途描述：新手卡发送日志实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.novicecard;

import java.util.Date;

public class NoviceCardReceiveLogBean {

	// 主键编号
	private Integer code;
	// 卡号
	private String cardNo;
	// 用户ID
	private Integer userId;
	// 用户账号
	private String account;
	// 游戏ID
	private Integer gameId;
	// 服务器ID
	private Integer serverId;
	// 物品
	private String virtualItem;
	// 错误ID
	private Integer errorCode;
	// 错误描述
	private String errorDesc;
	// 发放时间
	private Date createTime;
	// 新手卡类型编号
	private Integer typeId;
	// 手机号码
	private String cellPhone;
	//
	private String roleName;
	// 批次号
	private Integer batchNo;
	// 添加人：王雷 （查询用户是否领取过本批次号的新手卡时用到）
	private String typeName;
	// 检查类型
	private String checkType;

	public NoviceCardReceiveLogBean() {

	}

	public NoviceCardReceiveLogBean(Integer userId, String account,
			String cardNo, Integer gameId, Integer serverId,
			String virtualItem, Integer typeId, String cellPhone,
			String checkType) {
		this.userId = userId;
		this.account = account;
		this.cardNo = cardNo;
		this.gameId = gameId;
		this.serverId = serverId;
		this.virtualItem = virtualItem;
		this.typeId = typeId;
		this.cellPhone = cellPhone;
		this.checkType = checkType;
	}

	public Integer getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getVirtualItem() {
		return virtualItem;
	}

	public void setVirtualItem(String virtualItem) {
		this.virtualItem = virtualItem;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
