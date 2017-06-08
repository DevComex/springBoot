package cn.gyyx.action.ui.wdyy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;

@Controller
@RequestMapping(value = "/wdyy")
public class WDYYController {
	
	@RequestMapping(value = "/index")
	public String index() {
		return "wdyy/index";
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
		return new ResultBean<String>(false,"活动已结束！","");
	}
}
