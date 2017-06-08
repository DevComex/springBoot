/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月10日 下午12:10:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdblessingcard2017.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdblessingcard2017.BlessingCardBean;
import cn.gyyx.action.beans.wdblessingcard2017.Constants;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wdblessingcard2017.BlessingCardBeanBll;
import cn.gyyx.action.bll.wdblessingcard2017.RoleBindBeanBll;
import cn.gyyx.action.bll.wdblessingcard2017.impl.BlessingCardBeanBllImpl;
import cn.gyyx.action.bll.wdblessingcard2017.impl.RoleBindBeanBllImpl;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.wdblessingcard2017.BlessingCardBeanService;
import cn.gyyx.action.ui.wdblessingcard2017.QueryBean;
import cn.gyyx.core.Text;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * <p>
 * 祝福卡Service
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class BlessingCardBeanServiceImpl implements BlessingCardBeanService {

    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(BlessingCardBeanServiceImpl.class);
    private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    BlessingCardBeanBll blessingCardBeanBll = new BlessingCardBeanBllImpl();
    RoleBindBeanBll roleBindBeanBll = new RoleBindBeanBllImpl();
    MyBatisBaseDAO myBatisBaseDAO = new MyBatisBaseDAO();
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
            .getMemcache();

    @Override
    public BlessingCardBean getBlessingCardByCode(Integer code) {

        return blessingCardBeanBll.getBlessingCardByCode(code);
    }

    /**
     * 
     * <p>
     * OA后台-获取系统发出的总抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月17日 下午8:33:30 新增
     *
     * @return Integer
     */
    public Integer getSystemLotteryTimes() {
        return blessingCardBeanBll.getSystemLotteryTimes();
    }

    /**
     * 
     * <p>
     * OA后台-获取玩家的总抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月17日 下午8:33:30 新增
     *
     * @return Integer
     */
    public Integer getUsersLotteryTimes() {
        return blessingCardBeanBll.getUsersLotteryTimes();
    }

    /**
     * 
     * <p>
     * 批量审核祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午1:17:55 新增
     *
     * @param codesStr
     * @param verifyStatus
     * @param verifyAdmin
     */
    public ResultBean<String> batchVerifyBlessingCard(String codesStr,
            int verifyStatus, String verifyAdmin) {
        ResultBean<String> result = new ResultBean<String>();
        String resultMsgs = "";
        for (String id : codesStr.split(",")) {
            resultMsgs += verifyBlessingCardSingle(Integer.parseInt(id),
                verifyStatus, verifyAdmin) + "\n";
        }

        result.setIsSuccess(true);
        result.setMessage(resultMsgs);
        return result;
    }

    /**
     * <p>
     * 获取活动当前的状态
     * </p>
     *
     * @action bozhencheng add 2017年3月4日 下午6:16:34 描述
     *
     * @param activityId
     * @return ResultBean<Object>
     */
    public ResultBean<String> getActivityStatus(int activityId) {

        ActivityStatus status = new DefaultHdConfigBLL().getStatus(activityId);
        ResultBean<String> result = new ResultBean<>();

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

    /**
     * 
     * <p>
     * 往游戏内发虚拟物品
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午11:22:11 新增
     *
     * @param account
     * @param giftPackage
     * @param serverCode
     * @return boolean
     */
    public boolean reciveGift(String account, String giftPackage, int serverCode) {
        boolean result = true;
        try {
            // 获取服务器信息
            ServerInfoBean serverInfo = callWebApiAgent
                    .getServerStatusFromWebApi(serverCode);
            // 发放物品
            CallWebServiceAgent.givePresents(2, account, giftPackage,
                "20250902235959", Constants.HD_NAME, serverInfo);
        } catch (Exception e) {
            LOG.error(
                "invoke Service[BlessingCardBeanServiceImpl-reciveGift] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            result = false;
        }
        return result;
    }

    /**
     * 
     * <p>
     * 根据抽奖次数判断玩家可获得哪种称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午1:19:31 新增
     *
     * @param lotteryTimes
     * @return String
     */
    private String getGameTitle(int lotteryTimes) {
        String title = "";
        switch (lotteryTimes) {
            case 1:
                title = "祝福卡片活动奖励新手上路称谓(20170303)";
                break;
            case 2:
                title = "祝福卡片活动奖励新手上路称谓(20170303)";
                break;
            case 3:
                title = "祝福卡片活动奖励准老司机称谓(20170303)";
                break;
            case 4:
                title = "祝福卡片活动奖励准老司机称谓(20170303)";
                break;
            case 5:
                title = "祝福卡片活动奖励准老司机称谓(20170303)";
                break;
            case 6:
                title = "祝福卡片活动奖励问道老司机称谓(20170303)";
                break;
            case 7:
                title = "祝福卡片活动奖励问道老司机称谓(20170303)";
                break;
            case 8:
                title = "祝福卡片活动奖励问道老司机称谓(20170303)";
                break;
            case 9:
                title = "祝福卡片活动奖励老司机中的战斗机称谓(20170303)";
                break;
            case 10:
                title = "祝福卡片活动奖励老司机中的战斗机称谓(20170303)";
                break;
            case 11:
                title = "祝福卡片活动奖励老司机中的战斗机称谓(20170303)";
                break;
            default:
                title = "祝福卡片活动奖励新手上路称谓(20170303)";
                break;
        }
        return title;
    }

    /**
     * 
     * <p>
     * 玩家领取游戏称号
     * </p>
     *
     * @action laixiancai 2017年3月16日 上午1:35:07 新增
     *
     * @param account
     * @return ResultBean<String>
     */
    public ResultBean<String> getGameTitle(String account) {
        ResultBean<String> result = new ResultBean<String>();
        try (DistributedLock lock = new DistributedLock(Constants.HD_CODE + "_"
                + "getGameTitle" + "_" + account)) {
            lock.weakLock(60, 5);
            RoleBindBean roleBind = roleBindBeanBll
                    .getRoleBindBeanByAccount(account);
            if (roleBind == null) {
                result.setIsSuccess(false);
                result.setMessage("请先绑定角色信息！");
                return result;
            }
            LOG.info(
                Constants.HD_NAME
                        + "[BlessingCardBeanService.getGameTitle], 获取用户绑定信息roleBind : {}",
                JSONObject.fromObject(roleBind));
            // 未领取过称号
            if (!roleBind.getIsReceivedTitle()) {
                int userRegisterYear = roleBind.getRegisterYear();
                // 根据注册年份得到相应的抽奖次数
                int lotteryTimesByRegisterYear = getLotteryTimesByRegisterYear(userRegisterYear);
                String gameTitle = getGameTitle(lotteryTimesByRegisterYear);
                LOG.info(
                    Constants.HD_NAME
                            + "[BlessingCardBeanService.getGameTitle], 根据注册年份判断获取的称号gameTitle : {}",
                    gameTitle);
                // gameTitle = "特殊铭牌奖励活动奖励花朵铭牌(140902)";// 内网测试用，上外网后务必将之注释
                boolean reciveGiftResult = reciveGift(account, gameTitle,
                    roleBind.getServerCode());
                if (reciveGiftResult) {
                    // 称号发放成功，则更新用户绑定信息
                    roleBindBeanBll.updateUserReceivedTitle(account);
                    result.setIsSuccess(true);
                    result.setMessage("领取称号成功！");
                    return result;
                } else {
                    result.setIsSuccess(false);
                    result.setMessage(Constants.FRIENDLY_PROMPT);
                    return result;
                }
            } else {
                result.setIsSuccess(false);
                result.setMessage("您已领取过了称号了！");
                return result;
            }
        } catch (Exception e) {
            LOG.error(
                Constants.HD_NAME
                        + " [blessingCardBeanService-getGameTitle] catch exception: {}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<String>(false, "您的操作太频繁啦，请稍后再试！", null);
        }
    }

    /**
     * 
     * <p>
     * 获得玩家当前可用的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午3:10:50 新增
     *
     * @param roleBindBean
     * @param sqlSession
     * @return int
     */
    public int getUserAvailableLotteryTimes(RoleBindBean roleBindBean,
            SqlSession sqlSession) {
        if (roleBindBean.getRemainingTimes() > 0) {
            // 如果玩家的“可获得抽奖次数”字段为true，说明该玩家并没有通过后台审核祝福卡获得过抽奖次数，则玩家当前的可用抽奖次数必来自玩拼图游戏，该抽奖次数为1次
            if (roleBindBean.getEnableGetLotteryTimes()) {
                return roleBindBean.getRemainingTimes();
            }
            BlessingCardBean blessingCardBean = new BlessingCardBean();
            if (sqlSession != null) {
                blessingCardBean = getBlessingCardByAccount(
                    roleBindBean.getAccount(), sqlSession);
            } else {
                blessingCardBean = getBlessingCardByAccount(roleBindBean
                        .getAccount());
            }
            LOG.info(
                "BlessingCardBeanServiceImpl[getUserAvailableLotteryTimes] invoke BlessingCardBeanService[getBlessingCardByAccount]，result blessingCardBean: {}",
                new Object[] { JSONObject.fromObject(blessingCardBean) });
            if (blessingCardBean == null) {
                return 0;
            }
            Date nowDate = new Date();
            long difftime = (nowDate.getTime() - blessingCardBean
                    .getVerifyTime().getTime()) / 1000;// 祝福卡审核时间与当前时间的时间差
            // 当前时间与祝福卡审核时间的时间差大于或等于60秒，则玩家当前的可用抽奖次数是remaining_times字段值
            int remainingTimes = roleBindBean.getRemainingTimes();
            if (difftime >= 60) {
                return remainingTimes;
            } else {
                // 如果时间差在60秒内，审核状态如果是拒绝，那么可以直接返回当前的剩余抽奖次数
                if (blessingCardBean.getVerifyStatus() == Constants.BLESSINGCARD_REFUSED) {
                    return remainingTimes;
                }
                int userRegisterYear = roleBindBean.getRegisterYear();
                // 根据注册年份得到相应的抽奖次数
                int lotteryTimesByRegisterYear = getLotteryTimesByRegisterYear(userRegisterYear);
                return (remainingTimes - lotteryTimesByRegisterYear) > 0 ? remainingTimes
                        - lotteryTimesByRegisterYear
                        : 0;
            }
        }

        return 0;
    }

    /**
     * 
     * <p>
     * 玩家通过玩拼图游戏获取抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午10:35:36 新增
     *
     * @param account
     * @return ResultBean<String>
     */
    public ResultBean<String> playPuzzleGameToGetLotteryTime(String account) {
        ResultBean<String> result = new ResultBean<String>();
        try (DistributedLock lock = new DistributedLock(Constants.HD_CODE + "_"
                + "playPuzzleGameToGetLotteryTime" + "_" + account)) {
            lock.weakLock(30, 5);
            RoleBindBean roleBind = roleBindBeanBll
                    .getRoleBindBeanByAccount(account);
            if (roleBind == null) {
                result.setIsSuccess(false);
                result.setMessage("请先绑定角色信息！");
                return result;
            }

            boolean enableGetLotteryTimeByPlayGame = false;// 玩家是否可以通过玩拼图游戏获得抽奖机会
            int currentLotteryTimes = roleBind.getLotteryTimes();// 玩家当前总的抽奖次数
            // 如果玩家的总抽奖次数为0，则判定此玩家没有通过玩拼图游戏获得抽奖机会，那么给予1次抽奖机会
            if (currentLotteryTimes == 0) {
                LOG.info("玩家当前的总抽奖次数为：" + currentLotteryTimes);
                enableGetLotteryTimeByPlayGame = true;
            }
            if (currentLotteryTimes > 0 && !roleBind.getEnableGetLotteryTimes()) {
                LOG.info("玩家当前的总抽奖次数大于0，而且“可通过注册年份获得抽奖次数”属性值为false");
                int userRegisterYear = roleBind.getRegisterYear();
                // 根据注册年份得到相应的抽奖次数
                int lotteryTimesByRegisterYear = getLotteryTimesByRegisterYear(userRegisterYear);
                // 如果玩家当前的总抽奖次数减去玩家通过注册年份获得的抽奖次数，为0，则说明玩家没有通过玩游戏获得过抽奖次数
                if ((currentLotteryTimes - lotteryTimesByRegisterYear) == 0) {
                    enableGetLotteryTimeByPlayGame = true;
                }
            }

            // 玩家可获得抽奖次数
            if (enableGetLotteryTimeByPlayGame) {
                int lotteryTimesUpdating = roleBind.getLotteryTimes() + 1;
                int remainingTimesUpdating = roleBind.getRemainingTimes() + 1;

                LOG.info(
                    Constants.HD_NAME
                            + "[start invoke roleBindBeanBll.updateUserLotteryTimesAfterPlayGame], params  account: {},lotteryTimesUpdating: {},remainingTimesUpdating: {}",
                    new Object[] { account, lotteryTimesUpdating,
                            remainingTimesUpdating });
                roleBindBeanBll.updateUserLotteryTimesAfterPlayGame(account,
                    lotteryTimesUpdating, remainingTimesUpdating);
                result.setIsSuccess(true);
                result.setMessage("您获得了一次抽奖机会了！");
                LOG.info(
                    Constants.HD_NAME
                            + "[end invoke roleBindBeanBll.updateUserLotteryTimesAfterPlayGame], result : {}",
                    JSONObject.fromObject(result));
                return result;
            } else {
                result.setIsSuccess(false);
                result.setMessage("您已获得过了抽奖机会了！");
                return result;
            }
        } catch (Exception e) {
            LOG.error(
                Constants.HD_NAME
                        + " [blessingCardBeanService-playPuzzleGameToGetLotteryTime] catch exception: {}",
                Throwables.getStackTraceAsString(e));
            return new ResultBean<String>(false, "您的操作太频繁啦，请稍后再试！", null);
        }
    }

    /**
     * 
     * <p>
     * 根据注册年份得到相应的抽奖次数
     * </p>
     *
     * @action laixiancai 2017年3月15日 下午8:45:58 新增
     *
     * @param userRegisterYear
     * @return int
     */
    public int getLotteryTimesByRegisterYear(int userRegisterYear) {
        int lotteryTimes = 1;// 祝福卡审核通过后，玩家通过注册年份获得的抽奖次数，最少为1次
        if ((2017 - userRegisterYear) > 1) {
            lotteryTimes = 2017 - userRegisterYear;
            // 如果玩家账号注册年份是2006年以前，比如2005，给的抽奖机会也是11次
            if (lotteryTimes > 11) {
                lotteryTimes = 11;
            }
        }
        return lotteryTimes;
    }

    /**
     * 
     * <p>
     * 祝福卡审核结果提示语模板
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午9:40:01 新增
     *
     * @param code
     * @param account
     * @param verifyType
     *            ，1表示审核通过，-1表示审核拒绝
     * @param verifyResult
     * @return String
     */
    private String resultMsgTemplate(int code, String account, int verifyType,
            String verifyResult) {
        return MessageFormat.format("祝福卡编号【{0}】，玩家账号【{1}】，操作类型【{2}】，操作结果【{3}】",
            code, account, verifyType == 1 ? "审核通过" : "审核拒绝", verifyResult);
    }

    /**
     * 
     * <p>
     * 祝福卡审核结果提示语模板
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午9:52:55 新增
     *
     * @param code
     * @param verifyDesc
     * @param verifyResult
     * @return String
     */
    private String resultMsgTemplate(int code, String verifyDesc,
            String verifyResult) {
        return MessageFormat.format("祝福卡编号【{0}】，操作类型【{1}】，操作结果【{2}】", code,
            verifyDesc, verifyResult);
    }

    /**
     * 
     * <p>
     * 祝福卡审核-通过
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午9:01:31 新增
     *
     * @param code
     * @param sqlSession
     *            void
     */
    private void passBlessingCard(int code, String account,
            RoleBindBean roleBindBean, SqlSession sqlSession) {
        if (roleBindBean.getEnableGetLotteryTimes()) {
            LOG.info("玩家可获得抽奖次数，roleBindBean：{}",
                new Object[] { JSONObject.fromObject(roleBindBean) });
            // 如果enable_get_lottery_times字段为true，则表示可以获得抽奖次数
            int userRegisterYear = roleBindBean.getRegisterYear();
            // 根据注册年份得到相应的抽奖次数
            int lotteryTimesByRegisterYear = getLotteryTimesByRegisterYear(userRegisterYear);
            int lotteryTimesUpdating = roleBindBean.getLotteryTimes()
                    + lotteryTimesByRegisterYear;// 加
            int remainingTimesUpdating = roleBindBean.getRemainingTimes()
                    + lotteryTimesByRegisterYear;// 加
            LOG.info(
                "invoke roleBindBeanBll.updateUserLotteryTimes, params account: {}, lotteryTimes: {}, remainingTimes: {}, enableGetLotteryTimes: {}",
                new Object[] { account, lotteryTimesUpdating,
                        remainingTimesUpdating, false });
            roleBindBeanBll
                    .updateUserLotteryTimes(account, lotteryTimesUpdating,
                        remainingTimesUpdating, false, sqlSession);
        }
    }

    /**
     * 
     * <p>
     * 祝福卡审核-拒绝
     * </p>
     *
     * @action laixiancai 2017年3月13日 下午9:02:50 新增
     *
     * @param code
     * @param sqlSession
     *            void
     */
    private void refuseBlessingCard(int code, String account,
            RoleBindBean roleBindBean, SqlSession sqlSession) {
        int userRegisterYear = roleBindBean.getRegisterYear();
        // 根据注册年份得到相应的抽奖次数
        int lotteryTimesByRegisterYear = getLotteryTimesByRegisterYear(userRegisterYear);
        int lotteryTimesUpdating = roleBindBean.getLotteryTimes()
                - lotteryTimesByRegisterYear;// 减
        int remainingTimesUpdating = roleBindBean.getRemainingTimes()
                - lotteryTimesByRegisterYear;// 减

        LOG.info(
            "invoke roleBindBeanBll.updateUserLotteryTimes, params account: {}, lotteryTimes: {}, remainingTimes: {}, enableGetLotteryTimes: {}",
            new Object[] { account, lotteryTimesUpdating,
                    remainingTimesUpdating, true });
        // 收回玩家之前通过注册年份获得的抽奖次数后，需要把用户信息表的enable_get_lottery_times字段置为true
        roleBindBeanBll.updateUserLotteryTimes(account, lotteryTimesUpdating,
            remainingTimesUpdating, true, sqlSession);
    }

    /**
     * 
     * <p>
     * 祝福卡审核通过/不通过的业务流程： 1.判断祝福卡和用户绑定信息是否存在，如果不存在则返回相关提示信息； 2.获取祝福卡的审核状态，
     * 与当前请求的审核操作类型作比较，如果一样（都是通过或拒绝），则返回相关提示（比如“审核失败，该祝福卡已审核通过”）； 3.审核类型是“审核通过”，
     * 先判断用户信息表enable_get_lottery_times字段是否为true， 如果为true，
     * 则更新用户信息表wdblessingcard_role_bind_tb的lottery_times和remaining_times两个字段
     * （当前的数量加上根据注册年份获取到的数量），并将enable_get_lottery_times字段置为false，
     * 更新祝福卡表wdblessingcard_blessing_card_tb的verify_status
     * 、verify_time、verify_admin
     * ；4.审核类型是“审核拒绝”，判断祝福卡表的verify_time值与当期时间是否相差大于一分钟，如果大于
     * ，则更新祝福卡表wdblessingcard_blessing_card_tb的verify_status
     * 、verify_time、verify_admin；如果小于，
     * 则更新用户信息表wdblessingcard_role_bind_tb的lottery_times和remaining_times两个字段
     * （当前的数量减去根据注册年份获取到的数量），并将enable_get_lottery_times字段置为true，
     * 同时更新祝福卡表wdblessingcard_blessing_card_tb的verify_status
     * 、verify_time、verify_admin
     * </p>
     *
     * @action laixiancai 2017年3月11日 下午3:19:12 新增
     *
     * @param code
     * @param verifyStatus
     *            ，1表示祝福卡通过审核，-1表示祝福卡不通过审核
     * @param verifyAdmin
     * @return ResultBean<String>
     */
    private String verifyBlessingCardSingle(int code, int verifyStatus,
            String verifyAdmin) {
        LOG.info(
            "进入Service[verifyBlessingCardSingle]，请求参数code：{}, verifyStatus：{}, verifyAdmin：{}",
            new Object[] { code, verifyStatus, verifyAdmin });
        String resultMsg = "";
        SqlSession sqlSession = myBatisBaseDAO.getSession();
        try {
            // 验证祝福卡和玩家绑定数据是否存在
            BlessingCardBean blessingCardBean = blessingCardBeanBll
                    .getBlessingCardByCodeOa(code, sqlSession);
            if (blessingCardBean == null) {
                resultMsg = resultMsgTemplate(code, "审核祝福卡", "审核失败，祝福卡信息不存在");
                return resultMsg;
            }

            String account = blessingCardBean.getAccount();
            RoleBindBean roleBindBean = roleBindBeanBll
                    .getRoleBindBeanByAccount(account, sqlSession);
            if (roleBindBean == null) {
                resultMsg = resultMsgTemplate(code, "审核祝福卡", "审核失败，用户绑定信息不存在");
                return resultMsg;
            }

            int currentStatus = blessingCardBean.getVerifyStatus();// 当前祝福卡的审核状态
            // 如果当前祝福卡的审核状态不是“待审核”，而且与审核操作类型一样，则不继续后面的审核流程
            if (currentStatus != Constants.BLESSINGCARD_VERIFYPENDING
                    && currentStatus == verifyStatus) {
                resultMsg = resultMsgTemplate(code, account, verifyStatus,
                    "该记录已经审核");
                return resultMsg;
            }

            // 祝福卡审核通过
            if (verifyStatus == Constants.BLESSINGCARD_VERIFYED) {
                passBlessingCard(code, account, roleBindBean, sqlSession);
            }

            // 祝福卡审核不通过
            if (verifyStatus == Constants.BLESSINGCARD_REFUSED) {
                // 判断审核时间是否为null，如果为null，则该记录没有被审核过，不需要做收回抽奖次数的业务逻辑
                if (blessingCardBean.getVerifyTime() != null) {
                    Date nowDate = new Date();
                    long difftime = (nowDate.getTime() - blessingCardBean
                            .getVerifyTime().getTime()) / 1000;// 祝福卡审核时间与当前时间的时间差
                    // 祝福卡审核时间与当前时间的时间差在60秒内，且玩家的抽奖次数大于0才需要收回玩家的通过注册年份获得的抽奖次数
                    if (difftime < 60 && roleBindBean.getLotteryTimes() > 0
                            && roleBindBean.getRemainingTimes() > 0) {
                        refuseBlessingCard(code, account, roleBindBean,
                            sqlSession);
                    }
                }
            }
            // 更新wdblessingcard_blessing_card_tb表的相应字段
            SimpleDateFormat dfs = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss.SSS");// 精确到毫秒
            blessingCardBeanBll.verifyBlessingCard(code, verifyStatus,
                verifyAdmin, dfs.format(new Date()), sqlSession);
            // 提交事务
            sqlSession.commit(true);

            if (verifyStatus == Constants.BLESSINGCARD_VERIFYED) {
                resultMsg = resultMsgTemplate(code, account, 1, "祝福卡已通过");
            } else {
                resultMsg = resultMsgTemplate(code, account, -1, "祝福卡已拒绝");
            }

            LOG.info("End Service[verifyBlessingCardSingle]");
            return resultMsg;
        } catch (Exception e) {
            sqlSession.rollback();
            LOG.error(
                "invoke Service[verifyBlessingCardSingle] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            resultMsg = resultMsgTemplate(code, "审核祝福卡", "审核失败，系统异常");
            return resultMsg;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 
     * <p>
     * OA后台获取祝福卡列表
     * </p>
     *
     * @action laixiancai 2017年3月10日 上午11:59:12 新增方法
     *
     * @param beginTime
     * @param endTime
     * @param account
     * @param verifyStatus
     * @param pageSize
     * @param pageNo
     * @return List<BlessingCardBean>
     */
    public ResultBeanWithPage<List<BlessingCardBean>> getBlessingCardList(
            String beginTime, String endTime, String account,
            String verifyStatus, int pageSize, int pageNo) {
        ResultBeanWithPage<List<BlessingCardBean>> result = new ResultBeanWithPage<>();
        List<BlessingCardBean> list = new ArrayList<BlessingCardBean>();
        int count = 0;

        // 总数
        count = blessingCardBeanBll.getBlessingCardCount(beginTime, endTime,
            account, verifyStatus);

        // 数据列表
        list = blessingCardBeanBll.getBlessingCardList(beginTime, endTime,
            account, verifyStatus, pageSize, pageNo);
        result.setProperties(true, "查询成功", list, count);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageBean<BlessingCardBean> getVerifyedBlessingCardPaging(
            QueryBean queryBean) {
        // 生成缓存key
        String memKeyCommon = queryBean.getTitle() + "_"
                + queryBean.getRegisterYearFrom() + "_"
                + queryBean.getRegisterYearEnd() + "_" + queryBean.getOrderBy()
                + "_" + queryBean.getRegisterYear();
        if (!Text.isNullOrEmpty(queryBean.getRoleName())) {
            memKeyCommon = memKeyCommon + "_" + queryBean.getRoleName();
        }

        // 必须为已审核状态
        String memKeyTotalCount = memKeyCommon + "_memKeyTotalCount";
        String totalCountMem = memcachedClientAdapter.get(memKeyTotalCount,
            String.class);
        if (Text.isNullOrEmpty(totalCountMem)) {
            int totalCount = blessingCardBeanBll.getVerifyedBlessingCardCount(
                queryBean.getTitle(), Constants.BLESSINGCARD_VERIFYED,
                queryBean.getRegisterYearFrom(),
                queryBean.getRegisterYearEnd(), queryBean.getRoleName(),
                queryBean.getOrderBy(), queryBean.getRegisterYear());

            totalCountMem = String.valueOf(totalCount);
            // 放入缓存中
            memcachedClientAdapter.set(memKeyTotalCount, 60, totalCountMem);
        }

        String memKeyBlessingCardList = memKeyCommon + "_"
                + queryBean.getPage() + "_" + queryBean.getSize()
                + "_memKeyBlessingCardList";
        List<BlessingCardBean> list = memcachedClientAdapter.get(
            memKeyBlessingCardList, List.class);
        if (list == null) {
            list = blessingCardBeanBll.getVerifyedBlessingCardPaging(
                queryBean.getTitle(), Constants.BLESSINGCARD_VERIFYED,
                queryBean.getPage(), queryBean.getSize(),
                queryBean.getRegisterYearFrom(),
                queryBean.getRegisterYearEnd(), queryBean.getRoleName(),
                queryBean.getOrderBy(), queryBean.getRegisterYear());
            // 放入缓存中
            if (list != null && list.size() > 0) {
                memcachedClientAdapter.set(memKeyBlessingCardList, 60, list);
            }
        }

        int totalCount2 = (totalCountMem != null && totalCountMem != "") ? Integer
                .valueOf(totalCountMem) : 0;
        PageBean<BlessingCardBean> pageBean = PageBean.createPage(totalCount2,
            queryBean.getPage(), queryBean.getSize(), list);

        return pageBean;
    }

    @Override
    public BlessingCardBean getBlessingCardByAccount(String account) {
        return blessingCardBeanBll.getBlessingCardByAccount(account);
    }

    /**
     * <p>
     * 按用户账号查询祝福卡
     * </p>
     *
     * @action laixiancai 2017年3月11日 上午11:52:15 新增
     *
     * @param account
     * @param sqlSession
     * @return BlessingCardBean
     */
    public BlessingCardBean getBlessingCardByAccount(String account,
            SqlSession sqlSession) {
        return blessingCardBeanBll
                .getBlessingCardByAccount(account, sqlSession);
    }

    @Override
    public int insert(BlessingCardBean blessingCard) {
        return blessingCardBeanBll.insert(blessingCard);
    }

    /**
     * <p>
     * 给祝福卡点赞
     * </p>
     *
     * @action niushuai 2017年3月12日 下午8:23:17 描述
     *
     * @param account
     *            点赞的用户
     * @param code
     *            祝福卡编号
     * @param ip
     */
    public ResultBean<String> upvoteBlessing(String account,
            Integer blessingCardCode, String ip) {
        ResultBean<String> result = new ResultBean<String>();

        SqlSession sqlSession = myBatisBaseDAO.getSession();
        try {
            try (DistributedLock lock = new DistributedLock(Constants.HD_CODE
                    + "_" + "upvoteBlessing" + "_" + account + "_"
                    + blessingCardCode)) {
                lock.weakLock(30, 5);
                RoleBindBean roleBind = roleBindBeanBll
                        .getRoleBindBeanByAccount(account, sqlSession);
                if (roleBind == null) {
                    result.setIsSuccess(false);
                    result.setMessage("请先绑定角色信息！");
                    return result;
                }

                if (roleBind.getUpvoteTimes() < 1) {
                    result.setIsSuccess(false);
                    result.setMessage("你没有点赞机会了！");
                    return result;
                }

                blessingCardBeanBll.upvoteBlessing(roleBind, blessingCardCode,
                    ip, sqlSession);
                // 提交事务
                sqlSession.commit(true);
                result.setIsSuccess(true);
                result.setMessage("点赞成功！");
                return result;
            } catch (Exception e) {
                LOG.error(
                    Constants.HD_NAME
                            + " [BlessingCardBeanServiceImpl-upvoteBlessing] catch exception: {}",
                    Throwables.getStackTraceAsString(e));
                return new ResultBean<String>(false, "您的操作太频繁啦，请稍后再试！", null);
            }
        } catch (Exception e) {
            sqlSession.rollback();
            LOG.error(
                "invoke BlessingCardBeanServiceImpl[upvoteBlessing] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("服务器繁忙，请稍后再试！");
            return result;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 
     * <p>
     * 微信端点赞
     * </p>
     *
     * @action laixiancai 2017年3月16日 下午8:16:27 新增
     *
     * @param openId
     * @param blessingCardCode
     * @param ip
     *            ResultBean<String>
     */
    public ResultBean<String> wxVote(String openId, Integer blessingCardCode,
            String ip) {
        ResultBean<String> result = new ResultBean<String>();

        SqlSession sqlSession = myBatisBaseDAO.getSession();
        try {
            try (DistributedLock lock = new DistributedLock(Constants.HD_CODE
                    + "_" + "wxVote" + "_" + openId + "_" + blessingCardCode)) {
                lock.weakLock(30, 5);

                BlessingCardBean blessingCardBean = blessingCardBeanBll
                        .getBlessingCardByCode(blessingCardCode);
                if (blessingCardBean == null) {
                    result.setIsSuccess(false);
                    result.setMessage("祝福卡信息不存在！");
                    return result;
                }

                int count = blessingCardBeanBll.getVoteCountByAccount(openId,
                    sqlSession);
                if (count > 0) {
                    result.setIsSuccess(false);
                    result.setMessage("一天只能点赞一次哦！");
                    return result;
                }

                blessingCardBeanBll.wxVote(openId, blessingCardCode, ip,
                    sqlSession);
                // 提交事务
                sqlSession.commit(true);
                result.setIsSuccess(true);
                result.setMessage("点赞成功！");
                return result;
            } catch (Exception e) {
                LOG.error(
                    Constants.HD_NAME
                            + " [BlessingCardBeanServiceImpl-wxVote] catch exception: {}",
                    Throwables.getStackTraceAsString(e));
                return new ResultBean<String>(false, "您的操作太频繁啦，请稍后再试！", null);
            }
        } catch (Exception e) {
            sqlSession.rollback();
            LOG.error(
                "invoke BlessingCardBeanServiceImpl[wxVote] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("服务器繁忙，请稍后再试！");
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateBlessingCard(BlessingCardBean blessingCard) {
        blessingCardBeanBll.updateBlessingCard(blessingCard);
    }

}
