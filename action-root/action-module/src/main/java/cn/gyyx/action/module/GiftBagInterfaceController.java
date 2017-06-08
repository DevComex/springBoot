/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月18日下午12:22:39
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.module;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.giftinterface.GiftBagBean;
import cn.gyyx.action.service.giftbaginterface.GiftBagInterfaceService;

@Controller
@RequestMapping(value = "/giftbag")
public class GiftBagInterfaceController {
	GiftBagInterfaceService giftService = new GiftBagInterfaceService();

	/***
	 * 获取各个礼包的数量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getinfo",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResultBean<List<GiftBagBean>> getGiftBagBeans(
			@RequestParam("day") String day,@RequestParam("serverName") String serverName) {
		return giftService.getGiftBagBeans(day,serverName);
	}
}
