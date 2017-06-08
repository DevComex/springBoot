/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 17:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wish11th;

import org.apache.ibatis.session.SqlSession;
import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 问道许愿活动角色绑定数据访问类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thRoleBindDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 通过角色获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:15:11 描述
     *
     * @param roleId
     * @return Wish11thLightBean
     */
    public Wish11thRoleBindBean getUserByRoleID(String roleId) {
        try (SqlSession session = getSession(true)) {
            Wish11thRoleBindBeanMapper mapper = session
                    .getMapper(Wish11thRoleBindBeanMapper.class);
            return mapper.getUserByRoleID(roleId);
        }
    }

    /**
     * <p>
     * 通过用户ID获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:15:11 描述
     *
     * @param userId
     * @return Wish11thLightBean
     */
    public Wish11thRoleBindBean getUserByUserID(int userId) {
        try (SqlSession session = getSession(true)) {
            Wish11thRoleBindBeanMapper mapper = session
                    .getMapper(Wish11thRoleBindBeanMapper.class);
            return mapper.getUserByUserID(userId);
        }
    }

    /**
     * 添加角色绑定信息
     * 
     * @param record
     *            角色绑定信息
     * @return 是否绑定成功
     */
    public int addRoleBind(Wish11thRoleBindBean record) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            Wish11thRoleBindBeanMapper mapper = session
                    .getMapper(Wish11thRoleBindBeanMapper.class);
            result = mapper.insertSelective(record);
            session.commit();
        } catch (Exception e) {
            logger.warn(e.toString());
            session.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    /**
     * <p>
     * 更新用户消费积分
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:34:58 描述
     *
     * @param code
     * @param consumeScore
     * @param sqlSession
     * @return int
     */
    public int updateUserConsumeScore(int code, int consumeScore) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            Wish11thRoleBindBeanMapper mapper = session
                    .getMapper(Wish11thRoleBindBeanMapper.class);
            result = mapper.updateUserConsumeScore(code, consumeScore);
            session.commit();
        } catch (Exception e) {
            logger.warn(e.toString());
            session.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }
}
