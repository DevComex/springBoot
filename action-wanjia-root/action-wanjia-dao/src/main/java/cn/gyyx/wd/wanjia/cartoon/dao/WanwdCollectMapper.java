package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;

public interface WanwdCollectMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdCollect record);

	int insertSelective(WanwdCollect record);

	WanwdCollect selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdCollect record);

	int updateByPrimaryKey(WanwdCollect record);

	List<Map<String, Object>> userCatalogList(@Param("userId") Integer userId);

	WanwdCollect selectByManhuaCode(@Param("manhuaCode") Integer manhuaCode, @Param("userId") Integer userId);

	void saveCollect(Integer manhuaCode, Integer userId);

	/**
	 * 查询收藏状态和阅读记录
	 * 
	 * @param collect
	 * @return
	 */
	WanwdCollect selectCollectionStatusAndReadLog(WanwdCollect collect);
	/**
	 * 更新阅读记录
	 * @param record
	 * @return
	 */
	int updateReadLogByPrimaryKey(WanwdCollect record);
}