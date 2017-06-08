/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：bozhencheng
 * @联系方式：bozhencheng@gyyx.cn
 * @创建时间：2016年5月24日 
 * @版本号：
 * @本类主要用途描述： 问道十年缤纷活动
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearcolorful;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdtenyearcolorful.CommentBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: CommentDao
 * @description 
 * @author bozhencheng
 * @date 2016年5月25日
 */
public class CommentDao implements ICommentMapper {

	SqlSessionFactory factory;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CommentDao.class);

	
	public CommentDao() {
		factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
	}
	
	/* (non-Javadoc)
	 * @see cn.gyyx.action.dao.wdtenyearcolorful.ICommentMapper#insertComment(cn.gyyx.action.beans.wdtenyearcolorful.CommentBean)
	 */
	@Override
	public boolean insertComment(CommentBean commentBean) {
		try(SqlSession session = factory.openSession()) {
			ICommentMapper mapper = session.getMapper(ICommentMapper.class);
			mapper.insertComment(commentBean);
			session.commit();
			return true;
		} catch (PersistenceException e) {
			logger.warn("PersistenceException in CommentDao.insertComment :" + e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see cn.gyyx.action.dao.wdtenyearcolorful.ICommentMapper#updateCommentStatus(int[], int)
	 */
	@Override
	public boolean updateCommentStatus(int[] commentIds, int status) {
		try(SqlSession session = factory.openSession()) {
			ICommentMapper mapper = session.getMapper(ICommentMapper.class);
			mapper.updateCommentStatus(commentIds, status);
			session.commit();
			return true;
		} catch (PersistenceException e) {
			logger.warn("PersistenceException in CommentDao.updateCommentStatus :" + e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see cn.gyyx.action.dao.wdtenyearcolorful.ICommentMapper#selectValidComments()
	 */
	@Override
	public List<CommentBean> selectValidComments() {
		try(SqlSession session = factory.openSession()) {
			ICommentMapper mapper = session.getMapper(ICommentMapper.class);
			return mapper.selectValidComments();
		}
	}

	/* (non-Javadoc)
	 * @see cn.gyyx.action.dao.wdtenyearcolorful.ICommentMapper#selectAllComments(int, int)
	 */
	@Override
	public List<CommentBean> selectAllComments(int pageIndex, int pageSize) {
		try(SqlSession session = factory.openSession()) {
			ICommentMapper mapper = session.getMapper(ICommentMapper.class);
			return mapper.selectAllComments(pageIndex, pageSize);
		}
	}

	/* (non-Javadoc)
	 * @see cn.gyyx.action.dao.wdtenyearcolorful.ICommentMapper#selectAllCommentsCount()
	 */
	@Override
	public int selectAllCommentsCount() {
		try(SqlSession session = factory.openSession()) {
			ICommentMapper mapper = session.getMapper(ICommentMapper.class);
			return mapper.selectAllCommentsCount();
		}
	}

}
