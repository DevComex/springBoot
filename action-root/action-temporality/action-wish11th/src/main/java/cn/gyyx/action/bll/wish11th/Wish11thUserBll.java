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
package cn.gyyx.action.bll.wish11th;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;
import cn.gyyx.action.dao.wish11th.Wish11thRoleBindDao;

/**
 * <p>
 * 许愿活动用户相关业务操作层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thUserBll {

    // 角色绑定相关数据访问层
    Wish11thRoleBindDao userDao = new Wish11thRoleBindDao();

    /**
     * <p>
     * 通过角色id获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:15:11 描述
     *
     * @param roleId
     * @return Wish11thLightBean
     */
    public Wish11thRoleBindBean getUserByRoleID(String roleId) {
        return userDao.getUserByRoleID(roleId);
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
        return userDao.getUserByUserID(userId);
    }

    /**
     * <p>
     * 添加角色绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午9:26:57 描述
     *
     * @param roleBindBean
     *            角色绑定实体
     * @return int 影响的行数
     */
    public int addRoleBind(Wish11thRoleBindBean roleBindBean) {
        return userDao.addRoleBind(roleBindBean);
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
       return userDao.updateUserConsumeScore(code, consumeScore);
    }
}
