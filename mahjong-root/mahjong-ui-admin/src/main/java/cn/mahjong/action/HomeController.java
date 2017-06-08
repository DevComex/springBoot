package cn.mahjong.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserRole;
import cn.mahjong.bll.UserBll;

@Controller
public class HomeController {

    private UserBll userBll;

    @Autowired
    public HomeController(UserBll userBll) {
        this.userBll = userBll;
    }

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
        CookieUserInfo user = userBll.checkLogin(request);

        if (UserRole.SALESMAN.getValue().equals(user.getRole())) {
            return "redirect:/head";
        } else {
            return "redirect:/sub";
        }
    }
}
