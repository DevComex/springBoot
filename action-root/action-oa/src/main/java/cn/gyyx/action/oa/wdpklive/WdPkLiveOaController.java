/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：lizhihai 
 * 联系方式：lizhihai@gyyx.cn 
 * 创建时间：2016年11月03日
 * 版本号：v1.0
 */
package cn.gyyx.action.oa.wdpklive;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 问道全民PK公会直播活动后台
 * 
 * @author lizhihai
 *
 */
@Controller
@RequestMapping(value = "/wdpkliveoa")
public class WdPkLiveOaController {

	@RequestMapping(value = "/index")
	public String index(Model model, HttpServletRequest request) {

		return "wdpklive/index";
	}
}
