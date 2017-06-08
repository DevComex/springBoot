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
package cn.gyyx.action.dao.wish11th;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wish11th.Wish11thLightBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

public class Wish11thLightDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 获取蛋糕的所有层数信息
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午4:15:16 描述
     *
     * @return List<Wish11thLightBean>
     */
    public List<Wish11thLightBean> getAllLights() {
        try (SqlSession session = getSession(true)) {
            Wish11thLightBeanMapper mapper = session
                    .getMapper(Wish11thLightBeanMapper.class);
            return mapper.getAllLights();
        }
    }

    
    /**
      * <p>
      *   通过层数获取蛋糕层
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月8日 上午10:04:48 描述
      *
      * @param actionCode 活动ID
      * @param level      许愿层
      * @return Wish11thLightBean
      */
    public Wish11thLightBean getLightByLevel(int level) {
        try (SqlSession session = getSession(true)) {
            Wish11thLightBeanMapper mapper = session
                    .getMapper(Wish11thLightBeanMapper.class);
            return mapper.getLightByLevel(level);
        }
    }

    /**
     * <p>
     * 更新当前蛋糕层的点亮状态
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午4:27:37 描述
     *
     * @param lightBean
     *            void
     */
    public int updateLightType(int code,int actionCode, int lightType) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            Wish11thLightBeanMapper mapper = session
                    .getMapper(Wish11thLightBeanMapper.class);
            result = mapper.updateLightType(code,actionCode, lightType);
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
     * 更新当前蛋糕层的点亮人数
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午4:27:37 描述
     *
     * @param lightBean
     *            void
     */
    public int updateLightLimitNum(int actionCode,int level, int limitNum) {
        SqlSession session = getSession(false);
        int result = 0;
        try {
            Wish11thLightBeanMapper mapper = session
                    .getMapper(Wish11thLightBeanMapper.class);
            result = mapper.updateLightLimit(actionCode,level, limitNum);
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
