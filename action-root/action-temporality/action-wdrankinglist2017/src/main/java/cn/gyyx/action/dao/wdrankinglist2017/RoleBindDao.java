/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月6日 上午9:23:38
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wdrankinglist2017;

import org.apache.ibatis.session.SqlSession;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 账号绑定dao
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class RoleBindDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 查询某账号的账号绑定信息
     * </p>
     *
     * @action niushuai 2017年4月6日 上午9:40:09 描述
     *
     * @param account
     * @return RoleBindBean
     */
    public RoleBindBean getRoleBindBeanByOpenId(String openId) {
        try (SqlSession sqlSession = this.getSession(true)) {
            RoleBindBeanMapper roleBindBeanMapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            return roleBindBeanMapper.getRoleBindBeanByOpenId(openId);
        }
    }

    /**
     * 
     * <p>
     * 查询某账号的账号绑定信息（重载）
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午6:55:45 描述
     *
     * @param openId
     * @param sqlSession
     * @return RoleBindBean
     */
    public RoleBindBean getRoleBindBeanByOpenId(String openId,
            SqlSession sqlSession) {
        RoleBindBeanMapper roleBindBeanMapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);
        return roleBindBeanMapper.getRoleBindBeanByOpenId(openId);
    }

    /**
     * <p>
     * 添加账号绑定
     * </p>
     *
     * @action niushuai 2017年4月6日 上午10:18:50 描述
     *
     * @param roleBindBean
     * @return int
     */
    public int insertRoleBind(RoleBindBean roleBindBean) {
        try (SqlSession sqlSession = this.getSession(false)) {
            RoleBindBeanMapper roleBindBeanMapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            int count = roleBindBeanMapper.insertRoleBind(roleBindBean);
            sqlSession.commit();
            return count;
        }
    }

    /**
     * <p>
     * 根据账号查询绑定信息
     * </p>
     *
     * @action niushuai 2017年4月8日 下午3:53:07 描述
     *
     * @param account
     * @return RoleBindBean
     */
    public RoleBindBean getRoleBindBeanByAccount(String account) {
        try (SqlSession sqlSession = this.getSession(true)) {
            RoleBindBeanMapper roleBindBeanMapper = sqlSession
                    .getMapper(RoleBindBeanMapper.class);
            return roleBindBeanMapper.getRoleBindBeanByAccount(account);
        }
    }

    /**
     * 
     * <p>
     * OA后台-宣言审核拒绝后更新用户的被拒绝次数
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午1:19:56 描述
     *
     * @param account
     * @param refusedCount
     * @param sqlSession
     *            void
     */
    public void updateUserRefusedCount(String account, int refusedCount,
            SqlSession sqlSession) {
        RoleBindBeanMapper roleBindBeanMapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);
        roleBindBeanMapper.updateUserRefusedCount(account, refusedCount);
    }

    /**
     * 
     * <p>
     * OA后台-根据account查询用户绑定信息
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午2:25:26 描述
     *
     * @param account
     * @param sqlSession
     * @return RoleBindBean
     */
    public RoleBindBean getUserBindByAccount(String account,
            SqlSession sqlSession) {
        RoleBindBeanMapper roleBindBeanMapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);
        return roleBindBeanMapper.getUserBindByAccount(account);
    }

    /**
     * 
     * <p>
     * 更新用户抽奖时间
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午6:36:53 描述
     *
     * @param openId
     * @param lotteryTime
     * @param sqlSession
     *            void
     */
    public void updateUserLotteryTime(String openId, String lotteryTime,
            SqlSession sqlSession) {
        RoleBindBeanMapper roleBindBeanMapper = sqlSession
                .getMapper(RoleBindBeanMapper.class);
        roleBindBeanMapper.updateUserLotteryTime(openId, lotteryTime);
    }

}
