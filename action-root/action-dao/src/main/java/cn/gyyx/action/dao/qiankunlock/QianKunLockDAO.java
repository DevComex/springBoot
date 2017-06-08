package cn.gyyx.action.dao.qiankunlock;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.QianKunLock.QianKunLockModel;
import cn.gyyx.action.beans.backstage.LimitBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.backstage.ILimitMapper;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class QianKunLockDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(QianKunLockDAO.class);

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public QianKunLockModel selectBindAcount(int userId){
		SqlSession session = getSession();
		QianKunLockModel result = null;
		try{
			IQianKunLockMapper mapper = session.getMapper(IQianKunLockMapper.class);
			result = mapper.selectBindAcount(userId);
		}catch(Exception e){
			logger.warn("selectBindAcount"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	public QianKunLockModel SelectByEkeySn(String EkeySn){
		SqlSession session = getSession();
		QianKunLockModel result = null;
		try{
			IQianKunLockMapper mapper = session.getMapper(IQianKunLockMapper.class);
			result = mapper.SelectByEkeySn(EkeySn);
		}catch(Exception e){
			logger.warn("SelectByEkeySn"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	public void insertLog(HashMap<String, Object> map){
		SqlSession session = getSession();
		try{
			IQianKunLockMapper mapper = session.getMapper(IQianKunLockMapper.class);
			mapper.insertLog(map);
			session.commit();
		}catch(Exception e){
			logger.warn("SelectByEkeySn"+e.toString());
		}finally{
			session.close();
		}
	}
	public void updateLog(QianKunLockModel qianKunLockModel){
		SqlSession session = getSession();
		try{
			IQianKunLockMapper mapper = session.getMapper(IQianKunLockMapper.class);
			mapper.updateLog(qianKunLockModel);
			session.commit();
		}catch(Exception e){
			logger.warn("SelectByEkeySn"+e.toString());
		}finally{
			session.close();
		}
	}
}
