package cn.gyyx.action.ui.wdallpklottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.wdninestory.ServerBean;

@Controller
@RequestMapping(value = "/wdallpk2016")
public class WdAllPkLotteryController {

    /***
     * 主页
     * 
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {

        return "wdallpklottery2016/index";
    }

    /**
     * 
     * @Title: getServers @Description: TODO 获取服务器信息 @param @param typeCode @param @return @return
     *         ResultBean<List <ServerBean>> @throws
     */
    @RequestMapping(value = "/getServers", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<List<ServerBean>> getServers(@RequestParam(
        value = "netType") String typeCode) {
        ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
                false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * @Title: checkServer @Description: 查询当前区服是否激活 @param serverId 服务器ID
     * 
     * @param request
     *            请求 @return ResultBean<ServerBean> @throws
     */
    @RequestMapping(value = { "/checkServer", "/lottery", "/getAddress",
            "/setAddress" })
    @ResponseBody
    public ResultBean<Object> checkServer(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "serverId") int serverId) {
        ResultBean<Object> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;

    }

    /**
     * 获取用户中奖信息的控制器
     * 
     * @param request
     * @return
     */
    @RequestMapping(
        value = { "/getAllLotteryInfoByUser", "/getAllLotteryInfo" })
    @ResponseBody
    public ResultBean<List<ActionLotteryLogPO>> getAllLotteryInfoByUser(
            HttpServletRequest request) {
        ResultBean<List<ActionLotteryLogPO>> resultBean = new ResultBean<>(
                false, "谢谢参与，活动已结束", null);

        return resultBean;
    }

}
