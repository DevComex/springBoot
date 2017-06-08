package cn.gyyx.action.ui.lottery;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.ui.WeChatBaseController;

/**
 * 
 * @ClassName: ShowProductionController
 * @description 展示照片作品，日记作品，插入评论，展示评论 
 * @author luozhenyu
 * @date 2016年9月22日
 */
@Controller
@RequestMapping("/lottery")
public class ShowProductionController extends WeChatBaseController {

	@RequestMapping("/showPhotoDetail")
	public String showPhotoDetail(@RequestParam("OpenId") String openId,Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go报名页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/show_photo_detail";
	}

	@RequestMapping("/showTextDetail")
	public String showTextDetail(@RequestParam("OpenId") String openId,Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go报名页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/show_text_detail";
	}

	@RequestMapping("/showPhoto")
	public String showPhoto(String OpenId,Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go登录页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/show_phpto";
	}

	@RequestMapping("/showTxt")
	public String showTxt(Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go登录页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/show_txt";
	}

	/**
	 * 展示照片和日记
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/show/production", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> showProduction(@RequestParam String type, @RequestParam int page,
			@RequestParam int activityId, @RequestParam String openId, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<>();
		 // 是否微信浏览器
		 map.put("message", "活动已结束！");
		return new ResponseEntity<>((Object) map, HttpStatus.OK);
	}

	/**
	 * 模糊查询
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/vague/serching", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> vagueSerch(@RequestParam String param, @RequestParam String type,
			@RequestParam int page, @RequestParam int activityId, @RequestParam String openId,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<>();
		map.put("message", "活动已结束！");
		return new ResponseEntity<>((Object) map, HttpStatus.OK);
	}

	/**
	 * 插入评论
	 * 
	 * @param userId
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/insert/evaluate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> insertEvaluate(@RequestParam Integer userId, @RequestParam String content,
			@RequestParam Integer productCode, @RequestParam String type, @RequestParam int activityId,
			@RequestParam String openId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> map = new HashMap<>();
		map.put("message", "活动已结束！");
		return new ResponseEntity<>((Object) map, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 展示25条评论
	 * 
	 * @param productCode
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/show/evaluate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> showEvaluate(@RequestParam Integer productCode, @RequestParam String type,
			@RequestParam int activityId, @RequestParam String openId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		 // 不是微信浏览器，则返回
		 map.put("message", "活动已结束！");
		return new ResponseEntity<>((Object) map, HttpStatus.OK);
	}

	//页面跳转
	private boolean checkIsWeiXin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			if (!isWixXinBrowser(request)) {// 是微信浏览器
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(String.valueOf("<div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 30px;'>请在微信客户端打开链接<h4></div></div>"));
				response.getWriter().close();
				return false;
			}
		}catch(Exception e){
			throw e;
		}
		return true;
	}
	
	private boolean isWixXinBrowser(HttpServletRequest request){
		String ua = request.getHeader("user-agent").toLowerCase();
		//TODO 打开
//		return true;
		return !(ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1);
	}
}
