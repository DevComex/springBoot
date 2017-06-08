/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqi@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：玩家充值记录信息实体
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.lhzsgoldback;

public class RechangeBean {

	private int code;
	private String account;
	private int cashAmount;
	private int spentCash;
	//返还金币总数，临时数据
	private int coinNum;
	
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
	public int getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}
	public int getSpentCash() {
		return spentCash;
	}
	public void setSpentCash(int spentCash) {
		this.spentCash = spentCash;
	}
	public int getCoinNum() {
		return coinNum;
	}
	public void setCoinNum(int coinNum) {
		this.coinNum = coinNum;
	}
	
	@Override
	public String toString() {
		return "RechangeBean [code=" + code + ", account=" + account
				+ ", cashAmount=" + cashAmount + ", spentCash=" + spentCash
				+ ", coinNum=" + coinNum + "]";
	}
}
