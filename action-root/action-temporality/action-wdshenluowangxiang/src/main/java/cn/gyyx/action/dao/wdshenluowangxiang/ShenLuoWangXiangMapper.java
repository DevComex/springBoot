package cn.gyyx.action.dao.wdshenluowangxiang;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangBean;

public interface ShenLuoWangXiangMapper  {

    int insert(ShenLuoWangXiangBean record);

    int insertSelective(ShenLuoWangXiangBean record);

    ShenLuoWangXiangBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ShenLuoWangXiangBean record);

    int updateByPrimaryKey(ShenLuoWangXiangBean record);

	ShenLuoWangXiangBean getUserInfoByUserId(Integer userId);

	boolean updateLuckNum(Integer userId);
}