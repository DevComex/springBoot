/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-ui
  * @作者：guoyonggang
  * @联系方式：guoyonggang@gyyx.cn
  * @创建时间：2017年3月2日 上午10:11:01
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.ui.wechatcharge;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wechatcharge.WechatChargeBean;
import cn.gyyx.action.bll.wechatcharge.WechatChargeBLL;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.action.service.wechatcharge.WechatChargeService;

/**
 * <p>
 * 前台兑换操作
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "wechatcharge")
public class WechatChargeController {

    private static final Logger logger = LoggerFactory
            .getLogger(WechatChargeController.class);
    private WechatChargeService chargeService = new WechatChargeService();
    private WechatChargeBLL chargeBll=new WechatChargeBLL();

    /**
     * <p>
     * 首页
     * </p>
     *
     * @action guoyonggang 2017年3月2日 上午10:12:26 描述
     *
     * @return String
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        // 微信浏览器判断
        if(!isWeiXin(request)){
            return "wechatcharge/error";
        }
        return "wechatcharge/index";
    }

    /**
     * <p>
     * 兑换码兑换 实物：需要进一步填写信息 虚拟物品：直接兑换成功
     * </p>
     *
     * @action guoyonggang 2017年3月2日 下午1:03:00 描述
     *
     * @return ResultBean<ChargeResultBean>
     */
    @RequestMapping(value = "charge", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String,Object>> charge(WechatChargeBean chargeBean,HttpServletRequest request) {
        logger.info("WechatChargeController charge begin params：{}",
            chargeBean);
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        if(!isWeiXin(request)){
            result.setMessage("请使用微信浏览器打开");
            return result;
        }
        try {
            result = chargeService.charge(chargeBean);
            logger.info("WechatChargeController charge end result：{}",
            result);
            return result;
        } catch (Exception ex) {
            logger.error("WechatChargeController charge error exception：{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("服务器跑开一会，请稍后再试！");
            return result;
        }
    }

    /**
     * <p>
     * 实物存在确认兑换操作，需要填写收件地址
     * </p>
     *
     * @action guoyonggang 2017年3月2日 下午5:49:55 描述
     *
     * @param chargeBean
     * @return ResultBean<WechatChargeBean>
     */
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String,Object>> confirmcharge(
            WechatChargeBean chargeBean,HttpServletRequest request) {
        ResultBean<Map<String,Object>> result = new ResultBean<>();
        if(!isWeiXin(request)){
            result.setMessage("请使用微信浏览器打开");
            return result;
        }
        try {
            logger.info("WechatChargeController confirmcharge begin params：{}",
                chargeBean);
            result = chargeService.confirm(chargeBean);
            logger.info("WechatChargeController confirmcharge end result：{}",
            result);
            return result;
        } catch (Exception ex) {
            logger.error(
                "WechatChargeController confirmcharge error exception：{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("服务器跑开一会，请稍后再试！");
            return result;
        }
    }
    /**
      * <p>
      *    获取微信兑换记录
      * </p>
      *
      * @action
      *    guoyonggang 2017年3月3日 上午9:30:10 描述
      *
      * @param openId
      * @return ResultBean<List<WechatChargeBean>>
     */
    @RequestMapping(value="chargelist",method=RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<Map<String,Object>>> getChargeList(String openId,HttpServletRequest request){
        ResultBean<List<Map<String,Object>>> result = new ResultBean<>();
        if(!isWeiXin(request)){
            result.setMessage("请使用微信浏览器打开");
            return result;
        }
        try {
            logger.info("WechatChargeController getChargeList begin openId:{}",
                openId);
            List<Map<String,Object>> list = chargeBll.queryWechatChargeListByOpenId(openId);
            logger.info("WechatChargeController getChargeList end openId:{},result:{}",
                openId,list);
            result.setIsSuccess(true);
            result.setMessage("获取成功");
            result.setData(list);
        } catch (Exception ex) {
            logger.error(
                "WechatChargeController getChargeList error exception：{}",
                Throwables.getStackTraceAsString(ex));
            result.setIsSuccess(false);
            result.setMessage("服务器跑开一会，请稍后再试！");
        }
        return result;
    }

    /**
      * <p>
      *    微信浏览器判断
      * </p>
      *
      * @action
      *    guoyonggang 2017年3月3日 上午9:25:15 描述
      *
      * @param request
      * @return boolean
     */
    private boolean isWeiXin(HttpServletRequest request) {
        String ua = ((HttpServletRequest) request).getHeader("user-agent")
                .toLowerCase();
        if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 不是微信浏览器
            return false;
        } else {
            return true;
        }
    }
}
