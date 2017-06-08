package cn.gyyx.action.dao.wdsigned;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdsigned.WdAppSignOaBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdAppSignOaDao implements IWdAppSignOaMapper {
	WdAppSignOaBean wdAppSignOaBean=new WdAppSignOaBean();
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignOaDao.class);
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	@Override
	public int insert(WdAppSignOaBean bean) {
		int result = 0;
		SqlSession session = getSession();
		try {
			IWdAppSignOaMapper wdAppSignOaMapper = session.getMapper(IWdAppSignOaMapper.class);
			result = wdAppSignOaMapper.insert(bean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	public WdAppSignOaBean getWdAppSignOaBean(String batch) {
		WdAppSignOaBean result = new WdAppSignOaBean();
		SqlSession session = getSession();
		try {
			IWdAppSignOaMapper wdAppSignOaMapper = session.getMapper(IWdAppSignOaMapper.class);
			result = wdAppSignOaMapper.getWdAppSignOaBean(batch);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		
		} finally {
			session.close();
		}
		return result;
	}
}
