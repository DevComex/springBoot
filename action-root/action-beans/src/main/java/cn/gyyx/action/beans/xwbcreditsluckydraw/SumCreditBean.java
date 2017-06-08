/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:55:57
 * @版本号：
 * @本类主要用途描述：玩家总积分实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class SumCreditBean {

	// 主键编号
	private int code;
	// 用户账号
	private String account;
	// 总积分
	private int sumCredit;

	public SumCreditBean() {

	}

	public SumCreditBean(String account, int sumCredit) {
		this.account = account;
		this.sumCredit = sumCredit;
	}

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

	public int getSumCredit() {
		return sumCredit;
	}

	public void setSumCredit(int sumCredit) {
		this.sumCredit = sumCredit;
	}

}
