package dao;

import beans.UserIdAccountBean;

public interface UserIdAccountDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserIdAccountBean record);

    int insertSelective(UserIdAccountBean record);

    UserIdAccountBean selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserIdAccountBean record);

    int updateByPrimaryKey(UserIdAccountBean record);
}