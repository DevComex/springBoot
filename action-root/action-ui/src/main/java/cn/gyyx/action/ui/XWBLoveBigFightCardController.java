/*************************************************
       Copyright ©, 2016, GY Game
       Author: 范永良、陈佩岚
       Created: 2016年-01月-16日
       Note:炫舞吧爱情大作战发放新手卡
 ************************************************/
package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerBean;


@Controller
@RequestMapping("XWBLoveBigFightCard")
public class XWBLoveBigFightCardController {
	/**
	 * 炫舞吧爱情大作战新手卡首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "lovefight/index";
	}

	
	/**
	 * 炫舞吧爱情大作战获取服务器列表
	 * @param gameId
	 * @param netType
	 * @param batchNo
	 * @return
	 */
	@RequestMapping("/getXWBServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers() {
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(false, "谢谢参与，活动已结束", null);
		
		return result;
	}

	/**
	 * 炫舞吧爱情大作战发放新手卡
	 * @param request
	 * @param parameter
	 * @param response
	 * @return
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public ResultBean<String> receive(HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
		return result;
	}

}
