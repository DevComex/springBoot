/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月30日下午2:07:00
 * 版本号：v1.0
 * 本类主要用途叙述：提名展示的业务层
 */

package cn.gyyx.action.bll.wdtenyearfans;

import java.util.Date;
import java.util.List;

import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.dao.wdtenyearfans.WdNominationInfoDAO;

public class WdNominationInfoBll {
	private WdNominationInfoDAO wdNominationInfoDAO = new WdNominationInfoDAO();

	/***
	 * 根据角色筛选提名作品
	 * 
	 * @param roleName
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByRoleName(
			String roleName, int pageSize, int pageNo, int status) {
		return wdNominationInfoDAO.getWdNominationInfoBeansByRoleName(roleName,
				pageSize, pageNo, status);
	}

	/**
	 * 根据主键获取白粉的排名
	 * 
	 * @param code
	 * @return
	 */
	public Integer getWhiteNumCount(int code) {
		return wdNominationInfoDAO.getWhiteNumCount(code);
	}

	/**
	 * 根据主键获取白粉的排名
	 * 
	 * @param code
	 * @return
	 */
	public Integer getBlackNumCount(int code) {
		return wdNominationInfoDAO.getBlackNumCount(code);
	}

	/***
	 * 根据角色状态筛选提名作品数量
	 * 
	 * @param roleName
	 * @param status
	 * @return
	 */
	public Integer getCountWdNominationInfoBeansByRoleName(String roleName,
			int status) {
		return wdNominationInfoDAO.getCountWdNominationInfoBeansByRoleName(
				roleName, status);
	}

	/***
	 * 根据时间顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByOrder(
			int pageSize, int pageNo, int status) {
		return wdNominationInfoDAO.getWdNominationInfoBeansByOrder(pageSize,
				pageNo, status);
	}

	/**
	 * 获取所有作品的数量
	 * 
	 * @param code
	 * @return
	 */
	public Integer getCountWdNominationInfoBeans(int status) {
		return wdNominationInfoDAO.getCountWdNominationInfoBeans(status);
	}

	/***
	 * 根据白粉顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByWhite(
			int pageSize, int pageNo, int status) {
		return wdNominationInfoDAO.getWdNominationInfoBeansByWhite(pageSize,
				pageNo, status);
	}

	/***
	 * 根据黑粉顺序获取提名的信息
	 * 
	 * @return
	 */
	public List<WdNominationInfoBean> getWdNominationInfoBeansByBlack(
			int pageSize, int pageNo, int status) {
		return wdNominationInfoDAO.getWdNominationInfoBeansByBlack(pageSize,
				pageNo, status);
	}

	/***
	 * 增加投票数
	 * 
	 * @param type
	 * @param code
	 */
	public void setVoteNum(int type, int code) {
		wdNominationInfoDAO.setVoteNum(type, code);
	}

	/**
	 * 根据主键获取作品信息
	 * 
	 * @param code
	 * @return
	 */
	public WdNominationInfoBean getWdNominationInfoBeanByCode(int code) {
		return wdNominationInfoDAO.selectWdNominationInfoBeanByCode(code);
	}

	/**
	 * 
	 * @Title: selectNominatedContentByCode
	 * @Description: TODO 查询提名简介
	 * @param @param code
	 * @param @return
	 * @return WdNominationInfoBean
	 * @throws
	 */
	public WdNominationInfoBean getNominatedContentByCode(int code) {
		return wdNominationInfoDAO.selectNominatedContentByCode(code);
	}

	/***
	 * 得到相同名次下白名单的最终名次
	 * 
	 * @param code
	 * @param whiteNum
	 * @return
	 */
	public Integer getWhiteOrderWhenSameOrder(Date code, int whiteNum) {
		return wdNominationInfoDAO.getWhiteOrderWhenSameOrder(code, whiteNum);
	}

	/***
	 * 得到相同名次下黑名单的最终名次
	 * 
	 * @param code
	 * @param whiteNum
	 * @return
	 */
	public Integer getBlackOrderWhenSameOrder(Date code, int blackNum) {
		return wdNominationInfoDAO.getBlackOrderWhenSameOrder(code, blackNum);
	}

}
