package dao;

import beans.WdgiftOrderBean;

public interface WdgiftOrderDao {
    int deleteByPrimaryKey(Integer code);

    int insert(WdgiftOrderBean record);

    int insertSelective(WdgiftOrderBean record);

    WdgiftOrderBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(WdgiftOrderBean record);

    int updateByPrimaryKey(WdgiftOrderBean record);
}