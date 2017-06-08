package cn.gyyx.action.dao.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import cn.gyyx.action.beans.common.ActionCommonSignBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SignDao {
	private static final GYYXLogger  logger = GYYXLoggerFactory
			.getLogger(SignDao.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public int insertSign(ActionCommonSignBean sign) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertSign(sign,session);
		} catch (Exception e) {
			logger.warn(e);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int insertSign(ActionCommonSignBean sign,SqlSession session) {
		int result = 0;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		result = mapper.insertSign(sign);
		return result;
	}

	public ActionCommonSignBean getSign(ActionCommonSignBean sign) {
		ActionCommonSignBean result = null;
		SqlSession session = getSession();
		try {
			result = getSign(sign,session);
		} catch (Exception e) {
			logger.warn(e);
		}finally{
			session.close();
		}
		return result;
	}
	
	public ActionCommonSignBean getSign(ActionCommonSignBean sign,SqlSession session) {
		ActionCommonSignBean result = null;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		result = mapper.getSign(sign);
		return result;
	}
	
	public int updateSign(ActionCommonSignBean sign) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = updateSign(sign,session);
		} catch (Exception e) {
			logger.warn(e);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int updateSign(ActionCommonSignBean sign,SqlSession session) {
		int result = 0;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		result = mapper.updateSign(sign);
		return result;
	}
	
	public ActionCommonSignBean getRecentSign(int actionCode,String account) {
		ActionCommonSignBean result = null;
		SqlSession session = getSession();
		try {
			result = getRecentSign(actionCode,account,session);
		} catch (Exception e) {
			logger.warn(e);
		}finally{
			session.close();
		}
		return result;
	}

	public ActionCommonSignBean getRecentSign(int actionCode,String account, SqlSession session) {
		ActionCommonSignBean result = null;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		ActionCommonSignBean param = new ActionCommonSignBean();
		param.setAccount(account);
		param.setActionCode(actionCode);
		result = mapper.getRecentSign(param);
		return result;
	}
	
}
