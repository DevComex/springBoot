package cn.gyyx.action.bll.lottery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.CommentBean;
import cn.gyyx.action.dao.lottery.CommentDao;

public class CommentBLL {
	private CommentDao comment = new CommentDao();

	/**
	 * 查询出按要求总共有多少评论
	 * 
	 * @author MT
	 */
	public int selectDisPageNum() {
		int disNum = comment.selectDisPageNum();
		return disNum;
	}

	/**
	 * 查询出按要求总共有多少评论
	 * 
	 * @author MT
	 */
	public int selectDisPageNumByActionCode(Integer actionCode) {
		int disNum = comment.selectDisPageNumByActionCode(actionCode);
		return disNum;
	}

	/**
	 * 通过评论状态分页查询评论信息
	 * 
	 * @author MT
	 * @param commentStatus
	 * @return
	 */
	public List<CommentBean> checkDisInfo(Integer nowPage) {
		return comment.checkDiscussInfo(nowPage);
	}

	/**
	 * 通过评论状态分页查询评论信息
	 * 
	 * @author MT
	 * @param nowPage
	 *            ,actionCode
	 * @return
	 */
	public List<CommentBean> getCommentBeansByPage(Integer nowPage,
			Integer actionCode) {
		return comment.getCommentBeansByPage(nowPage, actionCode);
	}

	/**
	 * 增加一条评论
	 * 
	 * @param commentBean
	 */
	public void addComment(CommentBean commentBean) {
		comment.addComment(commentBean);
	}

	/**
	 * 根据状态获取评论信息
	 * 
	 * @param status
	 * @return
	 */
	public List<CommentBean> getCommentBeansByStatus(String status,
			int actionCode) {
		return comment.getCommentBeansByStatus(status, actionCode);
	}

	/**
	 * 设定评论的状态
	 * 
	 * @param code
	 * @param status
	 */
	public void setCommentStatusByCode(int code, String status) {
		comment.setCommentStatusByCode(code, status);
	}

	/**
	 * 根据主键移除评论
	 * 
	 * @param code
	 */
	public void removeCommentByCode(int code) {
		comment.removeCommentByCode(code);
	}

	/***
	 * 得到某活动的评论数量
	 * 
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public Integer getCountComment(int actionCode, SqlSession sqlSession) {
		return comment.getCountComment(actionCode, sqlSession);
	}

	/***
	 * 得到前多少的许愿信息
	 * 
	 * @param num
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public Map<String, CommentBean> getCommentsByTop(int num, int actionCode,
			SqlSession sqlSession) {
		Map<String, CommentBean> map = new HashMap<>(1000);
		// 得到评论信息
		List<CommentBean> list = comment.getCommentsByTop(num, actionCode,
				sqlSession);
		if (list != null) {
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					map.put(list.get(i).getNickName(), list.get(i));
				}
			}
		}
		return map;

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
		return comment.getCommentsByAccount(actionCode, account, sqlSession);
	}

	/***
	 * 得到不重复评论的数量
	 * 
	 * @param actionCode
	 * @return
	 */
	public Integer getCountCommentNorepeat(int actionCode, SqlSession sqlSession) {
		return comment.getCountCommentNorepeat(actionCode, sqlSession);
	}

	/***
	 * 得到前多少的许愿信息
	 * 
	 * @param num
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public Map<String, CommentBean> getCommentsByTopNoRepeat(int num,
			int actionCode, SqlSession sqlSession) {
		Map<String, CommentBean> map = new HashMap<>(1000);
		// 得到评论信息
		List<CommentBean> list = comment.getCommentsByTopNoRepeat(num,
				actionCode, sqlSession);
		if (list != null) {
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					map.put(list.get(i).getNickName(), list.get(i));
				}
			}
		}
		return map;
	}
}
