package cn.gyyx.action.oa.wdtenyearcolorful;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tenyearcolorful")
public class WDTenYearColorfulController {
	
	/**
	 * index 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request){
	
		return "wdtenyearcolorful/index";
	}
	
}
