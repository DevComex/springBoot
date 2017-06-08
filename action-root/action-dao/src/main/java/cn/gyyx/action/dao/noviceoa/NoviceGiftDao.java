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

import cn.gyyx.action.beans.noviceoa.NoviceGiftBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 描述:新手卡后台，礼包持久层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceGiftDao {
    SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

    public int insert(NoviceGiftBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            mapper.insertSelective(bean);
            return bean.getCode() == null ? 0 : bean.getCode();
        }
    }

    public boolean update(NoviceGiftBean bean) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            return mapper.updateByPrimaryKeySelective(bean) > 0;
        }
    }

    public NoviceGiftBean selectBeanByCode(int code) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            return mapper.selectByPrimaryKey(code);
        }
    }

    public List<NoviceGiftBean> selectListByBatchId(int batchId) {
        try (SqlSession session = factory.openSession()) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            return mapper.selectListByBatchId(batchId);
        }
    }

    public NoviceGiftBean selectBeanByGiftName(int batchId, String giftName) {
        try (SqlSession session = factory.openSession()) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            return mapper.selectBeanByGiftName(batchId, giftName);
        }
    }

    public NoviceGiftBean selectBeanByGiftCode(int batchId, String giftCode) {
        try (SqlSession session = factory.openSession()) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            return mapper.selectBeanByGiftCode(batchId, giftCode);
        }
    }

    public boolean delete(int code) {
        try (SqlSession session = factory.openSession(true)) {
            NoviceGiftBeanMapper mapper = session.getMapper(NoviceGiftBeanMapper.class);
            return mapper.deleteGiftBeanByCode(code) > 0;
        }
    }
}
