package cn.gyyx.action.oa.wdlight2017;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.lottery.ResultBean;
import cn.gyyx.action.beans.wd10yearcoser.ResultBeanWithPage;
import cn.gyyx.action.beans.wdlight2017.ValidWishBean;
import cn.gyyx.action.service.wdlight2017.OaWishService;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/wdlight2017oa")
public class WdLight2017Controller {

	private static final GYYXLogger LOG = GYYXLoggerFactory.getLogger(WdLight2017Controller.class);
	OaWishService oaWishService = new OaWishService();

	@RequestMapping(value = "/index")
	public String index(Model model, HttpServletRequest request) {
		return "wdlight2017/index";

	}
	
	@RequestMapping(value = "/wish")
	public String wish(Model model, HttpServletRequest request) {
		return "wdlight2017/wish";

	}
	@RequestMapping(value = "/light")
	public String light(Model model, HttpServletRequest request) {
		return "wdlight2017/light";

	}
	

	@RequestMapping(value = "/wishList",method = RequestMethod.GET )
	@ResponseBody
	public ResultBeanWithPage<List<ValidWishBean>> getValidWish(@RequestParam(value = "pageSize") int pageNum,
			@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "checkType") String checkType, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.info("获取许愿审核列表:pageNum:{},pageIndex:{},checkType{}", new Object[] { pageNum, pageIndex, checkType });
		ResultBeanWithPage<List<ValidWishBean>> resultBean = new ResultBeanWithPage<>();
		int status=-1;
		if("CHECKED".equals(checkType)){
			status=2;
		}else if("CHECKING".equals(checkType)){
			status=1;
		}else if("UNCHECK".equals(checkType)){
			status=0;
		}
		resultBean = oaWishService.getValidWish(pageNum, pageIndex, status);
		LOG.info("获取许愿审核列表结束");
		return resultBean;
	}

	@RequestMapping(value = "/modifystatus",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> modifyWishStatusByCode(@RequestParam(value = "code") int code,
			@RequestParam(value = "status") int status, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("修改许愿状态:code:{},status{}", new Object[] { code, status });
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean = oaWishService.modifyWishStatusByCode(status, code);
		LOG.info("修改许愿状态结束");
		return resultBean;
	}
	
	@RequestMapping(value="/modifyPepNum",method=RequestMethod.POST)
    @ResponseBody
	public ResultBean<Object> modifyLevelPeopleNum(@RequestParam(value = "level") int level,
			@RequestParam(value = "limitNum") int limitNum, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("修改许愿层人数:level:{},limitNum{}", new Object[] { level, limitNum });
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean = oaWishService.modifyLightLimitNum(level, limitNum);
		LOG.info("修改许愿层人数结束");
		return resultBean;
	}
	
	@RequestMapping(value="/levelList",method=RequestMethod.GET)
    @ResponseBody
	public ResultBean<Object> getLevelList(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("获取许愿层信息");
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean = oaWishService.getLightLevel();
		LOG.info("获取许愿层信息结束");
		return resultBean;
	}
}

