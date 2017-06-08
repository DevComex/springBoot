/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年7月28日 
 * @版本号：v1.210
 * @本类主要用途描述：暑期问道缘排行抽奖 控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sumvacation")
public class WDSummerVacationController {
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/lottery")
	public String index(Model model) {
		return "wdsumvacation/lottery";
	}
	
}
