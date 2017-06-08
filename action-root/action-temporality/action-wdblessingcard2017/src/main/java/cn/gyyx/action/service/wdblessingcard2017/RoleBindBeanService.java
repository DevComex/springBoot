/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月10日 下午12:10:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdblessingcard2017;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;

/**
 * <p>
 * 用户角色信息绑定Service接口
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface RoleBindBeanService {

    /**
     * <p>
     * 通过用户的账号获取角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月10日 下午7:26:29 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByAccount(String account);

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月11日 上午10:17:09 描述
     *
     * @param roleBindBean
     *            void
     */
    int insertRoleBindBean(RoleBindBean roleBindBean);

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
     * 更新抽奖次数
     * </p>
     *
     * @action niushuai 2017年3月15日 下午1:36:06 描述
     *
     * @param roleBind
     * @return int
     */
    int updateRemainingTimes(RoleBindBean roleBind, SqlSession session);

    /**
     * <p>
     * 获取用户的地址信息
     * </p>
     *
     * @action niushuai 2017年3月16日 下午3:03:42 描述
     *
     * @param account
     * @return AddressBean
     */
    LotteryPrizesVO getAddressByAccount(String account, int actionCode, int userId);

    /**
      * <p>
      * 统计该角色的绑定次数
      * </p>
      *
      * @action
      *    niushuai 2017年3月17日 下午5:29:28 描述
      *
      * @param roleId
      * @return int
      */
    int getRoleBindBeanCount(String roleId);

    /**
      * <p>
      * 验证角色是否在所选的区服里
      * </p>
      *
      * @action
      *    niushuai 2017年3月28日 上午11:42:09 描述
      *
      * @param account
      * @param serverCode
      * @param roleId void
     */
    ResultBean<String> validateRole(String account, int serverCode, String roleId);

}
