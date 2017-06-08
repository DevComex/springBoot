/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午9:59:22
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.oa.wd10yearcoser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import cn.gyyx.action.beans.wd10yearcoser.CoserNovice;
import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.service.wd10yearcoser.Wd10yearcoserOaService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

/**
 * 同人活动 oa后台
 */
@Controller
@RequestMapping(value = "/wd10yearcoser")
public class CoserController {
	private Wd10yearcoserOaService fanzineService = new Wd10yearcoserOaService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CoserController.class);
	
	@RequestMapping(value = "/index")
	public String index(Model model, HttpServletRequest request) {
		return "wd10yearcoser/index";

	}

	/**
	 * 首页大图修改
	 */
	@RequestMapping(value = "/banner")
	public String checkUserInfo(Model model, HttpServletRequest request) {
		ResultBean<Map<String,String>> res = fanzineService.getBanner();
		if(res.getIsSuccess()){
			model.addAttribute("picUrl",res.getData().get("picUrl"));
			model.addAttribute("imgUrl",res.getData().get("imgUrl"));
		}
			
		return "wd10yearcoser/banner";

	}
	
	/***
	 * 保存大图
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> save(String picUrl, String imgUrl, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.save(userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					picUrl,imgUrl);
		} catch (Exception e) {
			logger.error("save", e);
			return new ResultBean<>(false, "修改失败", "");
		}
	}
	
	/**
	 * 图片轮播公告
	 */
	@RequestMapping(value = "/pic")
	public String pic(Model model, HttpServletRequest request) {
		ResultBean<List<Map<String,String>>> res = fanzineService.getImgNotice();
		if(res.getIsSuccess()){
			model.addAttribute("list",res.getData());
		}
		return "wd10yearcoser/pic";

	}
	
	/***
	 * 保存图片轮播
	 */
	@RequestMapping(value = "/saveImgNotice", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> saveImgNotice(String code, String title,String url,String picUrl, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.saveImgNotice(userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					code,title,url,picUrl);
		} catch (Exception e) {
			logger.error("save", e);
			return new ResultBean<>(false, "修改失败", "");
		}
	}
	
	/***
	 * 获取轮播图片信息
	 */
	@RequestMapping(value = "/getImgNotice/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Map<String,String>> getImgNotice(String code, String title,String url,String picUrl, HttpServletRequest request) {
		try {
			return fanzineService.getImgNotice(code);
		} catch (Exception e) {
			logger.error("save", e);
			return new ResultBean<>(false, "获取失败", null);
		}
	}
	
	/***
	 * 文字公告
	 */
	@RequestMapping(value = "/novice")
	public String novice(HttpServletRequest request) {
		return "wd10yearcoser/novice";
	}
	
	/***
	 * 文字公告列表
	 */
	@RequestMapping(value = "/noviceDataList", method = RequestMethod.GET)
	@ResponseBody
	public ResultBeanWithPage<List<CoserNovice>> noviceDataList(
			CoserNovice bean,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		bean.setPageSize(pageSize);
		bean.setCurrentPage(pageNo);
		return fanzineService.noviceDataList(bean);
	}

	/**
	 * 发布
	 */
	@RequestMapping(value = "/novicePub", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> pub(
			@RequestParam(value = "code") int code,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.pubNovice(code, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 文字公告add
	 */
	@RequestMapping(value = "/noviceAdd", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> noviceAdd(CoserNovice bean,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.insertNovice(bean,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 文字公告修改
	 */
	@RequestMapping(value = "/noviceMod", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> noviceMod(CoserNovice bean,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.updateNovice(bean,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	/**
	 * 文字公告删除
	 */
	@RequestMapping(value = "/noviceDel", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> noviceDel(
			@RequestParam(value = "code") int code, HttpServletRequest request)
			throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.deleteNovice(code,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 获取公告-根据ID
	 */
	@RequestMapping(value = "/getNotice", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<CoserNovice> getNotice(
			@RequestParam(value = "code") int code, HttpServletRequest request)
			throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<CoserNovice>(false,
						"您没登录,请先登录", null);
			}
			return fanzineService.getNovice(code);
		} catch (Exception e) {
			return new ResultBean<CoserNovice>(false, "您没登录", null);
		}
	}
	
	//--------------官方视频----------------------
	
	/***
	 * 官方视频
	 */
	@RequestMapping(value = "/video")
	public String video(HttpServletRequest request) {
		return "wd10yearcoser/video";
	}
	
	/***
	 * 官方视频列表
	 */
	@RequestMapping(value = "/videoDataList", method = RequestMethod.GET)
	@ResponseBody
	public ResultBeanWithPage<List<CoserOfficialVideo>> videoDataList(
			CoserOfficialVideo bean,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		bean.setPageSize(pageSize);
		bean.setPageIndex(pageNo);
		return fanzineService.videoDataList(bean);
	}

	/**
	 * 推荐
	 */
	@RequestMapping(value = "/videoTop", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> top(
			@RequestParam(value = "code") int code,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.topVideo(code, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 官方视频add
	 */
	@RequestMapping(value = "/videoAdd", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> videoAdd(CoserOfficialVideo bean,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.insertVideo(bean,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 官方视频修改
	 */
	@RequestMapping(value = "/videoMod", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> videoMod(CoserOfficialVideo bean,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.updateVideo(bean,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	/**
	 * 官方视频删除
	 */
	@RequestMapping(value = "/videoDel", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> videoDel(
			@RequestParam(value = "code") int code, HttpServletRequest request)
			throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.deleteVideo(code,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 获取视频-根据ID
	 */
	@RequestMapping(value = "/getVideo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<CoserOfficialVideo> getVideo(
			@RequestParam(value = "code") int code, HttpServletRequest request)
			throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<CoserOfficialVideo>(false,
						"您没登录,请先登录", null);
			}
			return fanzineService.getVideo(code);
		} catch (Exception e) {
			return new ResultBean<CoserOfficialVideo>(false, "您没登录", null);
		}
	}
	
	//-----------作品列表--------------
	@RequestMapping(value = "/workslist")
	public String workslist(Model model, HttpServletRequest request) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("curDate", sdf.format(new Date()));
		return "wd10yearcoser/workslist";

	}
	
	@RequestMapping(value = "/worksDataList")
	@ResponseBody
	public ResultBeanWithPage<List<ResourceBean>> worksDataList(@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo,ResourceBean bean,Model model, HttpServletRequest request) {
			bean.setPageSize(pageSize);
			bean.setCurrentPage(pageNo);
		if(!StringUtils.isEmpty(bean.getBeginTimeStr())){
			bean.setBeginTimeStr(bean.getBeginTimeStr() + " 00:00:00");
		}
		if(!StringUtils.isEmpty(bean.getEndTimeStr())){
			bean.setEndTimeStr(bean.getEndTimeStr() + " 23:59:59");
		}
		return fanzineService.worksDataList(bean);
	}
	
	/**
	 * 审核
	 */
	@RequestMapping(value = "/checkWorks", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> checkWorks(
			@RequestParam(value = "code") int code,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.checkWorks(code, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	/**
	 * 批量审核
	 */
	@RequestMapping(value = "/batchCheckWorks", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> batchCheckWorks(
			@RequestParam(value = "codesStr") String codesStr,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.batchCheckWorks(codesStr, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	
	/**
	 * 推荐
	 */
	@RequestMapping(value = "/topWorks", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> topWorks(
			@RequestParam(value = "code") int code,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return fanzineService.topWorks(code, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	
	
}
