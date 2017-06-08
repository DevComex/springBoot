/*************************************************
       Copyright ©, 2015, GY Game
       Author: 黄国强
       Created: 2015年12月21日
       Note：宠物领养呼吁页控制器
************************************************/
package cn.gyyx.action.ui;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/petadoption")
public class WDPetAdoptionController {
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "wdpetadoption/index";
	}
	
}
