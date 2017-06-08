package cn.gyyx.action.ui.wdnovicecard;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年11月17日 下午12:49:24
 * 描        述：问道新手卡
 */
@Controller
@RequestMapping("/")
public class NovicePcController {
	
	@RequestMapping("/wd2899card/index")
	public String index1(Model model,HttpServletRequest request) {
		return "wd2899/card/cardPcIndex";
	}
	
	@RequestMapping("/wd2899bag/index")
	public String index2(HttpServletRequest request) {
		return "wd2899/bag/bagPcIndex";
	}
	
	@RequestMapping("/wd2999card/index")
	public String index3(Model model,HttpServletRequest request) {
		return "wd2999/card/cardPcIndex";
	}
	
	@RequestMapping("/wd2999bag/index")
	public String index4(HttpServletRequest request) {
		return "wd2999/bag/bagPcIndex";
	}
}
