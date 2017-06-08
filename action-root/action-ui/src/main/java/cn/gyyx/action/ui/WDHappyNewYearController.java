package cn.gyyx.action.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/wdnewyear")
public class WDHappyNewYearController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDHappyNewYearController.class);

	/**
	 * 主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 是否是微信浏览器
		/*String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		return "wdnewyear/index";
	}

	/**
	 * 分享
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/share")
	public String zjkpShare(HttpServletRequest request,
			HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		// 是否是微信浏览器
		/*String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		String msg = request.getParameter("content");
		if(msg != null){
			msg = new String(msg.getBytes("iso-8859-1"),"utf-8");
		}
		model.addAttribute("msg", msg);
		return "wdnewyear/fx";
	}

}
