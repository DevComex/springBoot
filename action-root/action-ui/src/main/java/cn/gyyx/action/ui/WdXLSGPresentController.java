package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.service.MobileOsInfoService;
import cn.gyyx.action.service.wdxlsgpresent.WdXLSGPresentService;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/xlsglibao")
public class WdXLSGPresentController {
	protected static Logger logger = GYYXLoggerFactory.getLogger(WdXLSGPresentController.class);
	private WeChatAttention att = new WeChatAttention();
	private WdXLSGPresentService wdXLSGPresentService = new WdXLSGPresentService();

	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/wxlibao")
	public String index(HttpServletRequest request, HttpServletResponse response) {

		try {// 是否微信浏览器
			response.setContentType("text/html;charset=UTF-8");
			if (!checkIsWeiXin(request, response)) {
				return "wdxlsgpresent/wxerror";
			}
		} catch (Exception e) {
			logger.error("驯龙三国领取礼包功能[go默认页Controller]异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
		}
		return "wdxlsgpresent/index";
	}

	@RequestMapping(value = "/sharepage")
	public String sharepage(HttpServletRequest request, HttpServletResponse response) {

		try {// 是否微信浏览器
			response.setContentType("text/html;charset=UTF-8");
			if (!checkIsWeiXin(request, response)) {
				return "wdxlsgpresent/wxerror";
			}
		} catch (Exception e) {
			logger.error("驯龙三国领取礼包功能[go分享页sharepage]异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));

		}

		return "wdxlsgpresent/share";
	}

	@RequestMapping(value = "/getpresent", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<UserLotteryBean> getpresent(HttpServletRequest request, @RequestParam("openId") String openId,
			HttpServletResponse response) {
		ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>(false, "出错了", null);
		try {
		        int actionCode = 404;
			if (att.isAttention("xlsg", openId) != true) {
				result.setMessage("请先关注微信订阅号！");
				return result;
			}
			if (openId.trim() == null || openId.trim().length() <= 0) {
				result.setMessage("微信特征码不能为空");
				return result;
			}
			// 获得手机操作系统类型：安卓、IOS
			String os = MobileOsInfoService.getInstance().get(request.getHeader("user-agent"));
			if(os == "ios"){
			    actionCode = 415;
			}
			logger.info("wdXLSGPresentService.getLottery开始抽奖:");
			result = wdXLSGPresentService.getLottery(openId, actionCode,os);
			logger.info("wdXLSGPresentService.getLottery抽奖结束:");
		} catch (Exception e) {
			logger.error("驯龙三国领取礼包功能[领取礼包接口getpresent]异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
		}
		return result;
	}

	// 页面跳转
	private boolean checkIsWeiXin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String ua = request.getHeader("user-agent").toLowerCase();

			if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 不是微信浏览器
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(
						"<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'></head><body><div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 1.2rem;'>请在微信客户端打开链接<h4></div></div></body></html>");
				response.getWriter().close();
				return false;
			}
		} catch (Exception e) {
			logger.error("检查是否是微信浏览器失败,错误堆栈:{}", Throwables.getStackTraceAsString(e));
		}
		return true;
	}

}
