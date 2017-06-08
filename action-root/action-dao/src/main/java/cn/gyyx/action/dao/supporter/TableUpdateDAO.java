package cn.gyyx.action.dao.supporter;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.common.beans.InnerTransmissionData;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class TableUpdateDAO implements ITableUpdate{
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	@Override
	public int updateTable(InnerTransmissionData data) {
		try(SqlSession session = getSession()) {
			ITableUpdate mapper = session.getMapper(ITableUpdate.class);
			int res = mapper.updateTable(data);
			session.commit();
			return res;
		}
	}
}
