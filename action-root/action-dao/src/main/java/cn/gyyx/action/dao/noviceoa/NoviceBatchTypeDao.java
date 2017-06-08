/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/23 16:29
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceBatchTypeBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 描述:新手卡批次类型持久层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceBatchTypeDao {
    SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

    public int insert(NoviceBatchTypeBean bean) {
        try(SqlSession session = factory.openSession(true)) {
            NoviceBatchTypeBeanMapper mapper = session.getMapper(NoviceBatchTypeBeanMapper.class);
            mapper.insertSelective(bean);
            return bean.getCode() == null ? 0 : bean.getCode();
        }
    }

    public NoviceBatchTypeBean selectByCode(int code) {
        try(SqlSession session = factory.openSession(true)) {
            NoviceBatchTypeBeanMapper mapper = session.getMapper(NoviceBatchTypeBeanMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    public NoviceBatchTypeBean selectBeanByBatchType(String batchType){
        try(SqlSession session = factory.openSession(true)) {
            NoviceBatchTypeBeanMapper mapper = session.getMapper(NoviceBatchTypeBeanMapper.class);
            return mapper.selectBeanByBatchType(batchType);
        }
    }

    public List<NoviceBatchTypeBean> selectList() {
        try(SqlSession session = factory.openSession(true)) {
            NoviceBatchTypeBeanMapper mapper = session.getMapper(NoviceBatchTypeBeanMapper.class);
            return mapper.selectList();
        }
    }
}
