package cn.gyyx.action.module;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.wdsigned.IWdAppSignLogService;
import cn.gyyx.action.service.wdsigned.WdAppSignLogServiceImpl;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdappsign")
public class WdAppSignController {

	private static final Logger logger = GYYXLoggerFactory.getLogger(WdAppSignController.class);
	private IWdAppSignLogService wdAppSignLogService = new WdAppSignLogServiceImpl();
	
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	public @ResponseBody ResultBean<String> sigin(
			@RequestParam("qrCodeContent") String qrCodeContent,
			@RequestParam("a") String a) {
		
		logger.info("二维码信息：{}   appAccount", qrCodeContent, a);

		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "签到失败", null);
		if (StringUtils.isEmpty(qrCodeContent)) {
			resultBean.setProperties(false, "请传二维码", null);
			return resultBean;
		}
		try {
			return wdAppSignLogService.sign(qrCodeContent, a);
		} catch (Exception e) {
			logger.error("问道app签到[签到失败]异常", e);
		}
		return resultBean;
	}
}
