/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdblessingcard2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年3月13日 下午7:37:04
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdblessingcard2017;

import java.util.List;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdblessingcard2017.LotteryPrizesBean;
import cn.gyyx.core.auth.UserInfo;

/**
 * <p>
 * 中奖记录service
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public interface LotteryPrizesBeanService {

    /**
     * <p>
     * 获取自己的中奖记录
     * </p>
     *
     * @action niushuai 2017年3月12日 下午11:15:47 描述
     *
     * @param account
     * @param actionCode
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryListByAccount(String account,
            int actionCode);

    /**
     * <p>
     * 获取最新的几条中奖记录
     * </p>
     *
     * @action niushuai 2017年3月12日 下午11:15:27 描述
     *
     * @param hdCode
     * @param i
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryList(int hdCode, int i);

    /**
     * <p>
     * 抽奖
     * </p>
     *
     * @action niushuai 2017年3月15日 下午10:33:47 描述
     *
     * @return ResultBean<>
     */
    ResultBean<UserLotteryBean> doLottery(int activityId, UserInfo userInfo,
            String ip);

    /**
      * <p>
      * 获取用户中奖的数量
      * </p>
      *
      * @action
      *    niushuai 2017年3月17日 下午1:58:34 描述
      *
      * @param account
      * @param prizeType void
      */
    int prizeCount(String account, String prizeType);

}
