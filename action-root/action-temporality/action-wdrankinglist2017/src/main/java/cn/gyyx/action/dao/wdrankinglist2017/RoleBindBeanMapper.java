package cn.gyyx.action.dao.wdrankinglist2017;

import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;

/**
 * 
 * <p>
 * 玩家账号绑定信息Mapper
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public interface RoleBindBeanMapper {

    /**
     * <p>
     * 查询某账号的绑定信息
     * </p>
     *
     * @action niushuai 2017年4月6日 上午9:44:43 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByOpenId(String openId);

    /**
     * <p>
     * 统计账号绑定信息
     * </p>
     *
     * @action niushuai 2017年4月6日 上午9:49:32 描述
     *
     * @param roleId
     * @return int
     */
    int getRoleBindCountByRoleId(String roleId);

    /**
     * <p>
     * 添加账号绑定
     * </p>
     *
     * @action niushuai 2017年4月6日 上午10:19:03 描述
     *
     * @param roleBindBean
     * @return int
     */
    int insertRoleBind(RoleBindBean roleBindBean);

    /**
     * <p>
     * 根据账号查询绑定信息
     * </p>
     *
     * @action niushuai 2017年4月8日 下午3:53:47 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getRoleBindBeanByAccount(String account);

    /**
     * 
     * <p>
     * OA后台-宣言审核拒绝后更新用户的被拒绝次数
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午1:17:16 描述
     *
     * @param account
     * @param refusedCount
     *            void
     */
    void updateUserRefusedCount(@Param("account") String account,
            @Param("refusedCount") int refusedCount);

    /**
     * 
     * <p>
     * OA后台-根据account查询用户绑定信息
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:22:32 描述
     *
     * @param account
     * @return RoleBindBean
     */
    RoleBindBean getUserBindByAccount(@Param("account") String account);

    /**
     * 
     * <p>
     * 更新用户抽奖时间
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午6:35:17 描述
     *
     * @param openId
     * @param lotteryTime
     *            void
     */
    void updateUserLotteryTime(@Param("openId") String openId,
            @Param("lotteryTime") String lotteryTime);

}