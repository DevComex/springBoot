/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：niushuai
 * @联系方式：niushuai@gyyx.cn
 * @创建时间：2017年3月13日 下午7:37:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdblessingcard2017.impl;

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
import cn.gyyx.action.beans.wdblessingcard2017.Constants;
import cn.gyyx.action.beans.wdblessingcard2017.LotteryPrizesBean;
import cn.gyyx.action.beans.wdblessingcard2017.RoleBindBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.wdblessingcard2017.LotteryPrizesBeanDao;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.action.service.wdblessingcard2017.BlessingCardBeanService;
import cn.gyyx.action.service.wdblessingcard2017.LotteryPrizesBeanService;
import cn.gyyx.action.service.wdblessingcard2017.PrizeService;
import cn.gyyx.action.service.wdblessingcard2017.RoleBindBeanService;
import cn.gyyx.action.ui.wdblessingcard2017.QueryBean;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 中奖信息service实现
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class LotteryPrizesBeanServiceImpl implements LotteryPrizesBeanService {

    GYYXLogger LOG = GYYXLoggerFactory
            .getLogger(LotteryPrizesBeanServiceImpl.class);

    private static final LotteryPrizesBeanDao lotteryPrizesBeanDao = new LotteryPrizesBeanDao();
    private static final NewlotteryService newlotteryService = new NewlotteryService();
    private static final RoleBindBeanService roleBindBeanService = new RoleBindBeanServiceImpl();
    private static final PrizeService prizeService = new PrizeServiceImpl();
    private static final SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
            .getSqlActionDBV2SessionFactory();
    private static final IActionLotteryLogDAO actionLotteryLogDao = new ActionLotteryLogDAOImpl();
    private static final BlessingCardBeanService blessingCardBeanService = new BlessingCardBeanServiceImpl();

    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
            .getMemcache();

    @SuppressWarnings("unchecked")
    @Override
    public List<LotteryPrizesBean> getLotteryListByAccount(String account,
            int actionCode) {
        QueryBean queryBean = new QueryBean();
        queryBean.setAccount(account);
        queryBean.setActionCode(actionCode);

        String memKey = account + actionCode + "_getLotteryListByAccount";
        List<LotteryPrizesBean> list = memcachedClientAdapter.get(memKey,
            List.class);
        if (list == null) {
            list = lotteryPrizesBeanDao.getLotteryListByAccount(queryBean);
            // 放入缓存中
            if (list != null) {
                memcachedClientAdapter.set(memKey, 60, list);
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LotteryPrizesBean> getLotteryList(int hdCode, int i) {
        QueryBean queryBean = new QueryBean();
        queryBean.setHdCode(hdCode);
        queryBean.setSize(i);
        queryBean.setActionCode(Constants.HD_CODE);
        String memKey = hdCode + "_getLotteryList";
        List<LotteryPrizesBean> list = memcachedClientAdapter.get(memKey,
            List.class);
        if (list == null) {
            list = lotteryPrizesBeanDao.getLotteryList(queryBean);
            // 放入缓存中
            if (list != null) {
                memcachedClientAdapter.set(memKey, 60, list);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.LotteryPrizesBeanService#
     * doLottery(int, java.lang.String)
     */
    @Override
    public ResultBean<UserLotteryBean> doLottery(int actionCode,
            UserInfo userInfo, String ip) {
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
        result.setIsSuccess(false);
        result.setMessage("人太多了，请稍后再试！");
        RoleBindBean roleBind = null;
        // 开分布式锁 活动ID+用户ID
        DistributedLock lock = new DistributedLock(Constants.LOTTERY_PRIFIX
                + userInfo.getUserId());
        try {
            lock.weakLock(65, 5);
            // 是否有抽奖次数
            roleBind = roleBindBeanService
                    .getRoleBindBeanByAccount(userInfo.getAccount());
            if (roleBind == null) {
                result.setIsSuccess(false);
                result.setMessage("您还没有绑定角色！");
                return result;
            }
            try (SqlSession session = sqlSessionFactory.openSession()) {
                if (blessingCardBeanService.getUserAvailableLotteryTimes(
                    roleBind, session) < 1) {
                    result.setIsSuccess(false);
                    result.setMessage("您的抽奖机会用完了！");
                    return result;
                }

                /*
                 * 是否已中大奖？ 先查出当前活动所有的奖品信息,从奖品信息中过滤掉奖品中可以重复抽到的奖品
                 * 只保留不可以重复抽取的奖品信息，拿到奖品id的列表。
                 * 
                 * 再用这些奖品id列表去查询用户的中奖信息，如果用户已经中过这些奖， 则直接给用户返回一个小奖
                 */
                List<PrizeBean> prizes = prizeService
                        .getPrizes(Constants.HD_CODE);
                // 奖品编号,用于查询用户是否已中了大奖
                List<Integer> prizeCodeList = new ArrayList<Integer>();
                PrizeBean MPPrize = null; // 花朵铭牌，纪念奖
                for (PrizeBean prize : prizes) {
                    if (!Constants.SPECIFIED_PRIZE_NAME.equals(prize
                            .getChinese())
                            && !Constants.SPECIFIED_PRIZE_NAME_WULEILING
                                    .equals(prize.getChinese())) {
                        prizeCodeList.add(prize.getCode());
                    }
                    if (MPPrize == null
                            && Constants.SPECIFIED_PRIZE_NAME.equals(prize
                                    .getChinese())) {
                        MPPrize = prize;
                    }
                }
                if (MPPrize == null) {
                    result.setIsSuccess(false);
                    result.setMessage("您的抽奖机会用完了！");
                    LOG.error(Constants.ERROR_LOG
                            + "奖品没有变的话，肯定会有花朵铭牌的，所以这里是不可能进来的！");
                    return result;
                }
                int count = lotteryPrizesBeanDao.getUserLotteryCount(
                    Constants.HD_CODE, userInfo.getAccount(), ip, prizeCodeList);
                if (count == 0) {
                    // 没中大奖时按概率抽奖，并获取result
                    result = newlotteryService.lotteryByDataBase(
                        Constants.HD_CODE, userInfo.getUserId(), "byChance",
                        userInfo.getNickname(), roleBind.getServerName(),
                        roleBind.getServerCode(), ip, MPPrize, session);
                } else {
                    // 中大奖后再抽，只给纪念奖
                    UserLotteryBean bean = new UserLotteryBean();
                    bean.setUserCode(userInfo.getUserId());
                    bean.setPrizeCode(MPPrize.getCode());
                    bean.setPrizeChinese(MPPrize.getChinese());
                    bean.setPrizeEnglish(MPPrize.getEnglish());
                    bean.setConfigCode(MPPrize.getNum());
                    bean.setIsReal(Constants.PRIZE_TYPE_NOREALPRIZE);
                    // 纪念奖的返回信息
                    result.setData(bean);
                    result.setIsSuccess(true);
                    result.setMessage(bean.getPrizeChinese());
                }
                // 减少抽奖次数
                roleBind.setRemainingTimes(roleBind.getRemainingTimes() - 1);
                roleBindBeanService.updateRemainingTimes(roleBind, session);
                // 返回时带着可抽奖次数
                result.setTotal(roleBind.getRemainingTimes());
                // 记录
                LOG.info(String
                        .format(
                            "活动名称：[%s]，活动编号：[%s]，用户[%s]，奖品信息:[%s], resultBeanInfo:[%s]",
                            Constants.HD_NAME, Constants.HD_CODE, userInfo
                                    .getAccount(), result.getData()
                                    .getPrizeChinese(), result));
                // 记录抽奖结果
                ActionLotteryLogPO log = new ActionLotteryLogPO();
                log.setActivityId(Constants.HD_CODE);
                log.setUserId(userInfo.getAccount());
                log.setGameId(Constants.GAMEID + "");
                log.setServerId(roleBind.getServerCode().toString());
                log.setPrizeType(result.getData().getIsReal());
                log.setPrizeCode(result.getData().getPrizeCode());
                log.setPrizeName(result.getData().getPrizeChinese());
                log.setPrizeNum(1); // 奖品数量
                log.setCardCode(result.getData().getCardCode());
                log.setWinningAt(new Date());
                log.setWinningIp(ip);
                log.setIsAvailable(result.getData().getIsAvailable());
                log.setActivityType(Constants.HD_TYPE);
                actionLotteryLogDao.putData(log, session);
                if (result.getIsSuccess()) {
                    session.commit();
                } else {
                    session.rollback();
                }
            }
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "抽奖失败！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("人太多了，请稍后再试！");
        } finally {
            try {
                if(lock != null)lock.close();
            } catch (DLockException e) {
                LOG.error(Constants.ERROR_LOG + "关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        // 发送奖品到游戏中，不考虑发送失败的情况,如果失败则人工补发
        // 这样可能会出现漏发的情况，但是不会出现多发的情况
        if (result.getIsSuccess()) {
            reciveGiftToGame(result, roleBind.getServerCode(),
                userInfo.getAccount());
        }
        return result;
    }

    /***
     * 发奖到游戏里 针对抽奖
     * 
     * @param resultBean
     * @param serverCode
     * @param account
     * @return
     */
    private ResultBean<UserLotteryBean> reciveGiftToGame(
            ResultBean<UserLotteryBean> resultBean, int serverCode,
            String account) {
        String nameString = null;

        if ("8000yinyuanbao".equals(resultBean.getData().getPrizeEnglish())) {
            nameString = "2017年官网活动奖励8000银元宝礼包(161128)";
        } else if ("5000yinyuanbao".equals(resultBean.getData()
                .getPrizeEnglish())) {
            nameString = "2017年官网活动奖励5000银元宝礼包(161128)";
        } else if ("3000yinyuanbao".equals(resultBean.getData()
                .getPrizeEnglish())) {
            nameString = "2017年官网活动奖励3000银元宝礼包(161128)";
        } else if ("WuLeiLing".equals(resultBean.getData().getPrizeEnglish())) {
            nameString = "2017年官网活动奖励五雷令*1(161128)";
        } else if ("HuaDuoMingPai".equals(resultBean.getData()
                .getPrizeEnglish())) {
            nameString = "特殊铭牌奖励活动奖励花朵铭牌(140902)";
        }
        if (nameString != null) {
            boolean is = blessingCardBeanService.reciveGift(account,
                nameString, serverCode);
            // 奖品发放到游戏失败时
            if (!is) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("发放到游戏失败");
                LOG.error(Constants.ERROR_LOG + "抽奖发放到游戏时失败。{}", resultBean);
            }
        }
        return resultBean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.gyyx.action.service.wdblessingcard2017.LotteryPrizesBeanService#
     * prizeCount(java.lang.String, java.lang.String)
     */
    @Override
    public int prizeCount(String account, String prizeType) {
        String memKey = account + prizeType + "_prizeCount";
        String prizeCountMem = memcachedClientAdapter.get(memKey, String.class);
        if (prizeCountMem == null) {
            int prizeCount = prizeService.prizeCount(account, prizeType,
                Constants.HD_CODE);
            // 放入缓存中
            prizeCountMem = String.valueOf(prizeCount);
            memcachedClientAdapter.set(memKey, 60, prizeCountMem);
        }

        return (prizeCountMem != null && prizeCountMem != "") ? Integer
                .valueOf(prizeCountMem) : 0;
    }

}
