package cn.mahjong.service.impl;

import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.GameUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.common.Common;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.ExchangeLogBll;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.service.ExchangeService;
import cn.mahjong.service.GameService;
import java.util.Date;
import java.util.List;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

  @Autowired
  private GameService gameService;
  @Autowired
  private ExchangeLogBll exchangeLogBll;
  @Autowired
  private UserInventoryBll userInventoryBll;
  @Autowired
  private UserBll userBll;

  @Override
  public PageResultBean<List<ExchangeLog>> get(Integer userId, Integer gameUserId, int year,
      int month, int pageIndex) {
    Interval interval = Common.getMonthInterval(year, month);
    List<ExchangeLog> logs = exchangeLogBll.get(userId, gameUserId, interval.getStart().toDate(),
        interval.getEnd().toDate(), pageIndex);
    return new PageResultBean<>(true, "获取成功", logs);
  }

  @Override
  public ResultBean<ExchangeSummary> getSummary(Integer userId, Integer gameUserId, int year,
      int month) {
    Interval interval = Common.getMonthInterval(year, month);
    ExchangeSummary summary = exchangeLogBll
        .getSummary(userId, gameUserId, interval.getStart().toDate(), interval.getEnd().toDate());
    return new ResultBean<>(true, "获取成功", summary);
  }

  @Override
  @Transactional
  public ResultBean<Object> exchange(int userId, int gameUserId, int amount) {
    if (userId <= 0 || gameUserId <= 0 || amount <= 0) {
      return new ResultBean<>(false, "参数错误", null);
    }

    UserBean userBean = userBll.get(userId);
    if (userBean == null) {
      return new ResultBean<>(false, "用户不存在", null);
    }

    UserInventory inventory = userInventoryBll.get(userId);
    if (inventory.getInventory() + inventory.getGiftInventory() < amount) {
      return new ResultBean<>(false, "库存不足", null);
    }

    ResultBean<GameUserInfo> gameUserInfoResultBean = gameService.getUserInfo(gameUserId);
    if (!gameUserInfoResultBean.getIsSuccess() || gameUserInfoResultBean.getData() == null) {
      return new ResultBean<>(false, "游戏用户不存在", null);
    }

    ExchangeLog log = new ExchangeLog();
    log.setOperator(userId);
    log.setAmount(amount);
    log.setCreateTime(new Date());
    log.setGameUserId(gameUserId);
    log.setGameNickName(gameUserInfoResultBean.getData().getNickName());
    log.setOperatorAccount(userBean.getAccount());
    userInventoryBll.decrease(userId, amount);
    // todo: call game interface
    ResultBean<Object> resultBean = gameService.exchange(userId, amount);
    log.setStatus(String.valueOf(resultBean.getIsSuccess()));
    exchangeLogBll.add(log);
    if (resultBean.getIsSuccess()) {
      return new ResultBean<>(true, "操作成功", null);
    } else {
      return new ResultBean<>(false, "操作失败", null);
    }
  }

  @Override
  public void setGameService(GameService gameService) {
    this.gameService = gameService;
  }

  @Override
  public void setExchangeLogBll(ExchangeLogBll exchangeLogBll) {
    this.exchangeLogBll = exchangeLogBll;
  }

  @Override
  public void setUserInventoryBll(UserInventoryBll userInventoryBll) {
    this.userInventoryBll = userInventoryBll;
  }

  @Override
  public void setUserBll(UserBll userBll) {
    this.userBll = userBll;
  }
}
