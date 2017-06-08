package cn.gyyx.wd.wanjia.cartoon.dao;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaGood;

public interface WanwdManhuaGoodMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdManhuaGood record);

	int insertSelective(WanwdManhuaGood record);

	WanwdManhuaGood selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdManhuaGood record);

	int updateByPrimaryKey(WanwdManhuaGood record);

	/**
	 * 查询漫画总点赞数
	 * 
	 * @param manhuaCode
	 * @return
	 */
	int getCountByManhuaCode(Integer manhuaCode);

	/**
	 * 查询当前用户是否对当前漫画点赞 0未点 1点赞 其他：数据错乱
	 * 
	 * @param manhuaGood
	 * @return
	 */
	int getGoodStatus(WanwdManhuaGood manhuaGood);
}