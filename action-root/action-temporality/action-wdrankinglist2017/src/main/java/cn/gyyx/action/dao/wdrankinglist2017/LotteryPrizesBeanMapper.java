/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月10日 下午6:07:09
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wdrankinglist2017;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.wdrankinglist2017.LotteryPrizesBean;

/**
 * <p>
 * 抽奖相关Mapper
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface LotteryPrizesBeanMapper {
    /**
     * 
     * <p>
     * 查询用户抽奖次数
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午6:07:41 描述
     *
     * @param activityId
     * @param account
     * @param prizeCodeList
     * @return int
     */
    int getUserLotteryCount(@Param("activityId") int activityId,
            @Param("account") String account,
            @Param("prizeCodeList") List<Integer> prizeCodeList);

    /**
     * 
     * <p>
     * 获取抽奖页面滚动中奖列表数据（200条）
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:03:06 描述
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
     * @action laixiancai 2017年4月11日 上午9:03:10 描述
     *
     * @param openId
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryListByOpenId(
            @Param("openId") String openId);
}
