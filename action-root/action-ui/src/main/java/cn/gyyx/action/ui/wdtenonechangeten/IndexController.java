/*************************************************	
   Copyright ©, 2016, GY Game
   Author: 王雷
   Created: 2016年-4月-18日
   Note:问道十周年 —— 一生换十年活动 
************************************************/
package cn.gyyx.action.ui.wdtenonechangeten;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wdonechangeten")
public class IndexController {
	/**
	 * 初始页
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(){
			return "wdTenOneChangeTen/index";
	}
}
