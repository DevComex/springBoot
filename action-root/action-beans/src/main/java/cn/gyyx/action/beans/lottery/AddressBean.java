/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 下午3:16:53
 * @版本号：
 * @本类主要用途描述：地址信息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.lottery;

public class AddressBean {

	private int code;
	private int userCode;
	private String userName;
	private String userPhone;
	private String userAddress;
	private int  actionCode;
	private  String userAccount;
    private String userQq;
    
	public AddressBean() {

	}



	public String getUserAccount() {
		return userAccount;
	}



	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}



	public AddressBean(int code, int userCode, String userName,
			String userPhone, String userAddress, int actionCode,
			String userAccount,String userQq) {
		this.code = code;
		this.userCode = userCode;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.actionCode = actionCode;
		this.userAccount = userAccount;
		this.userQq=userQq;
	}



	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
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
   
	


	public String getUserQq() {
		return userQq;
	}



	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}



	@Override
	public String toString() {
		return "AddressBean [code=" + code + ", userCode=" + userCode
				+ ", userName=" + userName + ", userPhone=" + userPhone
				+ ", userAddress=" + userAddress + ", actionCode=" + actionCode
				+ ", userAccount=" + userAccount + ",userQq = "+userQq+"]";
	}
}
