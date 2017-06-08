package cn.gyyx.action.ui.wdpklive;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;

/**
 * @Description: 问道全民PK市场宣传
 * @author 成龙
 * @date 2016年7月12日 下午19:00:00
 */
@Controller
@RequestMapping(value = "/wdpklive")
public class WdPkLiveController {

	/**
	 * 活动首页
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {

		return "wdpklive/index";
	}
	
	/**
	 * 获取用户状态
	 */
	@RequestMapping(value = "/getState", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> getState(Model model,
			HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", "");
		return resultBean;
	}

	/**
	 * 获取报名信息
	 */
	@RequestMapping(value = "/getSignInfo", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<Object> getInfo(Model model,
			HttpServletRequest request) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}

	/**
	 * 报名
	 */
	@RequestMapping(value = "/sign", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> sign(HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", "");
		return resultBean;
	}

	/**
	 * 取消报名
	 */
	@RequestMapping(value = "/cancle", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> cancle(HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", "");
		return resultBean;
	}
	
	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<Object> list(String time,Model model) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}
}
