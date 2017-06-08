/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei && zhouzhongkai
 * @联系方式：wanglei@gyyx.cn  zhouzhongkai@gyyx.cn
 * @创建时间： 2015年3月23日 
 * @版本号：
 * @本类主要用途描述：新手礼包Controller类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;

@Controller
@RequestMapping(value = "/novicebag")
public class WD9YearNoviceBagController {

	@RequestMapping("/index")
	public String index(Model model) {

		model.addAttribute("codeServer1", "s.gyyx.cn");
		model.addAttribute("codeServer2", "static.cn114.cn");
		// model.addAttribute("codeServer2", "127.0.0.1");
		return "wd9yearNoviceBag/index";
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
	public @ResponseBody ResultBean getServers1(String hdName, Model model) {
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
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
	public ResultBean<String> receive(HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
		return result;
	}
}
