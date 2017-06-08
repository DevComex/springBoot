package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CommentsDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CreditsDAO.class);

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: addComment
	 * @Description: TODO 新增评论
	 * @return void
	 */
	public void addComment(CommentsBean commentsBean){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.addComment(commentsBean);
			session.commit();
		}catch(Exception e){
			logger.warn("addComment"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * @日期：2015年7月14日
	 * @Title: getCommentByPage
	 * @Description: TODO 获取session
	 * @return List<CommentsBean>
	 * */
	public List<CommentsBean> getCommentByPage(Map<String,Object> paramMap){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getCommentByPage(paramMap);
		}catch(Exception e){
			logger.warn("getCommentByPage"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	/**
	 * @日期：2015年7月14日
	 * @Title: getCommentByPage
	 * @Description: TODO 获取session
	 * @return List<CommentsBean>
	 * */
	public Integer getCommentCount(CommentsBean commentsBean){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getCommentCount(commentsBean);
		}catch(Exception e){
			logger.warn("getCommentCount"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	/**
	 * @日期：2015年7月14日
	 * @Title: getCommentByPage
	 * @Description: TODO 获取session
	 * @return List<CommentsBean>
	 * */
	public void deleteComment(Integer code){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.deleteComment(code);
			session.commit();
		}catch(Exception e){
			logger.warn("deleteComment"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * @日期：2015年10月20日
	 * @Title: cleanBestCommon
	 * @Description: 取消最佳
	 * @return 
	 * */
	public void cleanBestCommon(Integer materialCode){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.cleanBestCommon(materialCode);
			session.commit();
		}catch(Exception e){
			logger.warn("cleanBestCommon"+e.toString());
		}finally{
			session.close();
		}
	}
	
	/**
	 * @日期：2015年10月20日
	 * @Title: setBestCommon
	 * @Description: 最佳
	 * @return 
	 * */
	public void setBestCommon(Integer code){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.setBestCommon(code);
			session.commit();
		}catch(Exception e){
			logger.warn("setBestCommon"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * @日期：2015年10月20日
	 * @Title: setBestCommon
	 * @Description: 最佳
	 * @return 
	 * */
	public void resetBestCommon(Integer code){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.resetBestCommon(code);
			session.commit();
		}catch(Exception e){
			logger.warn("resetBestCommon"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * 根据IP地址查询匿名评论
	 * @param materialCode
	 * @param ipAddress
	 * @return
	 */
	public CommentsBean getNoNameCommentWithAddr(Integer materialCode,String ipAddress){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getNoNameCommentWithAddr(materialCode,ipAddress);
		}catch(Exception e){
			logger.warn("getCommentCount"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	/**
	 * 查询评论
	 * @param commentAccount
	 * @param materialCode
	 * @param ipAddress
	 * @return
	 */
	public CommentsBean getComment(String commentAccount,Integer materialCode,String ipAddress){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getComment(commentAccount,materialCode,ipAddress);
		}catch(Exception e){
			logger.warn("getCommentCount"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	/**
	 * 根据分页查询评论
	 * @param materialCode
	 * @param pageNum
	 * @return
	 */
	public List<CommentsBean> getCommentListByPage(Integer materialCode,Integer pageNum){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getCommentListByPage(materialCode,pageNum);
		}catch(Exception e){
			logger.warn("allCommentByCode"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	/**
	 * 根据编号查询评论
	 * @param code
	 * @return
	 */
	public CommentsBean getCommonByCode(Integer code){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getCommonByCode(code);
		}catch(Exception e){
			logger.warn("getCommonByCode"+e.toString());
		}finally{
			session.close();
		}
		return null;
	}
	public List<CommentsBean> getCommentByUser(String commentAccount){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getCommentByUser(commentAccount);
		}catch(Exception e){
			logger.warn("getCommentByUser"+e.toString());
		}finally{
			session.close();
		}
		return null;
		
	}
	public void deleteByCode(Integer code){
		SqlSession session = getSession();
		
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.deleteByCode(code);
			session.commit();
		}catch(Exception e){
			logger.warn("deleteByCode"+e.toString());
		}finally{
			session.close();
		}
	}
	public void deleteByUser(String commentAccount){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			mapper.deleteByUser(commentAccount);
			session.commit();
		}catch(Exception e){
			logger.warn("deleteByCode"+e.toString());
		}finally{
			session.close();
		}
	}
	
	public Integer getCommentCountByUser(String commentAccount,Integer materialCode){
		SqlSession session = getSession();
		try{
			ICommentsMapper mapper = session.getMapper(ICommentsMapper.class);
			return mapper.getCommentCountByUser(commentAccount,materialCode);
		}catch(Exception e){
			logger.warn("getCommentCountByUser"+e.toString());
		}finally{
			session.close();
		}
		return 0;
	}
}
