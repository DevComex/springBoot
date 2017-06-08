/*
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月9日下午1:58:24
 * 版本号：v1.0
 * 本类主要用途叙述：问道火力无限活动
 */

package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/wdfire")
public class WDFireController {

    @RequestMapping("/index")
    public String toIndex(Model model) {
        return "wdfire/index";
    }

    @RequestMapping("/contribute")
    public String toContribute(Model model) {
        return "wdfire/contribute";
    }

    @RequestMapping("/rule")
    public String toRule(Model model) {
        return "wdfire/rule";
    }

    @RequestMapping("/lottery")
    public String toLottery(Model model) {
        return "wdfire/lottery";
    }

    @RequestMapping("/manifestolist")
    public String toManifestolist(Model model) {
        return "wdfire/manifestolist";
    }

    /***
     * 获取投稿作品的信息
     * 
     * @param check
     * @return
     */
    @RequestMapping(value = "/getWrittingInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getWrittingInfo() {

        ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", null);

        return resultBean;
    }

    /**
     * 
     * @日期：2015年4月9日
     * @Title: share
     * @Description: TODO 分享接口
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = {"/share", "/sign", "/point", "/addComment"})
    @ResponseBody
    public ResultBean<String> share(HttpServletRequest request) {
      
        ResultBean<String> result = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
    
        return result;
    }

    /**
     * 
     * @日期：2015年4月14日
     * @Title: getPageComment
     * @Description: TODO 根据页数获取评论
     * @param request
     * @param writingCode
     * @param page
     * @return ResultBean<List<CommentBean>>
     */
    @RequestMapping(value = {"/getPageComment", "/getPrize"})
    @ResponseBody
    public ResultBean getPageComment(HttpServletRequest request) {
      
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
         
        return result;
    }

   
    /**
     * 上传一个作品
     * 
     * @param writingBean
     * @return
     */
    @RequestMapping(value = {"/addWriting", "/getWriting"}, method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addWriting(HttpServletResponse response) {
       
        ResultBean<String> resultBean = new ResultBean<String>(false, "谢谢参与，活动已结束", null);
      
        return resultBean;
    }

    /**
     * 添加地址
     * 
     * @param model
     * @param address
     * @return
     */
    @RequestMapping(value = {"/resetAddress", "/getSpiritual"})
    @ResponseBody
    public ResultBean<Integer> resetAddress( HttpServletRequest request) {
        
        ResultBean<Integer> resultBean = new ResultBean<Integer>(false, "谢谢参与，活动已结束", null);
      
        return resultBean;

    }

}