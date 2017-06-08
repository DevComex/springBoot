/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/23 10:27
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceServerBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 描述:新手卡后台-服务器持久层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceServerDao {
    SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

    public int insert(NoviceServerBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            mapper.insertSelective(bean);
            return bean.getCode() == null ? 0 : bean.getCode();
        }
    }

    public boolean updateByCode(NoviceServerBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            return mapper.updateByPrimaryKeySelective(bean) > 0;
        }
    }

    public NoviceServerBean selectByCode(int code) {
        try (SqlSession session = factory.openSession()) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    public List<NoviceServerBean> selectByBatchId(int batchId) {
        try (SqlSession session = factory.openSession()) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            return mapper.selectByBatchId(batchId);
        }
    }

    public NoviceServerBean selectByServerId(int serverId, int gameId) {
        try (SqlSession session = factory.openSession()) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            return mapper.selectByServerId(serverId, gameId);
        }
    }

    public NoviceServerBean selectMaxServer(int gameId) {
        try (SqlSession session = factory.openSession()) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            return mapper.selectMaxServer(gameId);
        }
    }

    public List<Integer> selectServerIdsByGameId(int gameId) {
        try (SqlSession session = factory.openSession()) {
            NoviceServerBeanMapper mapper = session.getMapper(NoviceServerBeanMapper.class);
            return mapper.selectServerIdsByGameId(gameId);
        }
    }
}
