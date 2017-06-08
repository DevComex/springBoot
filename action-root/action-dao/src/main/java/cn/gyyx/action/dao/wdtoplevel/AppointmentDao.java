package cn.gyyx.action.dao.wdtoplevel;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdtoplevel.AppointmentBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class AppointmentDao implements IAppointment{
	static SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();
	@Override
	public void addAppointment(AppointmentBean appointment) {
		try(SqlSession session = sqlSessionFactory.openSession()){
			IAppointment mapper = session.getMapper(IAppointment.class);
			mapper.addAppointment(appointment);
			session.commit();
		}
	}

	@Override
	public boolean isAccountExists(String account) {
		try(SqlSession session = sqlSessionFactory.openSession()){
			IAppointment mapper = session.getMapper(IAppointment.class);
			return mapper.isAccountExists(account);
		}
	}

}
