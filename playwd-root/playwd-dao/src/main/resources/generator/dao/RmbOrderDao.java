package dao;

import beans.RmbOrderBean;

public interface RmbOrderDao {
    int deleteByPrimaryKey(Integer code);

    int insert(RmbOrderBean record);

    int insertSelective(RmbOrderBean record);

    RmbOrderBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RmbOrderBean record);

    int updateByPrimaryKey(RmbOrderBean record);
}