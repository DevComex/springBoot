package cn.gyyx.action.dao.qiankunlock;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.QianKunLock.QianKunLockLimitBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class QianKunLockLimitDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(QianKunLockLimitDAO.class);

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
	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: selectLockLimit
	 * @Description: TODO 获取乾坤锁号段
	 * @return selectLockLimit
	 */
	public QianKunLockLimitBean selectLockLimit(){
		SqlSession session = getSession();
		QianKunLockLimitBean result = null;
		try{
			IQianKunLockLimitMapper mapper = session.getMapper(IQianKunLockLimitMapper.class);
			result = mapper.selectLockLimit();
		}catch(Exception e){
			logger.warn("selectLockLimit"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
}
