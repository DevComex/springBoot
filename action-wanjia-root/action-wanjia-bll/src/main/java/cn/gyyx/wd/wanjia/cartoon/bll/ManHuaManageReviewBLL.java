/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-19
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，后台漫画管理审核的bll接口
 */
package cn.gyyx.wd.wanjia.cartoon.bll;

import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;

public interface ManHuaManageReviewBLL {
	/**
	 * 查询筛选条件下的所有记录
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getViewList(Map<String, Object> map);

	/**
	 * 更新漫画的状态
	 * 
	 * @param book
	 * @return
	 */
	public Integer updateByPrimaryKey(WanwdManhuaBook book);

	/**
	 * 查询所有未审核和审核通过的章节，通过章节主键，查询和章节同一漫画下的章节
	 * 
	 * @param bookCode
	 * @return
	 */
	List<Map<String, Object>> selectBelongSameManhuaWithNoFailByBookCode(Integer bookCode);

	/**
	 * 查询章节的所有漫画图片
	 * 
	 * @param bookCode
	 * @return
	 */
	List<Map<String, Object>> selectAllPageWithBookCode(Integer bookCode);

	/**
	 * 删除漫画章节的原有page
	 * 
	 * @param bookCode
	 * @return
	 */
	int changeIsDelete(Integer bookCode);

	/**
	 * 批量查询漫画章节记录，by主键
	 * 
	 * @param list
	 * @return
	 */
	List<WanwdManhuaBook> selectBookListByPrimaryKey(List<Integer> list);

	/**
	 * 批量修改记录为显示，by主键
	 * 
	 * @param list
	 * @return
	 */
	int updateBookListToShow(List<Integer> list);

	/**
	 * 批量修改记录为隐藏，by主键
	 * 
	 * @param list
	 * @return
	 */
	int updateBookListToHidden(List<Integer> list);

	/**
	 * 批量修改记录为审核不通过，by主键
	 * 
	 * @param list
	 * @return
	 */
	int updateBookListToStatusFail(List<Integer> list);
}
