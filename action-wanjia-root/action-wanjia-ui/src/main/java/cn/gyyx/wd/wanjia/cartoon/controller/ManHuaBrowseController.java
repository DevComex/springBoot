/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-12
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览的控制器
 */
package cn.gyyx.wd.wanjia.cartoon.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService;

@Controller
@RequestMapping("/browse")
public class ManHuaBrowseController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaBrowseController.class);
	@Autowired
	private ManHuaBrowseService manHuaBrowseService;

	/**
	 * 根据漫画code，章节数BookNum，漫画页数PageNum，查找对应页的信息
	 * 
	 * @param manhuaCode
	 * @param bookNum
	 * @param pageNum
	 * @param request
	 * @return
	 */
	@RequestMapping("/manhuaBrowse")
	@ResponseBody
	public ResultBean<Map<String, Object>> Browse(Integer manhuaCode, Integer bookNum, Integer pageNum,
			HttpServletRequest request) {
		ResultBean<Map<String, Object>> result = new ResultBean<>();
		logger.info("/manhuaBrowse,manhuaCode="+manhuaCode+",bookNum="+bookNum+",pageNum="+pageNum+"，manhuaUploadService.getManHuaAllType");
		if (manhuaCode == null) {
			result.setSuccess(false);
			result.setMessage("请求的数据不合法");
			logger.info("/manhuaBrowse,return:"+result);
			return result;
		}
		UserInfo userInfo = SignedUser.getUserInfo(request);
		try {
			Map<String, Object> manhuaBrowse = manHuaBrowseService.manhuaBrowse(userInfo, manhuaCode, bookNum, pageNum);
			if (manhuaBrowse == null) {
				result.setSuccess(false);
				result.setMessage("请求的内容不存在");
			} else {
				result.setSuccess(true);
				result.setData(manhuaBrowse);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据异常");
			logger.error("数据读取异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("/manhuaBrowse,return:"+result);
		return result;
	}

	/**
	 * 获取漫画最新章节的信息
	 * 
	 * @param manhuaCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/newestBook")
	@ResponseBody
	public ResultBean<Map<String, Object>> newestBook(Integer manhuaCode, HttpServletRequest request) {
		ResultBean<Map<String, Object>> result = new ResultBean<>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		logger.info("/newestBook,manhuaCode="+manhuaCode+",request="+request+"， manHuaBrowseService.getNewestBook(userInfo="+userInfo+", manhuaCode="+manhuaCode+")");
		
		if (manhuaCode == null) {
			result.setSuccess(false);
			result.setMessage("请求的数据不合法");
			logger.info("/newestBook,return:"+result);
			return result;
		}
		try {
			Map<String, Object> newestBook = manHuaBrowseService.getNewestBook(userInfo, manhuaCode);
			if (newestBook == null) {
				result.setSuccess(false);
				result.setMessage("请求的漫画不存在任何章节");
			} else {
				result.setSuccess(true);
				result.setData(newestBook);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据异常");
			logger.error("数据读取异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("/newestBook,return:"+result);
		return result;

	}

	/**
	 * 查询漫画的基本信息
	 * 
	 * @param manhuaCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/InfoAndStatus")
	@ResponseBody
	public ResultBean<Map<String, Object>> getManHua(Integer manhuaCode, HttpServletRequest request) {
		ResultBean<Map<String, Object>> result = new ResultBean<>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		logger.info("/InfoAndStatus,manhuaCode="+manhuaCode+",request="+request+"，manHuaBrowseService.getManHuaInfo(userInfo="+userInfo+", manhuaCode="+manhuaCode+")");
		
		if (manhuaCode == null) {
			result.setSuccess(false);
			result.setMessage("请求的数据不合法");
			logger.info("/InfoAndStatus,return:"+result);
			return result;
		}
		try {
			Map<String, Object> manHua = manHuaBrowseService.getManHuaInfo(manhuaCode, userInfo);
			if (manHua == null) {
				result.setSuccess(false);
				result.setMessage("请求的漫画不存在");
			} else {
				result.setSuccess(true);
				result.setData(manHua);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据异常");
			logger.error("数据读取异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("/InfoAndStatus,return:"+result);
		return result;

	}

	/**
	 * 点赞
	 * 
	 * @param manhuaCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/addGoodStatus")
	@ResponseBody
	public ResultBean<String> addGoodStatus(Integer manhuaCode, HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		logger.info("/addGoodStatus,manhuaCode="+manhuaCode+",request="+request+"， manHuaBrowseService.addGoodStatus("+userInfo+", manhuaCode="+manhuaCode+")");
		
		if (manhuaCode == null) {
			result.setSuccess(false);
			result.setMessage("请求的数据不合法");
			logger.info("/addGoodStatus,return:"+result);
			return result;
		}
		if (userInfo == null) {
			result.setSuccess(false);
			result.setMessage("您还未登录，请点击登录按钮进行登录");
			logger.info("/addGoodStatus,return:"+result);
			return result;
		}
		try {
			boolean addGoodStatus = manHuaBrowseService.addGoodStatus(userInfo, manhuaCode);
			result.setSuccess(addGoodStatus);
			if (addGoodStatus) {
				result.setMessage("点赞成功");
			} else {
				result.setMessage("点赞失败，已经点过赞了");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据异常");
			logger.error("数据读取异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("/addGoodStatus,return:"+result);
		return result;

	}

	/**
	 * 漫画阅读量+1
	 * 
	 * @param manhuaCode
	 * @return
	 */
	@RequestMapping("/readCountPlus")
	@ResponseBody
	public ResultBean<String> readCountPlus(@RequestParam(value = "manhuaCode") Integer manhuaCode) {
		ResultBean<String> result = new ResultBean<>();
		logger.info("/readCountPlus,manhuaCode="+manhuaCode+"， manHuaBrowseService.readCountPlus(manhuaCode="+manhuaCode+")");
		
		try {
			result.setSuccess(manHuaBrowseService.readCountPlus(manhuaCode));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据异常");
			logger.error("异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("/readCountPlus,return:"+result);
		return result;

	}
}
