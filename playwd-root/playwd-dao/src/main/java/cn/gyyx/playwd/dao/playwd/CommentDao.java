package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.CommentBean;

/**
 * 评论dao
 * @author chenglong
 *
 */
public interface CommentDao {

	/**
	 * 插入
	 * @param record
	 * @return
	 */
    int insert(CommentBean record);

	/**
	 * 修改评论状态
     * @param code
     * @return
     */
    int updateCommentIsShow(@Param("code")int code,@Param("isShow") Boolean isShow);
    
    /**
     * get By code
     * @param code
     * @return
     */
    CommentBean get(Integer code);

    /**
     * 获取内容的评论和参与的数量
     * @param contentType
     * @param contentId
     * @return
     */
	Map<String, Integer> getContentCommentCount(@Param("contentType") String contentType,
			@Param("contentId") int contentId);

	/**
	 * 获取浏览页评论列表
	 * @param contentType
	 * @param contentId
	 * @param startSize
	 * @param endSize
	 * @return
	 */
	List<CommentBean> getBrowsePageCommentList(@Param("contentType") String contentType,
			@Param("contentId") Integer contentId,@Param("startSize") int startSize,@Param("endSize") int endSize);
	

	/**
	 * 回复列表
	 * @param commentId 
	 * @return
	 */
	List<CommentBean> getBrowsePageReplyList(@Param("commentId") int commentId);

	/**
	 * count
	 * @param contentType
	 * @param contentId
	 * @return
	 */
	int getCount(@Param("contentType") String contentType,@Param("contentId") Integer contentId);

	List<Map<String, Object>> selectCommentList(Map<String, Object> map);
        int selectCommentListCount(Map<String, Object> map);

        List<Map<String, Object>> viewDetailsByCode(Map<String, Object> map);

        void auditing(@Param("code")Integer code,@Param("status") String status);
}