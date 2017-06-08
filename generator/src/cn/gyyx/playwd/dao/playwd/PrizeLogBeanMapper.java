package cn.gyyx.playwd.dao.playwd;

import cn.gyyx.playwd.beans.playwd.PrizeLogBean;

public interface PrizeLogBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(PrizeLogBean record);

    int insertSelective(PrizeLogBean record);

    PrizeLogBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(PrizeLogBean record);

    int updateByPrimaryKey(PrizeLogBean record);
}