 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月27日下午3:47:26
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.oa.viewmodel.ReportManagementViewModel;
import cn.gyyx.playwd.service.ReportService;

/**
 * 举报管理
 * @author lidudi
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(RecommendController.class);
	
	@Autowired
	public ReportService reportService; 
	
	/**
	 * 举报管理
	 * @return
	 */
	@GetMapping
	public String getManagement() {
		return "reportManagement";
	}
	
	/**
	 * 获取举报管理列表
	 * @param viewModel
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public PageBean<Map<String, Object>> getManagementList(@Valid ReportManagementViewModel viewModel,
			BindingResult bindingResult,HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return PageBean.createPage("incorrect-login", 0, 1, 10, null, "用户没有登录");
			}
			
			logger.info("获取举报管理列表开始：账号:{}", userInfoModel.getAccount());
			if(bindingResult.hasErrors()){
				String messageString=validationData(bindingResult);
				String [] messageStrings=messageString.split("\\|");
				logger.info("获取举报管理列表结束:账号:{};错误信息:{}",userInfoModel.getAccount(),messageString);
				return PageBean.createPage(messageStrings[0], 0, 1, 10, null, messageStrings[1]);
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			Date startDate=format.parse(viewModel.getStartDate());
			Date endDate=format.parse(viewModel.getEndDate());
			if( endDate.getTime() < startDate.getTime()){
				return PageBean.createPage("invalid-date", 0, 1, 10, null, "开始时间应该小于结束时间");
			}			
			endDate=new DateTime(endDate).plusDays(1).toDate();
			
			logger.info("获取举报管理列表结束：成功。账号:{}", userInfoModel.getAccount());
			return reportService.getManagementList(startDate,endDate,viewModel.getContentType(),viewModel.getStatus(),
					viewModel.getCommentAccount(),viewModel.getReportAccount(),viewModel.getPageSize(),viewModel.getPageIndex());
		} catch (Exception e) {
			logger.error("获取举报管理列表异常", e);
			return PageBean.createPage("unknown_error", 0, 1, 10, null, "请稍后查询");
		}	
	}
	
	/**
	 * 举报处理
	 * @param commentId
	 * @param reportId
	 * @param commentStatus
	 * @param request
	 * @return
	 */
	@PostMapping("/process")
	@ResponseBody
	public ResultBean<Object> changeProcess(Integer reportId,String commentStatus, HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<Object>("incorrect-login", "用户没有登录", null);
			}
			logger.info("举报处理开始：账号:{}", userInfoModel.getAccount());
			if(reportId==null||reportId.intValue()<=0||StringUtils.isEmpty(commentStatus)
					||(!commentStatus.equals("hidden")&&!commentStatus.equals("show"))){
				logger.info("举报处理结束：参数错误。账号:{};reportId:{};commentStatus:{}", userInfoModel.getAccount(),reportId,commentStatus);
				return new ResultBean<Object>("incorrect-parameters", "参数错误", null);
			}
			
			ResultBean<Object>resultBean=reportService.changeProcess(reportId, commentStatus, userInfoModel.getAccount());
			logger.info("举报处理结束：{}。账号:{}",resultBean.getMessage(), userInfoModel.getAccount());
			return resultBean;
		} catch (Exception e) {
			logger.error("举报处理异常", e);
			return new ResultBean<Object>("unknown_error", "举报处理异常", null);
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
