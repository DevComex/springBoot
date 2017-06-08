/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月15日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——我的保单业务参数类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

import java.util.Date;

public class MyOrderParameterBean {
	
	//Cookie中获取的用户账户
	String account;
	//保单状态
	String status;
	//保单查询条件：起始时间
	Date startTime;
	//保单查询条件：结束时间
	Date endTime;
	//保单查询条件：起始时间
	String startTimeStr;
	//保单查询条件：结束时间
	String endTimeStr;
	//查询分页的页数
	int pageNum;
	//分页的每页的显示数
	int num;
	//订单号
	String orderNum;
	//游戏名
	String gameName;

	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setParameters(String account, String status, Date startTime,
			Date endTime, int pageNum){
		this.account = account;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pageNum = pageNum;
	}
	public MyOrderParameterBean(String account, String status, Date startTime,
			Date endTime, int pageNum) {
		super();
		this.account = account;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pageNum = pageNum;
	}
	public void setParameters(String account, String status, Date startTime,
			Date endTime, int pageNum, String orderNum){
		this.account = account;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pageNum = pageNum;
		this.orderNum = orderNum;
	}
	public MyOrderParameterBean(String account, String status, Date startTime,
			Date endTime, int pageNum, int num, String orderNum) {
		super();
		this.account = account;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pageNum = pageNum;
		this.num = num;
		this.orderNum = orderNum;
	}
	public MyOrderParameterBean() {
		super();
	}
	
}
