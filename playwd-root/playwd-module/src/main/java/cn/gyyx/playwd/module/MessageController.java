package cn.gyyx.playwd.module;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.module.viewmodule.AddMessageViewModel;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/message")
public class MessageController {

	private static final Logger logger = GYYXLoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	public MessageBll messageBll; 
	
	/**
	 * 增加我的消息
	 * @param addMessageViewModel
	 * @param bindResult
	 * @param response
	 * @return
	 */
	@PostMapping
	public ResultBean<String> addMessage(@Valid AddMessageViewModel addMessageViewModel,BindingResult bindingResult, HttpServletResponse response) {
		try {
			logger.info("增加我的消息开始,userId:{}",addMessageViewModel.getUserId());
			if(bindingResult.hasErrors()){
				response.setStatus(400);
				String messageString=validationData(bindingResult);
				String [] messageStrings=messageString.split("\\|");
				logger.info("增加我的消息结束:userId:{};错误信息:{}",addMessageViewModel.getUserId(),messageStrings);
				return new ResultBean<String>(messageStrings[0], messageStrings[1], null);
			}
			
			boolean result=messageBll.add(addMessageViewModel.getMessage(), addMessageViewModel.getMessageType(), 
					addMessageViewModel.getContentId(), addMessageViewModel.getContentType(), 
					addMessageViewModel.getUserId(), addMessageViewModel.getContentTitle());
			if(result){
				response.setStatus(200);
				return new ResultBean<String>("success", "成功", null);
			}
			response.setStatus(400);
			return new ResultBean<String>("incorrect-failed", "失败", null);			
		} catch (Exception e) {
			response.setStatus(500);
			logger.error("增加我的消息异常", e);
			return new ResultBean<String>("unknown_error", "增加我的消息异常", null);	
		}
	}
	
	/**
	 * 获取错误信息
	 * @param vResultBean
	 * @return
	 */
	private String validationData(BindingResult  bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		FieldError fieldError = fieldErrors.get(0);
		return fieldError.getDefaultMessage();
	}	
}
