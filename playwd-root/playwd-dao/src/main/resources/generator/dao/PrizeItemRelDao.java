package dao;

import beans.PrizeItemRelBean;

public interface PrizeItemRelDao {
    int deleteByPrimaryKey(Integer code);

    int insert(PrizeItemRelBean record);

    int insertSelective(PrizeItemRelBean record);

    PrizeItemRelBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(PrizeItemRelBean record);

    int updateByPrimaryKey(PrizeItemRelBean record);
}