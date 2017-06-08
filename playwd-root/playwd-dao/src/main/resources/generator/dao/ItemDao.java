package dao;

import beans.ItemBean;

public interface ItemDao {
    int deleteByPrimaryKey(Integer code);

    int insert(ItemBean record);

    int insertSelective(ItemBean record);

    ItemBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ItemBean record);

    int updateByPrimaryKey(ItemBean record);
}