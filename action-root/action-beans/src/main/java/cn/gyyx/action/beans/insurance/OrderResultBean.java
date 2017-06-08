/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月15日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——我的订单业务返回类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

import java.util.List;

public class OrderResultBean {

	//条件查询的分页保单信息
	List<OrderBean> orderList;
	//分页总页数
	Integer count;
	//未支付
	Integer nopay;
	//支付成功
	Integer succpay;
	//支付失败
	Integer failpay;
	//有效保单 
	Integer efforder;
	//失效保单 
	Integer noefforder;
	//理赔处理中
	Integer reparation;
	//理赔成功 
	Integer succreparation;
	//理赔失败 
	Integer failreparation;
	
	public Integer getSuccpay() {
		return succpay;
	}
	public void setSuccpay(Integer succpay) {
		this.succpay = succpay;
	}
	public List<OrderBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderBean> orderList) {
		this.orderList = orderList;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getNopay() {
		return nopay;
	}
	public void setNopay(Integer nopay) {
		this.nopay = nopay;
	}
	public Integer getFailpay() {
		return failpay;
	}
	public void setFailpay(Integer failpay) {
		this.failpay = failpay;
	}
	public Integer getEfforder() {
		return efforder;
	}
	public void setEfforder(Integer efforder) {
		this.efforder = efforder;
	}
	public Integer getNoefforder() {
		return noefforder;
	}
	public void setNoefforder(Integer noefforder) {
		this.noefforder = noefforder;
	}
	public Integer getReparation() {
		return reparation;
	}
	public void setReparation(Integer reparation) {
		this.reparation = reparation;
	}
	public Integer getSuccreparation() {
		return succreparation;
	}
	public void setSuccreparation(Integer succreparation) {
		this.succreparation = succreparation;
	}
	public Integer getFailreparation() {
		return failreparation;
	}
	public void setFailreparation(Integer failreparation) {
		this.failreparation = failreparation;
	}
	public void setStatusCount(Integer nopay, Integer succpay, Integer failpay, Integer efforder,
			Integer noefforder, Integer reparation, Integer succreparation,
			Integer failreparation) {
		this.nopay = nopay;
		this.succpay = succpay;
		this.failpay = failpay;
		this.efforder = efforder;
		this.noefforder = noefforder;
		this.reparation = reparation;
		this.succreparation = succreparation;
		this.failreparation = failreparation;
	}

	public OrderResultBean(List<OrderBean> orderList, Integer count,
			Integer nopay, Integer succpay, Integer failpay, Integer efforder,
			Integer noefforder, Integer reparation, Integer succreparation,
			Integer failreparation) {
		super();
		this.orderList = orderList;
		this.count = count;
		this.nopay = nopay;
		this.succpay = succpay;
		this.failpay = failpay;
		this.efforder = efforder;
		this.noefforder = noefforder;
		this.reparation = reparation;
		this.succreparation = succreparation;
		this.failreparation = failreparation;
	}
	public OrderResultBean() {
		super();
	}
	
}
