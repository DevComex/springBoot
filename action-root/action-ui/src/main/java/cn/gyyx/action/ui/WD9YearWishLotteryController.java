/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：祝福九周年抽奖活动
 * @作者：liuyongzhi
 * @联系方式：liuyongzhi@gyyx.cn
 * @创建时间： 2015年4月9日 下午5:22:24
 * @版本号：
 * @本类主要用途描述：问道9周年祝福抽奖活动
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 问道9周年祝福抽奖活动
 */
@Controller
@RequestMapping("/wd9year")
public class WD9YearWishLotteryController {
	/**
	 * 抽奖首页
	 * @日期：2015年4月9日
	 * @Title: toLottery 
	 * @return 
	 * String
	 */
	@RequestMapping(value="wishLottery")
	public String toLottery(Model model){
		model.addAttribute("staticSource",
				"http://static.cn114.cn/action/wd9yearWishLottery");
		return "wd9year/lottery";
	}

}
