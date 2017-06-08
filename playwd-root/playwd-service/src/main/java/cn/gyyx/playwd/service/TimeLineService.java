package cn.gyyx.playwd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.TimeLineBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CollectBll;
import cn.gyyx.playwd.bll.TimeLineBll;

@Service
public class TimeLineService {
	
	/**
	 * 获取用户信息
	 */
	@Autowired
	private TimeLineBll timeLineBll;
	
	/**
	 * 图文Bll
	 */
	@Autowired
	private ArticleBll articleBll;
	
	/**
	 * 收藏Bll
	 */
	@Autowired
	private CollectBll collectBll;
	
	/**
	 * 插入点赞日志
	 */
	public ResultBean<String> insertLike(int userId,String contentType,int contentId) throws Exception {
        
		int toUserId = 0;
		if(contentType.equals(CategoryBean.CategoryType.article.Value())){
			//判断是否点过赞
	        int count = timeLineBll.getCount(CategoryBean.CategoryType.article.Value(),
	        		contentId,userId,TimeLineBean.OperateType.like.Value());
	        if(count > 0){
	        	return new ResultBean<>(false, "已经点过赞了", "");
	        }
			ArticleBean article = articleBll.getArticle(contentId);
			if(article == null){
				return new ResultBean<>(false, "点赞失败,资源不存在");
			}
			toUserId = article.getUserId();
			//增加点赞数量
			articleBll.increaseLikeCount(contentId);
		}
		//插入点赞日志
		timeLineBll.insert(userId, toUserId, contentType, 
				contentId, TimeLineBean.OperateType.like.Value(),
				new Date(), "点赞+1");
		return new ResultBean<>(true, "点赞成功");
	}
	
	/**
	 * 插入分享日志
	 */
	public ResultBean<String> insertShare(int userId,String contentType,int contentId,String description) throws Exception {
		int toUserId = 0;
		if(contentType.equals(CategoryBean.CategoryType.article.Value())){
			ArticleBean article = articleBll.getArticle(contentId);
			if(article == null){
				return new ResultBean<>(false, "分享失败,资源不存在");
			}
			toUserId = article.getUserId();
		}
		//插入分享日志
		timeLineBll.insert(userId, toUserId, contentType, 
				contentId, TimeLineBean.OperateType.share.Value(),
				new Date(), description);
		return new ResultBean<>(true, "分享成功");
	}
	
	/**
	 * 插入收藏日志
	 */
	@Transactional
	public ResultBean<String> insertCollect(int userId,String contentType,int contentId,String account,String mark) throws Exception {
		int toUserId = 0;
		if(contentType.equals(CategoryBean.CategoryType.article.Value())){
			//判断是否收藏过
	        int count =   collectBll.getCollectCount(userId,contentId);
	        if(count > 0){
	        	return new ResultBean<>(false, "已经收藏过了", "");
	        }
			ArticleBean article = articleBll.getArticle(contentId);
			if(article == null){
				return new ResultBean<>(false, "收藏失败,资源不存在");
			}
			toUserId = article.getUserId();
			//增加收藏数量
			articleBll.increaseCollectCount(contentId);
			//收藏记录表插入
			collectBll.insert(contentType, contentId, userId, account);
		}
		//插入分享日志
		timeLineBll.insert(userId, toUserId, contentType, 
				contentId, TimeLineBean.OperateType.collect.Value(),
				new Date(), mark);
		return new ResultBean<>(true, "收藏成功");
	}
	
}
