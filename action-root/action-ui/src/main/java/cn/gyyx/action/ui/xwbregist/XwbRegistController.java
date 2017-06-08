package cn.gyyx.action.ui.xwbregist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.service.mobile.website.CallApi;
import cn.gyyx.action.service.xwbregist.XwbRegistServiceImpl;
import cn.gyyx.core.captcha.Captcha;

@Controller
@RequestMapping("/xwbregist")
public class XwbRegistController {

	@RequestMapping("/info")
	public String services() {
		return "xwbRegist/register";
	}

	/**
	 * 注册
	 */
	@RequestMapping("/regist")
	@ResponseBody
	public cn.gyyx.action.beans.ResultBean<String> getServices(String phone, String password, String verifyCode,
			HttpServletRequest request, HttpServletResponse response) {
		return new XwbRegistServiceImpl().longinAction(phone, password, verifyCode, request, response);
	}

	/**
	 * 发送短信验证码
	 */
	@RequestMapping("smscode")
	@ResponseBody
	public cn.gyyx.action.beans.ResultBean<String> obtainCAPTCHA(String phone, String CAPTCHA,
			HttpServletRequest request, HttpServletResponse response) {
		cn.gyyx.action.beans.ResultBean<String> result = new cn.gyyx.action.beans.ResultBean<>();
		result.setProperties(false, "发送短信验证码失败", "");
		if (StringUtils.isBlank(phone)) {
			result.setMessage("手机号码不能为空");
			return result;
		}
		if (StringUtils.isBlank(CAPTCHA)) {
			result.setMessage("图片验证码不能为空");
			return result;
		}
		 if (!new Captcha(request, response).equals(CAPTCHA)) {
		 result.setProperties(false, "图片验证码填写错误", "");
		 return result;
		 }
//		if (!new SimpleCaptcha().validate(request, CAPTCHA)) {
//			result.setProperties(false, "图片验证码填写错误", "");
//			return result;
//		}
		result = new CallApi().getCAPTCHA(phone);
		return result;
	}

}
