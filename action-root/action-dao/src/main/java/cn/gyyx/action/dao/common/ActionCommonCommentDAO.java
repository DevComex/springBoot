package cn.gyyx.action.dao.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.common.ActionCommonCommentBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ActionCommonCommentDAO implements IActionCommonCommentBean{
	private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();
	@Override
	public void insertActionCommonCommentBean(ActionCommonCommentBean bean) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonCommentBean mapper = session.getMapper(IActionCommonCommentBean.class);
			mapper.insertActionCommonCommentBean(bean);
			session.commit();
		}
		
	}

	@Override
	public ActionCommonCommentBean selectActionCommonCommentBeanBycode(int code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateActionCommonCommentBean(ActionCommonCommentBean bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteActionCommonCommentBeanBycode(int code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTalk(ActionCommonCommentBean bean) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonCommentBean mapper = session.getMapper(IActionCommonCommentBean.class);
			mapper.updateTalk(bean);
			session.commit();
		}
		
	}

	@Override
	public List<ActionCommonCommentBean> getTalkByPage(int pageIndex,
			int actionCode, String status) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonCommentBean mapper = session.getMapper(IActionCommonCommentBean.class);
			return mapper.getTalkByPage(pageIndex, actionCode, status);
		}
	}

	@Override
	public ActionCommonCommentBean selectTalkBycode(int code) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonCommentBean mapper = session.getMapper(IActionCommonCommentBean.class);
			return mapper.selectTalkBycode(code);
		}
	}

	@Override
	public List<ActionCommonCommentBean> getTalkByFormCode(int formCode) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonCommentBean mapper = session.getMapper(IActionCommonCommentBean.class);
			return mapper.getTalkByFormCode(formCode);
		}
	}

	@Override
	public List<ActionCommonCommentBean> getTalk(int actionCode) {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			IActionCommonCommentBean mapper = session.getMapper(IActionCommonCommentBean.class);
			return mapper.getTalk(actionCode);
		}
	}
	
}
