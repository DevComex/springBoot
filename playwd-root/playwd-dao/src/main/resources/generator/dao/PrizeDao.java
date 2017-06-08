package dao;

import beans.PrizeBean;

public interface PrizeDao {
    int deleteByPrimaryKey(Integer code);

    int insert(PrizeBean record);

    int insertSelective(PrizeBean record);

    PrizeBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(PrizeBean record);

    int updateByPrimaryKey(PrizeBean record);
}