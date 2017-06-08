package cn.gyyx.action.dao.wdblessingcard2017;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 用户角色绑定Dao
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class RoleBindBeanDao extends MyBatisBaseDAO {

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
    public void updateUserReceivedTitle(String account) {
        SqlSession sqlSession = this.getSession();
        try {
            RoleBindBeanMapper roleBindBeanMapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            roleBindBeanMapper.updateUserReceivedTitle(account);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 
     * <p>
     * OA后台-祝福卡审核通过后更新用户的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月12日 下午8:57:26 新增
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
        RoleBindBeanMapper roleBindBeanMapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);
        roleBindBeanMapper.updateUserLotteryTimes(account, lotteryTimes,
            remainingTimes, enableGetLotteryTimes);
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
        SqlSession sqlSession = this.getSession();
        try {
            RoleBindBeanMapper roleBindBeanMapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            roleBindBeanMapper.updateUserLotteryTimesAfterPlayGame(account,
                lotteryTimes, remainingTimes);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 通过用户的账号获取角色绑定信息
     * <p>
     * 通过用户的账号获取角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月10日 下午7:26:29 描述
     *
     * @param account
     * @return RoleBindBean
     */
    public RoleBindBean getRoleBindBeanByAccount(String account) {
        try (SqlSession sqlSession = this.getSession(true)) {
            RoleBindBeanMapper mapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            return mapper.getRoleBindBeanByAccount(account);
        }
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
        RoleBindBeanMapper mapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);
        return mapper.getRoleBindBeanByAccount(account);
    }

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月11日 上午10:19:08 描述
     *
     * @param roleBindBean
     * @return int
     */
    public int insertRoleBindBean(RoleBindBean roleBindBean) {
        int count = 0;
        try (SqlSession sqlSession = this.getSession()) {
            RoleBindBeanMapper mapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            count = mapper.insertSelective(roleBindBean);
            sqlSession.commit();
        }
        return count;
    }

    /**
     * <p>
     * 更新用户抽奖次数
     * </p>
     *
     * @action niushuai 2017年3月15日 下午1:37:13 描述
     *
     * @param roleBind
     * @return int
     */
    public int updateRemainingTimes(RoleBindBean roleBind, SqlSession session) {
        int count = 0;
        RoleBindBeanMapper mapper = session.getMapper(RoleBindBeanMapper.class);
        count = mapper.updateRemainingTimes(roleBind);
        return count;
    }

    /**
     * <p>
     * 查询角色是否已经绑定过
     * </p>
     *
     * @action niushuai 2017年3月17日 下午5:32:38 描述
     *
     * @param roleId
     * @return int
     */
    public int getRoleBindBeanCount(String roleId) {
        int count = 0;
        try (SqlSession sqlSession = this.getSession(true)) {
            RoleBindBeanMapper mapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            count = mapper.getRoleBindBeanCount(roleId);
            sqlSession.commit();
        }
        return count;
    }

}
