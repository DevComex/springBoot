package cn.gyyx.action.dao.wdsigned;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdsigned.WdAppSignedSendPrizeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 问道app签到日志
 * 
 * @ClassName: WdAppSignSendPrizeDao
 * @description WdAppSignSendPrizeDao
 * @author luozhenyu
 * @date 2016年11月16日
 */
public class WdAppSignSendPrizeDao implements IWdAppSignSendPrizeMapper {

	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignSendPrizeDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	@Override
	public int insert(WdAppSignedSendPrizeBean bean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
			result = wdAppSignSendPrizaMapper.insert(bean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public WdAppSignedSendPrizeBean getSignSendPrizeByPrize(String account, int serverId, String gift, String batch) {
		WdAppSignedSendPrizeBean result = null;
		SqlSession session = getSession();
		try {
			IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
			result = wdAppSignSendPrizaMapper.getSignSendPrizeByPrize(account, serverId, gift, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public List<WdAppSignedSendPrizeBean> getSignSendPrizeByPrizes(String account, int serverId, List<String> gifts,
			String batch) {
		List<WdAppSignedSendPrizeBean> result = null;
		SqlSession session = getSession();
		try {
			IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
			result = wdAppSignSendPrizaMapper.getSignSendPrizeByPrizes(account, serverId, gifts, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int modifyPrizeStatus(String account, int serverId, String gift, String batch, int status, Date sendTime) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
			result = wdAppSignSendPrizaMapper.modifyPrizeStatus(account, serverId, gift, batch, status, sendTime);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	public WdAppSignedSendPrizeBean getUnclaimedPrizeByStatus(String account, String serverName, int status,
			String batch) {
		WdAppSignedSendPrizeBean result = null;
		SqlSession session = getSession();
		try {
			IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
			result = wdAppSignSendPrizaMapper.getUnclaimedPrizeByStatus(account, serverName, status, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<WdAppSignedSendPrizeBean> getSignSendPrize(String account, int serverId, String batch, int status) {
		List<WdAppSignedSendPrizeBean> result = new ArrayList<>();
		SqlSession session = getSession();
		try {
			IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
			result = wdAppSignSendPrizaMapper.getSignSendPrize(account, serverId, batch, status);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	

	@Override
	public int insert(WdAppSignedSendPrizeBean bean, SqlSession session) {
		IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
		return wdAppSignSendPrizaMapper.insert(bean);
	}

	@Override
	public WdAppSignedSendPrizeBean getSignSendPrizeByPrize(String account, int serverId, String gift, String batch,
			SqlSession session) {
		IWdAppSignSendPrizeMapper wdAppSignSendPrizaMapper = session.getMapper(IWdAppSignSendPrizeMapper.class);
		return wdAppSignSendPrizaMapper.getSignSendPrizeByPrize(account, serverId, gift, batch);
	}
	
}
