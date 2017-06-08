/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十年老玩家回归活动
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间：2016年4月20日
 * @版本号：
 * @本类主要用途描述：问道十年老玩家回归活动控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: WD10YearPlayerBackController
 * @Description: TODO 问道十年老玩家回归活动控制器
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年4月20日 上午11:38:34
 *
 */
@Controller
@RequestMapping(value = "/WDPlayerBack")
public class WD10YearPlayerBackController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WD10YearPlayerBackController.class);

	/**
	 * 
	 * @Title: toIndex @Description: TODO 活动首页 @param @param model @param @param
	 * request @param @return @return String @throws
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {
		return "wdowj/index";
	}
	
	@RequestMapping("/indexnew")
	public String toIndexnew(Model model, HttpServletRequest request) {
		return "wdowj/index1";
	}
	@RequestMapping("/indexnewwb")
	public String toIndexnewwb(Model model, HttpServletRequest request) {
		return "wdowj/index1_wb";
	}

	@RequestMapping("/tc1")
	public String totc1(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc1";
	}

	@RequestMapping("/tc2")
	public String totc2(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc2";
	}

	@RequestMapping("/tc3")
	public String totc3(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc3";
	}

	@RequestMapping("/tc4")
	public String totc4(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc4";
	}

	@RequestMapping("/tc5")
	public String totc5(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc5";
	}

	@RequestMapping("/tc6")
	public String totc6(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc6";
	}

	@RequestMapping("/tc7")
	public String totc7(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc7";
	}

	@RequestMapping("/tc8")
	public String totc8(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc8";
	}

	@RequestMapping("/tc9")
	public String totc9(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc9";
	}

	@RequestMapping("/tc10")
	public String totc10(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc10";
	}

	@RequestMapping("/tc11")
	public String totc11(Model model, HttpServletRequest request) {
		return "wdowj/tc/tc11";
	}

	/**
	 * 
	 * @Title: toIndexwb @Description: TODO 活动首页——wb @param @param
	 * model @param @param request @param @return @return String @throws
	 */
	@RequestMapping("/indexwb")
	public String toIndexwb(Model model, HttpServletRequest request) {
		return "wdowj/index_wb";
	}

	/**
	 * 
	 * @Title: addCookie @Description: TODO 增加cookie信息 @param @param
	 * cookieBean @param @param request @param @return @return
	 * ResultBean<Boolean> @throws
	 */
	@RequestMapping(value = "/addCookie", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> addCookie(HttpServletRequest request) {
		return new ResultBean<>(false,"活动已结束，感谢参与！",null);

	}

	/**
	 * 
	 * @Title: getServers @Description: TODO 获取服务器信息 @param @param
	 * typeCode @param @return @return ResultBean<List<ServerBean>> @throws
	 */
	@RequestMapping(value = "/getServers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<String>> getServers(@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
		return new ResultBean<>(false,"活动已结束，感谢参与！",null);
	}

	/**
	 * 
	 * @Title: addInvite @Description: TODO 添加邀请信息记录 @param @param
	 * wDInviteBean @param @param request @param @return @return
	 * ResultBean<Boolean> @throws
	 */
	@RequestMapping(value = "/addInvite", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> addInvite(HttpServletRequest request) {
		return new ResultBean<>(false,"活动已结束，感谢参与！",null);
	}

	/**
	 * 
	 * @Title: checkServerNameNew @Description: TODO 查询区服最新名称 @param @param
	 * serverNameOld @param @return @return
	 * ResultBean<List<ServerCheckBean>> @throws
	 */
	@RequestMapping(value = "/getServerNew", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<String>> checkServerNameNew(
			@RequestParam(value = "serverNameOld") String serverNameOld) {
		return new ResultBean<>(false,"活动已结束，感谢参与！",null);
	}

}
