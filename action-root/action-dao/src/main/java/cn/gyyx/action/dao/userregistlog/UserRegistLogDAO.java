package cn.gyyx.action.dao.userregistlog;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.userregistlog.UserRegistLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class UserRegistLogDAO {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(UserRegistLogDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 插入注册信息
	 * @param para
	 * @return
	 */
	public Integer insert(UserRegistLogBean para){
		Integer result = -1;
		SqlSession session = getSession();
		try{
			IUserRegistLogMapper registe = session.getMapper(IUserRegistLogMapper.class);
			result = registe.insert(para);
		}catch(Exception e){
			LOG.warn("insert mobile official website registe message erro!",e);
		}finally{
			session.commit();
			session.close();
		}
		return result;
	}
}
