/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十年老玩家回归活动
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间：2016年4月20日
 * @版本号：
 * @本类主要用途描述：问道十年老玩家回归活动控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName: WD10YearPlayerBackControllerCopy
 * @Description: TODO 问道十年老玩家回归活动控制器
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年5月3日 上午10:53:34
 *
 */
@Controller
@RequestMapping(value = "/wdplayerback")
public class WD10YearPlayerBackControllerCopy {

	@RequestMapping("/indexnewwb")
	public String toIndexnewwb(Model model, HttpServletRequest request) {
		return "wdowj/index1_wb";
	}

}
