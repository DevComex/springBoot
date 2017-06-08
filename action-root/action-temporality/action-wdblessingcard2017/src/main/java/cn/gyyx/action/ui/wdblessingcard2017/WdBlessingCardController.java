/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：niushuai
 * @联系方式：niushuai@gyyx.cn
 * @创建时间：2017年3月12日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wdblessingcard2017;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.Constants;
import cn.gyyx.action.beans.wdblessingcard2017.LotteryPrizesBean;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.lottery.ILotteryPrizesService;
import cn.gyyx.action.service.lottery.impl.LotteryPrizesService;
import cn.gyyx.action.service.wdblessingcard2017.BlessingCardBeanService;
import cn.gyyx.action.service.wdblessingcard2017.LotteryPrizesBeanService;
import cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService;
import cn.gyyx.action.service.wdblessingcard2017.impl.BlessingCardBeanServiceImpl;
import cn.gyyx.action.service.wdblessingcard2017.impl.LotteryPrizesBeanServiceImpl;
import cn.gyyx.action.service.wdblessingcard2017.impl.RoleBindBeanServiceImpl;
import cn.gyyx.core.Text;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.service.WDGameAgent;
import net.sf.json.JSONObject;

/**
 * <p>
 * 祝福卡活动页面的接口
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/wdblessingcard2017")
public class WdBlessingCardController extends BaseController {

    private static final GYYXLogger LOG = GYYXLoggerFactory
            .getLogger(WdBlessingCardController.class);

    BlessingCardBeanService blessingCardBeanService = new BlessingCardBeanServiceImpl();
    RoleBindBeanService roleBindBeanService = new RoleBindBeanServiceImpl();
    LotteryPrizesBeanService lotteryPrizesBeanService = new LotteryPrizesBeanServiceImpl();
    private WDGameAgent gameAgent = new WDGameAgent();
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
            .getMemcache();
    private ILotteryPrizesService lotteryPrizesService = new LotteryPrizesService();

    /**
     * <p>
     * 获取祝福卡列表，按上传时间倒序，返回的都是审核通过的祝福卡
     * </p>
     *
     * @action niushuai 2017年3月10日 下午4:41:02 描述
     *
     * @param queryBean
     * @return ResultBean<BlessingCardBean>
     */
    @RequestMapping(value = "/blessingcards", method = RequestMethod.GET)
    @ResponseBody
    public Object blessingcards(QueryBean queryBean) {
        ResultBean<String> result = new ResultBean<String>();
        PageBean<BlessingCardBean> page = null;
        try {
            if (queryBean.getPage() == null || queryBean.getPage() < 1)
                queryBean.setPage(1);
            if (queryBean.getSize() == null)
                queryBean.setSize(10);
            if (queryBean.getSize() > 100)
                queryBean.setSize(100);
            // 为空或者空字符串的角色名，则不作为条件
            if (queryBean.getRoleName() != null
                    && queryBean.getRoleName().trim().equals("")) {
                queryBean.setRoleName(null);
            }
            // 默认按创建时间排序
            if (Text.isNullOrEmpty(queryBean.getOrderBy())) {
                queryBean.setOrderBy("createTime");
            }
            page = blessingCardBeanService
                    .getVerifyedBlessingCardPaging(queryBean);
            return page;
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "[获取祝福卡列表]异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("请求错误！");
            return result;
        }
    }

    /**
     * <p>
     * 获取角色绑定信息
     * </p>
     *
     * @action niushuai 2017年3月10日 下午7:36:45 描述
     *
     * @param queryBean
     * @param request
     * @return ResultBean<RoleBindBean>
     */
    @RequestMapping(value = "/rolebind", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<RoleBindBean> getRoleBind(QueryBean queryBean,
            HttpServletRequest request) {
        ResultBean<RoleBindBean> result = new ResultBean<RoleBindBean>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            RoleBindBean roleBindBean = roleBindBeanService
                    .getRoleBindBeanByAccount(userInfo.getAccount());
            if (roleBindBean != null) {
                result.setIsSuccess(true);
                result.setMessage("查询成功！");
                result.setData(roleBindBean);
            } else {
                result.setIsSuccess(false);
                result.setMessage("您还没有绑定角色！");
            }
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "[获取祝福卡列表]异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 绑定角色信息
     * </p>
     *
     * @action niushuai 2017年3月11日 上午10:25:27 描述
     *
     * @param roleName
     * @param serverCode
     * @param serverName
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/rolebind", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> postRoleBind(String roleName, Integer serverCode,
            String serverName, String roleId, HttpServletRequest request,
            HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<String>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        if (Text.isNullOrWhitespace(roleId)) {
            result.setIsSuccess(false);
            result.setMessage("角色编号不能为空！");
            return result;
        }
        if (Text.isNullOrEmpty(roleName)) {
            result.setIsSuccess(false);
            result.setMessage("角色名不能为空！");
            return result;
        }
        if (serverCode == null || Text.isNullOrEmpty(serverName)) {
            result.setIsSuccess(false);
            result.setMessage("区服编号和区服不能为空！");
            return result;
        }
        UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }
        RoleBindBean data = roleBindBeanService
                .getRoleBindBeanByAccount(userInfo.getAccount());
        if (data != null) {
            result.setIsSuccess(false);
            result.setMessage("您已经绑定过角色！");
            return result;
        }
        // 查询角色是否被其它账号绑定过
        if (roleBindBeanService.getRoleBindBeanCount(roleId) > 0) {
            result.setIsSuccess(false);
            result.setMessage("该角色已绑定过！");
            return result;
        }
        try {
            // 绑定角色时验证用户是否在
            result = roleBindBeanService.validateRole(userInfo.getAccount(),
                serverCode, roleId);
            if (!result.getIsSuccess()) return result;

            RoleBindBean roleBindBean = new RoleBindBean();
            roleBindBean.setRoleId(roleId);
            roleBindBean.setUserId(userInfo.getUserId());
            roleBindBean.setAccount(userInfo.getAccount());
            roleBindBean.setIp(getIp(request));

            String regTime = CallWebServiceAgent.getAccountRegTime(userInfo
                    .getUserId());
            roleBindBean.setRegisterYear(Integer.parseInt(regTime.substring(0,
                4)));
            roleBindBean.setRemainingTimes(0);
            roleBindBean.setRoleName(roleName);
            roleBindBean.setServerCode(serverCode);
            roleBindBean.setServerName(serverName);
            roleBindBean.setUpvoteTimes(Constants.INITIAL_UPVOTE_TIMES);
            roleBindBean.setCreateTime(new Date());
            int count = roleBindBeanService.insertRoleBindBean(roleBindBean);
            if (count == 1) {
                result.setIsSuccess(true);
                result.setMessage("操作成功！");
                result.setData("绑定角色信息完成！");
            } else {
                result.setIsSuccess(false);
                result.setMessage("操作失败！");
                result.setData("不能重复绑定！");
            }
        } catch (Exception e) {
            LOG.error("添加角色绑定信息异常！{}", Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("绑定时出现了异常！请再试一次！");
        }

        return result;
    }

    /**
     * <p>
     * 查询自己的祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午1:29:18 描述
     *
     * @param request
     * @return ResultBean<BlessingCardBean>
     */
    @RequestMapping(value = "/blessingcard", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<BlessingCardBean> getBlessingCard(
            HttpServletRequest request) {
        ResultBean<BlessingCardBean> result = new ResultBean<BlessingCardBean>();
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            BlessingCardBean blessingCard = blessingCardBeanService
                    .getBlessingCardByAccount(userInfo.getAccount());
            if (blessingCard == null) {
                result.setIsSuccess(false);
                result.setMessage("你还没有填写祝福卡!");
                return result;
            }
            result.setData(blessingCard);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } catch (Exception e) {
            LOG.error("查询祝福卡异常！{}", Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 创建和更新自己的祝福卡
     * </p>
     *
     * @action niushuai 2017年3月11日 下午1:02:05 描述
     *
     * @param avatar
     * @param content
     * @param request
     * @return ResultBean<BlessingCardBean>
     */
    @RequestMapping(value = "/blessingcard", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<BlessingCardBean> postBlessingCard(String avatar,
            String content, HttpServletRequest request) {
        ResultBean<BlessingCardBean> result = new ResultBean<BlessingCardBean>();
        if (Text.isNullOrEmpty(avatar)) {
            result.setIsSuccess(false);
            result.setMessage("头像不能为空！");
            return result;
        }
        if (Text.isNullOrEmpty(content)) {
            result.setIsSuccess(false);
            result.setMessage("内容不能为空！");
            return result;
        }
        UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }
        RoleBindBean roleBindBean = roleBindBeanService
                .getRoleBindBeanByAccount(userInfo.getAccount());
        if (null == roleBindBean) {
            result.setIsSuccess(false);
            result.setMessage("您还没有绑定角色信息，绑定完之后就可以填写啦！");
            return result;
        }
        BlessingCardBean blessingCardBean = blessingCardBeanService
                .getBlessingCardByAccount(userInfo.getAccount());
        try {
            if (blessingCardBean != null) {
                // 更新祝福卡
                if (blessingCardBean.getVerifyStatus() == 0) {
                    result.setIsSuccess(false);
                    result.setMessage("您的祝福卡正在审核中！");
                    return result;
                }
                if (blessingCardBean.getVerifyStatus() == 1) {
                    result.setIsSuccess(false);
                    result.setMessage("您的祝福卡已经审核通过，不能再修改！");
                    return result;
                }
                blessingCardBean.setContent(content);
                blessingCardBean.setAvatar(avatar);
                LOG.info(Constants.HD_NAME + "[更新祝福卡]{}", blessingCardBean);
                blessingCardBeanService.updateBlessingCard(blessingCardBean);
                result.setIsSuccess(true);
                result.setMessage("更新成功！请等待审核！");
            } else {
                BlessingCardBean blessingCard = new BlessingCardBean();
                blessingCard.setUserId(userInfo.getUserId());
                blessingCard.setAccount(userInfo.getAccount());
                blessingCard.setAvatar(avatar);
                blessingCard.setContent(content);
                blessingCard.setCreateTime(new Date());
                blessingCard.setRoleName(roleBindBean.getRoleName());
                blessingCard.setServerName(roleBindBean.getServerName());
                blessingCard.setTitle(getTitle(roleBindBean.getRegisterYear()));
                blessingCard.setUpvoteNum(0);
                blessingCard.setVerifyStatus(0);
                blessingCard.setRegisterYear(roleBindBean.getRegisterYear());
                if (blessingCardBeanService.insert(blessingCard) == 1) {
                    result.setIsSuccess(true);
                    result.setMessage("添加成功！");
                } else {
                    result.setIsSuccess(false);
                    result.setMessage("操作失败，请重试！");
                }
            }
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "创建更新祝福卡异常！{}", Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("提交祝福卡失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 用注册年份获得对应的称号
     * </p>
     *
     * @action niushuai 2017年3月11日 下午12:12:47 描述
     *
     * @param registerYear
     * @return String
     */
    private String getTitle(int registerYear) {
        String title;
        switch (registerYear) {
            case 2003:
                title = "11年问道战斗机";
                break;
            case 2004:
                title = "11年问道战斗机";
                break;
            case 2005:
                title = "11年问道战斗机";
                break;
            case 2006:
                title = "11年问道战斗机";
                break;
            case 2007:
                title = "10年问道战斗机";
                break;
            case 2008:
                title = "9年问道战斗机";
                break;
            case 2009:
                title = "8年问道老司机";
                break;
            case 2010:
                title = "7年问道老司机";
                break;
            case 2011:
                title = "6年问道老司机";
                break;
            case 2012:
                title = "5年问道准老司机";
                break;
            case 2013:
                title = "4年问道准老司机";
                break;
            case 2014:
                title = "3年问道准老司机";
                break;
            case 2015:
                title = "2年新手司机上路";
                break;
            default:
                title = "1年新手司机上路";
                break;
        }
        return title;
    }

    /**
     * <p>
     * 查询用户的地址信息
     * </p>
     *
     * @action niushuai 2017年3月16日 下午5:09:19 描述
     *
     * @param request
     * @return ResultBean<AddressBean>
     */
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<LotteryPrizesVO> getAddress(HttpServletRequest request) {
        ResultBean<LotteryPrizesVO> result = new ResultBean<LotteryPrizesVO>();
        UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }
        try {
            RoleBindBean roleBindBean = roleBindBeanService
                    .getRoleBindBeanByAccount(userInfo.getAccount());
            if (null == roleBindBean) {
                result.setIsSuccess(false);
                result.setMessage("您没有绑定角色！");
                return result;
            }

            LotteryPrizesVO address = roleBindBeanService.getAddressByAccount(
                userInfo.getAccount(), Constants.HD_CODE, userInfo.getUserId());

            if (address == null) {
                result.setIsSuccess(false);
                result.setMessage("您还没有填写地址！");
            } else {
                result.setData(address);
                result.setIsSuccess(true);
                result.setMessage("查询成功！");
            }
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "查询绑定角色时失败!{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询角色失败！");
        }
        return result;
    }

    /**
     * 
     * <p>
     * 更新用户的地址信息
     * </p>
     *
     * @action niushuai 2017年3月16日 下午8:14:08 描述
     *
     * @param request
     * @param params
     * @param br
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> updateAddress(HttpServletRequest request,
            @Validated LotteryPrizesVO params, BindingResult br) {
        ResultBean<String> result = new ResultBean<String>();
        if (br.hasErrors()) {
            result.setMessage(br.getFieldError().getDefaultMessage());
            return result;
        }
        UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }

        try {
            RoleBindBean roleBindBean = roleBindBeanService
                    .getRoleBindBeanByAccount(userInfo.getAccount());
            if (null == roleBindBean) {
                result.setIsSuccess(false);
                result.setMessage("您没有绑定角色！");
                return result;
            }
            params.setActivityId(Constants.HD_CODE);
            params.setUserCode(userInfo.getUserId());
            params.setUserId(userInfo.getAccount());
            lotteryPrizesService.putAddress(params);
            LOG.info(Constants.HD_NAME + "，用户更新地址信息" + params);
            result.setIsSuccess(true);
            result.setMessage("更新成功！");
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "更新用户地址时失败!{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("更新失败！");
        }
        return result;
    }

    /**
     * <p>
     * 查询当前用户可用的抽奖次数
     * </p>
     *
     * @action niushuai 2017年3月11日 下午2:34:57 描述
     *
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> lottery(HttpServletRequest request) {
        ResultBean<Integer> result = new ResultBean<Integer>();
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            RoleBindBean roleBindBean = roleBindBeanService
                    .getRoleBindBeanByAccount(userInfo.getAccount());
            if (null == roleBindBean) {
                result.setIsSuccess(false);
                result.setMessage("您没有绑定角色！");
                return result;
            }
            LOG.info(
                Constants.HD_NAME
                        + "[start invoke blessingCardBeanService.getUserAvailableLotteryTimes], params  roleBindBean: {}",
                JSONObject.fromObject(roleBindBean));
            int userAvailableLotteryTimes = blessingCardBeanService
                    .getUserAvailableLotteryTimes(roleBindBean, null);
            LOG.info(
                Constants.HD_NAME
                        + "[end invoke blessingCardBeanService.getUserAvailableLotteryTimes], result  userAvailableLotteryTimes: {}",
                userAvailableLotteryTimes);
            result.setData(userAvailableLotteryTimes);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } catch (Exception e) {
            LOG.error("查询用户抽奖次数！{}", Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询时出错！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 用户抽奖
     * </p>
     *
     * @action niushuai 2017年3月14日 下午2:32:56 描述
     *
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> doLottery(HttpServletRequest request) {
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
        // 是否登录 ？
        UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }
        // 是否在活动期内？
        ActivityStatus status = new DefaultHdConfigBLL()
                .getStatus(Constants.HD_CODE);
        if (status != ActivityStatus.IS_NORMAL) {
            result.setIsSuccess(false);
            result.setMessage("不在活动期内！");
            return result;
        }
        DistributedLock lock = new DistributedLock(
                Constants.LOTTERY_PRIFIX + getIp(request));
        try {
            lock.weakLock(76, 5);
            result = lotteryPrizesBeanService.doLottery(Constants.HD_CODE,
                userInfo, getIp(request));
        } catch (Exception e) {
            result.setIsSuccess(false);
            result.setMessage("哎呀，活动太火爆了。请稍后重试抽奖！");
            LOG.error(Constants.ERROR_LOG + "用户抽奖时异常！错误信息：{}",
                Throwables.getStackTraceAsString(e));
        } finally {
            try {
                if (lock != null)
                    lock.close();
            } catch (DLockException e) {
                LOG.error(Constants.ERROR_LOG + "关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        return result;
    }

    /**
     * <p>
     * 查询 当前用户所有的中奖记录
     * </p>
     *
     * @action niushuai 2017年3月11日 下午2:47:25 描述
     *
     * @return ResultBean<LotteryPrizesBean>
     */
    @RequestMapping(value = "/prizes", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<LotteryPrizesBean> getPrizes(HttpServletRequest request) {
        ResultBean<LotteryPrizesBean> result = new ResultBean<LotteryPrizesBean>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            List<LotteryPrizesBean> list = lotteryPrizesBeanService
                    .getLotteryListByAccount(userInfo.getAccount(),
                        Constants.HD_CODE);
            result.setRows(list);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } catch (Exception e) {
            LOG.error("查询 当前用户所有的中奖记录异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 在前台流动显示的中奖信息
     * </p>
     *
     * @action niushuai 2017年3月12日 下午11:12:09 描述
     *
     * @return ResultBean<LotteryPrizesBean>
     */
    @RequestMapping(value = "/prizes/show", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<LotteryPrizesBean> prizesShow() {
        ResultBean<LotteryPrizesBean> result = new ResultBean<LotteryPrizesBean>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        try {
            List<LotteryPrizesBean> list = lotteryPrizesBeanService
                    .getLotteryList(Constants.HD_CODE, 200);
            result.setRows(list);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } catch (Exception e) {
            LOG.error("查询流动中奖信息异常！{}", Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！");
        }
        return result;
    }

    /**
     * <p>
     * 获取用户的游戏角色
     * </p>
     *
     * @action niushuai 2017年3月11日 下午9:38:01 描述
     *
     * @param serverId
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> rolelist(String valiCode, Integer serverId,
            HttpServletRequest request, HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<>(false, "获取角色列表异常", null);
        try {
            if (serverId == null) {
                result.setIsSuccess(false);
                result.setMessage("区服编号不能为空！");
                return result;
            }
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }

            if (!new Captcha(request, response).equals(valiCode)) {
                result.setProperties(false, "验证码填写错误", "");
                return result;
            }

            // 防刷
            int limitCount = 0;
            String memKey = userInfo.getUserId() + "_"
                    + Constants.HD_NAME_English;
            // 获取缓存
            String countInStr = memcachedClientAdapter
                    .get(memKey, String.class);

            if (Text.isNullOrEmpty(countInStr)) {
                // 增加缓存
                memcachedClientAdapter.set(memKey, 300, "0");
            } else {
                limitCount = Integer.valueOf(countInStr);
                memcachedClientAdapter.set(memKey, 300,
                    String.valueOf(limitCount + 1));
            }

            // 次数记录大于20次
            if (limitCount >= 4) {
                result.setIsSuccess(false);
                result.setMessage("禁止多次刷新");
                return result;
            }

            // 根据选择的服务器判断是否激活
            cn.gyyx.action.common.beans.ResultBean<Boolean> serverStatus = gameAgent
                    .accountIsActivated(Constants.GAMEID,
                        userInfo.getAccount(), serverId);
            if (serverStatus == null || !serverStatus.getIsSuccess()) {
                result.setIsSuccess(false);
                result.setMessage("该服务器尚未激活!");
                return result;
            }
            // 查询对应服务器的信息
            cn.gyyx.action.common.beans.ResultBean<JSONObject> server = gameAgent
                    .getServerByCode(Constants.GAMEID, serverId);
            if (server == null) {
                result.setIsSuccess(false);
                result.setMessage("该服务器信息不存在!");
                return result;
            }

            String roleInfo = CallWebServiceInsuranceAgent.getRoleInfo(
                userInfo.getAccount(), serverId);
            JSONObject jsonObj = JSONObject.fromObject(roleInfo);
            LOG.info(Constants.HD_NAME + "[查询角色信息][api]" + roleInfo);
            if (jsonObj == null || !jsonObj.getBoolean("IsSuccess")) {
                result.setProperties(false, "您在该区没有角色信息！", null);
                return result;
            } else {
                result.setProperties(true, "查询角色列表成功", roleInfo);
            }
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "[获取角色列表异常]{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("获取角色信息时出现了问题！请再试一下！");
        }
        return result;
    }

    /**
     * <p>
     * 用户给某一祝福卡点赞
     * </p>
     *
     * @action niushuai 2017年3月11日 下午2:33:03 描述
     *
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/blessingcard/{code}/upvote",
        method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> upvote(HttpServletRequest request,
            @PathVariable Integer code) {
        ResultBean<String> result = new ResultBean<String>();
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }

            result = blessingCardBeanService.upvoteBlessing(
                userInfo.getAccount(), code, getIp(request));

        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "，点赞失败！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("点赞失败！请重试！");
        }
        return result;
    }

    /**
     * 
     * <p>
     * 微信浏览器验证
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午8:25:30 新增
     *
     * @param request
     * @param response
     * @return boolean
     */
    private boolean wxCheck(HttpServletRequest request,
            HttpServletResponse response) {
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
            try {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(
                    String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
                response.getWriter().close();
                return false;
            } catch (IOException e) {
                LOG.error(Constants.HD_NAME + "，微信浏览器检查异常！{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        return true;
    }

    /**
     * 
     * <p>
     * 微信点赞
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午8:28:21 新增
     *
     * @param openId
     * @param blessingCardCode
     * @param request
     * @param response
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/wxVote", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> wxVote(
            @RequestParam(value = "openId") String openId, @RequestParam(
                value = "code") int blessingCardCode,
            HttpServletRequest request, HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<String>();
        if (!wxCheck(request, response)) {
            return null;
        }
        if (Text.isNullOrEmpty(openId)) {
            result.setIsSuccess(false);
            result.setMessage("openId不能为空！");
            return result;
        }
        
        try {
            result = blessingCardBeanService.wxVote(openId, blessingCardCode,
                getIp(request));
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "，微信点赞失败！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("服务器繁忙，请稍后再试！");
        }
        return result;
    }

    /**
     * 
     * <p>
     * 玩拼图游戏获得抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午4:11:44 新增
     *
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/playpuzzlegame", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> playPuzzleGame(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>();
        LOG.info("进入WdBlessingCardController[playPuzzleGame]");
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            // 判断当前活动的状态，是否在活动时间范围
            result = blessingCardBeanService
                    .getActivityStatus(Constants.HD_CODE);
            if (!result.getIsSuccess()) {
                return result;
            }

            String account = userInfo.getAccount();
            LOG.info(
                Constants.HD_NAME
                        + "[start invoke blessingCardBeanService.playPuzzleGameToGetLotteryTime], params  account: {}",
                account);
            result = blessingCardBeanService
                    .playPuzzleGameToGetLotteryTime(account);
            LOG.info(
                Constants.HD_NAME
                        + "[end invoke blessingCardBeanService.playPuzzleGameToGetLotteryTime], result : {}",
                JSONObject.fromObject(result));
            return result;
        } catch (Exception e) {
            LOG.error(
                Constants.HD_NAME
                        + " [blessingCardBeanService-playPuzzleGame] catch exception: {}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage(Constants.FRIENDLY_PROMPT);
        }
        return result;
    }

    /**
     * 
     * <p>
     * 玩家领取游戏称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午1:35:22 新增
     *
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/getgametitle", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> getGameTitle(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>();
        LOG.info("进入WdBlessingCardController[getGameTitle]");
        try {
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            // 判断当前活动的状态，是否在活动时间范围
            result = blessingCardBeanService
                    .getActivityStatus(Constants.HD_CODE);
            if (!result.getIsSuccess()) {
                return result;
            }

            String account = userInfo.getAccount();
            LOG.info(
                Constants.HD_NAME
                        + "[start invoke blessingCardBeanService.getGameTitle], params  account: {}",
                account);
            result = blessingCardBeanService.getGameTitle(account);
            LOG.info(
                Constants.HD_NAME
                        + "[end invoke blessingCardBeanService.getGameTitle], result : {}",
                JSONObject.fromObject(result));
            return result;
        } catch (Exception e) {
            LOG.error(
                Constants.HD_NAME
                        + " [blessingCardBeanService-getGameTitle] catch exception: {}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage(Constants.FRIENDLY_PROMPT);
        }
        return result;
    }

    /**
     * <p>
     * 返回用户已中奖品的数量，默认查询实物奖品
     * </p>
     *
     * @action niushuai 2017年3月17日 下午2:14:31 描述
     *
     * @param request
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/prizeCount", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> prizeCount(HttpServletRequest request,
            String prizeType) {
        ResultBean<Integer> result = new ResultBean<Integer>();
        try {
            if (Text.isNullOrWhitespace(prizeType)) {
                prizeType = Constants.PRIZE_TYPE_REALPRIZE; // 实物奖品
            }
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (null == userInfo) {
                result.setIsSuccess(false);
                result.setMessage("您没有登录！");
                return result;
            }
            int count = lotteryPrizesBeanService.prizeCount(
                userInfo.getAccount(), prizeType);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
            result.setData(count);
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "prizeCount()异常{}", e);
            result.setIsSuccess(false);
            result.setMessage("查询失败！");
        }
        return result;
    }

}
