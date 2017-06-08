package cn.mahjong.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;

public interface RechargeLogMapper {

  List<RechargeLog> selectByUserId(@Param("userId") int userId, @Param("start") Date start,
      @Param("end") Date end, @Param("skip") int skip, @Param("take") int take);

  int countByUserId(@Param("userId") int userId, @Param("start") Date start,
      @Param("end") Date end);

  RechargeSummary selectSummaryByUserId(@Param("userId") int userId, @Param("start") Date start,
      @Param("end") Date end);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午1:03:58 描述
    *
    * @param startTime
    * @param endT
    * @param pageIndex
    * @param pageSize
    * @param userId
    * @return List<Map<String,Object>>
    */
  List<Map<String, Object>> selectListByOperatorId(@Param("startTime")String startTime,@Param("endTime") String endTime,@Param("startSize") Integer startSize,
      @Param("endSize")Integer endSize,@Param("userId") int userId);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午1:46:34 描述
    *
    * @param start
    * @param end
    * @param userId
    * @return Map<String,Object>
    */
  Map<String, Object> selectCountByOperatorId(@Param("startTime")String start, @Param("endTime")String end, @Param("userId")int userId);
  void insert(RechargeLog log);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午2:22:33 描述
    *
    * @param start
    * @param end
    * @param startSize
    * @param endSize
    * @param userId
    * @return List<Map<String,Object>>
    */
  List<Map<String, Object>> selectListByUserId(@Param("startTime")String startTime,@Param("endTime") String endTime,@Param("startSize") Integer startSize,
      @Param("endSize")Integer endSize,@Param("userId") int userId);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午2:22:41 描述
    *
    * @param start
    * @param end
    * @param userId
    * @return Map<String,Object>
    */
  Map<String, Object> selectCountByUserId(@Param("startTime")String start, @Param("endTime")String end, @Param("userId")int userId);
}
