package cn.gyyx.action.dao.cs2sign;

import java.util.Date;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.cs2sign.Cs2SignInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class Cs2SignInfoDAO {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(Cs2SignInfoDAO.class); 
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	public boolean insertCs2SignInfoBean(Cs2SignInfoBean cs2SignInfoBean) {
		try(SqlSession session = factory.openSession()) {
			ICs2SignInfoBean mapper = session.getMapper(ICs2SignInfoBean.class);
			mapper.insertCs2SignInfoBean(cs2SignInfoBean);
			session.commit();
			return true;
		} catch (PersistenceException e) {
			LOG.warn("PersistenceException in insertCs2SignInfoBean:" + e);
			return false;
		}
	}
	public int selectCs2SignInfoCountByAccount(String account) {
		try(SqlSession session = factory.openSession()) {
			ICs2SignInfoBean mapper = session.getMapper(ICs2SignInfoBean.class);
			return mapper.selectCs2SignInfoCountByAccount(account);
		}
	}
	public Cs2SignInfoBean selectCs2SignInfoBeanByAccount(String account) {
		try(SqlSession session = factory.openSession()) {
			ICs2SignInfoBean mapper = session.getMapper(ICs2SignInfoBean.class);
			ICs2BindInfoBean bindMapper = session.getMapper(ICs2BindInfoBean.class);
			Cs2SignInfoBean result = mapper.selectCs2SignInfoBeanByAccount(account);
			result.setBind(bindMapper.selectCs2BindInfoBeanByAccount(account));
			return result;
		}
	}
	public void updateCs2SignInfo(Date date,int continuity,String account) {
		try(SqlSession session = factory.openSession()) {
			ICs2SignInfoBean mapper = session.getMapper(ICs2SignInfoBean.class);
			mapper.updateCs2SignInfo(date,continuity,account);
			session.commit();
		}
	}
}