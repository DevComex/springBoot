/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月9日 上午10:50:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdblessingcard2017.impl;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.bll.wdblessingcard2017.RoleBindBeanBll;
import cn.gyyx.action.dao.wdblessingcard2017.RoleBindBeanDao;

/**
 * <p>
 * 用户角色信息绑定Bll
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class RoleBindBeanBllImpl implements RoleBindBeanBll {

    RoleBindBeanDao roleBindBeanDao = new RoleBindBeanDao();

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
    public void updateUserLotteryTimes(String account, int lotteryTimes,
            int remainingTimes, Boolean enableGetLotteryTimes,
            SqlSession sqlSession) {
        roleBindBeanDao.updateUserLotteryTimes(account, lotteryTimes,
            remainingTimes, enableGetLotteryTimes, sqlSession);
    }

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
    public void updateUserLotteryTimesAfterPlayGame(String account,
            int lotteryTimes, int remainingTimes) {
        roleBindBeanDao.updateUserLotteryTimesAfterPlayGame(account,
            lotteryTimes, remainingTimes);
    }

    @Override
    public RoleBindBean getRoleBindBeanByAccount(String account) {
        return roleBindBeanDao.getRoleBindBeanByAccount(account);
    }

    /**
     * 
     * <p>
     * 更新玩家已领取过游戏称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午12:36:12 新增
     *
     * @param account
     * @param sqlSession
     *            void
     */
    public void updateUserReceivedTitle(String account) {
        roleBindBeanDao.updateUserReceivedTitle(account);
    }

    /**
     * 
     * <p>
     * 通过用户的账号获取角色绑定信息，添加方法重载参数SqlSession
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午8:34:59 新增
     *
     * @param account
     * @param sqlSession
     * @return RoleBindBean
     */
    public RoleBindBean getRoleBindBeanByAccount(String account,
            SqlSession sqlSession) {
        return roleBindBeanDao.getRoleBindBeanByAccount(account, sqlSession);
    }

    @Override
    public int insertRoleBindBean(RoleBindBean roleBindBean) {
        return roleBindBeanDao.insertRoleBindBean(roleBindBean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.bll.wdblessingcard2017.RoleBindBeanBll#
     * updateRemainingTimes(cn.gyyx.action.beans.wdblessingcard2017.
     * RoleBindBean)
     */
    @Override
    public int updateRemainingTimes(RoleBindBean roleBind, SqlSession session) {
        return roleBindBeanDao.updateRemainingTimes(roleBind, session);
    }

    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.wdblessingcard2017.RoleBindBeanBll#getRoleBindBeanCount(java.lang.String)
     */
    @Override
    public int getRoleBindBeanCount(String roleId) {
        return roleBindBeanDao.getRoleBindBeanCount(roleId);
    }

}
