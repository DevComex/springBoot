/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：niushuai
 * @联系方式：niushuai@gyyx.cn
 * @创建时间：2017年3月10日 下午12:10:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdblessingcard2017;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;

/**
 * <p>
 * 角色绑定的业务层
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public interface RoleBindBeanBll {

    /**
     * 
     * <p>
     * 更新玩家已领取过游戏称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午12:36:12 新增
     *
     * @param account
     *            void
     */
    void updateUserReceivedTitle(String account);

    /**
     * 
     * <p>
     * OA后台-祝福卡审核通过后更新用户的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午9:03:38 新增
     *
     * @param account
     * @param lotteryTimes
     * @param remainingTimes
     * @param enableGetLotteryTimes
     * @param sqlSession
     *            void
     */
    void updateUserLotteryTimes(String account, int lotteryTimes,
            int remainingTimes, Boolean enableGetLotteryTimes,
            SqlSession sqlSession);

    /**
     * 
     * <p>
     * 玩家前台-玩家玩了拼图游戏后更新玩家的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午8:57:26 新增
     *
     * @param account
     * @param lotteryTimes
     * @param remainingTimes
     *            void
     */
    void updateUserLotteryTimesAfterPlayGame(String account, int lotteryTimes,
            int remainingTimes);

    /**
     * <p>
     * 根据用户账号获取角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月13日 下午8:30:44 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByAccount(String account);

    /**
     * 
     * <p>
     * 根据账号获取用户绑定信息
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午8:26:39 新增
     *
     * @param account
     * @param sqlSession
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByAccount(String account, SqlSession sqlSession);

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月13日 下午8:29:57 描述
     *
     * @param roleBindBean
     * @return int
     */
    int insertRoleBindBean(RoleBindBean roleBindBean);

    /**
     * <p>
     * 更新抽奖次数
     * </p>
     *
     * @action niushuai 2017年3月15日 下午1:35:26 描述
     *
     * @param roleBind
     * @return int
     */
    int updateRemainingTimes(RoleBindBean roleBind, SqlSession session);

    /**
      * <p>
      * 统计该角色的绑定次数
      * </p>
      *
      * @action
      *    niushuai 2017年3月17日 下午5:31:40 描述
      *
      * @param roleId
      * @return int
      */
    int getRoleBindBeanCount(String roleId);

}
