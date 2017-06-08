package cn.gyyx.action.ui;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.enums.UserAgentEnums;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
  * <p>
  *   将活动中共用的方法提取到本类中，本类中有如下方法：
  * </p>
  * <ol>
  *    <li>获取操作系统类型</li>
  *    <li>获取用户IP</li>
  *    <li>判断验证码是否正确</li>
  * </ol>  
  * @author bozhencheng
  * @since 0.0.1
  */
public class BaseController {

    protected static Logger logger = GYYXLoggerFactory
            .getLogger(BaseController.class);

    /**
     * <p>
     * 根据请求获取手机操作系统
     * </p>
     *
     * @action bozhencheng 2017年3月4日 下午2:10:46 添加注释
     *
     * @param request
     * @return String
     */
    protected String getUserAgent(HttpServletRequest request) {
        if (null == request)
            return null;

        String agent = request.getHeader("user-agent");

        // iphone
        if (Pattern.compile("(iPhone|iPh|iOS|Mac)+").matcher(agent).find()) {
            return UserAgentEnums.Iphone.toString();
        }

        // ipad
        if (Pattern.compile("(iPad)+").matcher(agent).find()) {
            return UserAgentEnums.Iphone.toString();
        }

        // android
        if (Pattern.compile("(Android|Adr|Linux)+").matcher(agent).find()) {
            return UserAgentEnums.Android.toString();
        }

        return UserAgentEnums.Pc.toString();
    }

    /**
     * <p>
     * 获得IP地址
     * </p>
     *
     * @action bozhencheng 2017年3月4日 下午2:11:13 添加注释
     *
     * @param request
     * @return String
     */
    protected String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * <p>
     * 判断验证码是否相等
     * </p>
     *
     * @action add 2017年3月4日 下午2:11:45 添加验证码验证
     *
     * @param inputCaptcha
     * @param request
     * @param response
     * @return boolean
     */
    public boolean validateCapatcha(String inputCaptcha,
            HttpServletRequest request, HttpServletResponse response) {
        return new Captcha(request, response).equals(inputCaptcha);
    }

    
    /**
      * <p>
      *    获取活动当前的状态
      * </p>
      *
      * @action
      *    bozhencheng add 2017年3月4日 下午6:16:34 描述
      *
      * @param activityId
      * @return ResultBean<Object>
      */
    public ResultBean<Object> getActivityStatus(int activityId) {
        
        ActivityStatus status = new DefaultHdConfigBLL().getStatus(activityId);
        ResultBean<Object> result = new ResultBean<>();
        
        if (status == ActivityStatus.IS_NORMAL) {
            result.setIsSuccess(true);
        } else if (status == ActivityStatus.HAS_NOT_START) {
            result.setProperties(false, "活动尚未开始，敬请期待！", null);
        } else if (status == ActivityStatus.IS_INVALID) {
            result.setProperties(false, "此活动已下线", null);
        } else if (status == ActivityStatus.IS_OVER) {
            result.setProperties(false, "活动已经结束", null);
        }
        return result;
    }
}
