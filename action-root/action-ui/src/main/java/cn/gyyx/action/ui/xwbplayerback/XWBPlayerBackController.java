/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2015年12月18日
 * @版本号：
 * @本类主要用途描述：炫舞吧老玩家回归控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.xwbplayerback;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: XWBPlayerBackController
 * @Description: TODO 炫舞吧老玩家回归活动
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2015年12月18日 上午9:25:36
 *
 */

@Controller
@RequestMapping(value = "/xwbplayerback")
public class XWBPlayerBackController {


	/**
	 * 进入页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String collectIndex(Model model, HttpServletRequest request) {
		return "xuanwuba-player-back/index";
	}



}
