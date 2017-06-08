/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日下午5:24:32
 * 版本号：v1.0
 * 本类主要用途叙述：评论的数据库接口
 */

package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.CommentBean;

public interface IComment {
	// 查询出按要求的评论的信息个数
	public int selectDisPageNum();

	// 查询出按要求的评论的信息个数
	public int selectDisPageNumByActionCode(Integer actionCode);

	/**
	 * 通过评论状态分页查询评论信息
	 * 
	 * @author MT
	 */
	public List<CommentBean> checkDiscussInfo(Integer nowPage);

	/**
	 * 通过评论状态分页查询评论信息
	 * 
	 * @author MT
	 */
	public List<CommentBean> getCommentBeansByPage(
			@Param("nowPage") Integer nowPage,
			@Param("actionCode") int actionCode);

	/**
	 * 增加一条评论信息
	 * 
	 * @param commentBean
	 */
	public void addComment(CommentBean commentBean);

	/**
	 * 
	 * 根据状态查询评论信息
	 * 
	 * @param status
	 * @return
	 */
	public List<CommentBean> getCommentBeansByStatus(
			@Param("status") String status, @Param("actionCode") int actionCode);

	/**
	 * 根据主键设定评论的状态
	 * 
	 * @param code
	 */
	public void setCommentStatusByCode(@Param("code") int code,
			@Param("status") String status);

	/**
	 * 根据主键删除一条评论
	 * 
	 * @param code
	 */
	public void removeCommentByCode(@Param("code") int code);

	/***
	 * 得到评论的数量
	 * 
	 * @param actionCode
	 * @return
	 */
	public Integer getCountComment(@Param("actionCode") int actionCode);
	
	/***
	 * 得到评论的数量
	 * 
	 * @param actionCode
	 * @return
	 */
	public Integer getCountCommentNorepeat(@Param("actionCode") int actionCode);

	/***
	 * 找到前多少的评论信息
	 * 
	 * @param num
	 * @return
	 */
	public List<CommentBean> getCommentsByTop(@Param("num") int num,
			@Param("actionCode") int actionCode);
	/***
	 * 找到前多少的评论信息
	 * 
	 * @param num
	 * @return
	 */
	public List<CommentBean> getCommentsByTopNoRepeat(@Param("num") int num,
			@Param("actionCode") int actionCode);
	/****
	 * 获取某个人自己的评论
	 * 
	 * @param actionCode
	 * @param account
	 * @return
	 */
	public List<CommentBean> getCommentsByAccount(
			@Param("actionCode") int actionCode,
			@Param("account") String account);

}
