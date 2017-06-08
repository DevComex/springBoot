package cn.gyyx.playwd.ui.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.agent.CaptchaValidate;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.Collect;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.beans.playwd.TimeLineBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.CollectBll;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.service.ArticleService;
import cn.gyyx.playwd.service.CollectService;
import cn.gyyx.playwd.service.MessageService;
import cn.gyyx.playwd.service.RoleService;
import cn.gyyx.playwd.service.TimeLineService;
import cn.gyyx.playwd.service.UserService;
import cn.gyyx.playwd.ui.common.web.BaseController;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
/**
 * 
  * <p>
  *   UserCenter
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/userCenter")
public class UserCenterController extends BaseController{
	
	private Logger logger = GYYXLoggerFactory.getLogger(getClass());
	
	@Autowired
	private CollectService collectService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	@Autowired
	private  MessageBll messageBll;	
	@Autowired
	private CollectBll collectBll;
	@Autowired
        private MessageService messageService;
        @Autowired
        private RoleService rolleService;
        @Autowired
        TimeLineService timeLineService;
        private CaptchaValidate captchaValidate = new CaptchaValidate();//验证码类

	/**
	 * 
	  * <p>
	  *    获取用户收藏图文list
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月7日 下午6:58:42 描述
	  *
	  * @param model
	  * @param request
	  * @return ResultBean<Object>
	 */
	@RequestMapping("/collectlist")
	@ResponseBody
	public PageBean<Collect>  collectlist(Model model,int pageIndex,int pageSize, HttpServletRequest request) {
	    UserInfo user = SignedUser.getUserInfo(request);
	    try {
	    	if (user == null) {
            	return PageBean.createPage(false, 0, 1, 10, null, "您还未登陆");
            }
	    	
            logger.info("获取用户收藏列表:userId{} account{}", user.getUserId(),user.getAccount());
            int startSize = (pageIndex - 1) * pageSize;
            int endSize =   pageSize;
            List<Collect> list =collectService.getCollectByUserId(user.getUserId(),startSize,endSize);
            int count =  collectService.getCollectCountByUserId(user.getUserId());
                
            return  PageBean.createPage(true,count, pageIndex,pageSize, list,"获取用户收藏图文成功");
        } catch (Exception e) {
        	logger.error("获取用户收藏列表错误 {}:",e);
            return  PageBean.createPage(false, 0, 0, null, "获取用户收藏图文接口错误");
        }
	}
	
	/**
	 * 
	  * <p>
	  *    获取我的上传文章
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月8日 下午6:24:49 描述
	  *
	  * @param model
	  * @param request
	  * @return ResultBean<Object>
	 */
	@RequestMapping("/uploadArticle")
    @ResponseBody
    public PageBean<ArticleBean>  uploadArticle(String State ,int pageIndex,int pageSize, HttpServletRequest request) {
        try {
        	UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return PageBean.createPage(false, 0, 1, 10, null, "您还未登陆");
            }
            if(State!=null&&State.equals("")){
                State=null;
            }
            logger.info("获取用户上传列表:userId{0} account{1}", user.getUserId(),user.getAccount());
          
            return articleService.getArticleByUserId(user.getUserId(),State, pageIndex, pageSize);
        } catch (Exception e) {
            logger.error("获取用户收藏列表错误 {}:", e);
            return PageBean.createPage(false, 0, 0, null, "获取用户上传文章接口错误");
        }      
    }
	
	/**
	 * 记者名片
	 */
	@RequestMapping(value = "/userinfo/{userId}", method = { RequestMethod.GET })
	@ResponseBody
	public ResultBean<Object> userInfo(@PathVariable("userId") int userId, Model model,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			UserBean memberInfoBean = userService.getUserInfoAll(userId);
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("userId", memberInfoBean.getUserId());
			resultMap.put("rankNum", memberInfoBean.getRankNum());
			resultMap.put("rewardSum", memberInfoBean.getRewardSum());
			resultMap.put("title", memberInfoBean.getTitle());
			resultMap.put("icon", memberInfoBean.getIcon());
			resultMap.put("articleCount", memberInfoBean.getArticleCount());
			return new ResultBean<>(true, "获取成功", resultMap);
	    } catch (Exception e) {
	        logger.error("获取记者名片接口异常 ", e);
	        return new ResultBean<>(false, "获取记者名片接口异常", null);
	    }
	}
	
	/**
	 * 财富排行
	 * @return
	 */
	@GetMapping("/wealthRanking")
	@ResponseBody
	public ResultBean<Object> getWealthRankingList(Integer topNumber) {
		try {
            //默认10条数据
            topNumber=(topNumber==null||topNumber.intValue()<=0)?10:topNumber;            
            return userService.getWealthRankingList(topNumber);
		} catch (Exception e) {
			logger.error("获取财富排行异常", e);
			return new ResultBean<Object>("unknown_error", "获取财富排行异常", null);
		}
	}
	
	 /**
     * 获取编辑回复列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/editorList")
    @ResponseBody
    public PageBean<Map<String, Object>> getListMessage(Integer pageIndex,Integer pageSize, HttpServletRequest request) {
    	try {
    		UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
            	return PageBean.createPage("incorrect-login", 0, 1, 10, new ArrayList<Map<String, Object>>(), "您还未登陆");
            }

    		if(pageIndex==null||pageIndex.intValue()<=0||pageSize==null||pageSize.intValue()<=0){
    			return PageBean.createPage("incorrect-parameter", 0, 1, 10, new ArrayList<Map<String, Object>>(), "参数错误");
    		}
    		
    		PageBean<MessageBean> resultBean=messageService.getListMessage(user.getUserId(), pageSize, pageIndex);
    		if(resultBean.getCount()==0){
    			return PageBean.createPage("success", 0, pageIndex, pageSize, new ArrayList<Map<String, Object>>(), "成功");
    		}

    		List<Map<String, Object>> ResultBeanList=FluentIterable.from(resultBean.getDataSet())
    				.transform(new Function<MessageBean, Map<String, Object>>() {

    					@Override
    					public Map<String, Object> apply(MessageBean info) {
    						
    						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    						String strDate="无";
    						if(info.getCreateTime()!=null){
    							strDate = format.format(info.getCreateTime());
    						}
    						
    						Map<String, Object> map=new HashMap<String, Object>();
    						map.put("code", info.getCode());
    						map.put("message", info.getMessage());
    						map.put("createDate", strDate);
    						return map;
    					}
    				}).toList();
    		return PageBean.createPage("success", resultBean.getCount(), pageIndex, pageSize, ResultBeanList, "成功");	
			
		} catch (Exception e) {
			logger.error("编辑回复列表异常", e);
			return PageBean.createPage("unknown_error", 0, 1, 10, new ArrayList<Map<String, Object>>(), "请稍后查询");
		}
    }
    
    /**
     * 我的消息列表
     * @param pageIndex
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/myMessageList")
    @ResponseBody
    public PageBean<Map<String, Object>> getMyMessageList(Integer pageIndex,Integer pageSize, HttpServletRequest request) {
    	try {
    		UserInfo user = SignedUser.getUserInfo(request);
    		if (user == null) {
            	return PageBean.createPage("incorrect-login", 0, 1, 10, new ArrayList<>(), "您还未登陆");
            }
            
            if(pageIndex==null||pageIndex.intValue()<=0||pageSize==null||pageSize.intValue()<=0){
    			return PageBean.createPage("invalid-parameter", 0, 1, 10, new ArrayList<>(), "参数错误");
    		}
    		
    		return messageService.getListMyMessage(user.getUserId(), pageSize, pageIndex);	
			
		} catch (Exception e) {
			logger.error("编辑回复列表异常", e);
			return PageBean.createPage("unknown_error", 0, 1, 10, new ArrayList<>(), "请稍后查询");
		}
    }
    /**
     * 
      * <p>
      *    取消收藏
      * </p>
      *
      * @action
      *    lihu 2017年4月18日 下午6:22:26 描述
      *
      * @param code
      * @param request
      * @return ResultBean<String>
     */
    @GetMapping("/cancelCollect")
    @ResponseBody
    public ResultBean<String> cancelCollect(@RequestParam("code")Integer code,
            HttpServletRequest request) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "用户未登录");
            }
             collectService.cancelCollect(user.getUserId(),code,user.getAccount());
             return new ResultBean<>(true, "取消收藏成功");
        } catch (Exception e) {
            logger.error("取消收藏接口异常", e);
            return new ResultBean<>(false, "取消收藏接口异常");
        }
    }
    
    
  /**
   * 
    * <p>
    *    同步角色(插入)
    * </p>
    *
    * @action
    *    lihu 2017年4月24日 下午7:53:08 描述
    *
    * @param netId
    * @param serverId
    * @param serverName
    * @param qq
    * @param request
    * @return ResultBean<String>
   */
    @PostMapping("/instertRole")
    @ResponseBody
    public ResultBean<String> instertRole(@RequestParam("netId")Integer netId,@RequestParam("validCode")String validCode,
            @RequestParam("serverId")Integer serverId,@RequestParam("serverName")String serverName,String qq,
            HttpServletRequest request) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "用户未登录");
            }
            if(serverName ==null||serverId==null||netId==null){
                return new ResultBean<>(false, "参数不可为空");
            }
            int captcha = captchaValidate.checkCaptcha(validCode, "jnajq", request);
             if(captcha!=0){
                 return new ResultBean<>(false, "验证码不正确");
             };
            return rolleService.instertRole(user.getUserId(),user.getAccount(),netId,serverId,serverName,qq);
        } catch (Exception e) {
            logger.error("同步角色接口异常", e);
            return new ResultBean<>(false, "同步角色接口异常");
        }
    }
    
    /**
     * 
      * <p>
      *    我的角色列表
      * </p>
      *
      * @action
      *    lihu 2017年4月25日 下午1:13:30 描述
      *
      * @param request
      * @return ResultBean<String>
     */
    @GetMapping("/myRole")
    @ResponseBody
    public ResultBean<List<RoleBean>> myRole( HttpServletRequest request) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "用户未登录");
            }
            
            
            return rolleService.myRole(user.getUserId());
        } catch (Exception e) {
            logger.error("我的角色列表接口异常", e);
            return new ResultBean<>(false, "我的角色列表接口异常");
        }
    }
    
    /**
     * 
      * <p>
      *    修改默认角色
      * </p>
      *
      * @action
      *    lihu 2017年4月25日 下午1:20:56 描述
      *
      * @param code
      * @param request
      * @return ResultBean<List<RoleBean>>
     */
    @GetMapping("/editRole")
    @ResponseBody
    public ResultBean<List<RoleBean>> editRole( Integer code,HttpServletRequest request) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "用户未登录");
            }
            
            
            rolleService.editRole(code,user.getUserId());
            return new ResultBean<>(true, "修改默认角色成功");
        } catch (Exception e) {
            logger.error("修改默认角色接口异常", e);
            return new ResultBean<>(false, "修改默认角色接口异常");
        }
    }
    
 /**
  * 
   * <p>
   *    获取默认角色
   * </p>
   *
   * @action
   *    lihu 2017年4月26日 上午10:02:30 描述
   *
   * @param request
   * @return ResultBean<List<RoleBean>>
  */
    @GetMapping("/defaultRole")
    @ResponseBody
    public ResultBean<RoleBean> defaultRole(HttpServletRequest request) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "用户未登录");
            }
            
            
            RoleBean  role= rolleService.defaultRole(user.getUserId());
            return new ResultBean<>(true, "获取默认角色成功",role);
        } catch (Exception e) {
            logger.error("修改默认角色接口异常", e);
            return new ResultBean<>(false, "获取默认角色接口异常");
        }
    }
    
    
}
