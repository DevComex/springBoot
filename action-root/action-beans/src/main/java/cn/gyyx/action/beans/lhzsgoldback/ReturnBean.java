/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqi@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：玩家金币返还记录信息实体
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.lhzsgoldback;

import java.util.Date;

public class ReturnBean {

	//主键
	private int code;
	//用户账号
	private String account;
	//用户code
	private int userCode;
	//返还金币数量
	private int returnAmount;
	//返还日期
	private Date returnDate;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public int getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(int returnAmount) {
		this.returnAmount = returnAmount;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	@Override
	public String toString() {
		return "RebackBean [code=" + code + ", account=" + account
				+ ", userCode=" + userCode + ", returnAmount=" + returnAmount
				+ ", returnDate=" + returnDate + "]";
	}
}
