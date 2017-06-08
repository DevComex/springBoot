package cn.gyyx.playwd.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.CommentBean;
import cn.gyyx.playwd.dao.playwd.CommentDao;
import cn.gyyx.playwd.utils.DateTools;

/**
 * 评论bll
 * @author lidudi
 *
 */
@Component
public class CommentBll {

	@Autowired
	CommentDao commentDao;
	
	/**
	 * 插入
	 * @param record
	 * @return
	 */
	public int insert(CommentBean record) {
    	return commentDao.insert(record);
    }

	/**
	 * 修改评论状态
	 * @param code
	 * @param isShow
	 * @return
	 */
	public boolean changeCommentIsShow(int code,Boolean isShow) {
		return commentDao.updateCommentIsShow(code, isShow)>0;
    }
    
    /**
     * get By code
     * @param code
     * @return
     */
	public CommentBean get(Integer code) {
		return commentDao.get(code);
    }

	/**
	 * 获取资源的评论数量和参与数量
	 * @param contentType
	 * @param contentId
	 * @return
	 */
	public Map<String, Integer> getContentCommentCount(String contentType, int contentId) {
		Map<String, Integer> map = commentDao.getContentCommentCount(contentType,contentId);
		if(map == null){
			map = new HashMap<>();
			map.put("threadCount", 0);//代表评论数量
			map.put("participationCount", 0);//代表参与回复和评论的数量
		}
		if(map.get("threadCount") == null){
			map.put("threadCount", 0);
		}
		if(map.get("participationCount") == null){
			map.put("participationCount", 0);
		}
		return map;
	}

	/**
	 * 获取浏览页评论列表
	 * @param contentType
	 * @param contentId
	 * @param startSize
	 * @param endSize
	 * @return
	 */
	public List<CommentBean> getBrowsePageCommentList(String contentType,
			Integer contentId, int startSize, int endSize) {
		return commentDao.getBrowsePageCommentList(contentType,contentId,startSize,endSize);
	}

	/**
	 * 设置会显评论时间
	 * 
	 * 当前时间距离发表时间小于1小时时显示X分钟前，大于1小时但不足2小时时显示1小时前，
	 * 大于2小时不足3小时时显示2小时前，一次类推直至5小时前。之后显示具体发表时间，
	 * 例如：2017-03-11  15：18
	 * 
	 * @param createTime
	 * @return
	 */
	public String converCommentDisplayTime(Date createTime) {
		Date now = new Date();
		long minusMinute = (long)(now.getTime() - createTime.getTime())/1000/60;
		if(minusMinute == 0){
			return "刚刚";
		}
		if(minusMinute < 60){
			return minusMinute + "分钟前";
		}
		if(minusMinute/60 <= 5){
			return minusMinute/60 + "小时前";
		}
		return DateTools.formatDate(createTime, "yyyy-MM-dd HH:mm");
	}
	

	/**
	 * 获取回复列表
	 * @param commentId
	 * @return
	 */
	public List<CommentBean> getBrowsePageReplyList(int commentId) {
	    List<CommentBean> list = commentDao.getBrowsePageReplyList(commentId);
	    if(list == null){
	        return new ArrayList<>() ;
	    }
		return list;
	}

	/**
	 * 获取数量
	 * @param contentType 内容类型
	 * @param contentId 内容ID
	 * @return
	 */
	public int getCount(String contentType, Integer contentId) {
		return commentDao.getCount(contentType,contentId);
	}
	/**
         * 
          * <p>
          *    获取评论列表
          * </p>
          *
          * @action
          *    lihu 2017年4月17日 上午11:51:17 描述
          *
          * @param pageIndex
          * @param pageSize
          * @param content
          * @param account
          * @param contentType
          * @param title
          * @param isShow
          * @param startTime
          * @param endTime
          * @return List<Map<String,Object>>
         */
        public List<Map<String, Object>>selectCommentList(Integer pageIndex, Integer pageSize, String content, String account, String contentType, String title, Integer isShow, String startTime, String endTime) {
            int startSize = (pageIndex - 1) * pageSize;
            int endSize =   pageSize;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("startSize", startSize);
            map.put("endSize", endSize);
            map.put("content", content);
            map.put("contentType", contentType);
            map.put("account", account);
            map.put("title", title);
            map.put("isShow", isShow);
            map.put("startTime", startTime);
            map.put("endTime", endTime); 
            return commentDao.selectCommentList(map);
        }
        /**
         * 
          * <p>
          *    获取评论列表 数量
          * </p>
          *
          * @action
          *    lihu 2017年4月17日 上午11:51:45 描述
          *
          * @param pageIndex
          * @param pageSize
          * @param content
          * @param account
          * @param contentType
          * @param title
          * @param isShow
          * @param startTime
          * @param endTime
          * @return int
         */
    public int selectCommentListCount(Integer pageIndex, Integer pageSize,
            String content, String account, String contentType, String title,
            Integer isShow, String startTime, String endTime) {
        int startSize = (pageIndex - 1) * pageSize;
        int endSize =   pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startSize", startSize);
        map.put("endSize", endSize);
        map.put("content", content);
        map.put("contentType", contentType);
        map.put("account", account);
        map.put("title", title);
        map.put("isShow", isShow);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return commentDao.selectCommentListCount(map);
    }
    /**
     * 
      * <p>
      *    获取详情
      * </p>
      *
      * @action
      *    lihu 2017年4月17日 下午4:21:51 描述
      *
      * @param code
     * @param parent 
      * @return ResultBean<CommentBean>
     */
        public List<Map<String, Object>> viewDetailsByCode(Integer code, String parent) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", code);
            map.put("parent", parent);
            return commentDao.viewDetailsByCode(map);
        }
    
}
