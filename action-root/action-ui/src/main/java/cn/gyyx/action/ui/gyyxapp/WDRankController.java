package cn.gyyx.action.ui.gyyxapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/wdrank")
public class WDRankController {
	@RequestMapping(value="/index")
	public String index(){
		return "/wdrank/index";
	}
}
