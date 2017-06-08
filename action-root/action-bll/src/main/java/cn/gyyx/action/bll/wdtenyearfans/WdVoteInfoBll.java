/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月30日下午4:45:31
 * 版本号：v1.0
 * 本类主要用途叙述：十周年提名榜投票的业务层
 */

package cn.gyyx.action.bll.wdtenyearfans;

import java.util.List;

import cn.gyyx.action.beans.wdtenyearfans.WdVoteInfoBean;
import cn.gyyx.action.dao.wdtenyearfans.WdVoteInfoDAO;

public class WdVoteInfoBll {
	private WdVoteInfoDAO wdVoteInfoDAO = new WdVoteInfoDAO();

	/***
	 * 查询账户投票的数量
	 * 
	 * @param account
	 * @return
	 */
	public Integer getCountWdVoteInfoBeanByAccount(String account) {
		return wdVoteInfoDAO.getCountWdVoteInfoBeanByAccount(account);
	}

	/***
	 * 查询某账户对某提名的投票情况
	 */
	public WdVoteInfoBean getWdVoteInfoBeanByAccountAndNominationCode(
			String account, int nominationCode) {
		return wdVoteInfoDAO.getWdVoteInfoBeanByAccountAndNominationCode(
				account, nominationCode);
	}
	
	/***
	 * 查询某账户对某提名的投票情况
	 */
	public WdVoteInfoBean getVoteTypeByAccount(WdVoteInfoBean wdVoteInfoBean) {
		return wdVoteInfoDAO.selectVoteTypeByAccount(wdVoteInfoBean);
	}

	/***
	 * 添加投票的信息
	 * 
	 * @param wdVoteInfoBean
	 */
	public void insertWdVoteInfoBean(WdVoteInfoBean wdVoteInfoBean) {
		wdVoteInfoDAO.insertWdVoteInfoBean(wdVoteInfoBean);
	}

	/***
	 * 获取账号投票的信息
	 * 
	 * @param account
	 * @return
	 */
	public List<WdVoteInfoBean> getWdVoteInfoBeanByAccount(String account) {
		return wdVoteInfoDAO.getWdVoteInfoBeanByAccount(account);
	}

	/**
	 * 查询投票账号数量
	 * @param accountName
	 * @return
	 */
	public Integer getVoteAccountNum(String accountName) {
		return wdVoteInfoDAO.getVoteAccountNum(accountName);
	}
	
	/**
	 * 查询投票IP数量
	 * @return
	 */
	public Integer getVoteIpNum() {
		return wdVoteInfoDAO.getVoteIpNum();
	}
	/**
	 * 根据账号查询投票次数
	 * @param accountName
	 * @return
	 */
	public Integer getVoteNumByAccount(String accountName) {
		return wdVoteInfoDAO.getVoteNumByAccount(accountName);
	}
	/**
	 * 根据账号查询所有信息
	 * @return
	 */
	public List<String> getVoteAccount(int pageSize,int pageNo,String accountName) {
		return wdVoteInfoDAO.getVoteAccount(pageSize, pageNo,accountName);
	}
	
	public List<String> getAllVoteAccount() {
		return wdVoteInfoDAO.getAllVoteAccount();
	}
}
