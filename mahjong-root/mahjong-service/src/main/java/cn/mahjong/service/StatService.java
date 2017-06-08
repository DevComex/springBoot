package cn.mahjong.service;

import java.util.List;
import java.util.Map;

import cn.mahjong.beans.common.ResultBean;

public interface StatService {

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *
    * @param start
    * @param end
    * @param pageIndex
    * @param pageSize
   * @param userId 
    * @return ResultBean<List<Map<String,Object>>>
    */
  ResultBean<Map<String, Object>> personSaleSum(String start, String end, Integer pageIndex,
      Integer pageSize, int userId);

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *
    * @param start
    * @param end
    * @param pageIndex
    * @param pageSize
    * @param userId
    * @return ResultBean<Map<String,Object>>
    */
  ResultBean<Map<String, Object>> personPurchaseSum(String start, String end, Integer pageIndex,
      Integer pageSize, int userId);

}
