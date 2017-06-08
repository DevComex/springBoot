package dao;

import beans.ReportBean;

public interface ReportBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ReportBean record);

    int insertSelective(ReportBean record);

    ReportBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ReportBean record);

    int updateByPrimaryKey(ReportBean record);
}