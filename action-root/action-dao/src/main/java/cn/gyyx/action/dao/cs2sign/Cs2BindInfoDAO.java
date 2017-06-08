package cn.gyyx.action.dao.cs2sign;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.cs2sign.Cs2BindInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class Cs2BindInfoDAO {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(Cs2BindInfoDAO.class);
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	public boolean insertCs2BindInfoBean(Cs2BindInfoBean cs2BindInfoBean) {
		try(SqlSession session = factory.openSession()) {
			ICs2BindInfoBean mapper = session.getMapper(ICs2BindInfoBean.class);
			mapper.insertCs2BindInfoBean(cs2BindInfoBean);
			session.commit();
			return true;
		} catch(PersistenceException e) {
			LOG.warn("PersistenceException in insertCs2BindInfoBean:" + e);
			return false;
		}
	}
	public List<Cs2BindInfoBean> selectCs2BindInfoBeanByAccount(String account) {
		try(SqlSession session = factory.openSession()) {
			ICs2BindInfoBean mapper = session.getMapper(ICs2BindInfoBean.class);
			return mapper.selectCs2BindInfoBeanByAccount(account);
		}
	}
	public int selectCs2BindInfoCountByServerAndChar(String server,String character) {
		try(SqlSession session = factory.openSession()) {
			ICs2BindInfoBean mapper = session.getMapper(ICs2BindInfoBean.class);
			return mapper.selectCs2BindInfoCountByServerAndChar(server,character);
		}
	}
}