/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月4日下午6:25:37
 * 版本号：v1.0
 * 本类主要用途叙述：名次与奖品相对应
 */



package cn.gyyx.action.beans.lottery;

public class OrderAndPrizeBean {
	//主键
	private int code;
	//排名
	private int order;
	//奖品code
	private int prizeCode;
	private int actionCode;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getPrizeCode() {
		return prizeCode;
	}
	public void setPrizeCode(int prizeCode) {
		this.prizeCode = prizeCode;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	

}
