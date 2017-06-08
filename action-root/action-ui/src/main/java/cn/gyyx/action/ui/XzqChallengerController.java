package cn.gyyx.action.ui;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;

/**
 * 版 权：光宇游戏 作 者：ChengLong 创建时间：2016年11月19日 下午7:18:18 描 述：薛之谦挑战大赛--以前是挑战,后来需求改为助力
 */
@Controller
@RequestMapping(value = "/2016/xzq")
public class XzqChallengerController {

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request,
            HttpServletResponse response) {
        return "darexzq/index";
    }

    /**
     * 获取初始化信息
     */
    @RequestMapping(value = { "/init", "/login", "/signUp", "/getSignInfo" },
        method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Object> getInitInfo(HttpServletRequest request,
            Model model) {
        ResultBean<Object> result = new ResultBean<Object>(false, "谢谢参与，活动已结束",
                null);

        return result;
    }

    /**
     * 修改报名信息
     */
    @RequestMapping(value = { "/updateSignUpInfo", "/getInviteCode",
            "/respondInvite", "/help" }, method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> updateSignUpInfo(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束",
                null);

        return result;
    }

    /**
     * 获取积分前10名
     */
    @RequestMapping(value = "/getTop10", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, String>>> getTopN(
            HttpServletRequest request) {
        ResultBean<List<Map<String, String>>> result = new ResultBean<List<Map<String, String>>>(
                false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * history 助力记录
     */
    @RequestMapping(value = "/historyHelpRecords",
        method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> historyHelpRecords(
            HttpServletRequest request) {
        ResultBean<Map<String, Object>> result = new ResultBean<Map<String, Object>>(
                false, "谢谢参与，活动已结束", null);

        return result;
    }

    /***
     * 分享
     */
    @RequestMapping(value = "/share", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> share(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束",
                null);

        return result;
    }

    /***
     * 抽奖
     */
    @RequestMapping(value = { "/lottery", "/getLotteryPrizeList" },
        method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean letUsLottery(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;

    }

}
