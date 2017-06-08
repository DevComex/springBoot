package dao;

import beans.UserTitleBean;

public interface UserTitleDao {
    int deleteByPrimaryKey(Integer code);

    int insert(UserTitleBean record);

    int insertSelective(UserTitleBean record);

    UserTitleBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(UserTitleBean record);

    int updateByPrimaryKey(UserTitleBean record);
}