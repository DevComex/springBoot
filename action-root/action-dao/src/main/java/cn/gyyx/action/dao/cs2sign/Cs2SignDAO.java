package cn.gyyx.action.dao.cs2sign;

import cn.gyyx.action.dao.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import cn.gyyx.action.beans.cs2sign.Cs2SignBean;
import org.apache.ibatis.session.SqlSession;

public class Cs2SignDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	public void insertCs2SignBean(Cs2SignBean cs2SignBean) {
		try(SqlSession session = factory.openSession()) {
			ICs2SignBean mapper = session.getMapper(ICs2SignBean.class);
			mapper.insertCs2SignBean(cs2SignBean);
			session.commit();
		}
	}
	public int selectCs2SignBeanTodayCount(String account) {
		try(SqlSession session = factory.openSession()) {
			ICs2SignBean mapper = session.getMapper(ICs2SignBean.class);
			return mapper.selectCs2SignBeanTodayCount(account);
		}
	}
}