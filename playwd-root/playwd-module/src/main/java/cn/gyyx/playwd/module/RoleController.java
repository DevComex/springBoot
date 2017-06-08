package cn.gyyx.playwd.module;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.bll.RoleBll;
/**
 * 
  * <p>
  *   lihu 同步角色获取
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@Controller
@RequestMapping("/defaultrole")
public class RoleController {

	private static final Logger logger = GYYXLoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	public RoleBll roleBll; 
	
	 /**
	  * 
	   * <p>
	   *    获取默认角色信息
	   * </p>
	   *
	   * @action
	   *    lihu 2017年4月24日 下午5:01:16 描述
	   *
	   * @param userId
	   * @param response
	   * @return ResultBean<String>
	  */
	@GetMapping
	@ResponseBody
	public ResultBean<RoleBean> defaultRole(Integer userId,HttpServletResponse response) {
		try {
			logger.info("获取默认角色信息开始,userId:{}",userId);
			 
			
			RoleBean roleBean=roleBll.getDefaultRole(userId  );
			if(roleBean!=null){
				response.setStatus(200);
				return new ResultBean<>("success", "成功", roleBean);
			}
			response.setStatus(400);
			return new ResultBean<>("incorrect-failed", "失败", null);			
		} catch (Exception e) {
			response.setStatus(500);
			logger.error("增加我的消息异常", e);
			return new ResultBean<>("unknown_error", "获取默认角色信息异常", null);	
		}
	}
	 
	
	 
}
