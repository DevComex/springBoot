package cn.gyyx.action.dao.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.common.NoteURLBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoteURLDAO implements INoteURLBean{
	private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoteURLDAO.class);
	@Override
	public void insertNoteURLBean(NoteURLBean noteURLBean) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			INoteURLBean mapper = session.getMapper(INoteURLBean.class);
			mapper.insertNoteURLBean(noteURLBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}

	@Override
	public List<NoteURLBean> selectNoteURLBeanList() {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			INoteURLBean mapper = session.getMapper(INoteURLBean.class);
			return mapper.selectNoteURLBeanList();
		}
	}
	
}
