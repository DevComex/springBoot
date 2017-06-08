package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;

public interface WanwdManhuaMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdManhua record);

	int insertSelective(WanwdManhua record);

	WanwdManhua selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdManhua record);

	int updateByPrimaryKey(WanwdManhua record);

	List<Map<String, Object>> selectByUserId(@Param("userId") Integer userId);

	/**
	 * 查询当前author下所有未完结的漫画
	 * 
	 * @param authorId
	 * @return
	 */
	List<WanwdManhua> selectManhuaUnfinishedByAuthorId(Integer authorId);

	/**
	 * 查询漫画名为xxx的漫画
	 * 
	 * @param title
	 * @return
	 */
	WanwdManhua selectManhuaByTitle(@Param("title") String title);

	/**
	 * 插入新漫画，返回漫画code
	 * 
	 * @param manhua
	 * @return
	 */
	int insertReturnCode(WanwdManhua manhua);
	/**
	 * 更新阅读数+1
	 * @param code
	 * @return
	 */
	int updateReadCountPlusOne(Integer code);

	List<Map<String, Object>> selectByUserId(Map<String, Integer> map);

	int selectCountByUserId(Map<String, Integer> map);

	int selectCountByUid(@Param("userId") Integer userId);

	List<Map<String, Object>> selectManhuaList(Map<String, Integer> map);

	int selectManhuaListCount(@Param("categoryCode") Integer categoryCode);

	List<Map<String, Object>> selectRightList(@Param("location")String location);
	/**
	 * @description 查询作者被推荐的漫画
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectRecommendManHua(Map<String, Object> map);
	/**
	 * @description 查询作者被推荐的漫画总数
	 * @param map
	 * @return
	 */
	int selectRecommendManHuaTotalSize(Map<String, Object> map);
	/**
	 * @description 查询用户收藏的ManHua
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectUserCollection(Map<String, Object> map);
	/**
	 * @description 查询用户收藏的ManHua总数量
	 * @param map
	 * @return
	 */
	int selectUserCollectionTotalSize(Map<String, Object> map);
	/**
	 * 查询浏览页漫画基本信息 bymanhuacode 
	 * @param code
	 * @return
	 */
	Map<String, Object> selectManhuaInfo(Integer code);

}