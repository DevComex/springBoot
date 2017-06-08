/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午4:18:38
 * 版本号：v1.0
 * 本类主要用途叙述：关于评论的基础业务层
 */

package cn.gyyx.action.bll.wdno1pet;

import java.util.List;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.Pagination;
import cn.gyyx.action.dao.wdno1pet.CommentDAO;
import cn.gyyx.action.dao.wdno1pet.IComment;

public class CommentBLL {
	
	private IComment commentDAO = new CommentDAO();
	/**
	 * 判断评论是否符合提交的要求
	 * 
	 * @param commentBean
	 * @return boolean
	 */
	public boolean isComment(CommentBean commentBean) {
		boolean isSuccess = true;
		if (Validation.isNickName(commentBean.getNickName()) == false) {
			isSuccess = false;
		}
		if (Validation.isComment(commentBean.getCommentContent()) == false) {
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 增加一条评论
	 * 
	 * @param commentBean
	 */
	public void addComment(CommentBean commentBean) {
		boolean isSuccess = isComment(commentBean);
		if (isSuccess) {
			IComment iComment = new CommentDAO();
			iComment.insertComment(commentBean);
		}

	}
	

	public Pagination<CommentBean> getComments(String pCode, String page) {
		// TODO 分页结果list，分页总结果数
		Pagination<CommentBean> pagination = new Pagination<CommentBean>();
		pagination.setPageNum(Integer.parseInt(page));
		List<CommentBean> comments = commentDAO.getCommentsByPagination(pCode,pagination);
		Integer count = commentDAO.getCommentsCountByPetCode(pCode);
		pagination.setObjects(comments);
		pagination.setCount(count);
		return pagination;
	}
	

}
