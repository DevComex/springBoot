package dao;

import beans.MessageBean;

public interface MessageBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(MessageBean record);

    int insertSelective(MessageBean record);

    MessageBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(MessageBean record);

    int updateByPrimaryKey(MessageBean record);
}