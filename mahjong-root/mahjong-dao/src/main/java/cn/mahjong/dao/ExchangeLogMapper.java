package cn.mahjong.dao;

import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.common.PageResultBean;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExchangeLogMapper {

  ExchangeSummary selectSummaryByUserIdGameUserId(@Param("userId") Integer userId,
      @Param("gameUserId") Integer gameUserId, @Param("start") Date start, @Param("end") Date end);

  void insert(ExchangeLog log);

  List<ExchangeLog> selectByUserIdGameUserId(@Param("userId") Integer userId,
      @Param("gameUserId") Integer gameUserId, @Param("start") Date start, @Param("end") Date end,
      @Param("skip") int skip, @Param("take") int take);
}
