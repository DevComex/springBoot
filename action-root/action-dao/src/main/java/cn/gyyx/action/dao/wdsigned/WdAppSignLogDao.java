package cn.gyyx.action.dao.wdsigned;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 问道app签到日志
 * 
 * @ClassName: WdAppSignLogDao
 * @description WdAppSignLogDao
 * @author luozhenyu
 * @date 2016年11月16日
 */
public class WdAppSignLogDao implements IWdAppSignLogMapper {

	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignLogDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	@Override
	public int insert(WdAppSignedLogBean bean) {

		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
			result = wdAppSignLogMapper.insert(bean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public WdAppSignedLogBean getTodaySignLog(String account, int serverId, String batch, String today) {
		WdAppSignedLogBean bean = null;
		SqlSession session = getSession();
		try {
			IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
			bean = wdAppSignLogMapper.getTodaySignLog(account, serverId, batch, today);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return bean;
	}

	@Override
	public List<String> getSignHistoryDate(String account, int serverId, String batch) {
		List<String> result = null;
		SqlSession session = getSession();
		try {
			IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
			result = wdAppSignLogMapper.getSignHistoryDate(account, serverId, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int getSignCountByAccount(String account, int serverId, String batch) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
			result = wdAppSignLogMapper.getSignCountByAccount(account, serverId, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int insert(WdAppSignedLogBean bean, SqlSession session) {
		IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
		return wdAppSignLogMapper.insert(bean);
	}

	@Override
	public int getSignCountByAccount(String account, int serverId, String batch, SqlSession session) {
		IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
		return wdAppSignLogMapper.getSignCountByAccount(account, serverId, batch);
	}

	@Override
	public WdAppSignedLogBean getTodaySignLog(String account, int serverId, String batch, String today,
			SqlSession session) {
		IWdAppSignLogMapper wdAppSignLogMapper = session.getMapper(IWdAppSignLogMapper.class);
		return wdAppSignLogMapper.getTodaySignLog(account, serverId, batch, today);
	}

}
