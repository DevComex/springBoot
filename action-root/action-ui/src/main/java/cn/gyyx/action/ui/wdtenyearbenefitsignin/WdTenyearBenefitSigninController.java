/*************************************************
       Copyright ©, 2016, GY Game
       Author: liqiang liqiang@gyyx.cn
       Created: 2016年3月14日
       Note：问道十周年福利活动
************************************************/
package cn.gyyx.action.ui.wdtenyearbenefitsignin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @ClassName: WdTenyearBenefitSigninController 
 * @Description: 问道十周年福利活动控制器
 * @author liqiang
 * @date 2016年3月14日 下午2:06:49 
 *  
 */
@Controller
@RequestMapping("/wdTenyearBenefitSignin")
public class WdTenyearBenefitSigninController {

	/**
	* @Title: toIndex 
	* @Description: 首页
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping("/index")
	public String toIndex() {
		return "wdTenyearBenefitSignin/index";
	}
	

}
