/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.SignLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class SignLogDAO {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignLogDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public int insertSignLog(SignLog signLog) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertSignLog(signLog,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int insertSignLog(SignLog signLog,SqlSession session) {
		int result = 0;
		ISignLogMapper mapper = session.getMapper(ISignLogMapper.class);
		result = mapper.insertSignLog(signLog);
		return result;
	}

}
