package cn.gyyx.action.dao.weixin.related;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class TokenConfigDAO {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(TokenConfigDAO.class);
	/**
	 * 
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public String getTokenPara (String keyName,String type){
		String result = "";
		try(SqlSession session = getSession()){
			ITokenConfigMapper tokenConfig = session.getMapper(ITokenConfigMapper.class);
			result = tokenConfig.getTokenPara(keyName,type);
		}catch(Exception e){
			LOG.warn("get token para error!",e);
		}
		return result;
	}
}
