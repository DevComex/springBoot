/*************************************************
       Copyright ©, 2015, GY Game
       Author: 王雷
       Created: 2015年-12月-21日
       Note: 微信端地址信息实体类
************************************************/
package cn.gyyx.action.beans.wechat;

public class WeiXinAddressBean {
	
	//主键
	private int code;
	//微信用户标示
	private String openId;
	//收货人（收货地址填写的）
	private String userName;
	//收货人电话（收货地址填写的）
	private String userPhone;
	//收货地址（收货地址填写的）
	private String userAddress;
	//活动编号
	private int  actionCode;
	//账号
	private  String userAccount;

	public String getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public WeiXinAddressBean() {
		super();
	}
	public WeiXinAddressBean(int code, String openId, String userName,
			String userPhone, String userAddress, int actionCode,
			String userAccount) {
		this.code = code;
		this.openId = openId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.actionCode = actionCode;
		this.userAccount = userAccount;
	}
}
