/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 15:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wish11th;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wish11th.Wish11thLotteryPrizesBean;

/**
 * <p>
 * 问道周年许愿活动抽奖日志接口类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public interface Wish11thLotteryPrizesBeanMapper {

    /**
     * <p>
     * 获取用户或ip抽取的奖品列表
     * </p>
     *
     * @action tanjunkai 2017年4月6日 下午8:34:40 描述
     *
     * @param userId
     *            用户ID
     * @param ip
     *            IP
     * @param actionCodeList
     *            活动code
     * @return Wish11thLotteryPrizesBean
     */
    List<Wish11thLotteryPrizesBean> getUserIPPrizeList(@Param("userid") String userid,
            @Param("ip") String ip,
            @Param("actioncodelist") List<Integer> actioncodelist);
    
    
    /**
     * <p>
     *    获取当前用户或ip中实物奖或银元宝的数量
     * </p>
     *
     * @action
     *    tanjunkai 2017年4月14日 上午1:49:34 描述
     *
     * @param userId
     * @param ip
     * @param actionCodeList
     * @return int
     */
    int getCountForRealOrSilver(@Param("userid") String userid,
            @Param("ip") String ip);
}
