/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/17 15:02
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wechatvideo;

import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.bll.wechatvideo.WeChatVideoRoleBindBll;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoLotteryPrizesBeanDao;

/**
 * <p>
 * 问道周年视频祝福活动抽奖服务类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WechatVideoLotteryPrizesService {
    GYYXLogger LOG = GYYXLoggerFactory
            .getLogger(WechatVideoLotteryPrizesService.class);
    private static final SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
            .getSqlActionDBV2SessionFactory();
    // 角色绑定业务
    private static final WeChatVideoRoleBindBll weChatVideoRoleBindBll = new WeChatVideoRoleBindBll();
    // 奖品服务
    private static final WeChatVideoPrizeService prizeService = new WeChatVideoPrizeService();
    // 抽奖数据访问
    private static final WeChatVideoLotteryPrizesBeanDao lotteryPrizesBeanDao = new WeChatVideoLotteryPrizesBeanDao();
    // 抽奖日志数据访问
    private static final IActionLotteryLogDAO actionLotteryLogDao = new ActionLotteryLogDAOImpl();
    // 抽奖服务
    private static final NewlotteryService newlotterService = new NewlotteryService();
    // 游戏相关服务类
    private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();

    /**
     * <p>
     * 问道周年视频纪念活动抽奖接口
     * </p>
     *
     * @action tanjunkai 2017年3月22日 下午3:58:24 描述
     *
     * @param actionCode
     *            活动编号
     * @param userInfo
     *            用户角色绑定信息
     * @param ip
     *            当前访问IP
     * @return ResultBean<UserLotteryBean>
     */
    public ResultBean<UserLotteryBean> doLottery(int actionCode, String openId,
            String ip) {
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();

        WeChatVideoRoleBindBean userInfo = null;
        // 开分布式锁 446_lottery_openId
        DistributedLock lock = new DistributedLock(
                Constants.LOTTERY_PRIFIX + openId);
        SqlSession session = null;
        try {
            lock.weakLock(65, 30);
            // 获取用户绑定信息
            userInfo = weChatVideoRoleBindBll.getUserBindRoleByOpenId(openId);
            // 抽奖资格的判断
            result = this.userLotteryJudge(userInfo);
            if (!result.getIsSuccess())
                return result;
            session = sqlSessionFactory.openSession();
            /*
             * 先查出当前活动所有的奖品信息,从奖品信息中过滤掉奖品中可以重复抽到的奖品 只保留不可以重复抽取的奖品信息，拿到奖品id的列表。
             * 
             * 再用这些奖品id列表去查询用户的中奖信息，如果用户已经中过这些奖， 则直接给用户返回谢谢参与奖
             */
            List<PrizeBean> prizes = prizeService
                    .getPrizes(Constants.ACTIVITY_ID);
            // 奖品编号,用于查询用户是否已中了大奖
            List<Integer> noReapetPrizeCodeList = new ArrayList<Integer>();
            PrizeBean thanksPrize = null;
            for (PrizeBean prize : prizes) {
                if (prize.getIsAvailable() != 0) {
                    // 过滤谢谢参与\花朵铭牌
                    if (!Constants.PRIZE_FLOWERS_NAME.equals(prize.getChinese())
                            && !Constants.PRIZE_THANKS_NAME
                                    .equals(prize.getChinese())) {
                        noReapetPrizeCodeList.add(prize.getCode());
                    }
                    if (thanksPrize == null && Constants.PRIZE_THANKS_NAME
                            .equals(prize.getChinese())) {
                        thanksPrize = prize;
                    }
                }
            }
            // 查看用户中的不可重复奖的次数 【相关表 action_lottery_log_tb】
            int count = lotteryPrizesBeanDao.getUserLotteryCount(
                Constants.ACTIVITY_ID, userInfo.getUserId(),
                noReapetPrizeCodeList);
            if (count == 0) {
                // 没中不可重复中的奖->进行抽奖
                result = newlotterService.lotteryByDataBase(
                    Constants.ACTIVITY_ID, userInfo.getUserId(), "byChance",
                    userInfo.getWechatAccount(), userInfo.getServerName(),
                    userInfo.getServerCode(), ip, thanksPrize, session);
            } else {
                // 用户已经中过不可重复中的奖品，返回谢谢参与
                UserLotteryBean bean = new UserLotteryBean();
                bean.setUserCode(userInfo.getUserId());
                if (null != thanksPrize) {
                    bean.setPrizeCode(thanksPrize.getCode());
                    bean.setPrizeChinese(thanksPrize.getChinese());
                    bean.setPrizeEnglish(thanksPrize.getEnglish());
                    bean.setConfigCode(thanksPrize.getNum());
                    bean.setIsReal(thanksPrize.getIsReal());
                    bean.setConfigCode(thanksPrize.getNum());
                } else {
                    bean.setPrizeCode(-1);
                    bean.setPrizeChinese("谢谢参与");
                    bean.setPrizeEnglish("thanks");
                    bean.setIsReal("false");
                }
                result.setData(bean);
                result.setIsSuccess(true);
                result.setMessage(bean.getPrizeChinese());
            }
            // 减少抽奖次数
            if (weChatVideoRoleBindBll
                    .reduceLotteryTimes(userInfo.getUserId()) > 0) {
                // 返回时带着剩余抽奖次数(更新成功抽奖次数减1,共前端显示)
                result.setTotal(userInfo.getRemainingTimes() - 1);
            } else {
                // 返回时带着剩余抽奖次数(更新失败是抽奖次数不变,供前端显示)
                result.setTotal(userInfo.getRemainingTimes());
            }
            // 记录
            LOG.info(String.format(
                "活动名称：[%s]，活动编号：[%s]，用户[%s]，奖品信息:[%s], resultBeanInfo:[%s]",
                Constants.ACTIVITY_NAME, Constants.ACTIVITY_ID,
                userInfo.getAccount(), result.getData().getPrizeChinese(),
                result));
            // 记录抽奖结果到抽奖记录日志表【action_lottery_log_tb】
            ActionLotteryLogPO log = new ActionLotteryLogPO();
            log.setActivityId(Constants.ACTIVITY_ID);
            log.setUserId(userInfo.getUserId().toString());
            log.setGameId(Constants.GAMEID + "");
            log.setServerId(userInfo.getServerCode().toString());
            log.setPrizeType(result.getData().getIsReal());
            log.setPrizeCode(result.getData().getPrizeCode());
            log.setPrizeName(result.getData().getPrizeChinese());
            log.setPrizeNum(1); // 奖品数量
            log.setCardCode(result.getData().getCardCode());
            log.setWinningAt(new Date());
            log.setWinningIp(ip);
            log.setIsAvailable(result.getData().getIsAvailable());
            log.setActivityType(Constants.ACTIVITY_TYPE);
            actionLotteryLogDao.putData(log, session);
            // 发送到游戏时如果失败也要回滚
            if (result.getIsSuccess()) {
                session.commit();
            } else {
                session.rollback();
            }
        } catch (DLockException e) {
            LOG.error(Constants.ERROR_LOG + "关闭分布式锁失败！异常：{}",
                Throwables.getStackTraceAsString(e));
            if (session != null)
                session.rollback();
            result.setIsSuccess(false);
            result.setMessage("人太多了，请稍后再试！");
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "抽奖失败！{}",
                Throwables.getStackTraceAsString(e));
            if (session != null)
                session.rollback();
            result.setIsSuccess(false);
            result.setMessage("人太多了，请稍后再试！");
        } finally {
            if (session != null)
                session.close();
            try {
                if (lock != null)
                    lock.close();
            } catch (DLockException e) {
                LOG.error(Constants.ERROR_LOG + "关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        // 发送奖品到游戏中，不考虑发送失败的情况,如果失败则人工补发
        // 这样可能会出现漏发的情况，但是不会出现多发的情况
        // 只有花朵铭牌才实时发送到游戏
        if (result.getIsSuccess() && result.getData().getPrizeChinese()
                .equals(Constants.PRIZE_FLOWERS_NAME) && userInfo != null) {
            reciveGiftToGame(result, userInfo.getServerCode(),
                userInfo.getAccount());
        }
        return result;
    }

    /***
     * 虚拟奖品发送到游戏
     * 
     * @param resultBean
     *            抽奖结果
     * @param serverCode
     *            区服编码
     * @param account
     *            账号
     * @return
     */
    private ResultBean<UserLotteryBean> reciveGiftToGame(
            ResultBean<UserLotteryBean> resultBean, int serverCode,
            String account) {
        String nameString = null;

        if ("HuaDuoMingPai".equals(resultBean.getData().getPrizeEnglish())) {
            nameString = "特殊铭牌奖励活动奖励花朵铭牌(140902)";
        }
        if (nameString != null) {
            boolean is = this.reciveGift(account, nameString, serverCode);
            // 奖品发放到游戏失败时
            if (!is) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("发放到游戏失败");
                LOG.error(Constants.ERROR_LOG + "抽奖发放到游戏时失败。{}", resultBean);
            }
        }
        return resultBean;
    }

    /**
     * <p>
     * 往游戏内发虚拟物品
     * </p>
     *
     * @action tanjunkai 2017年3月20日 下午2:54:41 描述
     *
     * @param account
     * @param giftPackage
     * @param serverCode
     * @return boolean
     */
    public boolean reciveGift(String account, String giftPackage,
            int serverCode) {
        boolean result = true;
        try {
            // 获取服务器信息
            ServerInfoBean serverInfo = callWebApiAgent
                    .getServerStatusFromWebApi(serverCode);
            // 发放物品
            CallWebServiceAgent.givePresents(2, account, giftPackage,
                "20250902235959", Constants.ACTIVITY_NAME, serverInfo);
        } catch (Exception e) {
            LOG.error(
                "invoke Service[WeChatVideoLotteryPrizesService-reciveGift] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            result = false;
        }
        return result;
    }

    /**
     * <p>
     * 用户抽奖资格的判断
     * </p>
     *
     * @action tanjunkai 2017年3月29日 下午6:59:12 描述
     *
     * @param userInfo
     * @return ResultBean<UserLotteryBean>
     */
    private ResultBean<UserLotteryBean> userLotteryJudge(
            WeChatVideoRoleBindBean userInfo) {
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
        // 判断openId是否已绑定角色信息
        if (userInfo == null) {
            result.setIsSuccess(false);
            result.setMessage("用户未绑定角色信息");
            return result;
        }
        // 是否有抽奖次数
        if (userInfo.getRemainingTimes() < 1) {
            LOG.info(String.format("[%s]_用户：openId:[%s] 开始抽奖结果-> 抽奖机会已用尽",
                Constants.ACTIVITY_NAME, userInfo.getOpenId()));
            result.setIsSuccess(false);
            result.setMessage("您的抽奖机会用完了！");
            return result;
        }
        result.setIsSuccess(true);
        return result;
    }
}