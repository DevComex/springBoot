package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/wdtoplevel")
public class WDTopLevelController {
	@RequestMapping(value = "/index")
	public String showIndex(Model model, HttpServletRequest request) {
		if (request.getCookies() != null) {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo != null) {
				String account = userInfo.getAccount();
				String res = account.substring(0, 2)
						+ "***"
						+ account.substring(account.length() - 2,
								account.length());
				model.addAttribute("account", res);
			}
		}
		return "wdtoplevel/index";
	}
}
