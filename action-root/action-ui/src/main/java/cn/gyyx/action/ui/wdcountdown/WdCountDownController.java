package cn.gyyx.action.ui.wdcountdown;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.lottery.ResultBean;
import cn.gyyx.action.service.wdcountdown.WdCountDownService;

@Controller
@RequestMapping("wdcountdown")
public class WdCountDownController {

	@RequestMapping("/show")
	public String showPhotoDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

		return "wdcountdown/index";
	}

	@RequestMapping("/time")
	@ResponseBody
	public ResultBean<String> countDownTime(HttpServletRequest request, HttpServletResponse response) {
		WdCountDownService wdCountDownService = new WdCountDownService();

		return wdCountDownService.countDownTime();
	}

	@RequestMapping("/lottery")
	@ResponseBody
	public ResultBean<String> loterry(String time, HttpServletRequest request, HttpServletResponse response) {
		WdCountDownService wdCountDownService = new WdCountDownService();
		if (StringUtils.isEmpty(time)) {
			ResultBean<String> resultBean = new ResultBean();
			resultBean.setProperties(false, "time不能为空", null);
			return resultBean;
		}
		return wdCountDownService.lottery(time);
	}
}
