package cn.gyyx.action.ui.christmasLottery;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/wxchristmas")
public class ChristmasLotteryController {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(ChristmasLotteryController.class);

    /***
     * 主页
     *
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request,
            HttpServletResponse response) {
        try {// 是否微信浏览器
            response.setContentType("text/html;charset=UTF-8");
            if (!checkIsWeiXin(request, response)) {
                return "wdxlsgpresent/wxerror";
            }
        } catch (Exception e) {
            logger.error("微信圣诞大转盘抽奖功能[go默认页Controller]异常,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));
        }
        return "wxchristmas/index";
    }

    @RequestMapping(value = "/loginpage")
    public String sharepage(HttpServletRequest request,
            HttpServletResponse response) {

        try {// 是否微信浏览器
            response.setContentType("text/html;charset=UTF-8");
            if (!checkIsWeiXin(request, response)) {
                return "wdxlsgpresent/wxerror";
            }
        } catch (Exception e) {
            logger.error("微信圣诞大转盘抽奖功能[go分享页sharepage]异常,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));

        }

        return "wxchristmas/loginpage";
    }

    @RequestMapping(value = "/bindpage")
    public String bindpage(HttpServletRequest request,
            HttpServletResponse response) {

        try {// 是否微信浏览器
            response.setContentType("text/html;charset=UTF-8");
            if (!checkIsWeiXin(request, response)) {
                return "wdxlsgpresent/wxerror";
            }
        } catch (Exception e) {
            logger.error("微信圣诞大转盘抽奖功能[go分享页sharepage]异常,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));

        }

        return "wxchristmas/bindpage";
    }

    /**
     *
     * @Title: getServers @Description: TODO 获取服务器信息 @param @param
     *         typeCode @param @return @return ResultBean<List
     *         <ServerBean>> @throws
     */
    @RequestMapping(value = "/getServers", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<List<ServerBean>> getServers(
            @RequestParam(value = "netType") String typeCode) {
        logger.info("typeCode", typeCode);
        ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
                true, "查询服务器失败", null);
        result.setProperties(false, "活动已结束，谢谢参与", null);
        logger.info("result", result);
        return result;
    }

    /**
     * @Title: checkServer @Description: 查询当前区服是否激活 @param serverId 服务器ID
     *
     * @param request
     *            请求 @return ResultBean<ServerBean> @throws
     */
    @RequestMapping(value = "/checkServer")
    @ResponseBody
    public ResultBean<Object> checkServer(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "serverId") int serverId) {
        ResultBean<Object> result = new ResultBean<>();
        logger.info("getservers  ", "serverId" + serverId);
        UserInfo userInfo = SignedUser.getUserInfo(request);
        if (userInfo == null) {
            result.setIsSuccess(false);
            result.setMessage("请先登录");
            return result;
        }
        result.setProperties(false, "活动已结束，谢谢参与", null);
        return result;
    }

    /**
     *
     * @throws ParseException
     * @Title: addRole @Description: TODO 绑定角色 @param @param
     *         wdAccountInfoBean @param @param request @param @return @return
     *         ResultBean<WdAccountInfoBean> @throws
     */
    @RequestMapping(value = "/bind", method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<WdPkRoleBindBean> addRole(
            @ModelAttribute WdPkRoleBindBean wdPkRoleBindBean,
            HttpServletRequest request, BindingResult br,
            @RequestParam("OpenId") String OpenId) throws ParseException {
        logger.info("wdPkRoleBindBean:" + wdPkRoleBindBean);
        ResultBean<WdPkRoleBindBean> result = new ResultBean<WdPkRoleBindBean>(
                false, "", null);
        if (br.hasErrors()) {
            result.setMessage(br.getFieldError().getDefaultMessage());
            return result;
        }
        UserInfo userInfo = SignedUser.getUserInfo(request);
        if (userInfo == null) {
            result.setIsSuccess(false);
            result.setMessage("请您先登录");
            return result;
        }
        result.setIsSuccess(false);
        result.setMessage("活动已结束，谢谢参与");
        return result;

    }

    /**
     * @throws ParseException
     *
     * @Title: getWdAccountInfoBeanByAccountName @Description:
     *         TODO判断是否绑定过角色，并获取绑定角色信息 @param @param accountName @param
     *         request @return ResultBean <WdAccountInfoBean> @throws
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HashMap<Object, Object> getWdAccountInfoBeanByAccountName(
            HttpServletRequest request, @RequestParam("OpenId") String OpenId)
            throws ParseException {
        ResultBean<WdPkRoleBindBean> result = new ResultBean<WdPkRoleBindBean>(
                false, "", null);
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        logger.info("result:" + result);
        // 判断是否登录过。isLogin
        ResultBean<WdPkRoleBindBean> login = new ResultBean<WdPkRoleBindBean>();
        login.setIsSuccess(false);
        login.setMessage("活动已结束，谢谢参与");
        map.put("isLogin", false);
        // 判断是否绑定过。isBind
        map.put("isBind", false);
        map.put("BindInfo", login.getData());
        return map;

    }

    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> userlogin(HttpServletRequest request,
            @RequestParam("OpenId") String OpenId) {
        ResultBean<Integer> result = new ResultBean<Integer>();
        result.setIsSuccess(false);
        result.setMessage("活动已结束，谢谢参与");
        return result;

    }

    @RequestMapping(value = "/lottery")
    @ResponseBody
    public ResultBean<UserLotteryBean> lottery(HttpServletRequest request,
            @RequestParam("OpenId") String OpenId) {
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>(
                false, "活动已结束，谢谢参与", null);
        return result;

    }

    /**
     * 获取用户中奖信息的控制器
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllLotteryInfoByUser")
    @ResponseBody
    public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(
            HttpServletRequest request, @RequestParam("OpenId") String OpenId) {
        ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>();
        resultBean.setProperties(false, "成功", null);
        return resultBean;
    }

    /**
     * 获取用户中奖信息的控制器
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllLotteryInfo")
    @ResponseBody
    public ResultBean<List<UserInfoBean>> getAllLotteryInfo(
            HttpServletRequest request) {
        ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>(false,
                "未知错误，查询失败", null);
        resultBean.setProperties(false, "活动已结束", null);
        return resultBean;
    }

    /**
     *
     * @日期：2016年11月14日 @Title: resetAddress
     * @Description: 插入地址信息
     * @param address
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/setAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address,
            HttpServletResponse response, HttpServletRequest request,
            String OpenId) {
        ResultBean<Integer> result = new ResultBean<>(false, "活动已结束，谢谢参与", 0);
        return result;
    }

    // 页面跳转
    private boolean checkIsWeiXin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String ua = request.getHeader("user-agent").toLowerCase();

            if (ua.indexOf("micromessenger") == -1
                    || ua.indexOf("windows") > -1) {// 不是微信浏览器
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(
                    "<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'></head><body><div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 1.2rem;'>请在微信客户端打开链接<h4></div></div></body></html>");
                response.getWriter().close();
                return false;
            }
        } catch (Exception e) {
            logger.error("检查是否是微信浏览器失败,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));
        }
        return true;
    }
}
