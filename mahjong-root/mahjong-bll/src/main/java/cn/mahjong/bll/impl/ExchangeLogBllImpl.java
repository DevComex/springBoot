package cn.mahjong.bll.impl;

import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.bll.ExchangeLogBll;
import cn.mahjong.dao.ExchangeLogMapper;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeLogBllImpl implements ExchangeLogBll {

  @Autowired
  private ExchangeLogMapper mapper;

  @Override
  public ExchangeSummary getSummary(Integer userId, Integer gameUserId, Date start, Date end) {
    return mapper.selectSummaryByUserIdGameUserId(userId, gameUserId, start, end);
  }

  @Override
  public void add(ExchangeLog log) {
    mapper.insert(log);
  }

  @Override
  public List<ExchangeLog> get(Integer userId, Integer gameUserId, Date start,
      Date end, int pageIndex) {
    int take = 10;
    int skip = take * (pageIndex - 1);
    return mapper.selectByUserIdGameUserId(userId, gameUserId, start, end, skip, take);
  }
}
