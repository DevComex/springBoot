package cn.gyyx.action.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerBean;

@Controller
@RequestMapping(value = "/heavenlybookcard")
public class HeavenlyBookCardController {

	@RequestMapping("/index")
	public String index(Model model) {

		model.addAttribute("codeServer1", "s.gyyx.cn");
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "heavenlybookcard/index";
	}

	/**
	 * 
	 * @日期：2014年12月10日
	 * @Title: getServers
	 * @Description: TODO
	 * @param gameCategory
	 * @param state
	 * @param netType
	 * @param batchNo
	 * @param model
	 * @return JSONObject
	 */

	@RequestMapping("/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(Integer gameCategory,
			Integer netType, Integer batchNo) {
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>();
		result.setIsSuccess(true);
		result.setMessage("活动已下线");
		ServerBean s = new ServerBean();
		s.setServerName("无");
		List<ServerBean> list = new ArrayList<ServerBean>();
		list.add(s);
		result.setData(list);
		return result;
	}

	/**
	 * 提交领取新手卡奖励
	 * 
	 * @param code
	 *            验证码
	 * @param serverId
	 *            服务器ID
	 * @param gameCategory
	 *            游戏
	 * @param card
	 *            卡号
	 * @param batchNo
	 *            批次
	 * @param PassWord
	 *            密码
	 * @return
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public ResultBean<String> receive(HttpServletRequest request,
			NoviceParameter parameter, HttpServletResponse response) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		result.setMessage("活动已下线");
		return result;
	}
}
