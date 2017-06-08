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
import cn.gyyx.action.beans.wd9yearnovicebag.ActionServerConfigBean;
import cn.gyyx.action.beans.wd9yearnovicebag.ServerBean;

/**
 * 新手礼包
 * @author Administrator
 *
 */
@Controller
@RequestMapping("WDSevenActivation")
public class WDSevenActivation {
	@RequestMapping("index")
	public String index(){
		return "WDSevenAcupuncture/index";
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
	public @ResponseBody ResultBean<List<ServerBean>> getServers1(
			String hdName, Model model) {
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>();
		result.setIsSuccess(true);
		result.setMessage("活动已下线");
		List<ServerBean> list = new ArrayList<>();
		ServerBean e = new ServerBean();
		e.setAreaName("双线");
		List<ActionServerConfigBean> serverList = new ArrayList<>();
		ActionServerConfigBean s = new ActionServerConfigBean();
		s.setServerName("无");
		s.setServerId(0);
		serverList.add(s);
		e.setServerList(serverList);
		list.add(e);
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
		result.setIsSuccess(true);
		result.setMessage("活动已下线");
		return result;
	}
}
