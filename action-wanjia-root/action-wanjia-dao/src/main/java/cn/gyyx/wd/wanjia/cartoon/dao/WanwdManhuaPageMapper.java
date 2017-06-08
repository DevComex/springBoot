package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;

public interface WanwdManhuaPageMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdManhuaPage record);

	int insertSelective(WanwdManhuaPage record);

	WanwdManhuaPage selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdManhuaPage record);

	int updateByPrimaryKey(WanwdManhuaPage record);

	/**
	 * 插入漫画新图片
	 * 
	 * @param record
	 * @return
	 */
	int insertPic(WanwdManhuaPage record);

	/**
	 * 查询漫画章节页数
	 * 
	 * @param core
	 * @return
	 */
	int selectPageSize(Integer core);

	/**
	 * 查询章节的所有漫画图片
	 * 
	 * @param bookCode
	 * @return
	 */
	List<Map<String, Object>> selectPageNumAndUrlByBookCode(Integer bookCode);

	/**
	 * 删除漫画章节的原有page
	 * 
	 * @param bookCode
	 * @return
	 */
	int changeIsDelete(Integer bookCode);

	List<WanwdManhuaPage> selectByParentCode(int parentCode);

	WanwdManhuaPage findNewPageByBookCode(Integer bookCode);

}