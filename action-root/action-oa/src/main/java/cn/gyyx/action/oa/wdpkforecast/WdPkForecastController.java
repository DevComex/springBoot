package cn.gyyx.action.oa.wdpkforecast;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping("/oawdpkforecast")
public class WdPkForecastController {

	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "WdPkForecast/index";
	}

	@RequestMapping(value = "/lottery", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> putlottery(HttpServletRequest request, int type) {
		return new ResultBean<>(true, "活动已结束", null);

	}

}
