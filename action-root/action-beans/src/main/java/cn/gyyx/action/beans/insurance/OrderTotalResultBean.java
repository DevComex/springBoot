/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——订单查询总计Bean
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

public class OrderTotalResultBean {
	//保费总计
	Float protectionSUM;
	//投保订单个数总计
	Float reparationSUM;
	//理赔金额总计
	Integer orderCount;
	//理赔订单个数总计
	Integer reparationCount;
	//理赔失败订单个数总计
	Integer failreparationCount;
	public Float getProtectionSUM() {
		return protectionSUM;
	}
	public void setProtectionSUM(Float protectionSUM) {
		this.protectionSUM = protectionSUM;
	}
	public Float getReparationSUM() {
		return reparationSUM;
	}
	public void setReparationSUM(Float reparationSUM) {
		this.reparationSUM = reparationSUM;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Integer getReparationCount() {
		return reparationCount;
	}
	public void setReparationCount(Integer reparationCount) {
		this.reparationCount = reparationCount;
	}
	public Integer getFailreparationCount() {
		return failreparationCount;
	}
	public void setFailreparationCount(Integer failreparationCount) {
		this.failreparationCount = failreparationCount;
	}

}
