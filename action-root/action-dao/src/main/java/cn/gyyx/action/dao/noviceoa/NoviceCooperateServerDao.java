/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/27 18:12
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
 * 描述:异业合作 服务器持久层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceCooperateServerDao {
    SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

    public int insert(NoviceServerBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceCooperateServerMapper mapper = session.getMapper(NoviceCooperateServerMapper.class);
            mapper.insertSelective(bean);
            return bean.getCode() == null ? 0 : bean.getCode();
        }
    }

    public NoviceServerBean selectByCode(int code) {
        try (SqlSession session = factory.openSession()) {
            NoviceCooperateServerMapper mapper = session.getMapper(NoviceCooperateServerMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    public boolean updateByCode(NoviceServerBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceCooperateServerMapper mapper = session.getMapper(NoviceCooperateServerMapper.class);
            return mapper.updateByPrimaryKeySelective(bean) > 0;
        }
    }

    public List<NoviceServerBean> selectByBatchId(int batchId) {
        try (SqlSession session = factory.openSession()) {
            NoviceCooperateServerMapper mapper = session.getMapper(NoviceCooperateServerMapper.class);
            return mapper.selectByBatchId(batchId);
        }
    }

    public NoviceServerBean selectByServerId(int batchId,int serverId) {
        try (SqlSession session = factory.openSession()) {
            NoviceCooperateServerMapper mapper = session.getMapper(NoviceCooperateServerMapper.class);
            return mapper.selectByServerId(batchId, serverId);
        }
    }

    public List<Integer> selectServerIdsByBatchIdGameId(int batchId, int gameId) {
        try (SqlSession session = factory.openSession()) {
            NoviceCooperateServerMapper mapper = session.getMapper(NoviceCooperateServerMapper.class);
            return mapper.selectServerIdsByBatchIdGameId(batchId, gameId);
        }
    }
}
