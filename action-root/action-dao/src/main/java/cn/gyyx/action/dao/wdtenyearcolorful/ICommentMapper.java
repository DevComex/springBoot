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

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtenyearcolorful.CommentBean;

/**
 * @ClassName: ICommentMapper
 * @description 评论Mapper
 * @author bozhencheng
 * @date 2016年5月24日
 */
public interface ICommentMapper {

	/**
	  * @Title: insert
	  * @Description:  插入评论信息
	  * @param commentBean 评论实体
	  * @return boolean 
	  */
	public boolean insertComment(CommentBean commentBean);
	
	/**
	  * @Title: updateCommentStatus
	  * @Description: 更新评论状态  
	  * @param commentIds 评论主键数组
	  * @param status 更新状态
	  * @return boolean 
	  * @throws
	  */
	public boolean updateCommentStatus(@Param("commentIds")int[] commentIds,@Param("status")int status);
	
	/**
	  * @Title: selectValidComments
	  * @Description: 获取首页展示评论 
	  * @return List<CommentBean> 评论列表
	  * @throws
	  */
	public List<CommentBean> selectValidComments();
	
	/**
	  * @Title: selectAllComments
	  * @Description: 获取所有评论 
	  * @param pageIndex 页面序数
	  * @param pageSize 单页面条数
	  * @return List<CommentBean> 评论列表
	  * @throws
	  */
	public List<CommentBean> selectAllComments(@Param("pageIndex")int pageIndex, @Param("pageSize")int pageSize);
	
	/**
	  * @Title: selectAllCommentsCount
	  * @Description:  获取所有评论的总数
	  * @param 
	  * @return int 
	  * @throws
	  */
	public int selectAllCommentsCount();
	
	
}
