package cn.gyyx.action.ui.wdyaoqinghan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wdyqh")
public class YaoQingHanController {

	@RequestMapping(value = {"", "/", "/index"})
	public String index() {
		return "wdYaoQingHan/pindex";
	}
	
	@RequestMapping(value = "/wcj")
	public String wcj() {
		return "wdYaoQingHan/scj";
	}
	
	@RequestMapping(value = "/windex")
	public String windex() {
		return "wdYaoQingHan/sindex";
	}
	
	@RequestMapping(value = "/wqd")
	public String wqd() {
		return "wdYaoQingHan/sqd";
	}
	
	@RequestMapping(value = "/wwd")
	public String wwd() {
		return "wdYaoQingHan/swd";
	}
}
