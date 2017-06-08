/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-19
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，后台漫画管理审核的bll
 */
package cn.gyyx.wd.wanjia.cartoon.bll.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaBookMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaPageMapper;

@Service
public class ManHuaManageReviewBllImpl implements ManHuaManageReviewBLL {
	@Autowired
	private WanwdManhuaBookMapper manhuaBookMapper;
	@Autowired
	private WanwdManhuaPageMapper manhuaPageMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#getViewList(java.util
	 * .Map)
	 */
	@Override
	public List<Map<String, Object>> getViewList(Map<String, Object> map) {
		return manhuaBookMapper.selectManhuaListByOrder(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#updateByPrimaryKey(cn
	 * .gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook)
	 */
	@Override
	public Integer updateByPrimaryKey(WanwdManhuaBook book) {
		return manhuaBookMapper.updateByPrimaryKeySelective(book);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#
	 * selectBelongSameManhuaWithNoFailByBookCode(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> selectBelongSameManhuaWithNoFailByBookCode(Integer bookCode) {
		return manhuaBookMapper.selectBelongSameManhuaWithNoFailByBookCode(bookCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#
	 * selectAllPageWithBookCode(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> selectAllPageWithBookCode(Integer bookCode) {
		return manhuaPageMapper.selectPageNumAndUrlByBookCode(bookCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#changeIsDelete(java.
	 * lang.Integer)
	 */
	@Override
	public int changeIsDelete(Integer bookCode) {
		return manhuaPageMapper.changeIsDelete(bookCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#
	 * selectBookListByPrimaryKey(java.util.List)
	 */
	@Override
	public List<WanwdManhuaBook> selectBookListByPrimaryKey(List<Integer> list) {
		return manhuaBookMapper.selectListByPrimaryKey(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#updateBookListToShow(
	 * java.util.List)
	 */
	@Override
	public int updateBookListToShow(List<Integer> list) {

		return manhuaBookMapper.updateListToShow(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#
	 * updateBookListToHidden(java.util.List)
	 */
	@Override
	public int updateBookListToHidden(List<Integer> list) {
		return manhuaBookMapper.updateListToHidden(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.ManHuaManageReviewBLL#
	 * updateBookListToStatusFail(java.util.List)
	 */
	@Override
	public int updateBookListToStatusFail(List<Integer> list) {
		return manhuaBookMapper.updateListToStatusFail(list);
	}

}
