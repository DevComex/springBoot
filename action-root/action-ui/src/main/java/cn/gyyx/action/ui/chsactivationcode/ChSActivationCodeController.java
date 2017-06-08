/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：chsActivationCode
 * @作者：范佳琪
 * @联系方式：fanjiaqi@gyyx.cn
 * @创建时间： 2016年04月23日
 * @版本号：
 * @本类主要用途描述：创世2激活码控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.chsactivationcode;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/chsActivationCode")
public class ChSActivationCodeController {
    /**
     * @Title: stepOneIndex
     * @Description: 步骤1 抢激活码页面
     * @param @return
     * @return String
     */
    @RequestMapping("/stepOneIndex")
    public String stepOneIndex() {
        return "chsActivationCode/step1";
    }

    /**
     * @Title: stepTwoIndex
     * @Description: 步骤2 激活游戏页面
     * @param @return
     * @return String
     */
    @RequestMapping("/stepTwoIndex")
    public String stepTwoIndex(HttpServletRequest request, Model model) {
        return "chsActivationCode/step2";
    }

    /**
     * @Title: stepThreeIndex
     * @Description: 步骤3 激活状态页面
     * @param @return
     * @return String
     */
    @RequestMapping("/stepThreeIndex")
    public String stepThreeIndex(HttpServletRequest request, Model model) {
        return "chsActivationCode/step3";
    }

    /**
     * @Title: remainLotteryTime
     * @Description: 获取用户名，剩余抽奖次数信息
     * @param @return
     * @return String
     */
    @RequestMapping("/remainLotteryChance")
    @ResponseBody
    public ResultBean remainLotteryChance(HttpServletRequest request) {
        ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return resultBean;
    }

    /**
     * @Title: getRegion
     * @Description: 获取游戏大区
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping("/getRegion")
    private ResultBean getRegion() {
        ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return resultBean;
    }

    /**
     * @Title: stepOne
     * @Description: 步骤1 抽激活码
     * @param @param model
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/stepOne")
    @ResponseBody
    public ResultBean stepOne(HttpServletRequest request) {
        ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return resultBean;
    }

    /**
     * @Title: stepTwo
     * @Description: 步骤2 激活游戏
     * @param @param model
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/stepTwo")
    @ResponseBody
    public ResultBean<String> stepTwo(HttpServletRequest request) {
        ResultBean<String> resultBean = new ResultBean<String>(false,
                "谢谢参与，活动已结束", null);
        return resultBean;
    }

    /**
     * @Title: stepThree
     * @Description: 步骤3 查询激活状态
     * @param @param model
     * @param @return
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/stepThree")
    @ResponseBody
    public ResultBean<Integer> stepThree(HttpServletRequest request) {
        ResultBean<Integer> resultBean = new ResultBean<Integer>(false,
                "谢谢参与，活动已结束", 0);
        return resultBean;
    }

}
