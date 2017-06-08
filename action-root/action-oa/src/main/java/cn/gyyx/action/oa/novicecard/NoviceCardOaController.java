package cn.gyyx.action.oa.novicecard;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新手卡 oa后台
 */
@Controller
@RequestMapping(value = "/wd/novice")
public class NoviceCardOaController {
	@RequestMapping(value = "/index")
	public String index(Model model, HttpServletRequest request) {
		return "novice/index";

	}
	
	/***
	 * 获取用户领取记录
	 */
	@RequestMapping(value = {"/receivelist", "/receiveDataList"})
	public String userlist(HttpServletRequest request) {
		return "novice/receivelist";
	}
	
	/***
	 * 设置服务器信息
	 */
	@RequestMapping(value = {"/serverlist", "/serverDataList", "/addOrUpdateServer", "/deleteServer"})
	public String serverlist(HttpServletRequest request) {
		return "novice/serverlist";
	}
	
	
	/***
	 * 统计
	 */
	@RequestMapping(value = {"/statistics", "/statisticsDataList"})
	public String statistics(HttpServletRequest request,Model model) {

	    return "novice/statistics";
	}
	
}
