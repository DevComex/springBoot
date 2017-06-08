package cn.gyyx.playwd.ui.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.TimeLineBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.bll.CollectBll;
import cn.gyyx.playwd.bll.CommentBll;
import cn.gyyx.playwd.bll.TimeLineBll;
import cn.gyyx.playwd.service.ArticleService;
import cn.gyyx.playwd.service.TimeLineService;

/**
 * @Description: 图文前台
 * @author 成龙
 * @date 2017年2月28日 下午17:00:00
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {
	private Logger logger = GYYXLoggerFactory.getLogger(getClass());

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CategoryBll categoryBll;
	
	@Autowired
	private ArticleBll articleBll;
	
	@Autowired
	private TimeLineService timeLineService;
	
	@Autowired
	private TimeLineBll timeLineBll;
	
	@Autowired
	private CommentBll commentBll;
	@Autowired
	private CollectBll collectBll;

	/**
	 * 图文浏览
	 */
	@RequestMapping(value = "/view/{code}", method = { RequestMethod.GET })
	public String view(@PathVariable("code") int code, Model model,
			HttpServletRequest request,HttpServletResponse response) {
	        UserInfo user = SignedUser.getUserInfo(request);
		//根据code查询
		ArticleBean article = articleBll.getArticle(code);
		//设置404
		if(article == null || (article.getIsDelete())){//删除不显示
			return "error/404";
		}
		if((!article.getStatus().equals(ArticleBean.State.passed.Value())
                        && !article.getStatus().equals(ArticleBean.State.recommended.Value()))){//审核通过且展示、推荐才展示
		    //如果是用户登录,审核不通过的也要显示
		    if(user != null && article.getUserId().equals(user.getUserId())){
		        
		    }else{
		        return "error/404";
		    }
		}
		//增加浏览量
		articleBll.increaseViewCount(code);
		article.setViewCount(article.getViewCount()+1);
		//设置分享全路径
		article.setShareFullUrl("http://"+request.getRemoteHost()+":"+request.getRemotePort()+article.getUrl());
		//设置发布时间 前10位
		if(article.getPublishTimeStr() != null && article.getPublishTimeStr().length() > 10){
			article.setPublishTimeStr(article.getPublishTimeStr().substring(0,10));
		}
		//转码内容
		try {
			article.setContent(URLDecoder.decode(article.getContent(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("文章内容转码失败",e);
		}
		//获取分类信息
		article.setSecondCategoryName(
				categoryBll.getCategroyByCode(article.getCategoryId()).getTitle());
		//设置一级分类id和名称
		CategoryBean firstCategory = categoryBll.getParentCategoryBySubCode(article.getCategoryId());
		article.setFirstCategoryId(firstCategory.getCode());
		article.setFirstCategoryName(
		    firstCategory.getTitle());
		//查询作者力作Top 10 按照 浏览量排序 状态为通过且展示、审核推荐的
		List<String> statusList = new ArrayList<>();
		statusList.add(ArticleBean.State.recommended.Value());
		statusList.add(ArticleBean.State.passed.Value());
		List<ArticleBean> userArticleList = articleBll.getUserArticleList(
				article.getUserId(),statusList,5,"a.view_count desc");
		
        if (user != null) {
        	//查询用户是否点过赞
	        int countPraise = timeLineBll.getCount(CategoryBean.CategoryType.article.Value(),
	        		code,user.getUserId(),TimeLineBean.OperateType.like.Value());
        	model.addAttribute("praise", countPraise > 0 ? "true" : "");
    		//查询用户是否收藏过
        	int countCollect = collectBll.getCollectCount(user.getUserId(),code);
        	model.addAttribute("collect", countCollect > 0 ? "true" : "");
        }
        //获取评论区跟帖和评论数
        Map<String,Integer> commentCountMap = commentBll.getContentCommentCount(CategoryBean.CategoryType.article.Value(),code);
		model.addAttribute("item", article);//文章信息
		model.addAttribute("userArticleList", userArticleList);//用户力作列表
		model.addAttribute("threadCount", commentCountMap.get("threadCount"));//跟帖数量
		model.addAttribute("participationCount", commentCountMap.get("participationCount"));//参与数量
		return "article/view";
	}
	
	/**
	 * 点赞
	 */
	@RequestMapping(value = "/praise/{code}", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> praise(@PathVariable("code") int code, Model model,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			UserInfo user = SignedUser.getUserInfo(request);
	        if (user == null) {
	            return new ResultBean<>(false, "您还未登陆","nologin" );
	        }
			return timeLineService.insertLike(user.getUserId(), 
					CategoryBean.CategoryType.article.Value(), code);
		} catch (Exception e) {
			logger.error("设置图文点赞接口异常", e);
			return new ResultBean<>(false, "系统繁忙", "");
		}
	}
	
	/**
	 * 分享
	 */
	@RequestMapping(value = "/share/{code}", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> share(@PathVariable("code") int code,String channel, Model model,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			UserInfo user = SignedUser.getUserInfo(request);
	        if (user == null) {
	            return new ResultBean<>(false, "您还未登陆","nologin" );
	        }
	        if (StringUtils.isEmpty(channel)) {
	            return new ResultBean<>(false, "分享渠道不能为空","" );
	        }
			return timeLineService.insertShare(user.getUserId(), 
					CategoryBean.CategoryType.article.Value(), code,"分享到:"+channel);
		} catch (Exception e) {
			logger.error("设置图文点赞接口异常", e);
			return new ResultBean<>(false, "系统繁忙", "");
		}
	}
	
	/**
	 * 收藏
	 */
	@RequestMapping(value = "/collect/{code}", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> collect(@PathVariable("code") int code, Model model,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			UserInfo user = SignedUser.getUserInfo(request);
	        if (user == null) {
	            return new ResultBean<>(false, "您还未登陆","nologin" );
	        }
			return timeLineService.insertCollect(user.getUserId(), 
					CategoryBean.CategoryType.article.Value(), code,user.getAccount(),"收藏+1");
		} catch (Exception e) {
			logger.error("设置图文收藏接口异常", e);
			return new ResultBean<>(false, "系统繁忙", "");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	  * <p>
	  *    方法说明
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月20日 下午2:20:09 描述
	  *
	  * @param categoryId 分类id  全部为0
	  * @param pageIndex 当前页
	  * @param pageSize 页面大小
	  * @param sortType  排序类型    最新:new  最热:view  收藏量:collect
	  * @param request
	  * @param response
	  * @return PageBean<ArticleBean>
	 */
        @RequestMapping(value = "/list" )
        @ResponseBody
        public PageBean<ArticleBean> list(Integer categoryId ,Integer pageIndex,Integer pageSize,@RequestParam("sortType")String sortType,
                 HttpServletRequest request,HttpServletResponse response) {
                if(categoryId==null||pageIndex==null||pageSize==null){
                    return new PageBean<ArticleBean>(false, "分类ID或分页属性不可为空");
                }
                if(!sortType.equals("new")&&!sortType.equals("view")&&!sortType.equals("collect")){
                    return new PageBean<ArticleBean>(false, "sortType只能为new和hot和collect");
                }
                 
                try {
                    logger.info("获取图文列表接口:categoryId:{},pageIndex:{},pageSize:{},sortType:{} ",categoryId,pageIndex,pageSize,sortType );
                    PageBean<ArticleBean> pageBean=articleService.findList(categoryId,pageIndex,pageSize,sortType);
                    return pageBean;
                } catch (Exception e) {
                    logger.error("获取图文列表接口异常:",e);
                    return new PageBean<ArticleBean>(false, "获取图文列表接口异常");
                }
        }
}
