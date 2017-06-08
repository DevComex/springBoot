package cn.gyyx.action.module;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.bll.activation.ActivationBLL;

@Controller
@RequestMapping(value = "/activation")
public class ActivationAPIController {
	private ActivationBLL activationBll = new ActivationBLL();

	/**
	 * 用于处理参数异常的统一返回
	 * 
	 * @return
	 */
	@ExceptionHandler({ BindException.class })
	public @ResponseBody Map<String, Object> bindExceptionHandler(
			BindException e) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "invalid-param");
		result.put("message", e.getMessage());
		result.put("data", "");
		return result;
	}

	@RequestMapping(value = "/validateActCode")
	public @ResponseBody Map<String, Object> validateActCode(
			@RequestParam("gameId") int gameId,
			@RequestParam("activationCode") String activationCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			int line = activationBll.validateActCode(gameId, activationCode);
			result.put("status","success");
			result.put("message","查询操作成功");
			result.put("data",line);
		}catch(Exception e) {
			result.put("status","validate-failed");
			result.put("message",e.getMessage());
			result.put("data","");
		}
		return result;
	}

	@RequestMapping(value = "/useActivateCode")
	public @ResponseBody Map<String, Object> useActivateCode(
			@RequestParam("serverId") int serverId,
			@RequestParam("userId") int userId,
			@RequestParam("gameId") int gameId,
			@RequestParam("activationCode") String activationCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			int line = activationBll.useActivateCode(serverId, userId, gameId, activationCode);
			result.put("status","success");
			result.put("message","更新操作成功");
			result.put("data",line);
		}catch(Exception e) {
			result.put("status","validate-failed");
			result.put("message",e.getMessage());
			result.put("data","");
		}
		return result;
	}
}
