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
package cn.gyyx.action.dao.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdblessingcard2017.LotteryPrizesBean;
import cn.gyyx.action.ui.wdblessingcard2017.QueryBean;

/**
 * <p>
 * 中奖记录
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public interface LotteryPrizesBeanMapper {

    /**
     * <p>
     * 通过用户账户获得中奖信息
     * </p>
     *
     * @action niushuai 2017年3月11日 下午5:25:27 描述
     *
     * @param queryBean
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryListByAccount(QueryBean queryBean);

    /**
     * <p>
     * 获取最新的几条中奖记录
     * </p>
     *
     * @action niushuai 2017年3月12日 下午11:21:36 描述
     *
     * @param queryBean
     * @return List<LotteryPrizesBean>
     */
    List<LotteryPrizesBean> getLotteryList(QueryBean queryBean);

    /**
     * <p>
     * 统计某一用户已经抽到了某一活动的奖品数量
     * <br>相同账号或者相同ip都认为是同一用户
     * </p>
     *
     * @action niushuai 2017年3月14日 下午3:31:55 描述
     *
     * @param activityId
     * @param account
     * @param lotteryCodeList
     * @return int
     */
    int getUserLotteryCount(@Param("activityId") int activityId,
            @Param("account") String account, @Param("ip") String ip,
            @Param("prizeCodeList") List<Integer> prizeCodeList);

    /**
     * <p>
     * 查询用户获取的数量
     * </p>
     *
     * @action niushuai 2017年3月17日 下午2:08:30 描述
     *
     * @param account
     * @param prizeType
     * @param actionCode
     * @return int
     */
    int prizeCount(@Param("account") String account,
            @Param("prizeType") String prizeType,
            @Param("actionCode") Integer actionCode);

}
