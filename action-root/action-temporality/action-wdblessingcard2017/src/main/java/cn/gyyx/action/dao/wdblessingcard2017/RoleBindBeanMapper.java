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

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;

public interface RoleBindBeanMapper {

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月11日 下午6:19:36 描述
     *
     * @param record
     * @return int
     */
    int insertSelective(RoleBindBean record);

    /**
     * 
     * <p>
     * 更新玩家已领取过游戏称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午12:34:33 新增
     *
     * @param account
     *            void
     */
    void updateUserReceivedTitle(@Param("account") String account);

    /**
     * 
     * <p>
     * OA后台-祝福卡审核通过后更新用户的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午8:54:49 新增
     *
     * @param account
     * @param lotteryTimes
     * @param remainingTimes
     * @param enableGetLotteryTimes
     *            void
     */
    void updateUserLotteryTimes(@Param("account") String account,
            @Param("lotteryTimes") int lotteryTimes,
            @Param("remainingTimes") int remainingTimes,
            @Param("enableGetLotteryTimes") Boolean enableGetLotteryTimes);

    /**
     * 
     * <p>
     * 玩家前台-玩家玩了拼图游戏后更新玩家的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午8:54:49 新增
     *
     * @param account
     * @param lotteryTimes
     * @param remainingTimes
     *            void
     */
    void updateUserLotteryTimesAfterPlayGame(@Param("account") String account,
            @Param("lotteryTimes") int lotteryTimes,
            @Param("remainingTimes") int remainingTimes);

    /**
     * <p>
     * 根据用户账号获取角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月10日 下午7:32:33 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByAccount(String account);

    /**
     * <p>
     * 更新用户点赞数（专用）
     * </p>
     *
     * @action niushuai 2017年3月12日 下午8:30:11 描述
     *
     * @param roleBind
     *            void
     */
    void updateUpvoteTimes(RoleBindBean roleBind);

    /**
     * <p>
     * 更新抽奖次数
     * </p>
     *
     * @action niushuai 2017年3月15日 下午1:44:15 描述
     *
     * @param roleBind
     * @return int
     */
    int updateRemainingTimes(RoleBindBean roleBind);

    /**
      * <p>
      * 统计该角色的绑定次数
      * </p>
      *
      * @action
      *    niushuai 2017年3月17日 下午5:33:22 描述
      *
      * @param roleId
      * @return int
      */
    int getRoleBindBeanCount(String roleId);

}