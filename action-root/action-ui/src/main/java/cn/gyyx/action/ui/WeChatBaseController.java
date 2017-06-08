package cn.gyyx.action.ui;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.bll.wdnationalday.WdNationaldayEnrollBll;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WeChatBaseController {

	protected static Logger logger = GYYXLoggerFactory.getLogger(WeChatBaseController.class);
	private static final String WX_SIGN_KEY = "Dfad124FAC518DF3";
	private String developType = "release";

	private WeChatAttention att = new WeChatAttention();

	protected void setDevelopType(String type) {
		this.developType = StringUtils.isEmpty(type) ? "release" : type;
	}

	// 是否为微信浏览器
	protected boolean isWechatBrowser(HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		String ua = request.getHeader("user-agent").toLowerCase();

		try {
			if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) { // 是微信浏览器

				if (developType.equalsIgnoreCase("debug")) {
					return true;
				} else {
					response.reset();
					response.setContentType("text/html;charset=UTF-8");
					OutputStream output = response.getOutputStream();
					output.write(String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>").getBytes());
					output.flush();
					output.close();
				}
			}

			else {
				result = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return result;
	}

	// 是否关注订阅号
	protected boolean isAttention(int activityId, String openId) {
		boolean result = false;
		String weChatType = StringUtils.EMPTY;

		switch (activityId) {
		default:
			weChatType = "Wd";
			break;
		}

		return att.isAttention(weChatType, openId);
	}

	// 获得IP地址
	protected String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	// 校验URL是否有效
	public HashMap<String, Object> valideURLSign(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		boolean res = false;
		String time = "0";
		String sign = "0";
		String message = "此链接地址无效！";
		String openId = StringUtils.EMPTY;
		String userId = StringUtils.EMPTY;
		String url = StringUtils.EMPTY;

		if (request.getQueryString() != null) {
			// 检查连接打开的时间
			String paramsStr = request.getQueryString();
			String uri = paramsStr.replace("%26", "&").replace("%3d", "=");

			String[] paramArray = uri.split("&");
			Map<String, String> paramMap = new HashMap<String, String>();

			for (int i = 0; i < paramArray.length; i++) {
				paramMap.put(paramArray[i].split("=")[0].toLowerCase(), paramArray[i].split("=")[1]);
			}

			if (paramMap.containsKey("time")) {
				time = paramMap.get("time");
			}

			if (paramMap.containsKey("sign")) {
				sign = paramMap.get("sign");
			}

			if (paramMap.containsKey("openid")) {
				openId = paramMap.get("openid");
			}

			url = "OpenId=" + openId + "&time=" + time;

			if (CheckSign(url, WX_SIGN_KEY, sign)) {
				res = true;
				result.put("openId", openId);
				long timestamp = System.currentTimeMillis() / 1000;
				if (Integer.parseInt(time) <= 0 || timestamp - Integer.parseInt(time) > 3600) {
					message = "链接地址已过有效期，请重新获取！";
				} else {
					res = true;
					result.put("openId", openId);
				}
			}
		}

		if (!res) {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(String.valueOf("<h1>" + message + "</h1>"));
			response.getWriter().close();
		}

		return result;
	}

	// 校验签名
	public boolean CheckSign(String url, String signKey, String sign) {
		logger.info("微信大转盘" + MD5.encode(url + signKey));
		return MD5.encode(url + signKey).equals(sign);
	}

	// 校验用户是否登录
	protected boolean validateLogin(int activityId, String userId) {
		boolean result = false;

		switch (activityId) {
		case 395:
			WdNationaldayEnrollBll wdNationaldayEnrollBll = new WdNationaldayEnrollBll();
			WdNationaldayEnrollBean bean = wdNationaldayEnrollBll.getUserInfoByOpenId(userId);
			if (bean != null)
				result = true;
			break;
		default:
			result = true;
			break;
		}

		return result;
	}

	// 根据活动ID获得登录页URL
	protected String getLoginUrl(int activityId) {
		String result = StringUtils.EMPTY;

		switch (activityId) {
		case 395:
			result = "/nationalDay/login";
			break;
		default:
			break;
		}

		return result;
	}
}
