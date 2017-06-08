package cn.mahjong.bll;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;

public interface RechargeLogBll {

  List<RechargeLog> get(int userId, Date start, Date end, int pageIndex);

  int count(int userId, Date start, Date end);

  RechargeSummary getSummary(int userId, Date start, Date end);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午1:01:38 描述
    *
    * @param start
    * @param end
    * @param pageIndex
    * @param pageSize
    * @param userId
    * @return List<Map<String,Object>>
    */
  List<Map<String, Object>> selectListByOperatorId(String start, String end, Integer pageIndex,
      Integer pageSize, int userId);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午1:45:31 描述
    *
    * @param start
    * @param end
    * @param userId
    * @return Map<String,Object>
    */
  Map<String, Object> selectCountByOperatorId(String start, String end, int userId);
  void insert(RechargeLog log);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午2:21:13 描述
    *
    * @param start
    * @param end
    * @param pageIndex
    * @param pageSize
    * @param userId
    * @return List<Map<String,Object>>
    */
  List<Map<String, Object>> selectListByUserId(String start, String end, Integer pageIndex,
      Integer pageSize, int userId);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月1日 下午2:21:23 描述
    *
    * @param start
    * @param end
    * @param userId
    * @return Map<String,Object>
    */
  Map<String, Object> selectCountByUserId(String start, String end, int userId);
}
