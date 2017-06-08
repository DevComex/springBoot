/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：光宇9周年祝福活动
 * @作者：liuyongzhi
 * @联系方式：liuyongzhi@gyyx.cn
 * @创建时间： 2015年3月12日 下午5:01:40
 * @版本号：
 * @本类主要用途描述：光宇9周年祝福图片及祝福语审核控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.wish;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.wd9year.WishBean;
import cn.gyyx.action.bll.wd9year.WishBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.service.wd9year.PageModel;
import cn.gyyx.service.wd9year.WishService;

/**
 * 光宇9周年祝福图片及祝福语审核控制器
 */
@Controller
@RequestMapping("/wishimage")
public class WishImageController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WishImageController.class);
	private WishService wishService = new WishService();
	private WishBll wishBll = new WishBll();

	/**
	 * 根据当前页，状态信息查询相关数据
	 * @日期：2015年3月16日
	 * @Title: selectWishBy 
	 * @param model
	 * @param page 要显示的分页数
	 * @param checkStatus 状态信息
	 * @param currentStatus 当前状态
	 * @return 
	 * String
	 */
	@RequestMapping("/queryByStatus")
	public String selectWishBy(Model model, Integer page,String checkStatus, String currentStatus){
		try{
			if(page == null){
				page = 1 ;
			}
			if(currentStatus == null || "".equals(currentStatus)){
				currentStatus = checkStatus;
			}
			List<WishBean> wishBeanList = wishService.selectByPage(page, currentStatus);
			Integer datatotal = wishBll.getCountByStatus(currentStatus);
			PageModel pageModel = new PageModel(datatotal, 10);
			pageModel.setCurrentPage(page);
			model.addAttribute("wishBeanList", wishBeanList);
			model.addAttribute("pageList", pageModel.getPageList());
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("checkStatus", currentStatus);
			model.addAttribute("page", page);
		}catch(Exception e){
			logger.error("Server Error:" + e.toString());
		}
		return "wishimage/showWishImage";
	}
	
	/**
	 * 更改当前数据状态
	 * @日期：2015年3月16日
	 * @Title: updateWishStatus 
	 * @param model
	 * @param page 要显示页面数
	 * @param code 修改状态信息主键code
	 * @param checkStatus 修改成的状态
	 * @param currentStatus 当前页面显示的状态
	 * @return 
	 * String
	 */
	@RequestMapping("/updateWishStatus")
	public String updateWishStatus(Model model,Integer page, Integer code, String checkStatus, String currentStatus){
		try{
			wishService.updateWishStatus(code, checkStatus);
			model.addAttribute("currentStatus", currentStatus);
			model.addAttribute("checkStatus", checkStatus);
			model.addAttribute("page", page);
		}catch(Exception e){
			logger.error("Server Error:" + e.toString());
		}
		return "redirect:queryByStatus";
	}
	

}
