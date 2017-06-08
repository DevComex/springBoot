package cn.mahjong.bll;

import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.common.PageResultBean;
import java.util.Date;
import java.util.List;

public interface ExchangeLogBll {

  ExchangeSummary getSummary(Integer userId, Integer gameUserId, Date start, Date end);

  void add(ExchangeLog log);

  List<ExchangeLog> get(Integer userId, Integer gameUserId, Date start, Date end,
      int pageIndex);
}
