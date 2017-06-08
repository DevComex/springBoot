package cn.gyyx.action.ui.xwbValentineDay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;

@Controller
@RequestMapping(value = "/xwbvalentineday")
public class XWBValentineDayController {
    private static final int ACTIONCODE = 439;

    /***
     * 主页
     * 
     * @param model
     * @return index
     */
    @RequestMapping("/index")
    public String index(Model model) {
        return "xwbvalentineday/index";
    }

    /***
     * 绑定区组
     * 
     * @param WdPkRoleBindBean
     * 
     * @return ResultBean<WdPkRoleBindBean>
     */
    @RequestMapping("/bind")
    @ResponseBody
    public ResultBean<WdPkRoleBindBean> bind(HttpServletRequest request,
            @Validated WdPkRoleBindBean wdPkRoleBindBean, BindingResult br) {
        return new ResultBean<WdPkRoleBindBean>(false, "活动已结束！", null);
    }

    /***
     * 检查是否绑定区组
     * 
     * @return ResultBean<WdPkRoleBindBean>
     */
    @RequestMapping("/checkbind")
    @ResponseBody
    public ResultBean<WdPkRoleBindBean> checkBind(HttpServletRequest request) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

    /***
     * 浇水(可看做签到逻辑，一天浇水一次)
     * 
     * @return ResultBean<HashMap<String, String>>
     */
    @RequestMapping("/watering")
    @ResponseBody
    public ResultBean<HashMap<String, String>> watering(
            HttpServletRequest request) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

    /***
     * 查询累计浇水次数
     * 
     * @return ResultBean<Integer>
     */
    @RequestMapping("/watertimes")
    @ResponseBody
    public ResultBean<Integer> getWaterTimes(HttpServletRequest request) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

    /***
     * 抽奖
     * 
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping("/lottery")
    @ResponseBody
    public ResultBean<UserLotteryBean> lottery(HttpServletRequest request) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

    /***
     * 获取抽奖机会
     * 
     * @return ResultBean<Integer>
     */
    @RequestMapping("/getlotterytimes")
    @ResponseBody
    public ResultBean<Integer> getLotteryTimes(HttpServletRequest request) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

    /***
     * 领取签到奖励
     * 
     * @param model
     * @return
     */
    @RequestMapping("/getsignprize")
    @ResponseBody
    public ResultBean<UserLotteryBean> getSignPrize(HttpServletRequest request,
            int code) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

    @RequestMapping("/checkPrizeStatus")
    @ResponseBody
    public ResultBean<Map<String, Integer>> checkPrizeStatus(
            HttpServletRequest request) {
    	return new ResultBean<>(false, "活动已结束！", null);
    }

}
