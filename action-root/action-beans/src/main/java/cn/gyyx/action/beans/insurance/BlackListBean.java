/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——黑名单实体类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.insurance;

public class BlackListBean {
	//主键
	int code;
	//订单号
	String orderCode;
	//姓名
	String name;
	//手机号
	String phone;
	//账号
	String account;
	//登陆IP
	String ip;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public BlackListBean(String orderCode, String name, String phone,
			String account, String ip) {
		super();
		this.orderCode = orderCode;
		this.name = name;
		this.phone = phone;
		this.account = account;
		this.ip = ip;

	}
	public BlackListBean() {
		super();
	}
	@Override
	public String toString() {
		return "BlackListBean [code=" + code + ", orderCode=" + orderCode
				+ ", name=" + name + ", phone=" + phone + ", account="
				+ account + ", ip=" + ip + "]";
	}
	
}
