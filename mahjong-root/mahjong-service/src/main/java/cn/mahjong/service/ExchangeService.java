package cn.mahjong.service;

import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.ExchangeLogBll;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import java.util.List;

public interface ExchangeService {

  PageResultBean<List<ExchangeLog>> get(Integer userId, Integer gameUserId, int year, int month,
      int pageIndex);

  ResultBean<ExchangeSummary> getSummary(Integer userId, Integer gameUserId, int year, int month);

  ResultBean<Object> exchange(int userId, int gameUserId, int amount);

  void setGameService(GameService gameService);

  void setExchangeLogBll(ExchangeLogBll exchangeLogBll);

  void setUserInventoryBll(UserInventoryBll userInventoryBll);

  void setUserBll(UserBll userBll);
}
