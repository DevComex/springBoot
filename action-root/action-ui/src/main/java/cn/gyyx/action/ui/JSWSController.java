package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping("/JSWS")
public class JSWSController {

    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request, Model model) {
        return "JSWSActivityCode/index";
    }

    @ResponseBody
    @RequestMapping("/independent")
    public ResultBean<String> getActivityCodePC(HttpServletRequest request,
            String phone, Model model) {
        ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束", null);

        return result;
    }

    @ResponseBody
    @RequestMapping("/login")
    public ResultBean<String> getLoginActivityCode(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
        
        return result;
    }
}
