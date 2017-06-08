package cn.gyyx.action.dao.wd11yearrechargerebate;

import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLog;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;

public interface RechargerebateAcessLogMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(RechargerebateAcessLogWithBLOBs record);

    int insertSelective(RechargerebateAcessLogWithBLOBs record);

    RechargerebateAcessLogWithBLOBs selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(RechargerebateAcessLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RechargerebateAcessLogWithBLOBs record);

    int updateByPrimaryKey(RechargerebateAcessLog record);
}