/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/17 18:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechatvideo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface  WeChatVideoLotteryPrizesBeanMapper {


    /**
      * <p>
      *    统计某一用户已经抽到了某一活动的奖品数量
      * </p>
      *
      * @action
      *    Administrator 2017年3月17日 下午5:45:16 描述
      *
      * @param activityId   活动ID
      * @param userId       用户ID
      * @param prizeCodeList 奖品码
      * @return int
      */
    int getUserLotteryCount(@Param("activityId") int activityId,
            @Param("userId") int userId,
            @Param("prizeCodeList") List<Integer> prizeCodeList);

}
