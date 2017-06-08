package cn.gyyx.action.ui.wd10yearcoser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wd10yearcoser.Constants.RESOURCETYPE;
import cn.gyyx.action.beans.wd10yearcoser.CoserNovice;
import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.bll.wd10yearcoser.ResourceBll;
import cn.gyyx.action.bll.wd10yearcoser.UserfavoriteBll;
import cn.gyyx.action.service.wd10yearcoser.ResourceService;
import cn.gyyx.action.service.wd10yearcoser.Wd10yearcoserOaService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;


/** 
 * @ClassName: WdTenyearBenefitSigninController 
 * @Description: 问道十周年福利活动控制器
 * @author wangandong
 * @date 2016年8月23日 下午2:06:49 
 *  
 */
@Controller
@RequestMapping("/wd10yearcoser")
public class Wd10yearCoserController {
	//日志
	private static final Logger LOG = LoggerFactory.getLogger(Wd10yearCoserController.class);

	private ResourceBll resourceBll = new ResourceBll();
	private ResourceService resourceService = new ResourceService();
	private UserfavoriteBll userfavoriteBll = new UserfavoriteBll();
	private Wd10yearcoserOaService coserOaService = new Wd10yearcoserOaService();
	int actionCode = 389;
	//业务接口
	
	/**
	* @Title: toIndex 
	* @Description: 首页
	* @param @return 
	* @return String
	* @throws
	 */
	/*@RequestMapping("/index")
	public String toIndex() {
		return "wd10yearcoser/index";
	}*/
	
	/**
	 * @Title: toIndex 
	 * @Description: 首页
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping("/index")
	public ModelAndView toIndex() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelMap model = new ModelMap();
		//获取活动公告
		List<Map<String,String>> imgNotices = coserOaService.getImgNotice().getData();
		model.addAttribute("imgNotices", imgNotices);
		//获取官方视频
		List<CoserOfficialVideo> videos  = coserOaService.videoFrontTopList(null).getData();
		model.addAttribute("videos", videos);
		//湖区最新公告
		List<CoserNovice> notices = coserOaService.noviceFontDataList(null).getData();
		for (CoserNovice coserNovice : notices) {
			Date create = coserNovice.getPubTime();//设置成推荐时间chenglong
			//coserNovice.setTime(String.format("%s/%s", create.getYear()+1900,create.getMonth()+1));
			coserNovice.setTime(sdf.format(create));
		}
		model.addAttribute("notices", notices);
		return new ModelAndView("wd10yearcoser/index",model);
	}
	
	/**
	 * @Title:  
	 * @Description: 推荐
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/showresources", method = RequestMethod.POST )
	@ResponseBody
	public ResultBean<List<ResourceBean>> push(String type,HttpServletRequest request) {
		try {
			// 用户信息
			UserInfo userInfo = SignedUser.getUserInfo(request);
			List<ResourceBean> list = resourceBll.findShowResources(type);
			//登录状态下 筛选已经点赞的资源
			if (userInfo != null) {
				List<UserfavoriteBean> favoriteList = userfavoriteBll.findFavoriteByUser(userInfo.getUserId());
				//过滤是否点赞过
				for (UserfavoriteBean userfavoriteBean : favoriteList) {
					for (ResourceBean resourceBean : list) {
						//使用equals比较对象类型 chenglong
						if (userfavoriteBean.getResourceCode().equals(resourceBean.getCode())) {
							resourceBean.setFlag(true);
							break;
						} 
					}
				}
			}
			return new ResultBean<List<ResourceBean>>(true,"数据返回成功",list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<List<ResourceBean>>(false,"数据返回失败",null);
		}
	}
	
	
	/**
	* @Title: toIndex 
	* @Description: 推荐资源列表 ：视频　音乐　同人画　素描
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping("/resourceslist")
	public String showIndexList(String type) {
		String result = "COS_PIC";
		if (type.equals(RESOURCETYPE.COS_PIC.toString())) {
			result = "wd10yearcoser/TwoPage_COS";
		} else if (type.equals(RESOURCETYPE.HANDPAINTED.toString())) {
			result = "wd10yearcoser/TwoPage_Picture";
		} else if (type.equals(RESOURCETYPE.MUSIC.toString())) {
			result = "wd10yearcoser/TwoPage_Music";
		} else if (type.equals(RESOURCETYPE.VIDEO.toString())) {
			result = "wd10yearcoser/TwoPage_Screen";
		}
		return result;
	}
	
	/**
	 * @Title: toIndex 
	 * @Description: 资源上传 ：视频　音乐　同人画　素描
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadResource(@RequestParam(required = false, defaultValue = "")String type) {
		String result = null;
		if (type.equals(RESOURCETYPE.COS_PIC.toString())) {
			result = "wd10yearcoser/upload_Cos";
		} else if (type.equals(RESOURCETYPE.HANDPAINTED.toString())) {
			result = "wd10yearcoser/upload_Picture";
		} else if (type.equals(RESOURCETYPE.MUSIC.toString())) {
			result = "wd10yearcoser/upload_Music";
		} else if (type.equals(RESOURCETYPE.VIDEO.toString())) {
			result = "wd10yearcoser/upload_Screen";
		} else {
			result = "wd10yearcoser/Manage_Upload";
		}
		return result;
	}
	
	/**
	* @Title: toIndex 
	* @Description: 跳转到官方视频页面接口
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping("/ovideolist")
	public String officialVideoList(String type) {
		return "wd10yearcoser/TwoPage_official_video";
	}
	
	/**
	 * @Title:  
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/ovideolist", method = RequestMethod.POST )
	@ResponseBody
	public ResultBeanWithPage<List<CoserOfficialVideo>> videoFrontDataList(
			CoserOfficialVideo bean,
			HttpServletRequest request,  
	        HttpServletResponse response) 
	{
		ResultBeanWithPage<List<CoserOfficialVideo>> result = new ResultBeanWithPage<List<CoserOfficialVideo>>();
		try {
			// 用户信息
			UserInfo userInfo = SignedUser.getUserInfo(request);
			result = coserOaService.videoFrontDataList(bean);
			List<CoserOfficialVideo> list = result.getData();
			// 登录状态下 筛选已经点赞的资源
			if (userInfo != null) {
				// 获取当前用户点过赞的对应资源
				List<UserfavoriteBean> favoriteList = userfavoriteBll.findOfficialFavoriteByUser(userInfo.getUserId());
				// 过滤是否点赞过
				for (UserfavoriteBean userfavoriteBean : favoriteList) {
					for (CoserOfficialVideo coserOfficialVideo : list) {
						if (userfavoriteBean.getResourceCode().equals(coserOfficialVideo.getCode())) {
							coserOfficialVideo.setFlag(true);
							break;
						} 
					}
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsSuccess(false);
			result.setMessage("获取错误");
			return result;
		}
	}
	
	
}
