package cn.gyyx.action.ui.wdyy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.ui.wdnovicecard.WDNoviceCommonController;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdyy2017")
public class WDYY2017Controller {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNoviceCommonController.class);
	private static final String hd_name="问道YY2017";
	private static final int actioncode=433;
	@RequestMapping(value = "/index")
	public String index() {
		return "wdyy/2017index";
	}
	
	/**
	 * PC端-领取新手卡
	 */
	@RequestMapping("/card/receive")
	@ResponseBody
	public ResultBean<String> receive(HttpServletRequest request,
			NoviceParameter parameter, HttpServletResponse response) {
		return new ResultBean<String>(false,"活动已结束！","");
	}
	
}
