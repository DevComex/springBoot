package cn.gyyx.action.dao.wdtombthreeteam;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtombthreeteam.TombReserveInfoBean;


/**
 * Created by DerCg on 2016/8/30.
 */
public interface TombIReserveInfoBean {
    int addReserveInfoBean(TombReserveInfoBean info);
    TombReserveInfoBean selectByCode(@Param("code")int code);
    TombReserveInfoBean selectByPhoneAndActionCode(@Param("phoneNum")String phoneNum,@Param("actionCode")int  actionCode);
    void updateIsPrizeStatus(int code);
}
