/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：playwd-service
  * @作者：lihu
  * @联系方式：lihu@gyyx.cn
  * @创建时间：2017年6月6日 下午7:28:47
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.playwd.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.bll.PrizeBll;
import cn.gyyx.playwd.bll.PrizeLogBll;
import cn.gyyx.playwd.utils.DateTools;

/**
  * <p>
  *   PrizeService描述
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
  */
@Service
public class PrizeService {
  
  @Autowired
  private PrizeLogBll prizeLogBll;

  /**
    * <p>
    *    方法说明
    * </p>
    *
    * @action
    *    lihu 2017年6月6日 下午7:30:28 描述
    *
    * @param contentType
    * @param searchType
    * @param trim
    * @param startTime
    * @param endTime
    * @param pageIndex
    * @param pageSize
    * @return PageBean<Map<String,Object>>
   * @throws ParseException 
    */
  public PageBean<Object> getPrizeLogList(String contentType, String searchType,
      String searchValue, String startTime, String endTime, Integer pageIndex, Integer pageSize) throws ParseException {
    if(endTime !=null&&!"".equals(endTime)){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      //结束时间往后延一天
      Date nextDate = DateTools.getNextDate(sdf.parse(endTime));
      endTime=sdf.format(nextDate);
  }
    
    List<Map<String , Object>> map = prizeLogBll.getPrizeLogList(contentType,searchType,searchValue,startTime,endTime,pageIndex,pageSize);
    int count = prizeLogBll.getPrizeLogCount(contentType,searchType,searchValue,startTime,endTime,pageIndex,pageSize);
    Map<String , Object> sumMap=prizeLogBll.selectPrizeLogResult(contentType,searchType,searchValue,startTime,endTime,pageIndex,pageSize);
    List<Object>  list = new ArrayList<>(); 
    list.add(map);
    list.add(sumMap);
    return PageBean.createPage(true, count, pageIndex, pageSize, list, "查询成功");
  }
}
