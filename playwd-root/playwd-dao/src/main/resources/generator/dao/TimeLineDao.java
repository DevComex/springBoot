package dao;

import beans.TimeLineBean;

public interface TimeLineBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(TimeLineBean record);

    int insertSelective(TimeLineBean record);

    TimeLineBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(TimeLineBean record);

    int updateByPrimaryKey(TimeLineBean record);
}