package cn.gyyx.action.ui.wd10yearcoser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wd10yearcoser.Constants.PASSTYPE;
import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd10yearcoser.ResourceDTO;
import cn.gyyx.action.beans.wd10yearcoser.ResultBeanWithPage;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.bll.wd10yearcoser.ResourceBll;
import cn.gyyx.action.bll.wd10yearcoser.UserfavoriteBll;
import cn.gyyx.action.service.wd10yearcoser.ResourceService;
import cn.gyyx.action.service.wd10yearcoser.UserfavoriteService;
import cn.gyyx.action.service.wd10yearcoser.Wd10yearcoserOaService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;


/** 
 * @ClassName: WdTenyearBenefitSigninController 
 * @Description: 问道十周coser 资源控制器
 * @author wangandong
 * @date 2016年9月1日 下午11:06:49 
 *  
 */
@Controller
@RequestMapping("/wd10coserresource")
public class ResourceController {
	//日志
	private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

	private ResourceBll resourceBll = new ResourceBll();
	private UserfavoriteBll userfavoriteBll = new UserfavoriteBll();
	
	private ResourceService resourceService = new ResourceService();
	private UserfavoriteService userfavoriteService = new UserfavoriteService();
	private Wd10yearcoserOaService coserOaService = new Wd10yearcoserOaService();
	int actionCode = 389;
	//业务接口
	
	
	/**
	* @Title: save 
	* @Description: 添加资源
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> saveResource(ResourceBean resource,String veCode,
			HttpServletRequest request,HttpServletResponse response) {
		// 用户信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<String>(false, "请先登录", null);
		}
		//验证二维码
		if (!new Captcha(request, response).equals(veCode)) {
			return new ResultBean<String>(false, "您输入的验证码错误或超时，请重新填写.", null);
		}

		//设置userID
		Date now = new Date();
		resource.setUserCode(userInfo.getUserId());
		resource.setUsername(userInfo.getAccount());
		resource.setCheckType(PASSTYPE.CHECKING.toString());
		resource.setCreateTime(now);
		resource.setUpdateTime(now);
		try {
			resourceBll.addResource(resource);
			return new ResultBean<String>(true, "保存成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>(false, "保存失败", null);
		}
	}
	
	/**
	* @Title: del 
	* @Description: 删除资源
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> delete(int code,HttpServletRequest request) {
		// 用户信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "请先登录", null);
		}
		try {
			resourceBll.deleteResourceByCode(code);
			return new ResultBean<String>(true, "删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>(false, "删除失败", null);
		}
	}
	
	
	/**
	 * @Title: mylist 
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET )
	public ModelAndView detail(int code) {
		ModelMap model = new ModelMap();
		ResourceBean resource = resourceBll.findResourceByCode(code);
		if (resource != null) {
			ResourceDTO dto = new ResourceDTO();
			try {
				//赋值到dto
				BeanUtils.copyProperties(dto, resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//设置works
			String works = resource.getWorks();
			if (StringUtils.isNotBlank(works)) {
				if (works.indexOf(",") > 0) {
					String[] worksArr = works.split(",");
					dto.setWorks(worksArr);
				} else {
					dto.setWorks(new String[]{works});
				}
			}
			model.addAttribute("resource", dto);
		}
		return new ModelAndView("wd10yearcoser/imgShowAll",model);  
	}
	
	/**
	 * @Title: my_manager 
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/myupload", method = RequestMethod.GET )
	public String myUpload() {
		return "wd10yearcoser/ManageMy_Upload";
	}
	
	/**
	 * @Title: talist 
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/talist", method = RequestMethod.GET )
	public ModelAndView taList(int taUserCode,String taUsername,HttpServletRequest request,  
	        HttpServletResponse response) {
		ModelMap model = new ModelMap();
		model.addAttribute("taUserCode", taUserCode);
		model.addAttribute("taUsername", taUsername);
		return new ModelAndView("wd10yearcoser/Picture_TwoPage_TA",model);  
	}
	
	/**
	 * @Title: talist 
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/talist", method = RequestMethod.POST )
	@ResponseBody
	public ResultBeanWithPage<List<ResourceBean>> taListByPage(
			ResourceBean resource,
			int pageSize, 
			int pageIndex,
			int taUserCode,
			HttpServletRequest request,  
	        HttpServletResponse response) 
	{
		ResultBeanWithPage<List<ResourceBean>> result = new ResultBeanWithPage<List<ResourceBean>>();
		try {
			// 用户信息
			UserInfo userInfo = SignedUser.getUserInfo(request);
			return resourceService.findTaResourcesByPage(userInfo, resource, taUserCode, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsSuccess(false);
			result.setMessage("获取错误");
			return result;
		}
	}
	
	/**
	* @Title: mylist 
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping(value = "/mylist", method = RequestMethod.GET )
	public String myList() {
		return "wd10yearcoser/Manage_My_list";
	}
	
	/**
	* @Title: list 
	* @Description: 根据查询条件分页查询  （不同类型 暂时有4个）
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping(value="/mylist",method=RequestMethod.POST)
	@ResponseBody
	public ResultBeanWithPage<List<ResourceBean>> findMyselfResources(
			ResourceBean resource,
			int pageSize, int pageIndex,
			HttpServletRequest request)
	{
		ResultBeanWithPage<List<ResourceBean>> result = new ResultBeanWithPage<List<ResourceBean>>();
		// 用户信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			result.setIsSuccess(false);
			result.setMessage("请登录");
			return result;
		}
		// 设置用户信息
		resource.setUserCode(userInfo.getUserId());
		resource.setUsername(userInfo.getAccount());
		try {
			return resourceService.findMyselfResourcesByPage(resource, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsSuccess(false);
			result.setMessage("获取错误");
			return result;
		}
		
	}
	
	/**
	 * @Title: list 
	 * @Description: 根据查询条件分页查询  （不同类型 暂时有4个）
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/otherlist",method=RequestMethod.POST)
	@ResponseBody
	public ResultBeanWithPage<List<ResourceBean>> findResources(
			ResourceBean resource,
			int pageSize, int pageIndex,
			HttpServletRequest request)
	{
		ResultBeanWithPage<List<ResourceBean>> result = new ResultBeanWithPage<List<ResourceBean>>();
		// 用户信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo != null) {
			//设置用户信息
			resource.setUserCode(userInfo.getUserId());
			resource.setUsername(userInfo.getAccount());
		}
		try {
			return resourceService.findOtherRecesByPage(resource, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsSuccess(false);
			result.setMessage("获取错误");
			return result;
		}
	}
	
	/**
	 * @Title: favorite 
	 * @Description: 根据code查询要点赞的资源
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/favorite",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> favorite(UserfavoriteBean userfavorite,HttpServletRequest request){
		// 用户信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "请先登录", null);
		}
		userfavorite.setCreateTime(new Date());
		userfavorite.setUserCode(userInfo.getUserId());
		userfavorite.setUsername(userInfo.getAccount());
		try {
			return userfavoriteService.saveUserfavorite(userfavorite,actionCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>(false, "点赞失败", null); 
		}
	}
	
	/**
	 * @Title: favorite 
	 * @Description: 根据code查询要点赞的资源
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/ofavorite",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> OfficialFavorite(UserfavoriteBean userfavorite,HttpServletRequest request){
		// 用户信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "请先登录", null);
		}
		userfavorite.setCreateTime(new Date());
		userfavorite.setUserCode(userInfo.getUserId());
		userfavorite.setUsername(userInfo.getAccount());
		try {
			return userfavoriteService.saveOfficialVideofavorite(userfavorite);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>(false, "点赞失败", null); 
		}
	}
	
	/**
	 * @Title: click 
	 * @Description: 根据code增加资源点击数
	 * @param @return 
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/click",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> favorite(int code){
		try {
			return userfavoriteService.saveResourceClick(code);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>(false, "点赞失败", null); 
		}
	}
	
	
}
