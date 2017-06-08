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
package cn.gyyx.action.bll.wdtenyearcolorful;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;

import com.microsoft.sqlserver.jdbc.ISQLServerDataSource;

import cn.gyyx.action.beans.wdtenyearcolorful.CommentBean;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.action.dao.wdtenyearcolorful.CommentDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import freemarker.core.Comment;

/**
 * @ClassName: CommentBll
 * @description 评论操作业务层
 * @author bozhencheng
 * @date 2016年5月25日
 */
public class CommentBll {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CommentBll.class);
	private CommentDao commentDao = null;
	
	public CommentBll(){
		commentDao = new CommentDao();
	}
	
	/**
	  * @Title: comment
	  * @Description:  保存评论
	  * @param bean 评论信息
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	public ResultBean<Boolean> comment(CommentBean bean){
		ResultBean<Boolean> result = new ResultBean<>();
		boolean isSuccess = false;
		try {
			bean.setStatus(CommentBean.INIT);
			bean.setCreateTime(new Date());
			isSuccess = commentDao.insertComment(bean);
		} catch (PersistenceException e) {
			logger.warn("PersistenceException in commentDao.insertComment :" + e);
			result.setMessage("保存评论故障");
		}
		result.setIsSuccess(isSuccess);
		return result;
	}
	
	
	/**
	  * @Title: commentStatusUpdate
	  * @Description:  更新评论状态
	  * @param commentIds 评论主键数组
	  * @param 要更新的状态
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	public ResultBean<Boolean> commentStatusUpdate(int[] commentIds, int status){
		ResultBean<Boolean> result = new ResultBean<>();
		boolean isSuccess = false;
		if(commentIds.length == 0){
			result.setMessage("请选择要更新的数据");
			return result;
		}
		try {
			isSuccess = commentDao.updateCommentStatus(commentIds, status);
		} catch (PersistenceException e) {
			logger.warn("更新评论状态失败, ids: "+commentIds + " status: "+status + e);
			isSuccess = false;
		}
		result.setIsSuccess(isSuccess);
		return result;
	}
	
	/**
	  * @Title: getValidComments
	  * @Description:  获取首页显示的评论
	  * @return List<CommentBean> 
	  * @throws
	  */
	public List<CommentBean> getValidComments(){
		return commentDao.selectValidComments();
	}
	
	/**
	  * @Title: getAllComments
	  * @Description:  获取所有的评论
	  * @param 
	  * @return List<CommentBean> 
	  * @throws
	 */
	public List<CommentBean> getAllComments(int pageIndex, int pageSize){
		return commentDao.selectAllComments(pageIndex, pageSize);
	}

	/**
	  * @Title: getAllComments
	  * @Description:  获取所有的评论总数
	  * @param 
	  * @return int
	  * @throws
	 */
	public int getAllCommentsCount(){
		return commentDao.selectAllCommentsCount();
	}
	
}
