package dao;

import beans.ReviewLogBean;

public interface ReviewLogBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ReviewLogBean record);

    int insertSelective(ReviewLogBean record);

    ReviewLogBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ReviewLogBean record);

    int updateByPrimaryKey(ReviewLogBean record);
}