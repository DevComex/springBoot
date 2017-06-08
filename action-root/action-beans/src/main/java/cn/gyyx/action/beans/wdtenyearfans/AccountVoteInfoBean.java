/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月30日下午5:33:18
 * 版本号：v1.0
 * 本类主要用途叙述：账号关注和拉黑的作品情况
 */

package cn.gyyx.action.beans.wdtenyearfans;

import java.util.List;

public class AccountVoteInfoBean {
	// 账号
	private String account;
	// 关注作品
	private List<WdNominationInfoBean> whitesBeans;
	// 拉黑作品
	private List<WdNominationInfoBean> blackBeans;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<WdNominationInfoBean> getWhitesBeans() {
		return whitesBeans;
	}

	public void setWhitesBeans(List<WdNominationInfoBean> whitesBeans) {
		this.whitesBeans = whitesBeans;
	}

	public List<WdNominationInfoBean> getBlackBeans() {
		return blackBeans;
	}

	public void setBlackBeans(List<WdNominationInfoBean> blackBeans) {
		this.blackBeans = blackBeans;
	}

	@Override
	public String toString() {
		return "AccountVoteInfoBean [account=" + account + ", whitesBeans="
				+ whitesBeans + ", blackBeans=" + blackBeans + "]";
	}

}
