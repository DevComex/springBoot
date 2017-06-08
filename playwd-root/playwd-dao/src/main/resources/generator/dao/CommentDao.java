package dao;

import beans.CommentBean;

public interface CommentBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(CommentBean record);

    int insertSelective(CommentBean record);

    CommentBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(CommentBean record);

    int updateByPrimaryKey(CommentBean record);
}