/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日下午5:30:25
 * 版本号：v1.0
 * 本类主要用途叙述：评论的数据库层
 */

package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.CommentBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class CommentDao {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 查询总共有多少评论信息
	 * 
	 * @author MT
	 */

	public int selectDisPageNum() {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.selectDisPageNum();

		}
	}

	/**
	 * 查询总共有多少评论信息
	 * 
	 * @author MT
	 */

	public int selectDisPageNumByActionCode(Integer actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.selectDisPageNumByActionCode(actionCode);

		}
	}

	/**
	 * 通过评论状态分页查询评论信息
	 * 
	 * @author MT
	 */

	public List<CommentBean> checkDiscussInfo(Integer nowPage) {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.checkDiscussInfo(nowPage);

		}
	}

	public List<CommentBean> getCommentBeansByPage(Integer nowPage,
			int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.getCommentBeansByPage(nowPage, actionCode);

		}
	}

	public void addComment(CommentBean commentBean) {

		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			iComment.addComment(commentBean);
			sqlSession.commit();

		}
	}

	public List<CommentBean> getCommentBeansByStatus(String status,
			int actionCode) {

		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.getCommentBeansByStatus(status, actionCode);

		}
	}

	public void setCommentStatusByCode(int code, String status) {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			iComment.setCommentStatusByCode(code, status);
			sqlSession.commit();

		}
	}

	public void removeCommentByCode(int code) {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			iComment.removeCommentByCode(code);
			sqlSession.commit();

		}
	}

	/***
	 * 得到某活动的评论数量
	 * 
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public Integer getCountComment(int actionCode, SqlSession sqlSession) {
		IComment iComment = sqlSession.getMapper(IComment.class);
		return iComment.getCountComment(actionCode);
	}

	/***
	 * 得到前多少的许愿信息
	 * 
	 * @param num
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public List<CommentBean> getCommentsByTop(int num, int actionCode,
			SqlSession sqlSession) {
		IComment iComment = sqlSession.getMapper(IComment.class);
		return iComment.getCommentsByTop(num, actionCode);
	}
	
	/***
	 * 得到前多少的许愿信息
	 * 
	 * @param num
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public List<CommentBean> getCommentsByTopNoRepeat(int num, int actionCode,
			SqlSession sqlSession) {
		IComment iComment = sqlSession.getMapper(IComment.class);
		return iComment.getCommentsByTop(num, actionCode);
	}

	/****
	 * 获取某个人自己的评论
	 * 
	 * @param actionCode
	 * @param account
	 * @return
	 */
	public List<CommentBean> getCommentsByAccount(int actionCode,
			String account, SqlSession sqlSession) {
		IComment iComment = sqlSession.getMapper(IComment.class);
		return iComment.getCommentsByAccount(actionCode, account);

	}

	/***
	 * 得到评论的数量
	 * 
	 * @param actionCode
	 * @return
	 */
	public Integer getCountCommentNorepeat(int actionCode, SqlSession sqlSession) {
		IComment iComment = sqlSession.getMapper(IComment.class);
		return iComment.getCountCommentNorepeat(actionCode);
	}
}
