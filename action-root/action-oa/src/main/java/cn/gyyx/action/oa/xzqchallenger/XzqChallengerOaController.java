package cn.gyyx.action.oa.xzqchallenger;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.common.ActionUserLoginLog;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;

/**
 * 同人活动 oa后台
 */
@Controller
@RequestMapping(value = "/xzq2016")
public class XzqChallengerOaController {
    @RequestMapping(value = "")
    public String index(Model model, HttpServletRequest request) {
        return "xzq2016/index";

    }

    /***
     * 用户信息列表
     */
    @RequestMapping(value = "/userlist")
    public String userlist(HttpServletRequest request) {
        return "xzq2016/userlist";
    }

    /***
     * 用户信息-数据
     */
    @RequestMapping(value = "/userDataList", method = RequestMethod.GET)
    @ResponseBody
    public ResultBeanWithPage<List<Map<String, String>>> userDataList() {

        return new ResultBeanWithPage<>(false, "谢谢参与，活动已结束", null, 0);
    }

    /***
     * 登录信息列表
     */
    @RequestMapping(value = "/loginlist")
    public String loginlist(HttpServletRequest request, Model model) {
        return "xzq2016/loginlist";
    }

    /***
     * 登录信息-数据
     */
    @RequestMapping(value = "/loginDataList", method = RequestMethod.GET)
    @ResponseBody
    public ResultBeanWithPage<List<ActionUserLoginLog>> loginDataList() {

        return new ResultBeanWithPage<>(false, "谢谢参与，活动已结束", null, 0);
    }

    /***
     * 薛之谦修改战力值页
     */
    @RequestMapping(value = "/score")
    public String score(HttpServletRequest request, Model model) {

        return "xzq2016/score";
    }

    /***
     * 修改
     */
    @RequestMapping(value = "/saveScore", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> saveScore(HttpServletRequest request, Long score) {
        return new ResultBean<>(false, "谢谢参与，活动已结束", "");
    }

}
