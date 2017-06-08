/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 17:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wish11th;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.bll.wish11th.Wish11thUserBll;
import cn.gyyx.action.bll.wish11th.Wish11thWishBll;
import cn.gyyx.action.service.wish11th.Wish11thUserService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 许愿活动用户相关接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wish11th")
public class Wish11thUserController extends BaseController {

    // 日志操作
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(Wish11thUserController.class);

    // 用户相关服务操作类
    private Wish11thUserService userService = new Wish11thUserService();

    // 用户相关业务逻辑层
    private Wish11thWishBll wishBll = new Wish11thWishBll();

    /**
     * <p>
     * 获取角色
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午5:31:26 描述
     *
     * @param request
     * @param response
     * @param serverId
     * @param veCode
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/getroleinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> changeRoleInfo(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "serverid") int serverId,
            @RequestParam(value = "vecode") String veCode) {
        logger.info(Constants.ACTIVITY_NAME + "access 获取角色信息");
        ResultBean<String> resultBean = new ResultBean<>();
        try {
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (null == userInfo) {
                resultBean.setProperties(false, "登录失效，请重新登录", "logout");
            }
            // 验证验证码
            if (!new Captcha(request, response).equals(veCode)) {
                resultBean.setProperties(false, "很抱歉，您的验证码填写不正确", "");
            } else {
                return userService.validRoleInfo(userInfo, serverId);
            }
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "获取角色信息失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setProperties(false, "登录失效，请重新登录", "logout");
        }
        logger.info(Constants.ACTIVITY_NAME + "setRoleCookies output",
            resultBean);
        return resultBean;
    }

    /**
     * <p>
     * 角色绑定
     * </p>
     *
     * @action tanjunkai 2017年4月1日 下午5:44:57 描述
     *
     * @param request
     * @param roleName
     *            角色名
     * @param roleCode
     *            角色ID
     * @param serverName
     *            服务器名
     * @param serverId
     *            服务器ID
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/role/bind", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addAccountRole(HttpServletRequest request,
            @RequestParam(value = "rolename") String roleName,
            @RequestParam(value = "rolecode") String roleCode,
            @RequestParam(value = "servername") String serverName,
            @RequestParam(value = "serverid") int serverId) {
        logger.info(Constants.ACTIVITY_NAME + "access 增加账户角色绑定信息");
        try {
            // 账户
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (null == userInfo) {
                return new ResultBean<>(false, "登录失效，请重新登录", "logout");
            }
            // 判断活动是否结束
            ResultBean resultBean = this
                    .getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                return new ResultBean<>(false, resultBean.getMessage(),
                        resultBean.getMessage());
            }

            return userService.addAccountRole(userInfo, roleName, roleCode,
                serverName, serverId);

        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "绑定角色失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "登录失效，请重新登录", "logout");
        }
    }

    /**
     * <p>
     * 获取用户绑定信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:37:35 描述
     *
     * @param request
     * @return ResultBean<Wish11thRoleBindBean>
     */
    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Wish11thRoleBindBean> getUserBindInfo(
            HttpServletRequest request) {
        logger.info(Constants.ACTIVITY_NAME + "access 得到账户的角色信息");
        // 获取用户登录情况
        try {
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (null == userInfo) {
                return new ResultBean<>(false, "登录失效，请重新登录", null);
            }
            // 判断活动是否结束
            ResultBean resultBean = this
                    .getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                return new ResultBean<>(false, resultBean.getMessage(), null);
            }
            return userService.getUserinfoBeanByUserId(userInfo.getUserId());
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "获取用户绑定信息异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "登录失效，请重新登录", null);
        }
    }

    /**
     * <p>
     * 添加用户地址信息(中实物奖后添加地址信息)
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:41:40 描述
     *
     * @param request
     * @param addressBean
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addAddress(HttpServletRequest request,
            AddressBean addressBean) {
        logger.info("access 修改用户地址信息");
        // 获取用户登录情况
        try {
            // 判断活动是否结束
            ResultBean resultBean = this
                    .getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                return new ResultBean<>(false, resultBean.getMessage(),
                        resultBean.getMessage());
            }
            // 判断登录状态
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (null == userInfo) {
                return new ResultBean<>(false, "登录失效，请重新登录", null);
            }
            List<Wish11thWishBean> wishList = wishBll
                    .getMyWishAll(userInfo.getUserId());
            Wish11thWishBean wishBean = null;
            if (wishList != null && wishList.size() > 0) {
                for (Wish11thWishBean wish : wishList) {
                    if (wish.getPresenttype().equals("realPrize")) {
                        wishBean = wish;
                        break;
                    }
                }
            }
            if (null == wishBean) {
                return new ResultBean<>(false, "未检测到你的中奖信息", null);
            }
            AddressBean address = new AddressBean();
            address.setActionCode(Constants.Wish11thMapperActionCode
                    .getActionCode(wishBean.getLevel()));
            address.setUserAccount(userInfo.getAccount());
            address.setUserCode(userInfo.getUserId());
            address.setUserName(addressBean.getUserName());
            address.setUserAddress(addressBean.getUserAddress());
            address.setUserPhone(addressBean.getUserPhone());
            return userService.addAddress(userInfo.getUserId(), address);

        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "添加用户地址信息异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "登录失效，请重新登录", null);
        }
    }

    /**
     * <p>
     * 添加用户地址信息(中实物奖后添加地址信息)
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午10:41:40 描述
     *
     * @param request
     * @param addressBean
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/address/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<AddressBean> getAddress(HttpServletRequest request) {
        // 判断活动是否结束
        ResultBean resultBean = this.getActivityStatus(Constants.ACTIVITY_ID);
        if (!resultBean.getIsSuccess()) {
            return new ResultBean<>(false, resultBean.getMessage(), null);
        }
        // 判断登录状态
        UserInfo userInfo = SignedUser.getUserInfo(request);
        if (null == userInfo) {
            return new ResultBean<>(false, "登录失效，请重新登录", null);
        }
        return userService.getAddress(userInfo.getUserId());
    }
}
