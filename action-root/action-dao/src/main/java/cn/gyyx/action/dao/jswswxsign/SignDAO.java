/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.Sign;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class SignDAO{
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public int insertSign(Sign sign) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertSign(sign,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int insertSign(Sign sign,SqlSession session) {
		int result = 0;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		result = mapper.insertSign(sign);
		return result;
	}

	public Sign getSign(Sign sign) {
		Sign result = null;
		SqlSession session = getSession();
		try {
			result = getSign(sign,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	
	public Sign getSign(Sign sign,SqlSession session) {
		Sign result = null;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		result = mapper.getSign(sign);
		return result;
	}
	
	public int updateSign(Sign sign) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = updateSign(sign,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}
	
	public int updateSign(Sign sign,SqlSession session) {
		int result = 0;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		result = mapper.updateSign(sign);
		return result;
	}
	
	public Sign getRecentSign(String openId) {
		Sign result = null;
		SqlSession session = getSession();
		try {
			result = getRecentSign(openId,session);
		} catch (Exception e) {
			logger.warn(e.toString());
		}finally{
			session.close();
		}
		return result;
	}

	public Sign getRecentSign(String openId, SqlSession session) {
		Sign result = null;
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		Sign param = new Sign();
		param.setOpenId(openId);
		result = mapper.getRecentSign(param);
		return result;
	}
	
	public void updateSignZero(SqlSession session) {
		ISignMapper mapper = session.getMapper(ISignMapper.class);
		mapper.updateSignZero();
	}
}
