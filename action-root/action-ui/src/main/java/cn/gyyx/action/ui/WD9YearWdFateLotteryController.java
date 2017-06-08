package cn.gyyx.action.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/wd9yearFate")
@Controller
public class WD9YearWdFateLotteryController {

	/**
	 * 抽奖首页
	 * @日期：2015年4月9日
	 * @Title: toLottery 
	 * @return 
	 * String
	 */
	@RequestMapping(value="lottery")
	public String toLottery(Model model){
		model.addAttribute("staticSource",
				"http://static.cn114.cn/action/wd9yearFateLottery");
		return "wd9yearFate/lottery";
	}


	
}
