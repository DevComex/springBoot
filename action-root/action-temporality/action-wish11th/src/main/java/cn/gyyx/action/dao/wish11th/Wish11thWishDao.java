/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/5 10:28
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wish11th;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishInfoBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 许愿先关数据访问层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thWishDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 根据用户ID获取用户所有许愿内容
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:29:08 描述
     *
     * @param userId
     *            用户ID
     * @return List<Wish11thWishBean> 许愿列表
     */
    public List<Wish11thWishBean> getMyWishAll(int userId) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getMyWishAll(userId);
        }
    }

    /**
     * <p>
     * 获取指定层的许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:43:34 描述
     *
     * @param level
     *            层级
     * @return List<Wish11thWishBean> 许愿列表
     */
    public List<Wish11thWishBean> getWishsBylevel(int level, int status) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getWishsBylevel(level, status);
        }
    }

    /**
     * <p>
     * 获取指定层的前N条许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月6日 上午11:55:24 描述
     *
     * @param topCount
     * @param level
     * @param status
     * @return List<Wish11thWishBean>
     */
    public List<Wish11thWishBean> getTopWishsBylevel(int topCount, int level,
            int status) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getTopWishsBylevel(topCount, level, status);
        }
    }

    /**
     * <p>
     * 获取当前层数所有许愿通过的人数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:48:22 描述
     *
     * @param level
     *            层级
     * @return int 获取当前层数所有许愿通过的人数
     */
    public int getWishUserCountByLevel(int level, int status) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getWishUserCountByLevel(level, status);
        }
    }

    /**
     * <p>
     * 获取当前层指定状态的的许愿或抽奖次数(未过滤user)
     * </p>
     *
     * @action tanjunkai 2017年4月8日 下午3:35:53 描述
     *
     * @param level
     *            层
     * @param status
     *            状态
     * @return int
     */
    public int getWishCountByLevel(int level, int status) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getWishCountByLevel(level, status);
        }
    }

    /**
     * <p>
     * 获取用户在当前层的许愿次数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午2:29:06 描述
     *
     * @param userId
     *            用户ID
     * @param level
     *            当前层数
     * @return int
     */
    public int getUserWishNumByLevel(int userId, int level) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getUserWishNumByLevel(userId, level);
        }
    }

    /**
     * <p>
     * 添加许愿
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:46:53 描述
     *
     * @param bean
     *            void
     */
    public int addWish(Wish11thWishBean record) {

        SqlSession session = getSession(false);
        int result = 0;
        try {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
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
     * 获取所有许愿并中大奖的许愿信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午5:59:24 描述
     *
     * @param num
     * @return List<Wish11thWishBean>
     */
    public List<Wish11thWishInfoBean> findWishAll(int num) {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.findWishAll(num);
        }
    }

    /**
     * <p>
     * 获取所有许愿获奖的数量
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午7:02:04 描述
     *
     * @return int
     */
    public int getAllWishCount() {
        try (SqlSession session = getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getAllWishCount();
        }
    }

    /**
     * <p>
     * 审核许愿内容
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午8:11:41 描述
     *
     * @param code
     *            主键
     * @param status
     *            状态
     * @return int
     */
    public int wishAudit(String code, int status) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            result = mapper.wishAudit(code, status);
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
     * 通过条件获取许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午9:11:41 描述
     *
     * @param beginTime
     *            许愿时间
     * @param endTime
     *            许愿时间
     * @param roleName
     *            角色名
     * @param checkStatus
     *            审核状态
     * @param pageSize
     *            每页显示的条数
     * @param pageNo
     *            页码
     * @return List<Wish11thWishBean>
     */
    public List<Wish11thWishBean> getWishList(String beginTime, String endTime,
            String roleName, String checkStatus, int pageSize, int pageNo) {
        try (SqlSession session = this.getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getWishList(beginTime, endTime, roleName, checkStatus,
                pageSize, pageNo);
        }
    }

    /**
     * <p>
     * 通过条件获取许愿列表数量
     * </p>
     *
     * @action tanjunkai 2017年4月7日 下午9:13:16 描述
     *
     * @param beginTime
     *            许愿时间
     * @param endTime
     *            许愿时间
     * @param roleName
     *            角色名
     * @param checkStatus
     *            审核状态
     * @return int 列表条数
     */
    public int getWishListCount(String beginTime, String endTime,
            String roleName, String checkStatus) {
        try (SqlSession session = this.getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            return mapper.getWishListCount(beginTime, endTime, roleName,
                checkStatus);
        }
    }

    /**
     * <p>
     * 判断用户是否中过实物奖
     * </p>
     *
     * @action tanjunkai 2017年4月12日 下午5:02:28 描述
     *
     * @param userId
     *            用户ID
     * @return Boolean
     */
    public Wish11thWishBean getLotteryRealPrize(int userId) {
        try (SqlSession session = this.getSession(true)) {
            Wish11thWishBeanMapper mapper = session
                    .getMapper(Wish11thWishBeanMapper.class);
            Wish11thWishBean bean = mapper.getLotteryRealPrize(userId);
            return bean;
        }
    }
}
