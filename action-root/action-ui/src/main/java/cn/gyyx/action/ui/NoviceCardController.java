/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：H!Guo
 * 联系方式：guohai@gyyx.cn 
 * 创建时间： 2014年12月1日下午4:01:48
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 新手卡Controller
-------------------------------------------------------------------------*/
package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;

@Controller
@RequestMapping(value = "/novicecard")
public class NoviceCardController {

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("codeServer1", "s.gyyx.cn");
        model.addAttribute("codeServer2", "static.cn114.cn");
        return "noviceCard/novicecard155";
    }

    @RequestMapping("/wd9yearcard")
    public String wd9yearcard(Model model) {
        return "noviceCard/9yearnovicecard";
    }

    @RequestMapping("/nineyearcard")
    public String nineyearcard(Model model) {
        return "noviceCard/nineyearnovicecard";
    }

    /**
     * 
     * @日期：2014年12月10日
     * @Title: getServers
     * @Description: TODO
     * @param gameCategory
     * @param state
     * @param netType
     * @param batchNo
     * @param model
     * @return JSONObject
     */

    @RequestMapping("/getServers")
    @ResponseBody
    public ResultBean getServers() {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 提交领取新手卡奖励
     * 
     * @param code
     *            验证码
     * @param serverId
     *            服务器ID
     * @param gameCategory
     *            游戏
     * @param card
     *            卡号
     * @param batchNo
     *            批次
     * @param PassWord
     *            密码
     * @return
     */
    @RequestMapping("/receive")
    @ResponseBody
    public ResultBean<String> receive(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束",
                null);

        return result;
    }
}
