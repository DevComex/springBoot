package cn.gyyx.playwd.oa.core;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(MessageController.class);
    
    @Autowired
    private MessageService messageService;
    
    /**
     * 编辑回复
     * @param contentType
     * @param contentId
     * @param message
     * @param request
     * @return
     */
    @PostMapping("/editor")
    @ResponseBody
    public ResultBean<Object> editorMessage(String contentType,Integer contentId,String message, HttpServletRequest request) {
    	try {
    		OAUserInfoModel  userInfoModel = super.getOAUserInfoModel(request);
    		if(userInfoModel == null){
    			return new ResultBean<Object>("incorrect-login", "用户没有登录", null);
    		}
    		
    		if(StringUtils.isEmpty(contentType)||contentId==null||contentId.intValue()<=0||StringUtils.isEmpty(message)){
    			return new ResultBean<Object>("incorrect-parameter", "参数错误", null);
    		}
    		
    		return messageService.editorMessage(contentType, contentId, message);
			
		} catch (Exception e) {
			logger.error("推荐异常", e);
			return new ResultBean<Object>("unknown_error", "回复异常", null);
		}
    }
}
