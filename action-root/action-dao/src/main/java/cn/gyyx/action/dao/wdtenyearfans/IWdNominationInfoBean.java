/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动提名相关接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;

public interface IWdNominationInfoBean {
	/**
	 * 
	 * @Title: insertWdNominationInfoBean
	 * @Description: TODO 提名操作
	 * @param @param bean
	 * @return void
	 * @throws
	 */
	public void insertWdNominationInfoBean(WdNominationInfoBean bean);

	public WdNominationInfoBean selectWdNominationInfoBeanByCode(
			@Param("code") int code);

	public int selectWdNominationCount(PageBean pageBean);
	
	public int selectDeleteWdNominationCount(PageBean pageBean);

	public void updateWdNominationInfoBean(WdNominationInfoBean bean);

	public void updateAuditStatus(WdNominationInfoBean bean);

	public List<WdNominationInfoBean> selectWdNominationInfoList(
			PageBean pageBean);
	public List<WdNominationInfoBean> selectDeleteWdNominationInfoList(
			PageBean pageBean);
	
	public int getNomanitionCountForMe(PageBean pageBean);
	public int getNomanitionCountForHe(PageBean pageBean);

	/**
	 * 
	 * @Title: selectWdNominationInfoBeanByAccountName
	 * @Description: TODO 判断是否提名
	 * @param @param accountName
	 * @param @return
	 * @return WdNominationInfoBean
	 * @throws
	 */
	public WdNominationInfoBean selectWdNominationInfoBeanByAccountName(
			@Param("accountName") String accountName);

	/***
	 * 根据角色，页码，状态筛选提名作品
	 * 
	 * @param roleName
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByRoleName(
			@Param("roleName") String roleName,
			@Param("pageSize") int pageSize, @Param("pageNo") int pageNo,
			@Param("status") int status);

	/***
	 * 根据角色状态筛选提名作品数量
	 * 
	 * @param roleName
	 * @param status
	 * @return
	 */
	public Integer getCountWdNominationInfoBeansByRoleName(
			@Param("roleName") String roleName, @Param("status") int status);

	/**
	 * 根据主键获取白粉的排名
	 * 
	 * @param code
	 * @return
	 */
	public Integer getWhiteNumCount(@Param("code") int code);

	/**
	 * 根据主键获取白粉的排名
	 * 
	 * @param code
	 * @return
	 */
	public Integer getBlackNumCount(@Param("code") int code);

	/***
	 * 根据时间顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByOrder(
			@Param("pageSize") int pageSize, @Param("pageNo") int pageNo,
			@Param("status") int status);

	/***
	 * 根据时间顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByWhite(
			@Param("pageSize") int pageSize, @Param("pageNo") int pageNo,
			@Param("status") int status);

	/***
	 * 根据时间顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByBlack(
			@Param("pageSize") int pageSize, @Param("pageNo") int pageNo,
			@Param("status") int status);

	/**
	 * 获取所有作品的数量
	 * 
	 * @param code
	 * @return
	 */
	public Integer getCountWdNominationInfoBeans(@Param("status") int status);

	/***
	 * 增加投票数
	 * 
	 * @param type
	 * @param code
	 */
	public void setVoteNum(@Param("type") int type, @Param("code") int code,
			@Param("date") Date date);

	/**
	 * 获取所有作品的数量
	 * 
	 * @param code
	 * @return
	 */
	public Integer getPageNum();

	/**
	 * 
	 * @Title: selectNominated_contentByCode
	 * @Description: TODO 查询提名简介
	 * @param @param code
	 * @param @return
	 * @return WdNominationInfoBean
	 * @throws
	 */
	public WdNominationInfoBean selectNominatedContentByCode(
			@Param("code") int code);

	/***
	 * 得到相同名次下白名单的最终名次
	 * 
	 * @param code
	 * @param whiteNum
	 * @return
	 */
	public Integer getWhiteOrderWhenSameOrder(@Param("voteWhiteDate") Date voteWhiteDate,
			@Param("whiteNum") int whiteNum);

	/***
	 * 得到相同名次下黑名单的最终名次
	 * 
	 * @param code
	 * @param whiteNum
	 * @return
	 */
	public Integer getBlackOrderWhenSameOrder(@Param("voteBlackDate") Date voteBlackDate,
			@Param("blackNum") int blackNum);
	
	/**
	 * 查询所有提名账号
	 * @param pageSize
	 * @param pageNo
	 * @param accountName
	 * @return
	 */
	public List<String> selectNominationAccount(@Param("pageSize") int pageSize, @Param("pageNo") int pageNo,@Param("accountName") String accountName);
	public List<String> selectAllNominationAccount();
	
	/**
	 * 查询通过提名账号数量
	 * @param accountName
	 * @return
	 */
	public Integer selectNominationAccountNum(@Param("accountName") String accountName);
	
	/**
	 * 查询提名账号数量
	 * @return
	 */
	public Integer selectAllNominationAccountNum();
	/**
	 * 查询提名IP数量
	 * @return
	 */
	public Integer selectNominationIpNum();
	
	/**
	 * 根据账号查询提名相关信息
	 * @param accountName
	 * @return
	 */
	public WdNominationInfoBean selectNominationInfo(@Param("accountName") String accountName);
}