/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/25 20:17
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.activity;

import cn.gyyx.action.beans.activity.ActivityConfigBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * <p>
 * 描述:activity 活动配置持久层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class ActivityConfigDao {
    SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

    public int insert(ActivityConfigBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            ActivityConfigMapper mapper = session.getMapper(ActivityConfigMapper.class);
            mapper.insertSelective(bean);
            return bean.getCode() == null ? 0 : bean.getCode();
        }
    }

    public boolean updateByActivityCode(ActivityConfigBean activityConfigBean) {
        try (SqlSession session = factory.openSession(true)) {
            ActivityConfigMapper mapper = session.getMapper(ActivityConfigMapper.class);
            return mapper.updateByActivityCode(activityConfigBean) > 0;
        }
    }

    public ActivityConfigBean selectBeanByActivityCode(String activityCode) {
        try (SqlSession session = factory.openSession()) {
            ActivityConfigMapper mapper = session.getMapper(ActivityConfigMapper.class);
            return mapper.selectBeanByActivityCode(activityCode);
        }
    }
}
