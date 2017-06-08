/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月21日下午1:47:48
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.wdgetcardnum.WdGetCardNumService;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping(value = "/getCardNum")
public class WdGetCardNumController {

	private WdGetCardNumService wdGetCardNumService = new WdGetCardNumService();
	private int actionCode = 436;

	/***
	 * 获取卡号
	 * 
	 * @param veCode
	 * @param type
	 * @param actionCode
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping(value = "/getCard", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCard(@RequestParam(value = "veCode") String veCode,
			@RequestParam(value = "type") String type,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("jsoncallback");
		ResultBean<String> resultBean = wdGetCardNumService.getCard(veCode,
				type, actionCode, request, respons);
		return callback + "(" + DataFormat.objToJson(resultBean) + ")";

	}

}
