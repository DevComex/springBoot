package dao;

import beans.RecommendBean;

public interface RecommendDao {
    int deleteByPrimaryKey(Integer code);

    int insert(RecommendBean record);

    int insertSelective(RecommendBean record);

    RecommendBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RecommendBean record);

    int updateByPrimaryKey(RecommendBean record);
}