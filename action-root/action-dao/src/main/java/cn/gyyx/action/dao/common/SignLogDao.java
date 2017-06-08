package cn.gyyx.action.dao.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import cn.gyyx.action.beans.common.ActionCommonSignLogBean;
import java.util.ArrayList;
import java.util.List;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SignLogDao {

	private static final GYYXLogger logger = GYYXLoggerFactory
			.getLogger(SignLogDao.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public int insertSignLog(ActionCommonSignLogBean signLog) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertSignLog(signLog,session);
		} catch (Exception e) {
			logger.warn(e);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int insertSignLog(ActionCommonSignLogBean signLog,SqlSession session) {
		int result = 0;
		ISignLogMapper mapper = session.getMapper(ISignLogMapper.class);
		result = mapper.insertSignLog(signLog);
		return result;
	}
	
	public List<ActionCommonSignLogBean> selectSignLog(ActionCommonSignLogBean signLog){
		SqlSession session=this.getSession();
		List<ActionCommonSignLogBean> result=new ArrayList<ActionCommonSignLogBean>();
		ISignLogMapper mapper = session.getMapper(ISignLogMapper.class);
		result= mapper.selectSignLog(signLog);
		return result;
	}
}
