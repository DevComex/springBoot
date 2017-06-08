/*************************************************
       Copyright ©, 2015, GY Game
       Author: huangguoqiang
       Created: 2015年12月09日 
       Note：记录注册前浏览地址及注册地址 控制器
 ************************************************/
package cn.gyyx.action.ui.common;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.axiom.util.base64.Base64Utils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.common.NoteURLBean;
import cn.gyyx.action.bll.common.NoteURLBLL;
import cn.gyyx.action.service.agent.CallWebAPIUserInfoAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/NoteURL")
public class NoteURLController {
	private NoteURLBLL noteURLBLL = new NoteURLBLL();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoteURLController.class);

	@RequestMapping(value = "/note")
	@ResponseBody
	public String test(HttpServletRequest request)
			throws UnsupportedEncodingException {
		// 循环遍历request中的所有cookie，找到AUTH_COOKIE_KEY对应的cookie
		String message = "";
		if (request.getCookies() != null) {
			String referrerURL = null;
			String nowURL = null;
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName() != null) {
					if (cookie.getName().equals("GyyxVisitInfoOld")) {
						byte[] s = Base64Utils.decode(cookie.getValue());
						referrerURL = new String(s, "UTF-8");
						referrerURL = referrerURL.replaceAll("%3A", ":");
					} else if (cookie.getName().equals("GyyxVisitInfoNow")) {
						byte[] s = Base64Utils.decode(cookie.getValue());
						nowURL = new String(s, "UTF-8");
						nowURL = nowURL.replaceAll("%3A", ":");
					}
				}
			}
			if (referrerURL != null) {
				try {
					UserInfo userInfo = SignedUser.getUserInfo(request);
					logger.info("userInfo", userInfo);
					if (userInfo != null) {
						JSONObject json = CallWebAPIUserInfoAgent
								.getUserBasicInfoByUserId(userInfo.getUserId());
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd'T'HH:mm:ss");
						String regTimes = json.getString("RegTime");
						Date regTime = sdf.parse(regTimes);
						Date dateNow = new Date();
						// 如果判断当前时间在注册时间后的15分钟内，判定为新用户，存入数据库
						if ((dateNow.getTime() - regTime.getTime()) / 1000 < 900) {
							NoteURLBean noteURLBean = new NoteURLBean();
							noteURLBean.setUserID(userInfo.getUserId());
							noteURLBean.setAccount(userInfo.getAccount());
							noteURLBean.setNowURL(nowURL);
							noteURLBean.setReferrerURL(referrerURL);
							noteURLBean.setRegTime(regTime);
							noteURLBean.setDrawIP(userInfo.getLoginIP());
							noteURLBLL.insertNoteURLBean(noteURLBean);
							message = "success";
						}else{
							message = "userInfo is old";
						}
					}else{
						message = "userInfo is null";
					}
					return message;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					logger.error("NoteURLController", e.toString());
					e.printStackTrace();
					message = "JSONException";
					return message;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("NoteURLController", e.toString());
					e.printStackTrace();
					message = "ParseException";
					return message;
				}
			}else{
				message = "referrerURL is null";
			}
		}else{
			message = "cookie is null";
		}
		return message;
	}
}
