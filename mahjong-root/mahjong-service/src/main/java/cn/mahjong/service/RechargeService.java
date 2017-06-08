package cn.mahjong.service;

import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import java.util.List;

public interface RechargeService {

  PageResultBean<List<RechargeLog>> get(int userId, int year, int month, int pageIndex);

  ResultBean<RechargeSummary> getSummary(int userId, int year, int month);

  /**
   * （为下级用户充值）
   * @param amount 充值数量
   * @param gift 赠送数量
   * @param userId 被充值用户ID
   * @param operatorId 充值用户ID
   */
  ResultBean<Void> recharge(int amount, int gift, int userId, int operatorId);
}
