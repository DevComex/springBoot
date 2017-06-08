package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import cn.gyyx.playwd.beans.playwd.PrizeLogBean;

public interface PrizeLogDao {
    int deleteByPrimaryKey(Integer code);

    int insertSelective(PrizeLogBean record);

    PrizeLogBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(PrizeLogBean record);

    /**
      * <p>
      *    方法说明
      * </p>
      *
      * @action
      *    lihu 2017年6月6日 下午7:39:17 描述
      *
      * @param map
      * @return List<Map<String,Object>>
      */
    List<Map<String, Object>> selectPrizeLogList(Map<String, Object> map);
    Map<String, Object> selectPrizeLogResult(Map<String, Object> map);

    /**
      * <p>
      *    方法说明
      * </p>
      *
      * @action
      *    lihu 2017年6月6日 下午7:39:44 描述
      *
      * @param map
      * @return int
      */
    int getPrizeLogCount(Map<String, Object> map);

}