package dao;

import beans.RecommendContentBean;

public interface RecommendContentBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(RecommendContentBean record);

    int insertSelective(RecommendContentBean record);

    RecommendContentBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RecommendContentBean record);

    int updateByPrimaryKey(RecommendContentBean record);
}