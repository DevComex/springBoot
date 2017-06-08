/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——理赔表实体类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

import java.util.Date;

public class ReparationBean {

	//主键
	int code;
	//订单号
	String orderNum;
	//区组
	String serverGroup;
	//订单类型
	String type;
	//保费
	float protection;
	//保单周期
	int circle;
	//理赔金额（为保险公司在理赔成功后返回的）
	float reparation;
	//理赔说明（用户）
	String other;
	//理赔订单号
	String reparationOrderNum;
	//理赔结果说明（结果说明）
	String reparationResult;
	//理赔下单时间
	Date reparationOrderTime;
	//账号
	String account;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getReparationOrderTime() {
		return reparationOrderTime;
	}
	public void setReparationOrderTime(Date reparationOrderTime) {
		this.reparationOrderTime = reparationOrderTime;
	}
	public String getReparationResult() {
		return reparationResult;
	}
	public void setReparationResult(String reparationResult) {
		this.reparationResult = reparationResult;
	}
	public String getReparationOrderNum() {
		return reparationOrderNum;
	}
	public void setReparationOrderNum(String reparationOrderNum) {
		this.reparationOrderNum = reparationOrderNum;
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
	public String getServerGroup() {
		return serverGroup;
	}
	public void setServerGroup(String serverGroup) {
		this.serverGroup = serverGroup;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getProtection() {
		return protection;
	}
	public void setProtection(float protection) {
		this.protection = protection;
	}
	public int getCircle() {
		return circle;
	}
	public void setCircle(int circle) {
		this.circle = circle;
	}
	public float getReparation() {
		return reparation;
	}
	public void setReparation(float reparation) {
		this.reparation = reparation;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public ReparationBean( String orderNum, String serverGroup,
			String type, float protection, int circle,
			float reparation, String other,Date reparationOrderTime) {
		super();
		this.orderNum = orderNum;
		this.serverGroup = serverGroup;
		this.type = type;
		this.protection = protection;
		this.circle = circle;
		this.reparation = reparation;
		this.other = other;
		this.reparationOrderTime = reparationOrderTime;
	}
	

	public ReparationBean(String orderNum, String serverGroup, String type,
			float protection, int circle, float reparation, String other,
			String reparationOrderNum, String reparationResult,
			Date reparationOrderTime) {
		super();
		this.orderNum = orderNum;
		this.serverGroup = serverGroup;
		this.type = type;
		this.protection = protection;
		this.circle = circle;
		this.reparation = reparation;
		this.other = other;
		this.reparationOrderNum = reparationOrderNum;
		this.reparationResult = reparationResult;
		this.reparationOrderTime = reparationOrderTime;
	}
	public ReparationBean() {
		super();
	}
	@Override
	public String toString() {
		return "ReparationBean [code=" + code + ", orderNum=" + orderNum
				+ ", serverGroup=" + serverGroup + ", type=" + type
				+ ", protection=" + protection + ", circle=" + circle
				+ ", reparation=" + reparation + ", other=" + other
				+ ", reparationOrderNum=" + reparationOrderNum
				+ ", reparationResult=" + reparationResult
				+ ", reparationOrderTime=" + reparationOrderTime + ", account="
				+ account + "]";
	}
}
