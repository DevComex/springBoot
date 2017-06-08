/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月25日 上午10:26:35
 * @版本号：
 * @本类主要用途描述：问道九年抽奖活动控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/wdninestoryLottery")
public class WDNineLotteryController {


	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: index
	 * @Description: TODO 首页
	 * @param model
	 * @return String
	 */
	@RequestMapping("/lottery")
	public String index(Model model) {
		return "wdninestory/lottery";
	}


}
