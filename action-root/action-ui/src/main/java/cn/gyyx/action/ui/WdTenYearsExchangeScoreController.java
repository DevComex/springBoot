/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月8日下午3:07:32
 * 版本号：v1.0
 * 本类主要用途叙述：问道十周年积分兑换活动
 */

package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.exchangescore.PrizeExchangeLogBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;


@Controller
@RequestMapping("/tenyearsexchangescore")
public class WdTenYearsExchangeScoreController {


    /***
     * 页面跳转
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/index ")
    public String toIndex() {

        return "wdexchangescore/index";
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: getServers 获取服务器信息
     * @param typeCode
     * @return ResultBean<List<ServerBean>>
     */
    @RequestMapping(value = "/getServers")
    @ResponseBody
    public ResultBean getServers() {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /***
     * 获取角色信息
     * 
     * @param request
     * @param response
     * @param serverId
     * @param veCode
     * @return
     */
    @RequestMapping(value = "/changeRoleInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> changeRoleInfo(HttpServletRequest request) {

        ResultBean<String> resultBean = new ResultBean<>(false, "谢谢参与，活动已结束",
                null);

        return resultBean;
    }

    /****
     * 判断用户是否绑定角色
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/isBindRole", "/addRoleAccount" })
    @ResponseBody
    public ResultBean isBindRole(HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);

    }

    /****
     * 得到兑换奖品的信息
     * 
     * @return
     */
    @RequestMapping(value = "/getExchangePrizeInfo")
    @ResponseBody
    public ResultBean getExchangePrizeInfo(HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: getAddress 传递用户地址信息
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = "/getAddress")
    @ResponseBody
    public ResultBean getAddress(HttpServletRequest request) {

        ResultBean result = new ResultBean<>(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /***
     * 获取积分兑换奖品
     * 
     * @return
     */
    @RequestMapping(value = "/getExchangePrize", method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<String> getExchangePrize(HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: resetAddress
     * @重置或者插入地址信息
     * @param address
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/resetAddress", method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<Integer> resetAddress(HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /***
     * 获取所有中奖信息
     * 
     * @return
     */
    @RequestMapping(value = "/getAllPrizeExchangeLogBeans")
    @ResponseBody
    public ResultBean<List<PrizeExchangeLogBean>> getAllPrizeExchangeLogBeans() {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /***
     * 获取所有中奖信息
     * 
     * @return
     */
    @RequestMapping(value = { "/getPrizeExchangeLogBeansByUserCode",
            "/getScoreBeans" })
    @ResponseBody
    public ResultBean getPrizeExchangeLogBeansByUserCode(
            HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /***
     * 积分兑换抽奖
     * 
     * @param request
     * @param serverName
     * @param serverCode
     * @return
     */
    @RequestMapping(value = "/exchangeScoreLottery",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<UserLotteryBean> exchangeScoreLottery(
            HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /***
     * 看是否需要验证码
     * 
     * @return
     */
    @RequestMapping(value = "/isNeedIdentifying",
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<String> isNeedIdentifying() {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }
	
}
