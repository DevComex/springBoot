package cn.gyyx.action.dao.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.wdpetdating.CheckImgDTO;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ActionCommonImageDAO implements IActionCommonImageBean{
	private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();
	@Override
	public List<ActionCommonImageBean> getImgBeanByPage(CheckImgDTO checkImgDTO) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonImageBean mapper = session.getMapper(IActionCommonImageBean.class);
			return mapper.getImgBeanByPage(checkImgDTO);
		}
	}

	@Override
	public ActionCommonImageBean selectImgBycode(int code) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonImageBean mapper = session.getMapper(IActionCommonImageBean.class);
			return mapper.selectImgBycode(code);
		}
	}

	@Override
	public void updateImg(ActionCommonImageBean bean) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonImageBean mapper = session.getMapper(IActionCommonImageBean.class);
			mapper.updateImg(bean);
			session.commit();
		}
		
	}

	@Override
	public List<ActionCommonImageBean> getImgBeanByPagePass(CheckImgDTO checkImgDTO) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonImageBean mapper = session.getMapper(IActionCommonImageBean.class);
			return mapper.getImgBeanByPagePass(checkImgDTO);
		}
	}

	@Override
	public void insertActionCommonImageBean(ActionCommonImageBean bean) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonImageBean mapper = session.getMapper(IActionCommonImageBean.class);
			mapper.insertActionCommonImageBean(bean);
			session.commit();
		}
	}

	@Override
	public List<ActionCommonImageBean> getImgBeanByFormCode(int formCode) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonImageBean mapper = session.getMapper(IActionCommonImageBean.class);
			return mapper.getImgBeanByFormCode(formCode);
		}
	}

}
