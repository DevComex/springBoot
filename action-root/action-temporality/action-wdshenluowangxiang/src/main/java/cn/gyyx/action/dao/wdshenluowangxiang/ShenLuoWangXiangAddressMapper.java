package cn.gyyx.action.dao.wdshenluowangxiang;

import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangAddressBean;

public interface ShenLuoWangXiangAddressMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ShenLuoWangXiangAddressBean record);

    int insertSelective(ShenLuoWangXiangAddressBean record);

    ShenLuoWangXiangAddressBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ShenLuoWangXiangAddressBean record);

    int updateByPrimaryKey(ShenLuoWangXiangAddressBean record);

    ShenLuoWangXiangAddressBean selectByUserId(Integer userId);
}