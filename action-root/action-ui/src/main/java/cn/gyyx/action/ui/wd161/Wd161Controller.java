package cn.gyyx.action.ui.wd161;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;

/**
 * @Description: 问道1.61官网活动
 * @author lizhihai
 * @date 2016年11月17日
 */
@Controller
@RequestMapping(value = "/wd161")
public class Wd161Controller {
    /***
     * 主页
     */
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        return "wd161/index";
    }

    /***
     * 主页
     */
    @RequestMapping("/upload")
    public String upload(Model model, HttpServletRequest request) {
        return "wd161/upload";
    }

    /***
     * 抽奖页
     */
    @RequestMapping("/detail")
    public String lotteryPage(Model model, HttpServletRequest request) {
        return "wd161/detail";
    }

    /**
     * 获取用户初始化信息
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String, String>> getUserInfo(
            HttpServletRequest request) {
        ResultBean<Map<String, String>> result = new ResultBean<>(false,
                "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 获取服务器信息
     */
    @RequestMapping(value = "/getServers", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<List<ServerBean>> getServers(@RequestParam(
        value = "netType") String typeCode) {
        ResultBean<List<ServerBean>> result = new ResultBean<>(false,
                "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 保存上传的图片地址信息
     */
    @RequestMapping(value = "/savePicUrl", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> savePicUrl(HttpServletRequest request,
            String picUrl) {
        ResultBean<String> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 重新上传接口
     */

    @RequestMapping(value = "/reloadPicUrl", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> reloadPicUrl(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 重置或者插入地址信息
     */
    @RequestMapping(value = "/resetAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> resetAddress(
            @ModelAttribute AddressBean address, HttpServletRequest request) {
        ResultBean<Integer> result = new ResultBean<>(false, "谢谢参与，活动已结束", 0);
        return result;
    }

    /**
     * 获取用户地址信息
     */
    @RequestMapping(value = "/getAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getAddress(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String, String>> getRoleList(
            HttpServletRequest request) {
        ResultBean<Map<String, String>> resultBean = new ResultBean<>(false,
                "谢谢参与，活动已结束", null);
        return resultBean;
    }

    /**
     * 绑定角色
     */
    @RequestMapping(value = "/addAccountRole", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addAccountRole(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;

    }

    /**
     * 获取评论列表
     */
    @RequestMapping(value = "/getCommentsList", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<List<Map<String, String>>> getCommentsList(
            HttpServletRequest request, HttpServletResponse response) {
        ResultBean<List<Map<String, String>>> result = new ResultBean<>(false,
                "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 发布评论
     */
    @RequestMapping(value = "/publishComments", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> publishComments(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 投票
     */
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> vote(HttpServletRequest request) {

        ResultBean<String> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 查看投票情况 ,剩余投票数
     */

    @RequestMapping(value = { "/getVoteList", "/getWorkList", "/getTopTen" },
        method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getVoteList(HttpServletRequest request,
            HttpServletResponse response) {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 抽奖
     */
    @RequestMapping(value = { "/lottery", "/getLotteryInfoByUser",
            "/getAllLotteryInfo" }, method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> lottery(HttpServletRequest request,
            HttpServletResponse response) {

        ResultBean<UserLotteryBean> resultBean = new ResultBean<>(false,
                "谢谢参与，活动已结束", null);
        return resultBean;
    }

}
