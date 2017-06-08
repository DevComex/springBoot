package cn.gyyx.action.service.xwbregist;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.base.Splitter;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.mobile.website.CallApi;
import cn.gyyx.core.security.Aes;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class XwbRegistServiceImpl implements IXwbRegistService {

	private static final Logger LOG = GYYXLoggerFactory.getLogger(XwbRegistServiceImpl.class);
	private static final String REGULAR = "^1\\d{10}$";
	private static final String SKEY = "asdfghjklzxcvbnm";
	private static final String SIV = "qwertyuiasdfghjk";

	@Override
	public ResultBean<String> longinAction(String phone, String password, String verifyCode, HttpServletRequest request,
			HttpServletResponse response) {
		CallApi call = new CallApi();
		// 验证手机验证码是否成功
		ResultBean<String> resultCAPTCHA = call.examineCAPTCHA(phone, verifyCode);
		if (resultCAPTCHA.getIsSuccess()) {
			// 判断手机号是否符合
			if (phone.matches(REGULAR)) {
				// 注册
				String[] reg = getCookieValue(request);
				ResultBean<String> result = new CallApi().register(reg, phone, password, request, "phone");
				if (result.getIsSuccess()) {
					// 回写cookies
					String pwd = MD5.encode(password);
					// 0 pwd 1 id 2 phone
					Cookie cookie = new Cookie("GYYX_RP",
							Aes.encrypt(pwd + ";" + result.getData() + ";" + phone, SKEY, SIV));
					cookie.setMaxAge(60 * 60 * 24 * 15);
					response.addCookie(cookie);
					resultCAPTCHA.setProperties(true, "注册成功!", "");
				} else {
					resultCAPTCHA.setProperties(false, result.getMessage(), "");
				}

			} else {
				resultCAPTCHA.setProperties(false, "请填写正确的手机号!", "");
			}
		} else {
			resultCAPTCHA.setProperties(false, resultCAPTCHA.getMessage(), "");
		}
		return resultCAPTCHA;
	}

	/**
	 * ADVisitTemp 0
	 * ADVisitForEver 1
	 * PageVisitGuid 2
	 * 
	 * @return
	 */
	private String[] getCookieValue(HttpServletRequest request) {
		String[] result = new String[] { "default", "default", "default" };
		Iterator<String> it = Splitter.on("; ").split(request.getHeader("Cookie")).iterator();
		while (it.hasNext()) {
			String[] tempa = it.next().split("=");
			if (tempa[0].contains("ADVisitTemp")) {
				result[0] = tempa[1];
			} else if (tempa[0].contains("ADVisitForEver")) {
				result[1] = tempa[1];
			} else if (tempa[0].contains("PageVisitGuid")) {
				result[2] = tempa[1];
			}
		}
		LOG.debug("cookies :" + result[0] + "--" + result[1] + "--" + result[1]);
		return result;
	}

}
