 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月13日上午10:40:36
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.MessageBll;

@Service
public class MessageService {
	
	private MessageBll messageBll;

	private ArticleBll articleBll;

	/**
	 * 编辑回复
	 * @param contentType
	 * @param contentId
	 * @param message
	 * @return
	 */
	public ResultBean<Object> editorMessage(String contentType,Integer contentId,String message) {
		
		//获取文章信息
		ArticleBean articleBean=articleBll.getArticleById(contentId);
 		//增加消息
		boolean result= messageBll.add(message, MessageBean.MessageType.editor.Value(),contentId,
				contentType, articleBean.getUserId(),articleBean.getTitle());
		
		if(result){
			return new ResultBean<Object>("success", "成功", null);
		}
		return new ResultBean<Object>("incorrect-failed", "回复失败", null);
	}
	
	/**
	 * 获取编辑回复列表
	 * @param userId
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageBean<MessageBean> getListMessage(int userId,int pageSize,int pageIndex) {
		//获取数量
		int count=messageBll.getMessageCount(userId);
		if(count<=0)
		{
			return PageBean.createPage("success", 0, pageIndex, pageSize, null, "成功");
		}
		
		//获取列表
		List<MessageBean> list=messageBll.getMessageList(userId, pageIndex, pageSize);
		return PageBean.createPage("success", count, pageIndex, pageSize, list, "成功");	
	}
	
	/**
	 * 获取我的消息列表
	 * @param userId
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageBean<Map<String, Object>> getListMyMessage(int userId,int pageSize,int pageIndex) {
		//获取数量
		int count=messageBll.getMyMessageCount(userId);
		if(count<=0)
		{
			return PageBean.createPage("success", 0, pageIndex, pageSize, new ArrayList<>(), "成功");
		}
		
		//获取列表
		List<MessageBean> list=messageBll.getMyMessageList(userId, pageIndex, pageSize);
		
		List<Map<String, Object>> resultList=FluentIterable.from(list)
				.transform(new Function<MessageBean, Map<String, Object>>() {

					@Override
					public Map<String, Object> apply(MessageBean bean) {
						
						MessageBean info =messageBll.getMessageContentInfo(bean);
						
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						String strDate="无";
						if(info.getCreateTime()!=null){
							strDate = format.format(info.getCreateTime());
						}
						
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("code", info.getCode());
						map.put("contentTitle", info.getContentTitle());
						map.put("contentType", info.getContentType());
						map.put("message", info.getMessage());
						map.put("url", info.getContentUrl());
						map.put("createDate", strDate);
						return map;
					}
				}).toList();
		
		return PageBean.createPage("success", count, pageIndex, pageSize, resultList, "成功");	
	}

	public MessageBll getMessageBll() {
		return messageBll;
	}

	@Autowired
	public void setMessageBll(MessageBll messageBll) {
		this.messageBll = messageBll;
	}

	public ArticleBll getArticleBll() {
		return articleBll;
	}

	@Autowired
	public void setArticleBll(ArticleBll articleBll) {
		this.articleBll = articleBll;
	}
}
