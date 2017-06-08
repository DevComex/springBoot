package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.CommentsDAO;

public class CommentsBll {
	private CommentsDAO commentsDao = new CommentsDAO();
	
	/**
	 * 新增评论 
	 * */
	public void addComment(CommentsBean commentsBean){
		commentsDao.addComment(commentsBean);
	}
	/**
	 * 查询评论
	 * */
	public List<CommentsBean> getCommentsByPage(CommentsBean commentsBean, PageBean pageBean){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comments", commentsBean);
		map.put("page", pageBean);
		return commentsDao.getCommentByPage(map);
	}
	/**
	 * 查询评论数量
	 * */
	public Integer getCommentsCount(CommentsBean commentsBean){
		return commentsDao.getCommentCount(commentsBean);
	}
	/**
	 * 删除评论
	 * */
	public void deleteComments(Integer code){
		commentsDao.deleteComment(code);
	}
	/**
	 * 最佳评论
	 * */
	public void setBestCommon(Integer materialCode,Integer code){
		commentsDao.cleanBestCommon(materialCode);
		commentsDao.setBestCommon(code);
	}
	public void resetBestCommon(Integer code){
		commentsDao.resetBestCommon(code);
	}
	/**
	 * 根据IP地址查询匿名评论
	 * @param materialCode
	 * @param ipAddress
	 * @return
	 */
	public CommentsBean getNoNameCommentWithAddr(Integer materialCode,String ipAddress){
		return commentsDao.getNoNameCommentWithAddr(materialCode,ipAddress);
	}
	/**
	 * 查询评论
	 * @param commentAccount
	 * @param materialCode
	 * @param ipAddress
	 * @return
	 */
	public CommentsBean getComment(String commentAccount,Integer materialCode,String ipAddress){
		return commentsDao.getComment(commentAccount,materialCode,ipAddress);
	}	
	
	/**
	 * 根据分页查询评论
	 * @param materialCode
	 * @param pageNum
	 * @return
	 */
	public List<CommentsBean> getCommentListByPage(Integer materialCode,Integer pageNum){
		return commentsDao.getCommentListByPage(materialCode,pageNum);
	}
	/**
	 * 根据编号查询评论
	 * @param code
	 * @return
	 */
	public CommentsBean getCommonByCode(Integer code){
		return commentsDao.getCommonByCode(code);
	}
	public List<CommentsBean> getCommentByUser(String commentAccount){
		return commentsDao.getCommentByUser(commentAccount);
		
	}
	public void deleteByCode(Integer code){
		commentsDao.deleteByCode(code);
	}
	public void deleteByUser(String commentAccount){
		commentsDao.deleteByUser(commentAccount);
	}
	
	public Integer getCommentCountByUser(String commentAccount,Integer materialCode){
		return commentsDao.getCommentCountByUser(commentAccount,materialCode);
	}
}

