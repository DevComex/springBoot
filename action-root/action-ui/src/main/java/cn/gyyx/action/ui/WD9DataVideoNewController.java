package cn.gyyx.action.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "DataVideoNew")
public class WD9DataVideoNewController {

	@RequestMapping(value = "/WD9DataVideo")
	public String test(Model model) {
		model.addAttribute("staticSource",
				"http://static.cn114.cn/action/wdDataVideo");
		return "wd9yearDataVideo/wd9yearDataVideo";
	}
}
