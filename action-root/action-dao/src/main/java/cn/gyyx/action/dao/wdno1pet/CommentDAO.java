/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午3:58:55
 * 版本号：v1.0
 * 本类主要用途叙述：关于评论的DAO层实现
 */

package cn.gyyx.action.dao.wdno1pet;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.Pagination;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class CommentDAO implements IComment {
	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	@Override
	/**
	 * 向数据库中插入评论记录
	 */
	public void insertComment(CommentBean commentBean) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {

			IComment iComment = sqlSession.getMapper(IComment.class);
			iComment.insertComment(commentBean);
			sqlSession.commit();
		}

	}

	@Override
	public List<CommentBean> getCommentsByPagination(String pCode,
			Pagination<CommentBean> pagination) {
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.getCommentsByPagination(pCode, pagination);
		}
	}

	@Override
	public Integer getCommentsCountByPetCode(String pCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IComment iComment = sqlSession.getMapper(IComment.class);
			return iComment.getCommentsCountByPetCode(pCode);
		}
	}

}
