package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;

public interface ICommentsMapper {
	/**
	 * 增加评论
	 * */
	void addComment(CommentsBean commentsBean);
	/**
	 * 查询评论
	 * */
	List<CommentsBean> getCommentByPage(Map<String,Object> paramMap);
	/**
	 * 查询评论数量
	 * */
	Integer getCommentCount(CommentsBean commentsBean);
	/**
	 * 删除评论
	 * */
	void deleteComment(Integer code);
	/**
	 * 取消最佳
	 * */
	void cleanBestCommon(Integer materialCode);
	/**
	 * 最佳评论
	 * */
	void setBestCommon(Integer code);
	/**
	 * 最佳评论
	 * */
	void resetBestCommon(Integer code);
	/**
	 * 根据IP地址查询匿名评论
	 * @param materialCode
	 * @param ipAddress
	 * @return
	 */
	CommentsBean getNoNameCommentWithAddr(Integer materialCode,String ipAddress);
	/**
	 * 查询评论
	 * @param commentAccount
	 * @param materialCode
	 * @param ipAddress
	 * @return
	 */
	CommentsBean getComment(String commentAccount,Integer materialCode,String ipAddress);
	/**
	 * 根据分页查询评论
	 * @param materialCode
	 * @param pageNum
	 * @return
	 */
	List<CommentsBean> getCommentListByPage(Integer materialCode,Integer pageNum);
	/**
	 * 根据编号查询评论
	 * @param code
	 * @return
	 */
	CommentsBean getCommonByCode(Integer code);
	List<CommentsBean> getCommentByUser(String commentAccount);
	Integer deleteByCode(Integer code);
	Integer deleteByUser(String commentAccount);
	
	
	Integer getCommentCountByUser(String commentAccount,Integer materialCode);
}
