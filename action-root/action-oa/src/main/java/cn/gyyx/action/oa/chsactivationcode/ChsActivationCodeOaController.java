/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：chsActivationCode
 * @作者：范佳琪
 * @联系方式：fanjiaqi@gyyx.cn
 * @创建时间： 2016年04月23日
 * @版本号：
 * @本类主要用途描述：创世2激活码oa后台控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.chsactivationcode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/chsActivationCodeOa")
public class ChsActivationCodeOaController {

    /**
     * 查询用page
     * 
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {

        return "chsactivationcode/search";
    }

    /**
     * @Title: index
     * @Description: oa后台首页
     * @param @return
     * @return String
     */
    @RequestMapping("/index2")
    public String index2(Model model) {
        return "chsactivationcode/index";
    }

    @RequestMapping("/timeLimit")
    public String updateTimeLimit(String start, String end, int count,
            int code, Model model) {
        return "redirect:index2";
    }

    @ResponseBody
    @RequestMapping("/getActivityCodeMesCode")
    public ResultBean getActivityMarketDetailedToCode() {
        ResultBean result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
        return result;
    }

    /**
     * 默认查询市场详细
     * 
     * @param model
     * @param nowDate
     * @return
     */
    @RequestMapping("markedDetailed")
    public String getMarkedDetailed(Model model, String nowDate) {
        return "chsactivationcode/showMes";
    }

    /**
     * 通过渠道获获取激活个数
     * 
     * @param model
     * @param channel
     * @return
     */
    @RequestMapping("/activitionCount")
    @ResponseBody
    public ResultBean<String> searchActivitionCount(int channel, String time) {
        return new ResultBean<String>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 查询产品发出去个数
     * 
     * @return
     */
    @RequestMapping("/sendCount")
    @ResponseBody
    public ResultBean<String> searchSendCount(String time) {
        return new ResultBean<String>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 发放未激活
     * 
     * @return
     */
    @RequestMapping("/sendNotCount")
    @ResponseBody
    public ResultBean<String> searchSendNotCount(String time) {
        return new ResultBean<String>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 查询产品真正激活数量
     * 
     * @param model
     * @return
     */
    @RequestMapping("/realActi")
    @ResponseBody
    public ResultBean<String> searchRealActivition(String time) {
        return new ResultBean<String>(false, "谢谢参与，活动已结束", null);
    }

    @ResponseBody
    @RequestMapping(value = { "/updateHdTime", "/update", "/searchList" })
    public ResultBean<String> updateHdTime() {
        return new ResultBean<String>(false, "谢谢参与，活动已结束", null);
        
    }

}
