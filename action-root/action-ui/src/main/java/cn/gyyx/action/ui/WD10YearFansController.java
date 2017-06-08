/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：chenpeilan
 * 联系方式：chenpeilan@gyyx.cn
 * 创建时间： 2016年3月29日
 * 版本号：
 * 本类主要用途描述：问道十周年粉丝榜控制器
-------------------------------------------------------------------------*/
package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;

/**
 * @ClassName: WD10YearFansController
 * @Description: TODO 问道十周年粉丝榜控制器
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年3月29日 下午5:55:45
 *
 */
@Controller
@RequestMapping(value = "/WDFans")
public class WD10YearFansController {

	/**
	 * 
	 * @Title: toIndex
	 * @Description: TODO 活动首页
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {
		return "WdTenYearFans/index";
	}

	/**
	 * 
	 * @Title: toIndex
	 * @Description: TODO 提名页面
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/toNomination ")
	public String toNomination(Model model, HttpServletRequest request) {
		return "WdTenYearFans/toNomination";
	}

	/**
	 * 
	 * @Title: getServers
	 * @Description: TODO 获取服务器信息
	 * @param @param typeCode
	 * @param @return
	 * @return ResultBean<List<ServerBean>>
	 * @throws
	 */
	@RequestMapping(value = "/getServers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(@RequestParam(value = "netType") String typeCode) {
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(false, "谢谢参与，活动已结束", null);

		return result;
	}

	/***
	 * 获取角色信息
	 * 
	 * @param request
	 * @param response
	 * @param serverId
	 * @param veCode
	 * @return
	 */
	@RequestMapping(value = {"/getRoles", "/addRole", "/checkRole"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> getRoles(HttpServletRequest request,HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		
		return resultBean;
	}


	/**
	 * 
	 * @Title: getWdNominationInfoBeanByAccountName
	 * @Description: TODO 判断是否提名过
	 * @param @param accountName
	 * @param @param request
	 * @param @return
	 * @return ResultBean<WdNominationInfoBean>
	 * @throws
	 */
	@RequestMapping(value = {"/getIfNomination", "/addNomination"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean getWdNominationInfoBeanByAccountName(
			HttpServletRequest request) {
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
		return result;
	}

	/***
	 * 增加投票信息
	 * 
	 * @param nominationCode
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/addVote", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> addVote(
			@RequestParam(value = "nominationCode") int nominationCode,
			@RequestParam(value = "type") int type, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
		
		return resultBean;
	}

	/**
	 * 
	 * @Title: getWdAccountScoreBeanByAccountName
	 * @Description: TODO 查看积分表是否有数据，没有则增加
	 * @param @param accountName
	 * @param @param request
	 * @param @return
	 * @return ResultBean<WdAccountScoreBean>
	 * @throws
	 */
	@RequestMapping(value = "/checkScore", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean getWdAccountScoreBeanByAccountName(HttpServletRequest request) {
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
		return result;
		
	}

	/**
	 * 
	 * @Title: getNominationCheck
	 * @Description: TODO 展示提名信息
	 * @param @param accountName
	 * @param @param request
	 * @param @return
	 * @return ResultBean<WdNominationCheckBean>
	 * @throws
	 */
	@RequestMapping(value = "/checkNomination", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean getNominationCheck(
			HttpServletRequest request) {
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
		return result;
	}

	/**
	 * 
	 * @Title: getComments
	 * @Description: TODO 获取所有评论
	 * @param @return
	 * @return ResultBean<List<WdCommentsBean>>
	 * @throws
	 */
	@RequestMapping(value = "/getComments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean getComments(
			@RequestParam(value = "nominationCode") int nominationCode) {
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

		return result;
	}
}
