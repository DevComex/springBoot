package cn.gyyx.action.dao.novicecard;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceCardMutexBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoviceCardMutexDAO implements INoviceCardMutexMap {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(NoviceCardMutexDAO.class);
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 获取所有互斥信息
	 */
	@Override
	public List<NoviceCardMutexBean> getMutexMes(int symbol) {
		SqlSession session = getSession();
		List<NoviceCardMutexBean> result = new ArrayList<NoviceCardMutexBean>();
		try {
			INoviceCardMutexMap INovicecardMutex = session.getMapper(INoviceCardMutexMap.class);
			result = INovicecardMutex.getMutexMes(symbol);
		} catch (Exception e) {
			LOG.warn("获取所有互斥信息失败！",e);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
}
