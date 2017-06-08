/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动投票（关注/拉黑）相关接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdVoteInfoBean;

public interface IWdVoteInfoBean {
	public void insertWdVoteInfoBean(WdVoteInfoBean bean);

	public WdVoteInfoBean selectWdVoteInfoBeanByCode(@Param("code") int code);

	public void updateWdVoteInfoBean(WdVoteInfoBean bean);

	/***
	 * 查询账户投票的数量
	 * 
	 * @param account
	 * @return
	 */
	public List<WdVoteInfoBean> getWdVoteInfoBeanByAccount(@Param("account") String account);
	
	/***
	 * 查询账户投票的数量
	 * 
	 * @param account
	 * @return
	 */
	public Integer getCountWdVoteInfoBeanByAccount(@Param("account") String account);
	
	/***
	 * 查询某账户对某提名的投票情况
	 */
	public WdVoteInfoBean getWdVoteInfoBeanByAccountAndNominationCode(@Param("account") String account,
			@Param("nominationCode") int nominationCode	);
	/**
	 * 
	* @Title: selectVoteTypeByAccount
	* @Description: TODO 根据用户和作品code查询投票情况
	* @param @param wdVoteInfoBean
	* @param @return    
	* @return WdVoteInfoBean    
	* @throws
	 */
	public WdVoteInfoBean selectVoteTypeByAccount(WdVoteInfoBean wdVoteInfoBean);
	/**
	 * 查询投票账号数量
	 * @return
	 */
	public Integer getVoteAccountNum(@Param("accountName") String accountName);
	/**
	 * 查询投票IP数量
	 * @return
	 */
	public Integer getVoteIpNum();
	/**
	 * 根据账号查询投票次数
	 * @param accountName
	 * @return
	 */
	public Integer getVoteNumByAccount(@Param("accountName") String accountName);
	/**
	 * 查询所有投票账号
	 * @return
	 */
	public List<String> getVoteAccount(@Param("pageSize") int pageSize, @Param("pageNo") int pageNo,@Param("accountName") String accountName);
	public List<String> getAllVoteAccount();
	
	/**
	 * 每日参与关注账号数
	 * @param pageBean
	 * @return
	 */
	public Integer getAccountNumByWhite(PageBean pageBean);
	
	/**
	 * 每日参与拉黑账号数
	 * @param pageBean
	 * @return
	 */
	public Integer getAccountNumByBlack(PageBean pageBean);
	
	/**
	 * 每日参与投票账号数
	 * @param pageBean
	 * @return
	 */
	public Integer getAccountNumByVote(PageBean pageBean);
	
	/**
	 * 每日账号关注的数量
	 * @param pageBean
	 * @return
	 */
	public Integer getWhiteNumByAccountDate(PageBean pageBean);
	
	/**
	 * 每日账号拉黑的数量
	 * @param pageBean
	 * @return
	 */
	public Integer getBlackNumByAccountDate(PageBean pageBean);
	
	/**
	 * 每日投票账号列表
	 * @param pageBean
	 * @return
	 */
	public List<String> selectAllVoteAccount(PageBean pageBean);
}