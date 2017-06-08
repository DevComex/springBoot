package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;

public interface WanwdCategoryMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdCategory record);

	int insertSelective(WanwdCategory record);

	WanwdCategory selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdCategory record);

	int updateByPrimaryKey(WanwdCategory record);

	/**
	 * 查询当前分类的详细信息
	 * 
	 * @param parentId
	 * @return
	 */
	WanwdCategory selectByParentId(Integer parentId);

	/**
	 * 查询所有分类名
	 * 
	 * @return
	 */
	List<WanwdCategory> selectAllCategoryName();

}