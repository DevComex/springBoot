package cn.gyyx.action.ui;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.action.beans.wechat.WeChatCountBean;
import cn.gyyx.action.bll.wechat.WeChatCountBLL;

@Controller
@RequestMapping("/WeChatCount")
public class WeChatCountController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeChatCountController.class);
	private WeChatCountBLL weChatCountBLL = new WeChatCountBLL();
	private int actionCode = 1;


	@RequestMapping(value="/ajaxWeChatCount")
	public String ajaxWeChatCount(WeChatCountBean weChatCountBean,HttpServletRequest request) {
		if (weChatCountBean.getNickName() != null) {
			try {
				weChatCountBean.setNickName(new String(weChatCountBean
						.getNickName().getBytes("iso-8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				logger.info(e.toString());
			}
		}
		logger.info("微信答题后台统计参数:{}" + weChatCountBean.toString());
		weChatCountBean.setActionCode(actionCode);
		weChatCountBean.setCountTime(new Date());
		weChatCountBLL.accessCount(weChatCountBean);
		return "wechat/blank";
	}
}
