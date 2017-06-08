/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年7月28日 
 * @版本号：v1.210
 * @本类主要用途描述：暑期问道缘排行抽奖 控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/allPKLottery")
public class WDAllPKLotteryController {
    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping("/lottery")
    public String index(Model model) {
        return "wdallpklottery/index";
    }

    /**
     * 
     * @Title: getServers
     * @Description: TODO 获取服务器信息
     * @param typeCode
     * @return ResultBean<List<ServerBean>>
     */
    @RequestMapping(value = { "/getServers", "/getAddress", "/resetAddress",
            "/setAddress" })
    @ResponseBody
    public ResultBean getServers() {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 
     * @Title: resetAddress
     * @Description: TODO 查询用户是否中奖
     * @param address
     * @param request
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = "/getPrize")
    @ResponseBody
    public ResultBean getPrize(HttpServletRequest request) {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * @Title: getLotteryTime
     * @Description: TODO 获取用户抽奖次数
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/getLotteryTime")
    @ResponseBody
    public ResultBean<Integer> getLotteryTime(HttpServletRequest request) {
        ResultBean<Integer> result = new ResultBean<Integer>(false,
                "谢谢参与，活动已结束", 0);
        return result;
    }

    /**
     * 得到当前活动所有中奖的信息
     * 
     * @return
     */
    @RequestMapping(value = { "/getLotteryInfo", "/getLotteryInfoByUser",
            "/getAllLotteryInfoByUser" })
    @ResponseBody
    public ResultBean getAllLotteryInfo() {
        ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", null);
        return resultBean;
    }
}
