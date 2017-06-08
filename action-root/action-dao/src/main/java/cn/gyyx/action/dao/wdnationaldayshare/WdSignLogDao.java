package cn.gyyx.action.dao.wdnationaldayshare;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdnationalday.WdSignLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdSignLogDao {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdSignLogDao.class);

	/**
	 * 
	 * @日期：20160924
	 * 
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * 
	 * @日期：20160924
	 * @Title: insertsendlog
	 * @Description: TODO 
	 * @param sharebean
	 * @return int
	 */
	public int insertsendlog(WdSignLogBean wdSignLogBean ){
		SqlSession session = getSession();	
		int status=0;
		try {
			IWdSignLogMapper Mapper = session.getMapper(IWdSignLogMapper.class);
			status=Mapper.insertsendlog(wdSignLogBean);
			session.commit();
			return status;
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return status;
	}
	/**
	 * 
	 * @日期：20160924
	 * @Title: insertsendlog
	 * @Description: TODO 
	 * @param sharebean
	 * @return int
	 */
	public Integer selectstep(WdSignLogBean wdSignLogBean ){
		SqlSession session = getSession();	
		Integer step=999;
		try {
			IWdSignLogMapper Mapper = session.getMapper(IWdSignLogMapper.class);
			step=Mapper.selectstep(wdSignLogBean);
			return step;
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return step;
	}
}
