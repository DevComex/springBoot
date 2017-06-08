/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/5 11:04
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wish11th;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.bll.wish11th.Wish11thUserBll;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.wish11th.Wish11thLightService;
import cn.gyyx.action.service.wish11th.Wish11thWishService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 许愿活动许愿相关接口
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wish11th")
public class Wish11thWishController extends BaseController {
    // 日志操作
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(Wish11thWishController.class);

    // 许愿相关服务操作类
    private Wish11thWishService wishService = new Wish11thWishService();
    // 用户相关业务逻辑层
    private Wish11thUserBll userBll = new Wish11thUserBll();
    // 亮灯层相关服务
    private Wish11thLightService lightService = new Wish11thLightService();

    /**
     * <p>
     * 获取指定层的祝愿信息(审核通过的祝愿,点击具体层的时候会弹出)
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:35:31 描述
     *
     * @param level
     * @return ResultBean<List<WishBean>>
     */
    @RequestMapping(value = "/getlevelwish", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getLevelWishes(
            @RequestParam(value = "level") int level) {
        logger.info(Constants.ACTIVITY_NAME + "access 获取指定层的祝愿信息");
        return wishService.getWishsBylevel(level, Constants.WISH11TH_VERIFYED);
    }


    /**
      * <p>
      *    用户许愿
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月8日 上午9:51:13 描述
      *
      * @param request
      * @param level  许愿层数
      * @param content  许愿内容
      * @return ResultBean<UserLotteryBean>
      */
    @RequestMapping(value = "/wish", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> getUserWish(HttpServletRequest request,
            @RequestParam(value = "level") int level,
            @RequestParam(value = "content") String content) {
        logger.info(Constants.ACTIVITY_NAME + "access 用户许愿");

        ResultBean<UserLotteryBean> result = new ResultBean<>();
        try {
            // 判断活动是否已结束
            ResultBean resultBean = this
                    .getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                result.setIsSuccess(false);
                result.setMessage("当前活动已过期！");
                result.setStateCode(-8);
                return result;
            }
            // 判断账户是否登录
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("登录失效，请重新登录");
                result.setStateCode(-6);
                return result;
            }

            // 判断当前层是否已被激活使用(上一层已点亮)防止恶意传level值
            ResultBean<List<Integer>> floorList = lightService.getLightFloor();
            if (!floorList.getData().contains(level)) {
                result.setIsSuccess(false);
                result.setMessage("当前层还未被激活！");
                result.setStateCode(-7);
                return result;
            }

            String ip = this.getIp(request);
            // 用户许愿
            return wishService.userWish(userInfo.getUserId(), level, ip,
                content);

        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "用户许愿失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("当前活动有点小问题,请稍后重试！");
            result.setStateCode(-100);
            return result;
        }
    }

    /**
     * <p>
     * 判断用户参与此次许愿需要消耗的积分
     * </p>
     *
     * @action tanjunkai 2017年4月6日 下午2:02:35 描述
     *
     * @param request
     * @param level
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/needtoconsume", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> needToConsumeScore(HttpServletRequest request,
            @RequestParam(value = "level") int level) {
        ResultBean<String> result = new ResultBean<String>();
        // 账户
        UserInfo userInfo = SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("登录失效，请重新登录");
            return result;
        }
        // 获取角色绑定信息
        Wish11thRoleBindBean userBindInfo = userBll
                .getUserByUserID(userInfo.getUserId());
        if (null == userBindInfo) {
            result.setIsSuccess(false);
            result.setMessage("您还未绑定角色信息");
            return result;
        }
        // 获取此次许愿需要消耗的积分
        int needToConsume = wishService
                .calculateConsumeScore(userInfo.getUserId(), level);
        int score = userBindInfo.getScore();
        int consume = userBindInfo.getConsumeScore();
        if (score < consume + needToConsume) {
            result.setIsSuccess(false);
            result.setMessage("您的剩余积分不足");
            return result;
        }
        result.setIsSuccess(true);
        result.setMessage(needToConsume+"");
        return result;
    }

    /***
     * 获取当前用户所有的许愿信息
     * 
     * @param request
     * @param level
     * @return
     */
    @RequestMapping(value = "/myallwishes", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<Wish11thWishBean>> getMyWishAll(
            HttpServletRequest request) {
        logger.info(Constants.ACTIVITY_NAME + "access 获取当前用户所有的许愿信息");
        try {
            // 账户
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (null == userInfo) {
                return new ResultBean<>(false, "登录失效，请重新登录", null);
            }
            // 用户所有许愿
            return wishService.getMyAllWish(userInfo.getUserId());

        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "获取当前用户所有的许愿信息异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "登录失效，请重新登录", null);
        }
    }

    /***
     * 展示所有的许愿信息(中奖信息展示)
     * 
     * @param request
     * @param level
     * @return
     */
    @RequestMapping(value = "/getalllottery")
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getlotteryAll(
            HttpServletRequest request, @RequestParam(value = "num") int num) {
        logger.info(Constants.ACTIVITY_NAME + "access  展示所有的许愿信息");
        try {
            // 用户许愿
            return wishService.findWishAll(num);
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "所有的许愿信息(中奖信息展示)异常：{}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<>(false, "人气太过火爆，正在排队中...", null);
        }
    }
}
