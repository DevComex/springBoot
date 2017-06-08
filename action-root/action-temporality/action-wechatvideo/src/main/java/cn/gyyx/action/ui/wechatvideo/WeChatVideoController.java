/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/14 9:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wechatvideo;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.base.Throwables;

import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindViewBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoUpLoadLogViewBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoVoteLogBean;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoVoteLogViewBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wechatvideo.WeChatVideoRoleBindBll;
import cn.gyyx.action.bll.wechatvideo.WeChatVideoUpLoadLogBll;
import cn.gyyx.action.bll.wechatvideo.WeChatVideoVoteLogBll;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.lottery.ILotteryPrizesService;
import cn.gyyx.action.service.lottery.impl.LotteryPrizesService;
import cn.gyyx.action.service.wechatvideo.WeChatVideoPrizeService;
import cn.gyyx.action.service.wechatvideo.WechatVideoLotteryPrizesService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.service.WDGameAgent;
import net.sf.json.JSONObject;

/**
 * <p>
 * 问道周年视频祝福活动ui层
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
/**
 * <p>
 * WeChatVideoController描述
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wechatvideo")
public class WeChatVideoController extends BaseController {

    private WeChatVideoUpLoadLogBll weChatVideoUpLoadLogBll = new WeChatVideoUpLoadLogBll();
    private WeChatVideoRoleBindBll weChatVideoRoleBindBll = new WeChatVideoRoleBindBll();
    private WeChatVideoVoteLogBll weChatVideoVoteLogBll = new WeChatVideoVoteLogBll();
    private WDGameAgent gameAgent = new WDGameAgent();
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
            .getMemcache();
    private WechatVideoLotteryPrizesService lotteryService = new WechatVideoLotteryPrizesService();
    private ILotteryPrizesService lotteryPrizesService = new LotteryPrizesService();
    // 奖品相关服务
    private WeChatVideoPrizeService prizeService = new WeChatVideoPrizeService();

    /**
     * <p>
     * 搜索审核通过的视频(通过游戏账号、角色名、视频名称)
     * </p>
     *
     * @author tanjunkai
     *
     * @param searchPar
     *            账号、角色名、视频名称
     * @return ResultBean
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean videoSearch(@RequestParam("searchpar") String searchPar) {
        ResultBean resultBean = new ResultBean<>();
        logger.info("视频首页搜索开始：参数：" + searchPar);
        try {
            if (searchPar.equals("")) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("请输入搜索关键字");
                return resultBean;
            }
            List<WeChatVideoUpLoadLogBean> videoList = weChatVideoUpLoadLogBll
                    .videoSearch(searchPar);
            logger.info("视频首页搜索结束：结果：" + videoList);
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取成功");
            resultBean.setData(videoList);
        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[视频搜索]{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 首页根据页码获取视频列表
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午5:01:33 描述
     *
     * @param index
     * @param pageCount
     * @return ResultBean
     */
    @RequestMapping(value = "/videolist", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean getVideoByIndex(@RequestParam("index") int index,
            @RequestParam("pageCount") int pageCount) {
        ResultBean resultBean = new ResultBean<>();
        try {
            if (index < 1)
                index = 1;
            if (pageCount < 0)
                pageCount = 4;
            List<WeChatVideoUpLoadLogBean> list = weChatVideoUpLoadLogBll
                    .getVideoByIndex(index, pageCount);
            resultBean.setIsSuccess(true);
            resultBean.setData(list);
        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[获取视频列表]{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 获取视频详情
     * </p>
     *
     * @author tanjunkai
     *
     * @param videoId
     *            视频ID
     * @return ResultBean 返回结果
     */
    @RequestMapping(value = "/getvideobyid", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean getVideoById(@RequestParam("videoid") Integer videoId) {
        logger.info(String.format("[%s] 获取视频详情开始,视频ID:[%d]",
            Constants.ACTIVITY_NAME, videoId));
        ResultBean resultBean = new ResultBean<>();
        try {
            if (videoId < 0) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("参数错误");

                logger.info(String.format("[%s] 获取视频详情结果:[%s],视频ID:[%d]",
                    Constants.ACTIVITY_NAME, resultBean.getMessage(), videoId));

                return resultBean;
            }
            WeChatVideoUpLoadLogBean videoInfo = weChatVideoUpLoadLogBll
                    .selectByPrimaryKey(videoId);
            if (videoInfo != null && videoInfo
                    .getVideoStatus() == Constants.WECHATVIDEO_VERIFYPENDING) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("视频正在审核中...");

                logger.info(String.format("[%s] 获取视频详情结果:[%s],视频ID:[%d]",
                    Constants.ACTIVITY_NAME, resultBean.getMessage(), videoId));

                return resultBean;
            }
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取成功");
            resultBean.setData(videoInfo);
        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[获取视频详情]{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        logger.info(String.format("[%s] 获取视频详情结果:[%s],视频ID:[%d]",
            Constants.ACTIVITY_NAME, resultBean.getMessage(), videoId));
        return resultBean;
    }

    /**
     * <p>
     * 视频点赞
     * </p>
     *
     * @author tanjunkai
     *
     * @param request
     * @param voteView
     *            点赞视图
     * @return ResultBean
     */
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean videoVote(HttpServletRequest request,
            WeChatVideoVoteLogViewBean voteView) {
        logger.info(String.format(
            "[%s] 视频点赞开始,openId:[%s],videoId:[%d],voteSource:[%s]",
            Constants.ACTIVITY_NAME, voteView.getOpenId(),
            voteView.getVideoId(), voteView.getVoteSource()));

        ResultBean resultBean = new ResultBean<>();
        try {
            // 判断活动是否结束
            resultBean = this.getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                logger.info(String.format(
                    "[%s] 视频点赞结果：活动已结束,参数：openId:[%s],videoId:[%d],voteSource:[%s]",
                    Constants.ACTIVITY_NAME, voteView.getOpenId(),
                    voteView.getVideoId(), voteView.getVoteSource()));

                resultBean.setIsSuccess(false);
                resultBean.setMessage(resultBean.getMessage());
                return resultBean;
            }
            // 判断参数
            if (voteView.getVideoId() <= 0 || voteView.getOpenId().equals("")) {
                logger.info(String.format(
                    "[%s] 视频点赞结果：参数不正确,参数：openId:[%s],videoId:[%d],voteSource:[%s]",
                    Constants.ACTIVITY_NAME, voteView.getOpenId(),
                    voteView.getVideoId(), voteView.getVoteSource()));
                resultBean.setIsSuccess(false);
                resultBean.setMessage("参数不正确");
                return resultBean;
            }
            // 判断是否绑定角色
            WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                    .getUserBindRoleByOpenId(voteView.getOpenId());
            if (roleBindInfo == null) {
                logger.info(String.format(
                    "[%s] 视频点赞结果：未绑定角色,参数：openId:[%s],videoId:[%d],voteSource:[%s]",
                    Constants.ACTIVITY_NAME, voteView.getOpenId(),
                    voteView.getVideoId(), voteView.getVoteSource()));
                resultBean.setIsSuccess(false);
                resultBean.setMessage("请先绑定角色！");
                return resultBean;
            }

            WeChatVideoVoteLogBean info = new WeChatVideoVoteLogBean();
            info.setUserId(roleBindInfo.getUserId());
            info.setvideoId(voteView.getVideoId());
            info.setVoteSource(voteView.getVoteSource());
            info.setVoteDate(new Date());
            info.setIp(this.getIp(request));
            // 视频点赞
            resultBean = weChatVideoVoteLogBll.videoVote(info);

            logger.info(String.format(
                "[%s] 视频点赞结果：[%s],参数：openId:[%s],videoId:[%d],voteSource:[%s]",
                Constants.ACTIVITY_NAME, resultBean.getMessage(),
                voteView.getOpenId(), voteView.getVideoId(),
                voteView.getVoteSource()));

        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[视频点赞]{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 获取角色信息
     * </p>
     *
     * @action tanjunkai
     *
     * @param serverId
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> rolelist(Integer serverId, String openId,
            HttpServletRequest request) {

        logger.info(String.format("[%s] 获取角色信息开始,参数：serverId:[%d],openId:[%s]",
            Constants.ACTIVITY_NAME, serverId, openId));

        ResultBean<String> result = new ResultBean<>();
        try {
            if (serverId == null) {
                logger.info(String.format(
                    "[%s] 获取角色信息 结果：区服编号不能为空！,参数：serverId:[%d],openId:[%s]",
                    Constants.ACTIVITY_NAME, serverId, openId));
                result.setIsSuccess(false);
                result.setMessage("区服编号不能为空！");
                return result;
            }

            // 判断微信openId是否绑定角色
            WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                    .getUserBindRoleByOpenId(openId);
            if (roleBindInfo != null) {
                logger.info(String.format(
                    "[%s] 获取角色信息 结果：微信账号已经绑定过角色,参数：serverId:[%d],openId:[%s]",
                    Constants.ACTIVITY_NAME, serverId, openId));
                result.setIsSuccess(false);
                result.setMessage("该微信账号已经绑定过角色了");
                return result;
            }
            // 判断是否已登录
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }

            // 防刷
            String memKey = userInfo.getUserId() + "wechatvideo";
            if (memcachedClientAdapter.get(memKey, String.class) == null) {
                memcachedClientAdapter.set(memKey, 300, "0");
            } else {
                String countInStr = memcachedClientAdapter.get(memKey,
                    String.class);
                int limitCount = Integer.valueOf(countInStr);
                // 次数记录大于20次
                if (limitCount >= 10) {
                    result.setIsSuccess(false);
                    result.setMessage("禁止多次刷新");
                    return result;
                }
                memcachedClientAdapter.set(memKey, 300,
                    String.valueOf(limitCount + 1));
            }

            logger.info(String.format(
                "[%s] 获取角色信息->查看用户是否激活区服 开始,参数：serverId:[%d],account:[%s],openId:[%s]",
                Constants.ACTIVITY_NAME, serverId, userInfo.getAccount(),
                openId));
            // 根据选择的服务器判断是否激活
            cn.gyyx.action.common.beans.ResultBean<Boolean> serverStatus = gameAgent
                    .accountIsActivated(Constants.GAMEID, userInfo.getAccount(),
                        serverId);

            if (serverStatus == null || !serverStatus.getIsSuccess()) {
                logger.info(String.format(
                    "[%s] 获取角色信息->查看用户是否激活区服结果：该服务器尚未激活,参数：serverId:[%d],openId:[%s]",
                    Constants.ACTIVITY_NAME, serverId, openId));

                result.setIsSuccess(false);
                result.setMessage("该服务器尚未激活!");
                return result;
            }

            // 查询对应服务器的信息
            logger.info(String.format(
                "[%s] 获取角色信息->查看区服信息是否存在开始,参数：serverId:[%d],account:[%s],openId:[%s]",
                Constants.ACTIVITY_NAME, serverId, userInfo.getAccount(),
                openId));
            cn.gyyx.action.common.beans.ResultBean<JSONObject> server = gameAgent
                    .getServerByCode(Constants.GAMEID, serverId);
            if (server == null) {
                logger.info(String.format(
                    "[%s] 获取角色信息->查看区服信息是否存在结果：服务器信息不存在,参数：serverId:[%d],account:[%s],openId:[%s]",
                    Constants.ACTIVITY_NAME, serverId, userInfo.getAccount(),
                    openId));
                result.setIsSuccess(false);
                result.setMessage("该服务器信息不存在!");
                return result;
            }

            logger.info(String.format(
                "[%s] 获取角色信息->向区服获取角色信息开始,参数：serverId:[%d],account:[%s],openId:[%s]",
                Constants.ACTIVITY_NAME, serverId, userInfo.getAccount(),
                openId));
            String roleInfo = CallWebServiceInsuranceAgent
                    .getRoleInfo(userInfo.getAccount(), serverId);

            JSONObject jsonObj = JSONObject.fromObject(roleInfo);

            logger.info(String.format(
                "[%s] 获取角色信息->向区服获取角色信息结果:[%s],参数：serverId:[%d],account:[%s],openId:[%s]",
                Constants.ACTIVITY_NAME,
                jsonObj == null ? null : jsonObj.toString(), serverId,
                userInfo.getAccount(), openId));

            logger.info(Constants.ACTIVITY_NAME + "[查询角色信息][api]" + roleInfo);
            if (jsonObj == null || !jsonObj.getBoolean("IsSuccess")) {
                result.setIsSuccess(false);
                result.setMessage("您在该区没有角色信息！");
                return result;
            } else {
                result.setIsSuccess(true);
                result.setMessage("查询角色列表成功！");
                result.setData(roleInfo);
            }
        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[获取角色列表异常]{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return result;
    }

    /**
     * <p>
     * 角色绑定
     * </p>
     *
     * @author tanjunkai
     *
     * @param request
     * @param roleBindBean
     *            角色绑定视图
     * @return ResultBean
     */
    @RequestMapping(value = "/role/bind", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean roleBind(HttpServletRequest request,
            @Valid WeChatVideoRoleBindViewBean roleBindBean) {

        logger.info(String.format(
            "[%s] 角色绑定开始,参数：openId:[%s],微信名:[%s],游戏编码:[%s],区服名称:[%s],角色编码:[%s],角色名称:[%s]",
            Constants.ACTIVITY_NAME, roleBindBean.getOpenId(),
            roleBindBean.getWechatAccount(), roleBindBean.getServerCode(),
            roleBindBean.getServerName(), roleBindBean.getRoleId(),
            roleBindBean.getRoleName()));
        ResultBean resultBean = new ResultBean<>();
        try {
            // 判断活动是否结束
            resultBean = this.getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage(resultBean.getMessage());

                logger.info(String.format(
                    "[%s] 角色绑定结果:[%s],参数:openId:[%s],角色编码:[%s],角色名称:[%s]",
                    Constants.ACTIVITY_NAME, resultBean.getMessage(),
                    roleBindBean.getOpenId(), roleBindBean.getRoleId(),
                    roleBindBean.getRoleName()));

                return resultBean;
            }
            // 判断微信openId是否绑定角色
            WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                    .getUserBindRoleByOpenId(roleBindBean.getOpenId());
            if (roleBindInfo != null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("该微信账号已经绑定过角色了");

                logger.info(String.format(
                    "[%s] 角色绑定结果:[%s],参数：openId:[%s],角色编码:[%s],角色名称:[%s]",
                    Constants.ACTIVITY_NAME, resultBean.getMessage(),
                    roleBindBean.getOpenId(), roleBindBean.getRoleId(),
                    roleBindBean.getRoleName()));

                return resultBean;
            }
            // 判断是否登录
            UserInfo userInfo = SignedUser.getUserInfo(request);
            if (userInfo == null) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("您没有登录！");
                return resultBean;
            }
            WeChatVideoRoleBindBean info = new WeChatVideoRoleBindBean();
            info.setAccount(userInfo.getAccount());
            info.setUserId(userInfo.getUserId());
            info.setOpenId(roleBindBean.getOpenId());
            info.setWechatAccount(roleBindBean.getWechatAccount());
            info.setServerCode(roleBindBean.getServerCode());
            info.setServerName(roleBindBean.getServerName());
            info.setRoleId(roleBindBean.getRoleId());
            info.setRoleName(roleBindBean.getRoleName());
            resultBean = weChatVideoRoleBindBll.roleBind(info);

            logger.info(String.format(
                "[%s] 角色绑定结果:[%s],参数：openId:[%s],角色编码:[%s],角色名称:[%s]",
                Constants.ACTIVITY_NAME, resultBean.getMessage(),
                roleBindBean.getOpenId(), roleBindBean.getRoleId(),
                roleBindBean.getRoleName()));

        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[角色绑定]{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 查看账号是否绑定角色信息
     * </p>
     *
     * @action tanjunkai 2017年3月17日 上午11:16:05 描述
     *
     * @param userId
     *            用户ID
     * @return ResultBean
     */
    @RequestMapping(value = "/hasbindrole", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean hasBindRole(String openId) {
        logger.info(String.format("[%s] 查看账号 [%s] 是否绑定角色开始",
            Constants.ACTIVITY_NAME, openId));
        ResultBean resultBean = new ResultBean<>();
        if (openId == null || openId.equals("")) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("openId不能为空");
            return resultBean;
        }
        WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                .getUserBindRoleByOpenId(openId);
        if (roleBindInfo == null) {
            logger.info(String.format("[%s] 查看账号 [%s] 是否绑定角色结果:用户未绑定角色信息",
                Constants.ACTIVITY_NAME, openId));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("用户未绑定角色信息");
            return resultBean;
        }
        resultBean.setIsSuccess(true);
        resultBean.setData(roleBindInfo);
        resultBean.setMessage("用户已绑定角色信息");
        return resultBean;
    }

    /**
     * <p>
     * 视频上传保存接口(保存到数据库)
     * </p>
     *
     * @action tanjunkai 2017年3月15日 下午2:23:22 描述
     *
     * @param request
     * @param viewInfo
     *            视频上传视图
     * @return ResultBean
     */
    @RequestMapping(value = "/addvideoinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean AddVideoInfo(HttpServletRequest request,
            HttpServletResponse response,
            @Valid WeChatVideoUpLoadLogViewBean viewInfo) {

        logger.info(String.format(
            "用户开始视频上传,openId:[%s],视频地址：[%s],视频封面地址：[%s],视频名称：[%s]",
            viewInfo.getOpenId(), viewInfo.getVideoAddress(),
            viewInfo.getVideoCoverAddress(), viewInfo.getVideoName()));
        ResultBean resultBean = new ResultBean<>();
        try {
            // 判断活动是否结束
            resultBean = this.getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage(resultBean.getMessage());
                return resultBean;
            }

            WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                    .getUserBindRoleByOpenId(viewInfo.getOpenId());
            // 判断openId是否已绑定角色信息
            if (roleBindInfo == null) {
                logger.info(String.format("[%s] 用户：[%s] 上传视频结果：用户未绑定角色信息",
                    Constants.ACTIVITY_NAME, viewInfo.getOpenId()));
                resultBean.setIsSuccess(false);
                resultBean.setMessage("用户未绑定角色信息");
                return resultBean;
            }

            WeChatVideoUpLoadLogBean uploadInfo = new WeChatVideoUpLoadLogBean();
            uploadInfo.setUserId(roleBindInfo.getUserId());
            uploadInfo.setAccount(roleBindInfo.getAccount());
            uploadInfo.setVideoStatus(Constants.WECHATVIDEO_VERIFYPENDING);
            uploadInfo.setVideoName(viewInfo.getVideoName());
            uploadInfo.setVideoAddress(viewInfo.getVideoAddress());
            uploadInfo.setVideoCoverAddress(viewInfo.getVideoCoverAddress());

            resultBean = weChatVideoUpLoadLogBll.videoUpload(uploadInfo);
            logger.info("{} 用户：{} 上传视频结果：isSuccess:{},Message:{}",
                new Object[] { Constants.ACTIVITY_NAME, viewInfo.getOpenId(),
                        resultBean.getIsSuccess(), resultBean.getMessage() });
        } catch (Exception e) {
            logger.error(Constants.ACTIVITY_NAME + "[视频上传后信息保存接口]{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return resultBean;
    }

    /**
     * <p>
     * 用户抽奖
     * </p>
     *
     * @action tanjunkai 2017年3月17日 下午2:52:59 描述
     *
     * @param request
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> doLottery(HttpServletRequest request,
            String openId) {
        logger.info(String.format("[%s]_用户：openId:[%s] 开始抽奖",
            Constants.ACTIVITY_NAME, openId));
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
        try {
            // 判断活动是否结束
            ResultBean resultBean = this
                    .getActivityStatus(Constants.ACTIVITY_ID);
            if (!resultBean.getIsSuccess()) {
                result.setIsSuccess(false);
                result.setMessage(resultBean.getMessage());
                return result;
            }
            // 进行抽奖
            result = lotteryService.doLottery(Constants.ACTIVITY_ID, openId,
                this.getIp(request));
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "用户抽奖时失败!{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("人太多了，请稍后再试！");
        }
        return result;
    }

    /**
     * <p>
     * 中奖后添加地址信息
     * </p>
     *
     * @action tanjunkai 2017年3月22日 下午12:56:59 描述
     *
     * @param request
     * @param params
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> updateAddress(HttpServletRequest request,
            @Valid LotteryPrizesVO params) {
        ResultBean<String> result = new ResultBean<String>();
        logger.info(String.format(
            "[%s]中奖后填写地址信息,openId:[%s],活动ID：[%d],姓名：[%s],联系方式：[%s],地址:[%s]",
            Constants.ACTIVITY_NAME, params.getOpenId(), params.getActivityId(),
            params.getUserName(), params.getUserPhone(),
            params.getUserAddress()));
        try {
            WeChatVideoRoleBindBean roleBindBean = weChatVideoRoleBindBll
                    .getUserBindRoleByOpenId(params.getOpenId());
            // 判断openId是否已绑定角色信息
            if (roleBindBean == null) {
                result.setIsSuccess(false);
                result.setMessage("用户未绑定角色信息");

                logger.info(
                    String.format("[%s]中奖后填写地址信息结果:[%s],openId:[%s] 已绑定角色信息",
                        Constants.ACTIVITY_NAME, result.getMessage(),
                        params.getOpenId()));

                return result;
            }
            logger.info(String.format("[%s]中奖后填写地址信息,openId:[%s] 已绑定角色信息",
                Constants.ACTIVITY_NAME, params.getOpenId()));
            params.setActivityId(Constants.ACTIVITY_ID);
            params.setUserCode(roleBindBean.getUserId());
            params.setUserId(roleBindBean.getOpenId());
            lotteryPrizesService.putAddress(params);
            logger.info(Constants.ACTIVITY_NAME + ",开始添加用户中奖地址信息" + params);
            result.setIsSuccess(true);
            result.setMessage("添加成功！");
            logger.info(String.format(
                "[%s]中奖后填写地址信息成功,openId:[%s],活动ID：[%d],姓名：[%s],联系方式：[%s],地址:[%s]",
                Constants.ACTIVITY_NAME, params.getOpenId(),
                params.getActivityId(), params.getUserName(),
                params.getUserPhone(), params.getUserAddress()));
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + "添加用户中奖地址信息时失败!{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("添加失败！");
        }
        return result;
    }

    /**
     * <p>
     * 获取我的中奖纪录
     * </p>
     *
     * @action tanjunkai 2017年3月22日 下午2:11:23 描述
     *
     * @param openId
     *            微信OpenId
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/myprize", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<ActionLotteryLogPO>> getMyPrize(String openId) {
        ResultBean<List<ActionLotteryLogPO>> result = new ResultBean<List<ActionLotteryLogPO>>();
        try {
            logger.info(String.format("[%s]_获取我的中奖纪录开始,参数openId:[%s]",
                Constants.ACTIVITY_NAME, openId));
            WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                    .getUserBindRoleByOpenId(openId);
            // 判断openId是否已绑定角色信息
            if (roleBindInfo == null) {
                result.setIsSuccess(false);
                result.setMessage("用户未绑定角色信息");

                logger.info(String.format("[%s]_获取我的中奖纪录结果:[%s],参数openId:[%s]",
                    Constants.ACTIVITY_NAME, result.getMessage(), openId));

                return result;
            }
            List<ActionLotteryLogPO> myPrizeList = prizeService
                    .getMyPrize(roleBindInfo);
            result.setIsSuccess(true);
            result.setMessage("查询成功");
            result.setData(myPrizeList);
            logger.info(String.format("[%s]_获取我的中奖纪录结果:[%s],参数openId:[%s]",
                Constants.ACTIVITY_NAME, result.getMessage(), openId));
        } catch (Exception e) {
            logger.error(Constants.ERROR_LOG + " 获取我的中奖纪录信息时失败!{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("sorry,服务器出点小问题,请稍后重试！");
        }
        return result;
    }

    /**
     * <p>
     * 查看上传视频资格状态
     * </p>
     *
     * @action tanjunkai 2017年3月25日 下午5:03:53 描述
     *
     * @param openid
     * @return ResultBean
     */
    @RequestMapping(value = "/uploadstatus", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean uploadStatus(String openid) {
        ResultBean result = new ResultBean<>();
        if ("".equals(openid) || openid == null) {
            result.setIsSuccess(false);
            result.setMessage("未获取到openId");
        }
        WeChatVideoRoleBindBean roleBindInfo = weChatVideoRoleBindBll
                .getUserBindRoleByOpenId(openid);
        // 判断openId是否已绑定角色信息
        if (roleBindInfo == null) {
            logger.info(String.format("[%s] 用户：[%s] 查看上传视频资格状态：用户未绑定角色信息",
                Constants.ACTIVITY_NAME, openid));
            result.setIsSuccess(false);
            result.setMessage("用户未绑定角色信息");
            return result;
        }
        result = weChatVideoUpLoadLogBll
                .videoUploadJudge(roleBindInfo.getUserId());
        return result;
    }
}
