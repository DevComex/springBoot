package cn.gyyx.action.ui.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/commonS")
public class TempPageController {
	@RequestMapping("/index")
	public String index() {
		return "jsws/index";
	}
	@RequestMapping("/huanli")
	public String huanli() {
		return "jsws/huanli";
	}
	@RequestMapping("/jifen")
	public String jifen() {
		return "jsws/jifen";
	}
	@RequestMapping("/xiulian")
	public String commonTestPage() {
		return "jsws/xiulian";
	}
	@RequestMapping("/bugSubmit")
	public String bugSubmitIndex(){
		return "LHZSBugSubmit/bugsubmit";
	}
	@RequestMapping("/investigate")
	public String lhzsInvestigate(){
		return "LHZSInvestigate/index";
	}
	@RequestMapping("/wdAlone")
	public String wdAlone() {
		return "wdalone/index";
	}
}
