package cn.gyyx.action.service;

import cn.gyyx.action.common.beans.InnerTransmissionData;

public class CaptchaTransmissionAdapter {
	public int validate(InnerTransmissionData trans) {
		CaptchaValidate validate = new CaptchaValidate();
		return validate.checkCaptcha((String)trans.getExtra("captcha"), (String)trans.getData("cookieKey"));
	}
}
