package cn.mahjong.service.impl;

import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.common.Common;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.RechargeLogBll;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.service.RechargeService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RechargeServiceImpl implements RechargeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RechargeServiceImpl.class);
  @Autowired
  private RechargeLogBll rechargeLogBll;
  @Autowired
  private UserBll userBll;
  @Autowired
  private UserInventoryBll userInventoryBll;

  public void setRechargeLogBll(RechargeLogBll bll) {
    this.rechargeLogBll = bll;
  }

  public void setUserBll(UserBll userBll) {
    this.userBll = userBll;
  }

  public void setUserInventoryBll(UserInventoryBll userInventoryBll) {
    this.userInventoryBll = userInventoryBll;
  }

  @Override
  public PageResultBean<List<RechargeLog>> get(int userId, int year, int month, int pageIndex) {
    Interval interval = Common.getMonthInterval(year, month);
    List<RechargeLog> rechargeLogs = rechargeLogBll
        .get(userId, interval.getStart().toDate(), interval.getEnd().toDate(), pageIndex);
    int count = rechargeLogBll.count(userId, interval.getStart().toDate(), interval.getEnd().toDate());
    return new PageResultBean<>(true, "获取成功", rechargeLogs, count);
  }

  @Override
  public ResultBean<RechargeSummary> getSummary(int userId, int year, int month) {
    Interval interval = Common.getMonthInterval(year, month);
    RechargeSummary summary = rechargeLogBll
        .getSummary(userId, interval.getStart().toDate(), interval.getEnd().toDate());
    return new ResultBean<>(true, "获取成功", summary);
  }

  @Transactional
  @Override
  public ResultBean<Void> recharge(int amount, int gift, int userId, int operatorId) {
    LOGGER.debug("操作人: " + userId);

    if (amount < 0 || gift < 0 || (amount == 0 && gift == 0) || userId <= 0 || operatorId <= 0) {
      return new ResultBean<>(false, "参数错误", null);
    }

    UserBean user = userBll.get(userId);
    if (user == null) {
      return new ResultBean<>(false, "用户不存在", null);
    }

    List<UserBean> parentUsers = userBll.getParentUsers(user.getCode());
    Optional<UserBean> optional = parentUsers.stream().filter(u -> u.getCode() == operatorId)
        .findAny();
    LOGGER.debug("获取上级用户列表: " + parentUsers);
    if (!optional.isPresent()) {
      return new ResultBean<>(false, "无权限", null);
    }

    UserInventory userInventory = userInventoryBll.get(operatorId);
    LOGGER.debug("库存信息: " + userInventory);
    if (userInventory.getGiftInventory() + userInventory.getInventory() < amount + gift) {
      return new ResultBean<>(false, "库存不足", null);
    }
    LOGGER.debug("增加用户库存， amount: " + amount + ", gift: " + gift);
    userInventoryBll.increase(userId, amount, gift);
    LOGGER.debug("减少用户库存， amount: " + (amount + gift));
    userInventoryBll.decrease(operatorId, amount + gift);
    RechargeLog log = new RechargeLog(amount, gift, operatorId, optional.get().getAccount(),
        user.getCode(), user.getAccount(), new Date());
    rechargeLogBll.insert(log);
    LOGGER.debug("充值操作完成");
    return new ResultBean<>(true, "操作成功", null);
  }
}
