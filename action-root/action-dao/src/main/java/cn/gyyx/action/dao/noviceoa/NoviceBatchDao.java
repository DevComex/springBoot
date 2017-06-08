/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/23 10:26
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceBatchBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 描述:新手卡后台批次持久层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceBatchDao {
    SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

    public int insert(NoviceBatchBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            mapper.insertSelective(bean);
            return bean.getCode() == null ? 0 : bean.getCode();
        }
    }

    public boolean update(NoviceBatchBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            return mapper.updateByPrimaryKeySelective(bean) > 0;
        }
    }

    public NoviceBatchBean selectBeanByCode(int code) {
        try (SqlSession session = factory.openSession()) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    public NoviceBatchBean selectBeanByName(String batchName) {
        try (SqlSession session = factory.openSession()) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            return mapper.selectByBatchName(batchName);
        }
    }

    public int selectTotalCount(String batchType) {
        try (SqlSession session = factory.openSession()) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            return mapper.selectTotalCount(batchType);
        }
    }

    public List<NoviceBatchBean> selectPageList(int gameId, int index, int pageCount, String batchType) {
        int skipCount = (index - 1) * pageCount;
        try (SqlSession session = factory.openSession()) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            return mapper.selectPageList(gameId, skipCount, pageCount, batchType);
        }
    }

    public List<NoviceBatchBean> selectBatchList(int gameId, String batchType) {
        try (SqlSession session = factory.openSession()) {
            NoviceBatchBeanMapper mapper = session.getMapper(NoviceBatchBeanMapper.class);
            return mapper.selectBatchList(gameId, batchType);
        }
    }
}
