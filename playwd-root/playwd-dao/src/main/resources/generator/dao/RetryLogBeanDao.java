package dao;

import beans.RetryLogBean;

public interface RetryLogBeanDao {
    int deleteByPrimaryKey(Integer code);

    int insert(RetryLogBean record);

    int insertSelective(RetryLogBean record);

    RetryLogBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RetryLogBean record);

    int updateByPrimaryKey(RetryLogBean record);
}