package cn.gyyx.action.dao.activation;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ActivationDAO {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public int validateActCode(int gameId, String activationCode) {
		try(SqlSession session = getSession()) {
			IActivation mapper = session.getMapper(IActivation.class);
			return mapper.validateActCode(gameId, activationCode);
		}
	}

	public int useActivateCode(int serverId, int userId, int gameId,
			String activationCode) {
		try(SqlSession session = getSession()) {
			IActivation mapper = session.getMapper(IActivation.class);
			int res = mapper.useActivateCode(serverId, userId, gameId, activationCode);
			session.commit();
			return res;
		}
	}
	
}
