package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;
import java.util.Map;

import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;

public interface WanwdManhuaBookMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdManhuaBook record);

	int insertSelective(WanwdManhuaBook record);

	WanwdManhuaBook selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdManhuaBook record);

	int updateByPrimaryKey(WanwdManhuaBook record);

	/**
	 * 查询漫画的当前（最大）章节数，忽略审核
	 * 
	 * @param manhuaCode
	 * @return
	 */
	int selectMaxBookNumByManHuaCode(Integer manhuaCode);

	/**
	 * 插入新章节，返回book的主键code
	 * 
	 * @param book
	 * @return
	 */
	int insertReturnCode(WanwdManhuaBook book);

	/**
	 * 查询漫画的当前（最大）章节，审核通过
	 * 
	 * @param manhuaCode
	 * @return
	 */
	int selectMaxBookNumByManHuaCodeWithStatus(Integer manhuaCode);

	/**
	 * 查询浏览页漫画的章节，url等信息 manhuaCode,bookNum,pageNum
	 * 返回p.page_picture_url,p.code,m.title,b.book_name
	 * 
	 * @param map
	 * @return
	 */
	Map<String, Object> selectManhuaBrowseInfo(Map<String, Object> map);

	/**
	 * 查询上一章节的章节数 bookNum manhuaCode
	 * 
	 * @param map
	 * @return
	 */
	int selectUpBookNum(Map<String, Object> map);

	/**
	 * 查询下一章节的章节数 bookNum manhuaCode
	 * 
	 * @param map
	 * @return
	 */
	int selectDownBookNum(Map<String, Object> map);

	List<WanwdManhuaBook> getBookList(Integer manhuaCode);

	int getBookListCount(Integer manhuaCode);

	List<WanwdManhuaBook> getbookGroupList(Map<String, Object> map);

	/**
	 * 后台查询漫画列表,附带查询条件
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectManhuaListByOrder(Map<String, Object> map);

	/**
	 * 查询所有未审核和审核通过的章节，通过章节主键，查询和章节同一漫画下的章节
	 * 
	 * @param code
	 * @return
	 */
	List<Map<String, Object>> selectBelongSameManhuaWithNoFailByBookCode(Integer code);

	/**
	 * 查询最小章节
	 * 
	 * @param manhuaCode
	 * @return
	 */
	Integer selectMinBookNum(Integer manhuaCode);

	/**
	 * 批量查询记录，by主键
	 * 
	 * @param list
	 * @return
	 */
	List<WanwdManhuaBook> selectListByPrimaryKey(List<Integer> list);

	/**
	 * 批量修改记录为显示，by主键
	 * 
	 * @param list
	 * @return
	 */
	int updateListToShow(List<Integer> list);

	/**
	 * 批量修改记录为隐藏，by主键
	 * 
	 * @param list
	 * @return
	 */
	int updateListToHidden(List<Integer> list);

	/**
	 * 批量修改记录为审核不通过，by主键
	 * 
	 * @param list
	 * @return
	 */
	int updateListToStatusFail(List<Integer> list);

	WanwdManhuaBook findNewBookByManhuaCode(Integer manhuaCode);

	/**
	 * 查询用户上传的漫画章节信息，分页
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectUserUpload(Map<String, Object> map);

	/**
	 * 查询用户上传的漫画章节信息分页总页数
	 * 
	 * @param map
	 * @return
	 */
	int selectUserUploadTotalSize(Map<String, Object> map);

}