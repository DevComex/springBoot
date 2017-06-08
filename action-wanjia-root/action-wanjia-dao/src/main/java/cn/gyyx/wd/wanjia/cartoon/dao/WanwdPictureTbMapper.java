package cn.gyyx.wd.wanjia.cartoon.dao;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdPictureTb;

public interface WanwdPictureTbMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(WanwdPictureTb record);

    int insertSelective(WanwdPictureTb record);

    WanwdPictureTb selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(WanwdPictureTb record);

    int updateByPrimaryKey(WanwdPictureTb record);
}