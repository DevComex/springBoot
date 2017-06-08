/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——订单表实体类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

import java.util.Date;

public class OrderBean {

	//主键
	int code;
	//订单号
	String orderNum;
	//流水号
	String gyyxOrderNum;
	//姓名
	String name;
	//手机号
	String phone;
	//订单类型
	String orderType;
	//图片地址
	String pictureUrl;
	//账号
	String account;
	//区组
	String serverGroup;
	//名称
	String serverName;
	//等级
	Integer level;
	//保费
	float protection;
	//保单周期
	Integer circle;
	//下单时间
	Date creatTime;
	//下单时间字符串
	String creatTimeStr;
	//角色Id
	String roleId;
	
	
	//支付时间
	Date payTime;
	//有效时间
	Date validTime;
	//有效时间字符串
	String validTimeStr;
	//订单状态
	String status;
	//订单显示
	String statusShow;
	//理赔金额
	float reparation;
	//IP
	String ip;
	//游戏名
	String gameName;
	//角色名
	String roleName;
	
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getStatusShow() {
		return statusShow;
	}
	public void setStatusShow(String statusShow) {
		this.statusShow = statusShow;
	}
	public String getCreatTimeStr() {
		return creatTimeStr;
	}
	public void setCreatTimeStr(String creatTimeStr) {
		this.creatTimeStr = creatTimeStr;
	}
	public String getValidTimeStr() {
		return validTimeStr;
	}
	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getGyyxOrderNum() {
		return gyyxOrderNum;
	}
	public void setGyyxOrderNum(String gyyxOrderNum) {
		this.gyyxOrderNum = gyyxOrderNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getServerGroup() {
		return serverGroup;
	}
	public void setServerGroup(String serverGroup) {
		this.serverGroup = serverGroup;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public float getProtection() {
		return protection;
	}
	public void setProtection(float protection) {
		this.protection = protection;
	}
	public Integer getCircle() {
		return circle;
	}
	public void setCircle(Integer circle) {
		this.circle = circle;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getValidTime() {
		return validTime;
	}
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getReparation() {
		return reparation;
	}
	public void setReparation(float reparation) {
		this.reparation = reparation;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public OrderBean(int code, String orderNum, String gyyxOrderNum,
			String name, String phone, String orderType, String pictureUrl,
			String account, String serverGroup, String serverName,
			Integer level, float protection, Integer circle, Date creatTime,
			Date payTime, Date validTime, String status, float reparation,
			String ip, String roleName, String gameName) {
		this.code = code;
		this.orderNum = orderNum;
		this.gyyxOrderNum = gyyxOrderNum;
		this.name = name;
		this.phone = phone;
		this.orderType = orderType;
		this.pictureUrl = pictureUrl;
		this.account = account;
		this.serverGroup = serverGroup;
		this.serverName = serverName;
		this.level = level;
		this.protection = protection;
		this.circle = circle;
		this.creatTime = creatTime;
		this.payTime = payTime;
		this.validTime = validTime;
		this.status = status;
		this.reparation = reparation;
		this.ip = ip;
		this.roleName = roleName;
		this.gameName = gameName;
	}
	public OrderBean() {
		super();
	}
	@Override
	public String toString() {
		return "OrderBean [code=" + code + ", orderNum=" + orderNum
				+ ", gyyxOrderNum=" + gyyxOrderNum + ", name=" + name
				+ ", phone=" + phone + ", orderType=" + orderType
				+ ", pictureUrl=" + pictureUrl + ", account=" + account
				+ ", serverGroup=" + serverGroup + ", serverName=" + serverName
				+ ", level=" + level + ", protection=" + protection
				+ ", circle=" + circle + ", creatTime=" + creatTime
				+ ", creatTimeStr=" + creatTimeStr + ", roleId=" + roleId
				+ ", payTime=" + payTime + ", validTime=" + validTime
				+ ", validTimeStr=" + validTimeStr + ", status=" + status
				+ ", statusShow=" + statusShow + ", reparation=" + reparation
				+ ", ip=" + ip + ", gameName=" + gameName + ", roleName="
				+ roleName + "]";
	}

	
	
}
