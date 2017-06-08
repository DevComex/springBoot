package cn.gyyx.action.oa.wd161;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wd161.Wd161CommentsBean;
import cn.gyyx.action.beans.wd161.Wd161RoleAccountBean;
import cn.gyyx.action.service.wd161.Wd161OAService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
/**
 * wd161后台
 *
 */
@Controller
@RequestMapping(value = "/wd161oa")
public class Wd161OAController {

	private Wd161OAService oaService = new Wd161OAService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(Wd161OAController.class);
	
	@RequestMapping(value = "/picAudit")
	public String picAudit(Model model, HttpServletRequest request) {
		return "wd161oa/picAudit";

	}
	@RequestMapping(value = "/commentsAudit")
	public String commentsAudit(Model model, HttpServletRequest request) {
		return "wd161oa/commentsAudit";

	}
	/**
	 * 图片列表 
	 */
	@RequestMapping(value = "/picList",method = RequestMethod.POST)
	@ResponseBody
	public ResultBeanWithPage<List<Wd161RoleAccountBean>> picList(
			@RequestParam(value = "beginTime") String beginTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageIndex,
			HttpServletRequest request) {
		ResultBeanWithPage<List<Wd161RoleAccountBean>> result = new ResultBeanWithPage<>();
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				result.setProperties(false, "您没登录,请先登录", null, 0);
				return result;
			}
			if(!StringUtils.isEmpty(beginTime)){
				beginTime=beginTime+" 00:00:00";
			}
			if(!StringUtils.isEmpty(endTime)){
				endTime=endTime + " 23:59:59";
			}
			
			return oaService.picList(beginTime,endTime,state,pageSize,pageIndex);
		} catch (Exception e) {
			logger.error("图片列表异常",e);
			result.setProperties(false, "", null, 0);
			return result;
		}
	}
	
	
	
	
	/**
	 * 审核-图片
	 */
	@RequestMapping(value = "/checkPics",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> checkWorks(
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "userCode") int userCode,
			@RequestParam(value = "operation") String operation,
			HttpServletRequest request)  {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			
			ResultBean<String> result = new ResultBean<String>();
			result=oaService.checkPics(userName,userCode, operation,userInfoModel.getStaffCode(), userInfoModel.getRealName());
			return result;
		} catch (Exception e) {
			logger.error("审核-图片异常",e);
			return new ResultBean<>(false, "审核失败", "");
		}
	}
	
	/**
	 * 批量审核-图片
	 */
	@RequestMapping(value = "/batchCheckPics", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> batchCheckWorks(
			@RequestParam(value = "codesStr") String codesStr,
			@RequestParam(value = "operation") String operation,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return oaService.batchCheckPics(codesStr, operation,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			logger.error("批量审核-图片异常",e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	/**
	 * 评论列表 
	 */
	@RequestMapping(value = "/commentsList",method = RequestMethod.POST)
	@ResponseBody
	public ResultBeanWithPage<List<Wd161CommentsBean>> commentsList(
			@RequestParam(value = "beginTime") String beginTime,
			@RequestParam(value = "endTime") String endTime,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageIndex,
			HttpServletRequest request) {
		ResultBeanWithPage<List<Wd161CommentsBean>> result = new ResultBeanWithPage<>();
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				result.setProperties(false, "您没登录,请先登录", null, 0);
				return result;
			}
			if(!StringUtils.isEmpty(beginTime)){
				beginTime=beginTime+" 00:00:00";
			}
			if(!StringUtils.isEmpty(endTime)){
				endTime=endTime + " 23:59:59";
			}
			result=oaService.commentsList(beginTime,endTime,state,pageSize,pageIndex);
			
			return result;
		} catch (Exception e) {
			logger.error("评论列表 异常",e);
			result.setProperties(false, "", null, 0);
			return result;
		}
	}
	
	
	
	
	/**
	 * 审核-评论
	 */
	@RequestMapping(value = "/checkComments",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> checkComments(
			
			@RequestParam(value = "code") int code,
			@RequestParam(value = "operation") String operation,
			HttpServletRequest request)  {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			
			ResultBean<String> result = new ResultBean<String>();
			result=oaService.checkComments(code, operation,userInfoModel.getStaffCode(), userInfoModel.getRealName());
			return result;
		} catch (Exception e) {
			logger.error("审核-评论 异常",e);
			return new ResultBean<>(false, "审核失败", "");
		}
	}
	
	/**
	 * 批量审核-评论
	 */
	@RequestMapping(value = "/batchCheckComments", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> batchCheckComments(
			@RequestParam(value = "codesStr") String codesStr,
			@RequestParam(value = "operation") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return oaService.batchCheckComments(codesStr, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			logger.error("批量审核-评论",e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	
	
}
