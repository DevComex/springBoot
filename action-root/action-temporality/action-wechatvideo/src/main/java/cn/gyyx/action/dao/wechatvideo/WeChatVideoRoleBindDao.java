/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/14 14:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechatvideo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 角色绑定数据访问层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
/**
 * <p>
 * WeChatVideoRoleBindDao描述
 * </p>
 * 
 * @author Administrator
 * @since 0.0.1
 */
public class WeChatVideoRoleBindDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 通过UserId获取角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月14日 下午8:35:24 描述
     *
     * @param userId
     *            用户ID
     * @return WeChatVideoRoleBindBean
     */
    public WeChatVideoRoleBindBean selectByUserId(Integer userId) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
            return mapper.selectByUserId(userId);
        }
    }

    /**
     * 根据UserId或roleId获取角色绑定信息
     * 
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @return
     */
    public WeChatVideoRoleBindBean selectById(Integer userId, String roleId) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
            return mapper.selectById(userId, roleId);
        }
    }

    /**
     * <p>
     * 判断用户是否已绑定区组角色
     * </p>
     *
     * @action tanjunkai 2017年3月17日 上午11:37:15 描述
     *
     * @param openId
     *            微信OpenId
     * @return WeChatVideoRoleBindBean
     */
    public WeChatVideoRoleBindBean selectByOpenId(String openId) {
        try (SqlSession session = getSession(true)) {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
            return mapper.selectByOpenId(openId);
        }
    }

    /**
     * 添加角色绑定信息
     * 
     * @param record
     *            角色绑定信息
     * @return 是否绑定成功
     */
    public int addRoleBind(WeChatVideoRoleBindBean record) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
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
     * 更新角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年3月17日 下午5:58:22 描述
     *
     * @param record
     *            角色绑定信息
     * @return int 影响的行数
     */
    public int updateByPrimaryKey(WeChatVideoRoleBindBean record) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
            result = mapper.updateByPrimaryKeySelective(record);
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
     * 增加抽奖机会
     * </p>
     *
     * @action tanjunkai 2017年3月29日 下午6:32:27 描述
     *
     * @param userId
     * @return int
     */
    public int addLotteryTimes(int userId) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
            result = mapper.addLotteryTimes(userId);
            if (result <= 0)
                session.rollback();
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
     * 减少用户抽奖机会
     * </p>
     *
     * @action tanjunkai 2017年3月29日 下午6:32:27 描述
     *
     * @param userId
     * @return int
     */
    public int reduceLotteryTimes(int userId) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            WeChatVideoRoleBindBeanMapper mapper = session
                    .getMapper(WeChatVideoRoleBindBeanMapper.class);
            result = mapper.reduceLotteryTimes(userId);
            if (result <= 0)
                session.rollback();
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
