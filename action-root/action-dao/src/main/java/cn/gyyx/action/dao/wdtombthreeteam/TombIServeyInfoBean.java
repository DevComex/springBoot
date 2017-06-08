package cn.gyyx.action.dao.wdtombthreeteam;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtombthreeteam.TombServeyInfoBean;


/**
 * Created by DerCg on 2016/8/30.
 */
public interface TombIServeyInfoBean {
    int addServeyInfoBean(TombServeyInfoBean info);
    TombServeyInfoBean selectByReserveCode(@Param("reserveCode")int reserveCode);
}
