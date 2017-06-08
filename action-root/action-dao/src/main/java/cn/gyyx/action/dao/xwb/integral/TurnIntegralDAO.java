package cn.gyyx.action.dao.xwb.integral;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class TurnIntegralDAO {
	private static final Logger LOG = LoggerFactory
			.getLogger(TurnIntegralDAO.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	
	public boolean computeTurn(Map<String, String> para) {
		try(SqlSession sqlSession = getSession()){
			sqlSession.getMapper(ITurnIntegralMapper.class).computeTurn(para);
		}catch(Exception e){
			LOG.warn("转积分存储过程出错！",e);
			return false;
		}
		return true;
	}
	
}

