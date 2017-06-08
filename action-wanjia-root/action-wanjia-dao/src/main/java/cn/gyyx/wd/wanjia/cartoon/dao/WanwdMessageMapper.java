package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdMessage;

public interface WanwdMessageMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(WanwdMessage record);

	int insertSelective(WanwdMessage record);

	WanwdMessage selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(WanwdMessage record);

	int updateByPrimaryKey(WanwdMessage record);

	/**
	 * @description 查询漫画的编辑回复
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectMagagerManHuaReply(Map<String, Object> map);

	/**
	 * @description 查询漫画的编辑回复总条数
	 * @param map
	 * @return
	 */
	int selectMagagerManHuaReplyTotalSize(Map<String, Object> map);

	/**
	 * @description 查询漫画的消息通知
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectMessageAboutManHua(Map<String, Object> map);

	/**
	 * @description 查询漫画的消息通知总条数
	 * @param map
	 * @return
	 */
	int selectMessageAboutManHuaTotalSize(Map<String, Object> map);
}