package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdRecommend;

public interface WanwdRecommendMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(WanwdRecommend record);

    int insertSelective(WanwdRecommend record);

    WanwdRecommend selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(WanwdRecommend record);

    int updateByPrimaryKey(WanwdRecommend record);

	int selectByLocaltion(Integer localtion);

	List<WanwdRecommend> getManhuaFields(Integer manhuaCode);

	List<WanwdRecommend> getFieldsListByLocaltionId(Map<String, Object> map);

	WanwdRecommend selectByLocaltionAndManhuaCode(Integer localtion, Integer code);

	WanwdRecommend findByLocaltionAndOrderId(Integer location, Integer order);

	LinkedList<WanwdRecommend> getLinkListByLocaltionId(Integer location);
	List<Map<String, Object>>   selectRecommendHistory(Map<String, Object> map);

	void deleteByManhuaCode(Integer contentId);

	int selectRecommendHistoryCount(Map<String, Object> map);
}