package cn.gyyx.action.dao.wdsigned;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdsigned.WdAppSignedBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
/**
 * 问道app签到记录表
 * @ClassName: WdAppSignDao
 * @description WdAppSignDao
 * @author luozhenyu
 * @date 2016年11月16日
 */
public class WdAppSignDao implements IWdAppSignMapper {
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignDao.class);
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	@Override
	public int insert(WdAppSignedBean bean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignMapper wdAppSignMapper = session.getMapper(IWdAppSignMapper.class);
			result = wdAppSignMapper.insert(bean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	@Override
	public WdAppSignedBean getSign(String account, int serverId, String batch) {
		WdAppSignedBean result = new WdAppSignedBean();
		SqlSession session = getSession();
		try {
			IWdAppSignMapper wdAppSignMapper = session.getMapper(IWdAppSignMapper.class);
			result = wdAppSignMapper.getSign(account, serverId, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());

		} finally {
			session.close();
		}
		return result;
	}
	@Override
	public int updateSign(int serialDay, int totalDay, String account, int serverId, String batch) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignMapper wdAppSignMapper = session.getMapper(IWdAppSignMapper.class);
			result = wdAppSignMapper.updateSign(serialDay,totalDay,account,serverId, batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	@Override
	public int insert(WdAppSignedBean bean, SqlSession session) {
		IWdAppSignMapper wdAppSignMapper = session.getMapper(IWdAppSignMapper.class);
		return wdAppSignMapper.insert(bean);
	}
	@Override
	public int updateSign(int serialDay, int totalDay, String account, int serverId, String batch, SqlSession session) {
		IWdAppSignMapper wdAppSignMapper = session.getMapper(IWdAppSignMapper.class);
		return wdAppSignMapper.updateSign(serialDay,totalDay,account,serverId, batch);
	}
	@Override
	public WdAppSignedBean getSign(String account, int serverId, String batch, SqlSession session) {
		IWdAppSignMapper wdAppSignMapper = session.getMapper(IWdAppSignMapper.class);
		return wdAppSignMapper.getSign(account, serverId, batch);
	}


	
}
