/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年7月18日下午3:24:25
 * 版本号：v1.0
 * 本类主要用途叙述：投保成功的返回
 */



package cn.gyyx.action.beans.insurance;

public class TempOrderUrl {
	private String url;
	private String orderNum;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public TempOrderUrl(String url, String orderNum) {
		this.url = url;
		this.orderNum = orderNum;
	}
	

}
