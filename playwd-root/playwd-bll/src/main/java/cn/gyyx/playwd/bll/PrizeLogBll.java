/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：playwd-bll
  * @作者：lihu
  * @联系方式：lihu@gyyx.cn
  * @创建时间：2017年6月6日 下午5:35:03
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.playwd.bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.PrizeLogBean;
import cn.gyyx.playwd.dao.playwd.PrizeLogDao;

/**
  * <p>
  *   PrizeLogBll描述
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
  */
@Component
public class PrizeLogBll {
  @Autowired
  public PrizeLogDao prizeLogDao;
  
  public void  insertPrizeLog(PrizeLogBean prizeLogBean){
    prizeLogDao.insertSelective(prizeLogBean);
  };
  
  public void  updatePrizeLog(PrizeLogBean prizeLogBean){
    prizeLogDao.updateByPrimaryKeySelective(prizeLogBean);
  }

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月6日 下午7:33:36 描述
    *
    * @param contentType
    * @param searchType
    * @param searchValue
    * @param startTime
    * @param endTime
    * @param pageIndex
    * @param pageSize
    * @return List<Map<String,Object>>
    */
  public List<Map<String, Object>> getPrizeLogList(String contentType, String searchType,
      String searchValue, String startTime, String endTime, Integer pageIndex, Integer pageSize) {
    int startSize = (pageIndex - 1) * pageSize;
    int endSize =   pageSize;
    Map<String, Object> map = new HashMap<>();
    map.put("startSize", startSize);
    map.put("endSize", endSize);
    map.put("startTime", startTime);
    map.put("endTime", endTime);
    map.put("contentType", contentType);
    map.put("searchValue", searchValue);
    map.put("searchType", searchType);
    map.put("contentType", contentType);
    
    return prizeLogDao.selectPrizeLogList(map);
  }

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月6日 下午7:34:22 描述
    *
    * @param contentType
    * @param searchType
    * @param searchValue
    * @param startTime
    * @param endTime
    * @param pageIndex
    * @param pageSize
    * @return int
    */
  public int getPrizeLogCount(String contentType, String searchType, String searchValue,
      String startTime, String endTime, Integer pageIndex, Integer pageSize) {
    int startSize = (pageIndex - 1) * pageSize;
    int endSize =   pageSize;
    Map<String, Object> map = new HashMap<>();
    map.put("startSize", startSize);
    map.put("endSize", endSize);
    map.put("startTime", startTime);
    map.put("endTime", endTime);
    map.put("contentType", contentType);
    map.put("searchValue", searchValue);
    map.put("searchType", searchType);
    map.put("contentType", contentType);
    
    return prizeLogDao.getPrizeLogCount(map);
  }

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月8日 上午11:19:45 描述
    *
    * @param contentType
    * @param searchType
    * @param searchValue
    * @param startTime
    * @param endTime
    * @param pageIndex
    * @param pageSize
    * @return Map<String,Object>
    */
  public Map<String, Object> selectPrizeLogResult(String contentType, String searchType,
      String searchValue, String startTime, String endTime, Integer pageIndex, Integer pageSize) {
    int startSize = (pageIndex - 1) * pageSize;
    int endSize =   pageSize;
    Map<String, Object> map = new HashMap<>();
    map.put("startSize", startSize);
    map.put("endSize", endSize);
    map.put("startTime", startTime);
    map.put("endTime", endTime);
    map.put("contentType", contentType);
    map.put("searchValue", searchValue);
    map.put("searchType", searchType);
    map.put("contentType", contentType);
    
    return prizeLogDao.selectPrizeLogResult(map);
  };
  
  
}
