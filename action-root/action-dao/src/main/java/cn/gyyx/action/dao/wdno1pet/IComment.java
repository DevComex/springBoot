/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午3:56:54
 * 版本号：v1.0
 * 本类主要用途叙述：关于评论DAO层接口
 */

package cn.gyyx.action.dao.wdno1pet;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.Pagination;

public interface IComment {
	/**
	 * 向数据库中插入一条评论
	 * 
	 * @param commentBean
	 */
	public void insertComment(CommentBean commentBean);

	/**
	 * 从数据库获取评论列表
	 * 
	 * @param pCode
	 * @param pagination
	 * @return
	 */
	public List<CommentBean> getCommentsByPagination(
			@Param("petCode") String pCode,
			@Param("pagination") Pagination<CommentBean> pagination);

	/**
	 * 从数据库获取每个宠物对应的评论总数
	 * 
	 * @param pCode
	 * @return
	 */
	public Integer getCommentsCountByPetCode(@Param("petCode") String petCode);

}
