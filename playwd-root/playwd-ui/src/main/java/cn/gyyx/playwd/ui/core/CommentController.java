package cn.gyyx.playwd.ui.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.Ip;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.CommentBean;
import cn.gyyx.playwd.service.CommentService;

/**
 * 评论controller
 * 
 * @author chenglong
 *
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {
	
	private Logger logger = GYYXLoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommentService commentService;

	/**
	 * 获取浏览页评论
	 */
	@RequestMapping(value = "/list")
    @ResponseBody
    public ResultBean<List<Map<String,String>>> list(String contentType,Integer contentId,Integer pageIndex,Integer pageSize,
             HttpServletRequest request,HttpServletResponse response) {
			logger.info("获取评论列表接口:contentType:{},contentId:{} ,pageIndex:{},pageSize:{}",contentType,contentId,pageIndex,pageSize);
            try {
                if(!this.validContentType(contentType)){
            		return new ResultBean<>(false, "评论内容类型无效",null);
            	}
                if(contentId == null){
            		return new ResultBean<>(false, "评论内容ID不能为空",null);
            	}
                pageIndex = (pageIndex == null || pageIndex <=0 ) ? 1 : pageIndex; 
                pageSize = pageSize == null ? 10 : pageSize; 
                return commentService.getBrowsePageCommentList(contentType,contentId,pageIndex,pageSize);
            } catch (Exception e) {
                logger.error("获取评论列表接口异常",e);
                return new ResultBean<>(false, "获取评论列表接口异常",null);
            }
    }
	
	/**
	 * 查看回复
	 */
	@RequestMapping(value = "/reply/list")
    @ResponseBody
    public ResultBean<List<Map<String,String>>> replyList(Integer commentId,
             HttpServletRequest request,HttpServletResponse response) {
            try {
            	if(commentId == null){
            		return new ResultBean<>(false, "评论内容ID不能为空",null);
            	}
                logger.info("获取回复列表接口:commentId:{}",commentId);
                return commentService.getBrowsePageReplyList(commentId);
            } catch (Exception e) {
                logger.error("获取回复列表接口异常",e);
                return new ResultBean<>(false, "获取回复列表接口异常",null);
            }
    }
	
	/**
	 * 回复/评论
	 */
	@RequestMapping(value = "", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Object> comment(CommentBean bean,HttpServletRequest request,HttpServletResponse response) {
            try {
            	UserInfo user = SignedUser.getUserInfo(request);
                if (user == null) {
                    return new ResultBean<>(false, "您还未登陆","no-login" );
                }
                if(StringUtils.isEmpty(bean.getContent())){
            		return new ResultBean<>(false, "评论内容不能为空","");
            	}
                if(bean.getCode() == null){//代表是评论作品
                	if(!this.validContentType(bean.getContentType())){
                		return new ResultBean<>(false, "评论内容类型无效","");
                	}
                	if(bean.getContentId() == null){
                		return new ResultBean<>(false, "评论内容ID不能为空","");
                	}
                }
                bean.setFromUserId(user.getUserId());
                bean.setFromUserAccount(user.getAccount());
                bean.setFromIp(Ip.getCurrent(request).asString());
                return commentService.comment(bean);
            } catch (Exception e) {
                logger.error("评论回复接口异常",e);
                return new ResultBean<>(false, "评论回复接口异常");
            }
    }
	
	/**
	 * 验证评论内容类型
	 * @param contentType
	 * @return
	 */
	public boolean validContentType(String contentType){
		if(StringUtils.isEmpty(contentType) 
    			|| (!contentType.equals(CategoryBean.CategoryType.article.Value()))){
    		return false;
    	}
		return true;
	}
}
