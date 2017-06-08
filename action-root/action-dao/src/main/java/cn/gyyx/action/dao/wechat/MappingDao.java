package cn.gyyx.action.dao.wechat;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.wechat.MappingBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class MappingDao {

	private static final Logger logger = LoggerFactory
			.getLogger(MappingDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 增加映射信息
	 * @return
	 */
	public void addMappingInfo(MappingBean mappingBean) {
		SqlSession session = getSession();
		try {
			IMappingMapper mapper = session
					.getMapper(IMappingMapper.class);
			mapper.addMappingInfo(mappingBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addMappingInfo" + e.toString());
		} finally {
			session.close();
		}
	}
}
