/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月10日 下午5:06:21
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017;

import java.util.List;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdrankinglist2017.LotteryPrizesBean;

/**
 * <p>
 * 抽奖相关Service
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface LotteryPrizesBeanService {

    /**
     * 
     * <p>
     * 抽奖
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午5:07:38 描述
     *
     * @param openId
     * @param ip
     * @return ResultBean<UserLotteryBean>
     */
    ResultBean<UserLotteryBean> doLottery(String openId, String ip);

    /**
     * 
     * <p>
     * 获取抽奖页面滚动中奖列表数据（200条）
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:00:39 描述
     *
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryList();

    /**
     * 
     * <p>
     * 根据openId获取当前玩家的中奖记录
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:00:43 描述
     *
     * @param openId
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryListByOpenId(String openId);
}
