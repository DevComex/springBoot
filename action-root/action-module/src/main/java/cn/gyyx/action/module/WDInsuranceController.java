/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年7月15日 
 * @版本号：v1.0
 * @本类主要用途描述：问道虚拟物品保险项目 控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.activemq.filter.function.inListFunction;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.service.insurance.FroGYYXGO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/insurance")
public class WDInsuranceController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDInsuranceController.class);
	/**
	 * 异步回调光宇GO接口链接
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/froGoPay")
	public String froGoPay(HttpServletRequest request,
			HttpServletResponse response) {
		FroGYYXGO froGYYXGo = new FroGYYXGO();
		String result = null;
		try {
			result = froGYYXGo.froGOInterface(request);
		} catch (Exception e) {
			logger.error("GO购回调接口出错..." + e.toString());
			e.printStackTrace();
		}
		return result;
	}
}
