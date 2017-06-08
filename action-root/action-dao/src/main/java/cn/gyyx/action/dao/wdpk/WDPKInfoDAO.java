package cn.gyyx.action.dao.wdpk;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdallpk.AllPKInfo;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class WDPKInfoDAO {
	private long lastStamp = 0;
	private List<String> accounts = new ArrayList<String>(1);
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public boolean isAccountAvailable(String account) {
		long crtStamp = System.currentTimeMillis();
		if(crtStamp - lastStamp > 60 * 5 * 1000) {
			try(SqlSession session = getSession()) {
				IWDPKInfo mapper = session.getMapper(IWDPKInfo.class);
				accounts = mapper.getAvailableCount();
			}
			lastStamp = crtStamp;
		}
		for(String inner:accounts) {
			if(inner.equalsIgnoreCase(account)) {
				return true;
			}
		}
		return false;
	}

	public void addWDPKInfoBean(AllPKInfo bean) {
		try(SqlSession session = getSession()) {
			IWDPKInfo mapper = session.getMapper(IWDPKInfo.class);
			mapper.addWDPKInfoBean(bean);
			session.commit();
		}
	}
	
	public boolean isAccountExists(String account) {
		try(SqlSession session = getSession()) {
			IWDPKInfo mapper = session.getMapper(IWDPKInfo.class);
			return mapper.isAccountExist(account);
		}
	}
}
