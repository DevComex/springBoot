/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-1-3
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，用户中心，漫画部分内容控制器
 */
package cn.gyyx.wd.wanjia.cartoon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.google.common.base.Throwables;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;
import cn.gyyx.wd.wanjia.cartoon.service.UserCenterManHuaService;

@Controller
@RequestMapping("/user")
public class ManhuaUserCenterController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManhuaUserCenterController.class);
	@Autowired
	private UserCenterManHuaService userCenterManHuaService;

	/**
	 * @description 获取用户收藏漫画
	 * @param request
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getCollects")
	@ResponseBody
	public Map<String, Object> collectionList(HttpServletRequest request,
			@RequestParam("SourcesType") Integer resourceType, @RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize) {
		logger.info(
				"进入getCollects接口，参数：SourcesType:" + resourceType + " pageIndex:" + pageIndex + " pageSize:" + pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("success", false);
		result.put("DataSet", new ArrayList<>());
		if (pageIndex == null || pageSize == null) {
			result.put("message", "参数错误");
			logger.error("参数错误pageIndex：" + pageIndex + "pageSize:" + pageSize);
			return result;
		}

		try {
			// //登录验证
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.put("message", "您还未登陆");
				logger.info("返回：未登陆" + result);
				return result;
			}

			result = userCenterManHuaService.getCollection(userInfo.getUserId(), resourceType, pageIndex, pageSize);
			if (result == null || result.get("DataSet") == null || ((List) result.get("DataSet")).size() == 0) {
				result.put("message", "暂无消息");
				result.put("success", true);
				logger.info("无收藏漫画，返回：" + result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "内部运行错误");
			logger.error("内部运行错误：" + Throwables.getStackTraceAsString(e));
			return result;
		}

		result.put("success", true);
		result.put("message", "返回漫画收藏列表成功");
		logger.info("返回漫画收藏列表成功，返回：" + result);
		return result;

	}

	/**
	 * @description 获取筛选下的用户上传的漫画
	 * @param request
	 * @param status
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getUpload")
	@ResponseBody
	public Map<String, Object> uploadList(HttpServletRequest request, @RequestParam("status") String status,
			@RequestParam("SourcesType") Integer resourceType, @RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize) {
		logger.info("进入getUpload接口，参数：status:" + status + " SourcesType:" + resourceType + " pageIndex:" + pageIndex
				+ " pageSize:" + pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("success", false);
		result.put("DataSet", new ArrayList<>());
		if (pageIndex == null || pageSize == null) {
			result.put("message", "参数错误");
			logger.error("参数错误pageIndex：" + pageIndex + "pageSize:" + pageSize);
			return result;
		}
		try {
			// 登录验证
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.put("message", "您还未登陆");
				logger.info("返回：未登陆" + result);
				return result;
			}

			result = userCenterManHuaService.getUpload(status, resourceType, userInfo.getUserId(), pageIndex, pageSize);
			if (result == null || result.get("DataSet") == null || ((List) result.get("DataSet")).size() == 0) {
				result.put("message", "暂无消息");
				result.put("success", true);
				logger.info("无上传漫画，返回：" + result);
				return result;
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "内部运行错误");
			logger.error("内部运行错误：" + Throwables.getStackTraceAsString(e));
			return result;
		}

		result.put("success", true);
		result.put("message", "返回漫画已上传列表成功");
		logger.info("返回漫画已上传列表成功，返回：" + result);
		return result;

	}

	/**
	 * @description 获取用户漫画状态消息
	 * @param request
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getMessage")
	@ResponseBody
	public Map<String, Object> messageList(HttpServletRequest request,
			@RequestParam("SourcesType") Integer resourceType, @RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize) {
		logger.info(
				"进入getMessage接口，参数： SourcesType:" + resourceType + " pageIndex:" + pageIndex + " pageSize:" + pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("success", false);
		result.put("DataSet", new ArrayList<>());
		if (pageIndex == null || pageSize == null) {
			result.put("message", "参数错误");
			logger.error("参数错误pageIndex：" + pageIndex + "pageSize:" + pageSize);
			return result;
		}
		try {
			// 登录验证
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.put("message", "您还未登陆");
				logger.info("返回：未登陆" + result);
				return result;
			}

			result = userCenterManHuaService.getMessage(userInfo.getUserId(), resourceType, pageIndex, pageSize);
			if (result == null || result.get("DataSet") == null || ((List) result.get("DataSet")).size() == 0) {
				result.put("success", true);
				result.put("message", "暂无消息");
				logger.info("无漫画消息，返回：" + result);
				return result;
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "内部运行错误");
			logger.error("内部运行错误：" + Throwables.getStackTraceAsString(e));
			return result;
		}

		result.put("success", true);
		result.put("message", "返回漫画消息列表成功");
		logger.info("返回漫画消息列表成功，返回：" + result);
		return result;

	}

	/**
	 * @description 获取用户漫画编辑回复消息
	 * @param request
	 * @param resourceType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getManagerReply")
	@ResponseBody
	public Map<String, Object> managerReplyList(HttpServletRequest request,
			@RequestParam("SourcesType") Integer resourceType, @RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize) {
		logger.info("进入getManagerReply接口，参数： SourcesType:" + resourceType + " pageIndex:" + pageIndex + " pageSize:"
				+ pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("success", false);
		result.put("DataSet", new ArrayList<>());
		if (pageIndex == null || pageSize == null) {
			result.put("message", "参数错误");
			logger.error("参数错误pageIndex：" + pageIndex + "pageSize:" + pageSize);
			return result;
		}
		try {
			// 登录验证
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.put("message", "您还未登陆");
				logger.info("返回：未登陆" + result);
				return result;
			}

			result = userCenterManHuaService.getManagerReply(userInfo.getUserId(), resourceType, pageIndex, pageSize);
			if (result == null || result.get("DataSet") == null || ((List) result.get("DataSet")).size() == 0) {
				result.put("success", true);
				result.put("message", "暂无消息");
				logger.info("无漫画编辑回复，返回：" + result);
				return result;
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "内部运行错误");
			logger.error("内部运行错误：" + Throwables.getStackTraceAsString(e));
			return result;
		}

		result.put("success", true);
		result.put("message", "返回漫画编辑回复列表成功");
		logger.info("返回漫画编辑回复列表成功，返回：" + result);
		return result;

	}

	/**
	 * @description 获取角色信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/GetUser")
	@ResponseBody
	public Map<String, Object> getUser(HttpServletRequest request) {
		logger.info("进入GetUser接口，无参数");
		Map<String, Object> result = new HashMap<>();
		result.put("IsSuccess", false);
		result.put("Data", null);
		result.put("List", null);
		result.put("Values", null);
		try {
			// //登录验证
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.put("Message", "您还没有登录，请点击登录按钮进行登录。");
				result.put("Values", -3);
				logger.info("返回：未登陆" + result);
				return result;
			}

			List<WanwdUser> roleInfo = userCenterManHuaService.getRoleInfo(userInfo.getUserId());
			result.put("IsSuccess", true);
			result.put("Message", "获取用户信息成功");
			result.put("List", roleInfo);
			logger.info("获取用户信息成功，返回：" + result);
			return result;
		} catch (Exception e) {
			result.put("Message", "内部运行错误");
			logger.error("内部运行错误：" + Throwables.getStackTraceAsString(e));
			return result;
		}

	}
}

/**
 * @description 用于支持本controller类支持jsonp请求返回数据
 * @author 才帅
 * @createTime 2017-1-4
 */
@ControllerAdvice(basePackageClasses = ManhuaUserCenterController.class)
class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
	public JsonpAdvice() {
		super("callback", "jsonp");
	}
}