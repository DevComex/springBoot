package dao;

import beans.RecommendSlotBean;

public interface RecommendSlotDao {
    int deleteByPrimaryKey(Integer code);

    int insert(RecommendSlotBean record);

    int insertSelective(RecommendSlotBean record);

    RecommendSlotBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RecommendSlotBean record);

    int updateByPrimaryKey(RecommendSlotBean record);
}