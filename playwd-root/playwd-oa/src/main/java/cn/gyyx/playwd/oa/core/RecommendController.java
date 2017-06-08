 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月1日下午4:46:30
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean.CategoryType;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.oa.viewmodel.RecommendManagementViewModel;
import cn.gyyx.playwd.oa.viewmodel.RecommendRecordViewModel;
import cn.gyyx.playwd.oa.viewmodel.RecommendViewModel;
import cn.gyyx.playwd.service.RecommendService;

/**
 * 推荐功能
 * @author lidudi
 *
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController extends BaseController{
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(RecommendController.class);
	
	/**
	 * 推荐管理
	 */
	@Autowired
	public RecommendService recommendService;
	
	/**
	 * 文章管理
	 */
	@Autowired
	public ArticleBll articleBll;

	/**
	 * 推荐管理
	 * @return
	 */
	@GetMapping("/management")
	public String management() {
		return "recommendManagement";
	}
	
	/**
	 * 推荐记录
	 * @return
	 */
	@GetMapping("/record")
	public String record() {
		return "recommendRecord";
	}
	
	/**
	 * 推荐
	 * @param contentType 分类
	 * @return
	 */
	@PostMapping
	@ResponseBody
	public ResultBean<String> recommend(@Valid RecommendViewModel viewModel,
			BindingResult bindingResultBean,HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<String>("incorrect-login", "用户没有登录", null);
			}
			logger.info("后台推荐开始：账号:{},内容id:{}", userInfoModel.getAccount(),viewModel.getContentId());
			
			if(bindingResultBean.hasErrors()){
				String messageString=validationData(bindingResultBean);
				String [] messageStrings=messageString.split("\\|");
				logger.info("后台推荐结束:{};账号:{},内容id:{}",messageString,
						userInfoModel.getAccount(),viewModel.getContentId());
				return new ResultBean<String>(messageStrings[0],messageStrings[1], null);
			}
			
			ResultBean<String> ResultBean=new ResultBean<String>("invalid-contentType", "不存在该分类的推荐功能", null);
			//图文推荐
			if(viewModel.getContentType().equals(CategoryBean.CategoryType.article.Value())){
				ResultBean =recommendService.articleRecommend( viewModel.getContentId(), 
						viewModel.getRecommendSlotId(), viewModel.getPrizeId(), 
						StringUtils.isEmpty(viewModel.getPrizeType())?"":viewModel.getPrizeType(),
						userInfoModel.getAccount());
			}
			
			logger.info("后台推荐结束:{};账号:{},内容id:{}",ResultBean.getMessage(),userInfoModel.getAccount(),
					viewModel.getContentId());
			return ResultBean;
			
		} catch (Exception e) {
			logger.error("推荐异常", e);
			return new ResultBean<String>("unknown_error", "推荐异常", null);
		}
	}
	
	/**
	 * 获取推荐管理列表
	 * @param viewModel
	 * @param bindingResultBean
	 * @param request
	 * @return
	 */
	@GetMapping("/management/list")
	@ResponseBody
	public PageBean<Map<String, Object>> managementList(@Valid RecommendManagementViewModel viewModel,
			BindingResult bindingResult,HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return PageBean.createPage("incorrect-login", 0, 1, 10, null, "用户没有登录");
			}
			
			logger.info("获取推荐管理列表开始：账号:{}", userInfoModel.getAccount());
			if(bindingResult.hasErrors()){
				String messageString=validationData(bindingResult);
				String [] messageStrings=messageString.split("\\|");
				logger.info("获取推荐管理列表结束:账号:{};错误信息:{}",userInfoModel.getAccount(),messageString);
				return PageBean.createPage(messageStrings[0], 0, 1, 10, null, messageStrings[1]);
			}
			
			//图文推荐管理
			if(viewModel.getContentType().equals(CategoryBean.CategoryType.article.Value())){
				return recommendService.getArticleManagementList(viewModel.getRecommendSlotId(), 0, 
						"all", viewModel.getPageSize(), viewModel.getPageIndex());
			}	
			//小说推荐管理
			if(viewModel.getContentType().equals(CategoryBean.CategoryType.novel.Value())){
                            return recommendService.getNovelManagementList(viewModel.getRecommendSlotId(),viewModel.getPageSize(), viewModel.getPageIndex());
                        }
			return PageBean.createPage("invalid-contentType", 0, 1, 10, null, "请输入分类");
		} catch (Exception e) {
			logger.error("获取推荐管理列表异常", e);
			return PageBean.createPage("unknown_error", 0, 1, 10, null, "请稍后查询");
		}	
	}
	
	/**
	 * 获取文章对应的推荐位
	 * @param contentType
	 * @param contentId
	 * @param request
	 * @return
	 */
	@GetMapping("/management/slot")
	@ResponseBody
	public ResultBean<Object> getSlotsByContentId(String contentType,Integer contentId,
			HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<Object>("incorrect-login", "用户没有登录", null);
			}
			
			logger.info("获取文章对应的推荐位开始：账号:{},contentId:{}", userInfoModel.getAccount(),contentId);
			if(StringUtils.isEmpty(contentType)||contentId==null||contentId.intValue()<=0){
				logger.info("获取文章对应的推荐位结束：账号:{},contentId:{},message:{}",
						userInfoModel.getAccount(),contentId,"参数错误");
				return new ResultBean<Object>("incorrect-parameters", "参数错误", null);
			}
			
			if(contentType.equals(CategoryType.article.Value())){
				logger.info("获取文章对应的推荐位结束：账号:{},contentId:{},message:{}",
						userInfoModel.getAccount(),contentId,"成功");
				return new ResultBean<Object>("success", "成功", 
						recommendService.getSlotsByContentId(contentId));
			}
			
			if(contentType.equals(CategoryType.novel.Value())){
			    logger.info("获取小说对应的推荐位结束：账号:{},contentId:{},message:{}",
			        userInfoModel.getAccount(),contentId,"成功");
			    return new ResultBean<Object>("success", "成功", 
			            recommendService.getNovelSlotsByContentId(contentId));
			}
			
			logger.info("获取文章对应的推荐位结束：账号:{},contentId:{},message:{}",
					userInfoModel.getAccount(),contentId,"不存在分类");
			return new ResultBean<Object>("incorrect-contentType", "请输入分类", null);
		} catch (Exception e) {
			logger.error("获取文章对应的推荐位异常", e);
			return new ResultBean<Object>("unknown_error", "请稍后查询", null);
		}	
	}
	
	/**
	 * 修改文章对应的推荐位
	 * @param contentType
	 * @param contentId
	 * @param request
	 * @return
	 */
	@PostMapping("/management/slot")
	@ResponseBody
	public ResultBean<Object> changeSlotsByContentId(String contentType,Integer contentId,
			String recommendSlotId,HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<Object>("incorrect-login", "用户没有登录", null);
			}
			
			logger.info("修改文章对应的推荐位开始：账号:{},contentId:{}", userInfoModel.getAccount(),contentId);
			if(StringUtils.isEmpty(contentType)||contentId==null||contentId.intValue()<=0
					||StringUtils.isEmpty(recommendSlotId)){
				logger.info("修改文章对应的推荐位结束：账号:{},contentId:{},message:{}",
						userInfoModel.getAccount(),contentId,"参数错误");
				return new ResultBean<Object>("incorrect-parameters", "参数错误", null);
			}
			
			if(contentType.equals(CategoryType.article.Value())){
				logger.info("修改文章对应的推荐位结束：账号:{},contentId:{},message:{}",
						userInfoModel.getAccount(),contentId,"成功");
				return recommendService.changeSlotsByContentId(contentId,recommendSlotId);
			}
			
			if(contentType.equals(CategoryType.novel.Value())){
			    logger.info("修改文章对应的推荐位结束：账号:{},contentId:{},message:{}",
			        userInfoModel.getAccount(),contentId,"成功");
			    return recommendService.changeNovelSlotsByContentId(contentId,recommendSlotId);
			}
			
			logger.info("修改文章对应的推荐位结束：账号:{},contentId:{},message:{}",
					userInfoModel.getAccount(),contentId,"不存在分类");
			return new ResultBean<Object>("incorrect-contentType", "请输入分类", null);
		} catch (Exception e) {
			logger.error("修改文章对应的推荐位异常", e);
			return new ResultBean<Object>("unknown_error", "请稍后查询", null);
		}	
	}
	
	/**
	 * 获取推荐记录列表
	 * @param viewModel
	 * @param bindingResultBean
	 * @param request
	 * @return
	 */
	@GetMapping("/record/list")
	@ResponseBody
	public PageBean<Map<String, Object>> recordList(@Valid RecommendRecordViewModel viewModel,
			BindingResult bindingResult,HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return PageBean.createPage("incorrect-login", 0, 1, 10, null, "用户没有登录");
			}
			
			logger.info("获取推荐记录列表开始：账号:{}", userInfoModel.getAccount());
			if(bindingResult.hasErrors()){
				String messageString=validationData(bindingResult);
				String [] messageStrings=messageString.split("\\|");
				logger.info("获取推荐记录列表结束:账号:{};错误信息:{}",userInfoModel.getAccount(),messageString);
				return PageBean.createPage(messageStrings[0], 0, 1, 10, null, messageStrings[1]);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			Date startDate=format.parse(viewModel.getStartDate());
			Date endDate=format.parse(viewModel.getEndDate());
			if( endDate.getTime() <= startDate.getTime()){
				return PageBean.createPage("invalid-date", 0, 1, 10, null, "开始时间应该小于结束时间");
			}			
			endDate=new DateTime(endDate).plusDays(1).toDate();
					
			//图文推荐记录
			if(viewModel.getContentType().equals(CategoryBean.CategoryType.article.Value())){
				return recommendService.getArticleRecordList(viewModel.getPrizeId(), startDate, endDate,
						viewModel.getPageSize(), viewModel.getPageIndex());
			}	
			
			return PageBean.createPage("invalid-contentType", 0, 1, 10, null, "请输入分类");
		} catch (Exception e) {
			logger.error("获取推荐记录列表异常", e);
			return PageBean.createPage("unknown_error", 0, 1, 10, null, "请稍后查询");
		}	
	}
	
	/**
	 * 修改文章备注
	 * @param contentType
	 * @param contentId
	 * @param remark
	 * @param request
	 * @return
	 */
	@PostMapping("/record/remark")
	@ResponseBody
	public ResultBean<Object> recordList(String contentType,Integer contentId,String remark,HttpServletRequest request) {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<Object>("incorrect-login", "用户没有登录", null);
			}
			
			logger.info("修改文章备注开始：账号:{}", userInfoModel.getAccount());

			if(StringUtils.isEmpty(contentType)||contentId==null||contentId.intValue()<=0){
				logger.info("修改文章备注结束：账号:{},contentId:{},message:{}",
						userInfoModel.getAccount(),contentId,"参数错误");
				return new ResultBean<Object>("incorrect-parameters", "参数错误", null);
			}
			
			if(contentType.equals(CategoryType.article.Value())){
				if(articleBll.changeRemark(contentId, remark)){
					logger.info("修改文章备注结束：账号:{},contentId:{},message:{}",
							userInfoModel.getAccount(),contentId,"成功");
					return new ResultBean<Object>("success", "成功", null);
				}
				logger.info("修改文章备注结束：账号:{},contentId:{},message:{}",
						userInfoModel.getAccount(),contentId,"更新失败");
				return new ResultBean<Object>("incorrect-failed", "更新失败", null);
			}
			
			logger.info("修改文章备注结束：账号:{},contentId:{},message:{}",
					userInfoModel.getAccount(),contentId,"不存在分类");
			return new ResultBean<Object>("incorrect-contentType", "请输入分类", null);
			
		} catch (Exception e) {
			logger.error("修改文章备注异常", e);
			return new ResultBean<Object>("unknown_error", "请稍后查询", null);
		}	
	}
	
	/**
	 * 推荐记录导出
	 * @param viewModel
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/record/export")
	@ResponseBody
	public void recordExport(@Valid RecommendRecordViewModel viewModel,BindingResult bindingResult,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			//验证登录
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				response.getWriter().write("用户没有登录");
				return;
			}
			
			logger.info("推荐记录导出开始：账号:{}", userInfoModel.getAccount());
			if(bindingResult.hasErrors()){
				String messageString=validationData(bindingResult);
				String [] messageStrings=messageString.split("\\|");
				logger.info("推荐记录导出结束:账号:{};错误信息:{}",userInfoModel.getAccount(),messageString);
				response.getWriter().write(messageStrings[1]);
				return;
			}
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			Date startDate=format.parse(viewModel.getStartDate());
			Date endDate=format.parse(viewModel.getEndDate());
			if( endDate.getTime() <= startDate.getTime()){
				logger.info("推荐记录导出结束:账号:{};错误信息:开始时间应该小于结束时间",userInfoModel.getAccount());
				response.getWriter().write("开始时间应该小于结束时间");
				return;
			}
			
			//图文推荐记录
			if(viewModel.getContentType().equals(CategoryBean.CategoryType.article.Value())){
				
				//获取数量
				int count=articleBll.getRecommendRecordCount(viewModel.getPrizeId(), startDate, endDate);
				
				PageBean<Map<String, Object>> resultBean= recommendService.
						getArticleRecordList(viewModel.getPrizeId(), startDate, endDate,count, 1);
				
				HSSFWorkbook wb=toExcel(resultBean.getDataSet());
				
				OutputStream output=response.getOutputStream();  
			    response.reset();  
			    response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode("推荐管理", "UTF-8")+".xls");  
			    response.setContentType("application/msexcel");          
			    wb.write(output);  
			    output.close();	
				
			    logger.info("推荐记录导出结束：账号:{};结果：成功",userInfoModel.getAccount());		
			    return;
			}	
			logger.info("推荐记录导出结束:账号:{};错误信息:请输入分类",userInfoModel.getAccount());
			response.getWriter().write("请输入分类");
			return;
		} catch (Exception e) {
			logger.error("推荐记录导出异常", e);
			response.getWriter().write("请稍后再试");
			return;
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
	
	 /**
	 * 拼装excel记录
	 * @param list
	 * @return
	 */
	private HSSFWorkbook toExcel(List<Map<String, Object>> list){
	 	//生成exl			
		//创建HSSFWorkbook对象(excel的文档对象)  
		HSSFWorkbook wb = new HSSFWorkbook(); 		
		//建立新的sheet对象（excel的表单）  
		HSSFSheet sheet=wb.createSheet("推荐管理");  
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
		HSSFRow row1=sheet.createRow(0);        
        //创建单元格并设置单元格内容  
		row1.createCell(0).setCellValue("标题");  
		row1.createCell(1).setCellValue("服务器");      
		row1.createCell(2).setCellValue("上传账号");  
		row1.createCell(3).setCellValue("昵称"); 
		row1.createCell(4).setCellValue("查看量"); 
		row1.createCell(5).setCellValue("奖励等级"); 
		row1.createCell(6).setCellValue("推荐时间"); 
		row1.createCell(7).setCellValue("地址");
		row1.createCell(8).setCellValue("备注");
		
		int i=1;
		if(list!=null){
			for (Map<String, Object> info : list){			
				HSSFRow row=sheet.createRow(i); 
				row.createCell(0).setCellValue(info.get("title").toString());
				row.createCell(1).setCellValue(info.get("serverName").toString());
				row.createCell(2).setCellValue(info.get("account").toString());
				row.createCell(3).setCellValue(info.get("author").toString());
				row.createCell(4).setCellValue(info.get("viewCount").toString());
				row.createCell(5).setCellValue(info.get("prizeName").toString());
				row.createCell(6).setCellValue(info.get("recommmendTime").toString());
				row.createCell(7).setCellValue(info.get("url").toString());
				row.createCell(8).setCellValue(info.get("remark").toString());
				i++;
			}

		}					
	    return wb;
	 }
}

