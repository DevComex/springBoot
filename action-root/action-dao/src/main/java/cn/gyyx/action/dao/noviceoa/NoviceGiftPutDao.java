package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceGiftPutBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 描述:新手卡投放对象数据操作层
 * </p>
 *
 * @author tanjunkai
 */
public class NoviceGiftPutDao {
	SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

	public int insertSelective(NoviceGiftPutBean bean) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			mapper.insertSelective(bean);
			return bean.getCode() == null ? 0 : bean.getCode();
		}
	}

	public List<NoviceGiftPutBean> selectPageList(int putType, String putName, int index, int pageCount) {
		int skipCount = (index - 1) * pageCount;
		try (SqlSession session = factory.openSession()) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			return mapper.selectPageList(putType, putName, skipCount, pageCount);
		}
	}

	public List<NoviceGiftPutBean> selectByPutType(int putType)
	{
		try (SqlSession session = factory.openSession()) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			return mapper.selectByPutType(putType);
		}
	}
	
	public List<NoviceGiftPutBean> selectNoviceGiftPut(String putName, int putType) {
		try (SqlSession session = factory.openSession()) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			return mapper.selectNoviceGiftPut(putName, putType);
		}
	}

	public int selectTotalCount(int putType, String putName) {
		try (SqlSession session = factory.openSession()) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			return mapper.selectTotalCount(putType, putName);
		}
	}

	public int updateByPrimaryKey(NoviceGiftPutBean bean) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			return mapper.updateByPrimaryKey(bean);
		}
	}

	public NoviceGiftPutBean selectByPrimaryKey(Integer code) {
		try (SqlSession session = factory.openSession()) {
			NoviceGiftPutBeanMapper mapper = session.getMapper(NoviceGiftPutBeanMapper.class);
			return mapper.selectByPrimaryKey(code);
		}
	}

}
