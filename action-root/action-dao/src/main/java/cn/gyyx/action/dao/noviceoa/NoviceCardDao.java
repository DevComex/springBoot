package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceCardBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 描述:新手卡数据操作层
 * </p>
 *
 * @author tanjunkai
 */
public class NoviceCardDao {
	SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

	public String existTable(String tableName) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceCardBeanMapper mapper = session.getMapper(NoviceCardBeanMapper.class);
			return mapper.existTable(tableName);
		}
	}

	public String selectLastNoviceCardNo(String tbName) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceCardBeanMapper mapper = session.getMapper(NoviceCardBeanMapper.class);
			return mapper.selectLastNoviceCardNo(tbName);
		}
	}
	
	public List<NoviceCardBean> getNoviceCardListByTaskId(String tbName,int taskId) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceCardBeanMapper mapper = session.getMapper(NoviceCardBeanMapper.class);
			return mapper.getNoviceCardListByTaskId(tbName,taskId);
		}
	}
}
