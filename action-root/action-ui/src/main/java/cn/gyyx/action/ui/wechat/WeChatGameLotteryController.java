package cn.gyyx.action.ui.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping(value = "/wechatLottery")
public class WeChatGameLotteryController {
    /**
     * 游戏
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/zjkpGame")
    public String index() {
        return "wechat/gameIndex";
    }

    /**
     * 抽奖
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/zjkpLottery")
    public String zjkpLottery() {
        return "wechat/lotteryIndex";
    }

    /**
     * 分享
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/zjkpShare")
    public String zjkpShare() {
        return "wechat/lotteryShare";
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: resetAddress
     * @Description: TODO 重置或者插入地址信息
     * @param address
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/resetAddress", method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String resetAddress() {
        ResultBean<Integer> result = new ResultBean<Integer>(false,
                "谢谢参与，活动已结束", 0);
        return DataFormat.objToJson(result);
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: resetAddress
     * @Description: TODO 查询用户是否中奖
     * @param address
     * @param request
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = "/getPrize", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPrize() {
        ResultBean result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return DataFormat.objToJson(result);
    }

    /**
     * 获取用户中奖信息的控制器
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getLotteryInfoByUser",
        method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAllLotteryInfoByUser() {
        ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return DataFormat.objToJson(resultBean);
    }

    /**
     * @Title: getLotteryTime
     * @Description: TODO 获取用户抽奖次数
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/getLotteryTime", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getLotteryTime() {
        ResultBean<Integer> result = new ResultBean<Integer>(false,
                "谢谢参与，活动已结束", 0);
        return DataFormat.objToJson(result);
    }
}
