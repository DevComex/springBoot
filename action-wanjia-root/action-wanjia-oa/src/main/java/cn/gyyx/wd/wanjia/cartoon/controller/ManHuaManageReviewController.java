/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-19
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，后台漫画管理审核的控制器
 */
package cn.gyyx.wd.wanjia.cartoon.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.service.ManHuaManageReviewService;
import cn.gyyx.wd.wanjia.cartoon.service.upload.ManHuaUploadService;

@Controller
@RequestMapping("/review")
public class ManHuaManageReviewController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaManageReviewController.class);

	@Autowired
	private ManHuaManageReviewService manHuaManageReviewService;
	@Autowired
	private ManHuaUploadService manhuaUploadService;

	/**
	 * 返回筛选后的列表结果
	 * 
	 * @param categoryCode
	 * @param isClosed
	 * @param isShow
	 * @param reviewStatus
	 * @param netCode
	 * @param serverCode
	 * @param startTime
	 * @param endTime
	 * @param manhuaTitle
	 * @return
	 */
	@RequestMapping("/filter")
	@ResponseBody
	public ResultBean<List<Map<String, Object>>> filter(Integer categoryCode, Integer isClosed, Integer isShow,
			String reviewStatus, Integer netType, Integer areaCode, Date startTime, Date endTime, String manhuaTitle) {
		ResultBean<List<Map<String, Object>>> result = new ResultBean<>();
		result.setSuccess(false);
		Map<String, Object> map = new HashMap<>();
		map.put("categoryCode", categoryCode);
		map.put("isClosed", isClosed);
		map.put("isDelete", isShow);
		map.put("reviewStatus", reviewStatus);
		map.put("netType", netType);
		map.put("areaCode", areaCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("manhuaTitle", manhuaTitle);
		logger.info("尝试请求筛选后的列表结果，manHuaManageReviewService.getViewList(map)");
		try {
			List<Map<String, Object>> viewList = manHuaManageReviewService.getViewList(map);
			result.setSuccess(true);
			result.setData(viewList);
		} catch (Exception e) {
			result.setMessage("获取数据异常");
			logger.error("异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("返回，"+result);
		return result;

	}

	/**
	 * 批量展示
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping("/showAll")
	@ResponseBody
	public ResultBean<String> showAll(@RequestParam(value = "bookCode[]") List<Integer> list) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		logger.info("尝试批量展示操作，manHuaManageReviewService.showAll");
		try {
			result = manHuaManageReviewService.showAll(list);
		} catch (Exception e) {
			result.setMessage("获取数据异常");
			logger.error("异常" + Throwables.getStackTraceAsString(e));
		}
		return result;

	}

	/**
	 * 批量删除（审核失败）
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping("/statusFailAll")
	@ResponseBody
	public ResultBean<String> statusFailAll(@RequestParam(value = "bookCode[]") List<Integer> list) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		logger.info("尝试批量审核失败操作，manHuaManageReviewService.statusFailAll");
		try {
			result = manHuaManageReviewService.statusFailAll(list);
		} catch (Exception e) {
			result.setMessage("获取数据异常");
			logger.error("异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("返回，"+result);
		return result;

	}

	/**
	 * 批量隐藏
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping("/hideAll")
	@ResponseBody
	public ResultBean<String> hideAll(@RequestParam(value = "bookCode[]") List<Integer> list) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		logger.info("尝试批量隐藏操作，manHuaManageReviewService.hiddenAll");
		try {
			result = manHuaManageReviewService.hiddenAll(list);
		} catch (Exception e) {
			result.setMessage("获取数据异常");
			logger.error("异常" + Throwables.getStackTraceAsString(e));
		}
		logger.info("返回，"+result);
		return result;

	}

	/**
	 * 获取服务器列表 1：网通 2：电信 3： 双线
	 * 
	 * @param netType
	 * @return
	 */
	@RequestMapping("/serverlist")
	@ResponseBody
	public ResultBean serverlist(@RequestParam("netType") int netType) {
		ResultBean resultBean = new ResultBean<>();
		resultBean.setSuccess(false);
		try {
			return manhuaUploadService.serverlist(netType);
		} catch (Exception e) {
			logger.error("玩家天地-漫画上传[获取服务器列表异常]", Throwables.getStackTraceAsString(e));
		}
		return resultBean;
	}

	/**
	 * 获取所有漫画分类信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getalltype")
	public ResultBean<List<WanwdCategory>> getManhuaType() {
		ResultBean<List<WanwdCategory>> result = new ResultBean<>();
		result.setSuccess(false);
		List<WanwdCategory> manHuaAllType;
		logger.info("/getalltype,尝试获取漫画所有类型操作，manhuaUploadService.getManHuaAllType");
		try {
			manHuaAllType = manhuaUploadService.getManHuaAllType();
			result.setSuccess(true);
			result.setData(manHuaAllType);
			logger.info("/getalltype,返回"+result);
			return result;
		} catch (Exception e) {
			logger.error("获取所有类型数据失败" + Throwables.getStackTraceAsString(e));
			result.setMessage("获取所有类型数据失败");
			return result;
		}

	}

	/**
	 * 显示漫画的所有章节
	 * 
	 * @param bookCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showAllBook")
	public ResultBean<List<Map<String, Object>>> showAllBook(@RequestParam(value = "bookCode") Integer bookCode) {
		ResultBean<List<Map<String, Object>>> result = new ResultBean<>();
		result.setSuccess(false);
		logger.info("/showAllBook,尝试获取漫画所有章节信息操作，manHuaManageReviewService.showAllBookForOneManhua(bookCode="+bookCode+")");
		try {
			List<Map<String, Object>> list = manHuaManageReviewService.showAllBookForOneManhua(bookCode);
			result.setSuccess(true);
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取漫画所有章节失败" + Throwables.getStackTraceAsString(e));
			result.setMessage("获取漫画所有章节失败");
		}
		logger.info("/showAllBook,返回，"+result);
		return result;
	}

	/**
	 * 显示漫画章节信息和章节的所有图片
	 * 
	 * @param bookCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showBookInfoAndPage")
	public ResultBean<Map<String, Object>> showBookInfoAndPage(@RequestParam(value = "bookCode") Integer bookCode) {
		ResultBean<Map<String, Object>> result = new ResultBean<>();
		result.setSuccess(false);
		logger.info("/showBookInfoAndPage,尝试获取漫画章节信息及章节下漫画图片操作，manHuaManageReviewService.getBookInfoAndPage(bookCode"+bookCode+")");
		try {
			Map<String, Object> bookInfoAndPage = manHuaManageReviewService.getBookInfoAndPage(bookCode);
			result.setSuccess(true);
			result.setData(bookInfoAndPage);
		} catch (Exception e) {
			logger.error("获取漫画章节信息和图片失败" + Throwables.getStackTraceAsString(e));
			result.setMessage("获取漫画所有信息和图片失败");
		}
		logger.info("/showBookInfoAndPage,返回，"+result);
		return result;
	}

	/**
	 * 漫画审核成功
	 * 
	 * @param bookCode
	 * @param name
	 * @param urls
	 * @param reply
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reviewFinish")
	public ResultBean<String> reviewFinish(@RequestParam(value = "bookCode") Integer bookCode,
			@RequestParam(value = "pagesName[]", required = false) List<String> name,
			@RequestParam(value = "pagesUrl[]") List<String> urls, String reply) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		if (urls.size() < 2) {
			result.setMessage("请确定至少有一张封面和一页漫画");
			return result;
		}
		if (reply!=null&&reply.length()>300) {
			result.setMessage("编辑回复不能超过300字");
			return result;
		}
		logger.info("/reviewFinish,尝试将未审核漫画变为已审核状态操作，manHuaManageReviewService.reviewFinish(bookCode"+bookCode+", name:"+name+", urls"+urls+", reply"+reply+")");
		try {
			result = manHuaManageReviewService.reviewFinish(bookCode, name, urls, reply);
		} catch (Exception e) {
			logger.error("审核操作异常" + Throwables.getStackTraceAsString(e));
			result.setMessage("审核操作异常");
		}
		logger.info("/reviewFinish,返回，"+result);
		return result;
	}

	/**
	 * 漫画审核失败
	 * 
	 * @param bookCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reviewFailed")
	public ResultBean<String> reviewFailed(@RequestParam(value = "bookCode") Integer bookCode) {
		ResultBean<String> result = new ResultBean<>();
		result.setSuccess(false);
		List<Integer> list = new ArrayList<>();
		list.add(bookCode);
		logger.info("/reviewFailed,尝试将未审核漫画变为审核失败状态操作， manHuaManageReviewService.statusFailAll(list)"+list);
		try {
			result = manHuaManageReviewService.statusFailAll(list);
		} catch (Exception e) {
			logger.error("审核操作异常" + Throwables.getStackTraceAsString(e));
			result.setMessage("审核操作异常");
		}
		logger.info("/reviewFailed,返回，"+result);
		return result;
	}
}
