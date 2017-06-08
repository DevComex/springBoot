package cn.gyyx.playwd.ui.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.agent.WDGameAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.common.ServerBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.AuthorInfoBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.service.ArticleUploadService;
import cn.gyyx.playwd.ui.viewmodule.ArticleInstertModule;
import cn.gyyx.playwd.utils.MemcacheTools;


/**
 * 
  * <p>
  *   UploadController 前台UI上传页面
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@RequestMapping("/upload")
@Controller
public class UploadController {
	
    private static final String SERVER_LIST = "SERVER_LIST";
    private static final String USER_INFO = "USER_INFO";
    private MemcacheTools memcacheTools = new MemcacheTools();
    private XMemcachedClientAdapter memcache = memcacheTools.getMemcache();
    private Logger logger = GYYXLoggerFactory.getLogger(getClass());
    @Autowired
    private ArticleUploadService articleUploadService;
    @Autowired
    private ArticleBll articleBll;
    @Autowired
    private WDGameAgent gameAgent;
    
    /**
     * 
      * <p>
      *     获取服务器列表
      * </p>
      *
      * @action
      *    lihu 2017年3月7日 下午1:51:02 描述
      *
      * @param netType
      * @return ResultBean<Object>
     */
    @RequestMapping("/serverlist")
    @ResponseBody
    public ResultBean<Object> serverlist(@RequestParam("netType") int netType) {
            ResultBean<Object> resultBean = new ResultBean<>();
            ServerBean bean = memcache.get(SERVER_LIST+netType, ServerBean.class);
            try {
                    if(bean ==null){
                            bean=gameAgent.getServers(2, netType);
                            memcache.set(SERVER_LIST+netType, 3600 * 24, bean);
                    }
                    resultBean.setProperties(true, "返回服务器列表", bean);
            } catch (Exception e) {
                    resultBean.setProperties(false, "回服务器列表异常", null);
                    logger.warn("返回服务器列表异常:", e);
            }
            return resultBean;
    }

    /**
     * 
      * <p>
      *    添加图文
      * </p>
      *
      * @action
      *    lihu 2017年3月8日 上午11:40:02 描述
      *
      * @param articleModule
      * @param bindingResult
      * @param request
      * @return ResultBean<Object>
     */
    @RequestMapping("/instertArticle")
    @ResponseBody
    public ResultBean<Object> instertArticle(@Valid ArticleInstertModule articleModule,
            BindingResult bindingResult, HttpServletRequest request) {
        ResultBean<Object> resultBean = new ResultBean<>();
        
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                resultBean.setSuccess(false);
                resultBean.setMessage("请先登录");
                return resultBean;
            }
            
            ModelMapper modelMapper = new ModelMapper();
            if (bindingResult.hasErrors()) {
                String messageString = validationData(bindingResult);
                String[] messageStrings = messageString.split("\\|");
                return new ResultBean<Object>(false, messageStrings[1], null);
            }
            //验证bean和实体bean转换
            ArticleBean articleBean = modelMapper.map(articleModule, ArticleBean.class);
            //添加文章
            articleUploadService.instertArticle(articleBean,user.getAccount(),user.getUserId());
            
            //添加缓存
            AuthorInfoBean bean = new AuthorInfoBean(articleBean.getUserId(), articleBean.getAuthor(),
                articleBean.getAccount(), articleBean.getNetId(), articleBean.getServerId(), articleBean.getServerName());
             
            memcache.set(USER_INFO+articleBean.getUserId(), 3600 * 24, bean);
          resultBean.setProperties(true, "添加文章成功", null);
        } catch (Exception e) {
            resultBean.setProperties(false, "添加图文接口异常", null);
            logger.warn("添加图文接口异常 :", e);
        }
        return resultBean;
    }
    
    /**
     * 
      * <p>
      *    检测标题
      * </p>
      *
      * @action
      *    lihu 2017年3月7日 下午5:19:00 描述
      *
      * @param title
      * @return ResultBean<Object>
     */
    @RequestMapping("/checktitle")
    @ResponseBody
    public ResultBean<Object> checktitle(@RequestParam("title") String title, HttpServletRequest request) {
            ResultBean<Object> resultBean = new ResultBean<>();
            try {
                 UserInfo user = SignedUser.getUserInfo(request);
                if (user == null) {
                    return new ResultBean<Object>(false, "请先登录", null);
                }
                
                boolean isExsit =articleBll.findCountByTitle(title) ;
                if(isExsit){
                    resultBean.setProperties(false, "标题已存在", null);
                }else{
                    resultBean.setProperties(true, "标题可用", null);
                    
                }
                
            } catch (Exception e) {
                    resultBean.setProperties(false, "检测标题接口异常", null);
                    logger.warn("检测标题接口异常 :", e);
            }
            return resultBean;
    }
    /**
     * 
      * <p>
      *    登录用户信息
      * </p>
      *
      * @action
      *    lihu 2017年3月8日 下午4:13:35 描述
      *
      * @param title
      * @param request
      * @return ResultBean<Object>
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public ResultBean<Object> userInfo( HttpServletRequest request) {
        ResultBean<Object> resultBean = new ResultBean<>();
        try {
            UserInfo user = SignedUser.getUserInfo(request);
                if (user == null) {
                    resultBean.setProperties(false, "请先登录", null);
                    return resultBean;
                }
             AuthorInfoBean bean = memcache.get(USER_INFO+user.getUserId(), AuthorInfoBean.class);
            if(bean!=null){
                resultBean.setProperties(true, "获取用户信息成功", bean);
                return resultBean;
            } 
            List<ArticleBean> articleBean =articleBll.AuthorInfo(user.getUserId()) ;
            if(!articleBean.isEmpty()){
                resultBean.setProperties(true, "获取用户信息成功", articleBean.get(0));
                return resultBean;
            }
                
             
            
        } catch (Exception e) {
            resultBean.setProperties(false, "登录用户信息接口内部异常", null);
            logger.warn("登录用户信息接口内部异常 :", e);

            return resultBean;
        }
        resultBean.setProperties(false, "获取用户信息失败", null);
        return resultBean;
    }
    
    /**
     * 获取错误信息
     * @param vresult
     * @return
     */
    private String validationData(BindingResult bindingResult) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            return fieldError.getDefaultMessage();
    }       
}
