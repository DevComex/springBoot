/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：novicebag
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年5月12日 下午1:50:57
 * @版本号：
 * @本类主要用途描述：问道新手礼包通用控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wd9yearnovicebag.ServerBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/commonNovicebag")
public class CommonNoviceBagController {

	private static final Integer gameId = 2;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CommonNoviceBagController.class);
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("codeServer1", "s.gyyx.cn");
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "novicebag/novicebag";
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
		logger.info("hdName", hdName);
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);

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
	public ResultBean<String> receive() {
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}
}
