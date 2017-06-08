/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日上午11:24:33
 * 版本号：v1.0
 * 本类主要用途叙述：我是预言帝控制层
 */

package cn.gyyx.action.ui;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.CommentBean;

@Controller
@RequestMapping(value = "/predicable")
public class WDPredicableController {

    /**
     * 作品展示界面
     * 
     * @return
     */
    @RequestMapping(value = "/writingInfo")
    public String toWriting(Model model) {
        return "predicable/writingInfo";
    }

    /**
     * 规则页
     * 
     * @return
     */
    @RequestMapping(value = "/rule")
    public String toRule(Model model) {
        return "predicable/rule";
    }

    /**
     * 抽奖页
     * 
     * @return
     */
    @RequestMapping(value = "/lottery")
    public String toLottery(Model model, HttpServletRequest request) {

        return "predicable/lottery";
    }

    /**
     * 参赛页
     * 
     * @return
     */
    @RequestMapping(value = "/partake")
    public String toPartake(Model model) {
        return "predicable/partake";
    }

    /**
     * 增加用户的预测的接口
     * 
     * @param request
     * @param writingBean
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = {"/addPredicableInfo", "/addAlternative", "/vote", "/addComment"})
    @ResponseBody
    public ResultBean<Integer> addPredicableInfo(HttpServletRequest request)
            throws UnsupportedEncodingException {
       
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    
    /**
     * 返回最新作品列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getWritingByNew", "/getWritingByVote", "/getWritingByFilter", "/getWritingByFilterForPage"})
    @ResponseBody
    public ResultBean getWritingByNew(HttpServletRequest request) {

        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }


    /**
     * 获取用户的预测
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getWritingBeansByUserCode", "/getWritingBeansByAlternative"})
    @ResponseBody
    public ResultBean getWritingBeansByUserCode(HttpServletRequest request) {
        
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    

    /**
     * 得到作品的详细属性信息
     * 
     * @param writingCode
     * @return
     */
    @RequestMapping(value = "/getWritingProperty")
    @ResponseBody
    public ResultBean getWritingProperty() {
    
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 得到所有评论的信息
     * 
     * @return
     */
    @RequestMapping(value = "/getAllComment")
    @ResponseBody
    public ResultBean<List<CommentBean>> getAllComment() {
        
        return new ResultBean<>(false, "谢谢参与，活动已结束", null);
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: getServers
     * @Description: TODO 获取服务器信息
     * @param typeCode
     * @return ResultBean<List<ServerBean>>
     */
    @RequestMapping(value = "/getServers")
    @ResponseBody
    public ResultBean getServers() {

        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
    }

    /**
     * 抽奖
     * 
     * @param request
     * @param serverName
     * @param serverCode
     * @return
     */
    @RequestMapping(value = "/getPredicableLottery")
    @ResponseBody
    public ResultBean getPredicableLottery() {
        ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", null);

        return resultBean;
    }

    /**
     * 
     * @日期：2015年3月20日
     * @Title: getAddress
     * @Description: TODO 传递用户地址信息
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = "/getAddress")
    @ResponseBody
    public ResultBean getAddress(HttpServletRequest request) {
        ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);

        return result;
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
    @RequestMapping(value = {"/resetAddress", "/setAddress"})
    @ResponseBody
    public ResultBean<Integer> resetAddress(HttpServletRequest request) {
        ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", 0);

        return result;
    }


    /**
     * 获取用户中奖信息的控制器
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getLotteryInfoByUser", "/getAllLotteryInfoByUser"})
    @ResponseBody
    public ResultBean getRealLotteryInfoByUser(HttpServletRequest request) {
        ResultBean resultBean = new ResultBean(false, "谢谢参与，活动已结束", 0);

        return resultBean;
    }


    @RequestMapping(value = "/exportSearchSum")
    @ResponseBody
    public ResultBean<String> exportSearchSum(HttpServletRequest request) throws UnsupportedEncodingException {
        ResultBean<String> resultBean = new ResultBean<String>(false, "谢谢参与，活动已结束", null);

        return resultBean;
    }

    @RequestMapping(value = "/excel")
    public String excel(Model model) {
        
        return "predicable/excel";
    }

}
