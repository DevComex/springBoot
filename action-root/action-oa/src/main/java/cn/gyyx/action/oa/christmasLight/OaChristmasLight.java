/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月22日上午10:07:50
 * 版本号：v1.0
 * 本类主要用途叙述：我是预测帝的oa后台审核界面
 */

package cn.gyyx.action.oa.christmasLight;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/oachristmasLight")
public class OaChristmasLight {
	
	// 活动编号

	/**
	 * 通过评论类型查出10评论
	 * 
	 * @author MT
	 * @param commentStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkDisInfo")
	public String checkDisInfo(Model model) {
		
		return "christmasLight/checkDiscuss";
	}

	/**
	 * 作品审核界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkDisInfo1/{lip}")
	public String commentCheck(Model model,
			@PathVariable("lip") String nowpageNew) {
	
		return "christmasLight/pageDis";
	}

	/**
	 * 审核评论通过
	 * 
	 * @param commentCode
	 * @return
	 */
	@RequestMapping("/checkCommentSuccess/{commentCode}")
	public String checkCommentSuccess(
			@PathVariable("commentCode") String commentCode,
			@RequestParam String nowpageNew, Model model) {
		
		return "christmasLight/pageDis";
	}

	/**
	 * 审核评论不通过
	 * 
	 * @param commentCode
	 * @return
	 */
	@RequestMapping("/checkCommentFail/{commentCode}")
	public String checkCommentFail(
			@PathVariable("commentCode") String commentCode,
			@RequestParam String nowpageNew, Model model) {
		return "christmasLight/pageDis";

	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getServers
	 * @Description: TODO 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<String>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		return new ResultBean<List<String>>(false, "活动已结束", null);
	}
}
