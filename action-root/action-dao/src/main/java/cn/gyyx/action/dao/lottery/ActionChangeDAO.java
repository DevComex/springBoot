package cn.gyyx.action.dao.lottery;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.ActionChangeLog;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ActionChangeDAO implements IActionChangeMapper{

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(AddressDAO.class);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public int insertActionChangeLog(ActionChangeLog actionChangeLog){
		int result = 0;
		SqlSession session = getSession();
		try {
			IActionChangeMapper iActionChangeMapper = session
					.getMapper(IActionChangeMapper.class);
			result = iActionChangeMapper.insertActionChangeLog(actionChangeLog);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
}
