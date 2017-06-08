/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月10日 下午5:08:28
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdrankinglist2017.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdrankinglist2017.Constants;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.beans.wdrankinglist2017.LotteryPrizesBean;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.wdrankinglist2017.LotteryPrizesBeanDao;
import cn.gyyx.action.dao.wdrankinglist2017.RoleBindDao;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.action.service.wdrankinglist2017.CommonService;
import cn.gyyx.action.service.wdrankinglist2017.LotteryPrizesBeanService;
import cn.gyyx.action.service.wdrankinglist2017.RoleBindService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 抽奖相关service
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class LotteryPrizesBeanServiceImpl implements LotteryPrizesBeanService {
    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(DeclarationServiceImpl.class);

    MyBatisBaseDAO myBatisBaseDAO = new MyBatisBaseDAO();
    private RoleBindService roleBindService = new RoleBindServiceImpl();
    private CommonService commonService = new CommonServiceImpl();
    private NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
    private LotteryPrizesBeanDao lotteryPrizesBeanDao = new LotteryPrizesBeanDao();
    private NewlotteryService newlotteryService = new NewlotteryService();
    private RoleBindDao roleBindDao = new RoleBindDao();
    private IActionLotteryLogDAO actionLotteryLogDao = new ActionLotteryLogDAOImpl();
    private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcache = memcacheUtil.getMemcache();
    /**
     * 
     * <p>
     * 抽奖
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午5:07:38 描述
     *
     * @param openId
     * @param ip
     * @return ResultBean<UserLotteryBean>
     */
    public ResultBean<UserLotteryBean> doLottery(String openId, String ip) {
        LOG.info(
            "{}进入LotteryPrizesBeanServiceImpl[doLottery]，请求参数openId：{}, ip：{}",
            new Object[] { Constants.HD_NAME, openId, ip });
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();

        // 开分布式锁 活动ID+opendID
        DistributedLock lock = new DistributedLock(
                Constants.LOTTERY_PRIFIX + openId);
        try {
            lock.weakLock(65, 5);

            try (SqlSession sqlSession = myBatisBaseDAO.getSession()) {
                // 是否有抽奖机会
                ResultBean<Integer> resultIsCanLottery = roleBindService
                        .getLotteryTimes(openId, sqlSession);
                if (!resultIsCanLottery.getIsSuccess()) {
                    LOG.info("玩家{}没有抽奖机会", openId);
                    result.setIsSuccess(false);
                    result.setMessage(resultIsCanLottery.getMessage());
                    return result;
                }

                /*
                 * 是否已中大奖？ 先查出当前活动所有的奖品信息,从奖品信息中过滤掉奖品中可以重复抽到的奖品
                 * 只保留不可以重复抽取的奖品信息，拿到奖品id的列表。
                 * 
                 * 再用这些奖品id列表去查询用户的中奖信息，如果用户已经中过这些奖， 则直接给用户返回谢谢参与
                 */
                List<PrizeBean> prizes = userLotteryBll
                        .getPrize(Constants.HD_CODE, sqlSession);
                // 奖品编号,用于查询用户是否已中了大奖
                List<Integer> prizeCodeList = new ArrayList<Integer>();
                PrizeBean noPrize = null; // 本次活动中大奖后返回的奖品（谢谢参与）
                for (PrizeBean prize : prizes) {
                    if (!Constants.SPECIFIED_PRIZE_NAME_THANKS
                            .equals(prize.getChinese())
                            && !Constants.SPECIFIED_PRIZE_NAME_TONGQIANMINGPAI
                                    .equals(prize.getChinese())) {
                        prizeCodeList.add(prize.getCode());
                    }
                    if (noPrize == null && Constants.SPECIFIED_PRIZE_NAME_THANKS
                            .equals(prize.getChinese())) {
                        noPrize = prize;
                    }
                }
                RoleBindBean rolebind = roleBindDao
                        .getRoleBindBeanByOpenId(openId);
                int count = lotteryPrizesBeanDao.getUserLotteryCount(
                    Constants.HD_CODE, openId, prizeCodeList);

                if (count == 0) {
                    LOG.info("玩家{}没中过大奖，进入正常抽奖环节", openId);
                    // 没中大奖时按概率抽奖，并获取result
                    result = newlotteryService.lotteryByDataBase(
                        Constants.HD_CODE, 0, "byChance", openId,
                        commonService.getServerName(),
                        commonService.getServerCode(), ip, noPrize, sqlSession);

                } else {
                    LOG.info("玩家{}已中过大奖，返回谢谢参与", openId);
                    // 中大奖后再抽，只给纪念奖
                    UserLotteryBean bean = new UserLotteryBean();
                    bean.setUserCode(rolebind.getUserId());
                    bean.setPrizeCode(noPrize.getCode());
                    bean.setPrizeChinese(noPrize.getChinese());
                    bean.setPrizeEnglish(noPrize.getEnglish());
                    bean.setConfigCode(noPrize.getNum());
                    bean.setIsReal("noRealPrize");
                    // 纪念奖的返回信息
                    result.setData(bean);
                    result.setIsSuccess(true);
                    result.setMessage(bean.getPrizeChinese());
                }

                // 更新wdrankinglist_role_bind_tb表的lottery_time字段
                SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
                roleBindDao.updateUserLotteryTime(openId,
                    dfs.format(new Date()), sqlSession);
                // 返回时带着可抽奖次数
                result.setTotal(0);

                // 记录
                LOG.info(String.format(
                    "活动名称：[%s]，活动编号：[%s]，用户[%s]，奖品信息:[%s], resultBeanInfo:[%s]",
                    Constants.HD_NAME, Constants.HD_CODE, openId,
                    result.getData().getPrizeChinese(), result));
                // 记录抽奖结果
                ActionLotteryLogPO log = new ActionLotteryLogPO();
                log.setActivityId(Constants.HD_CODE);
                log.setUserId(openId);
                log.setGameId(Constants.GAMEID + "");
                log.setServerId(String.valueOf(commonService.getServerCode()));
                log.setPrizeType(result.getData().getIsReal());
                log.setPrizeCode(result.getData().getPrizeCode());
                log.setPrizeName(result.getData().getPrizeChinese());
                log.setPrizeNum(1); // 奖品数量
                log.setCardCode(result.getData().getCardCode());
                log.setWinningAt(new Date());
                log.setWinningIp(ip);
                log.setIsAvailable(result.getData().getIsAvailable());
                log.setActivityType(Constants.HD_TYPE);
                actionLotteryLogDao.putData(log, sqlSession);

                // 提交事务
                sqlSession.commit(true);
            }
        } catch (Exception e) {
            LOG.error(Constants.ERROR_LOG + "抽奖失败！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("人太多了，请稍后再试！");
            return result;
        } finally {
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
        // 本次冲榜活动只有铜钱铭牌才会即时发送到游戏内
        if (result.getIsSuccess() && result.getData().getPrizeChinese()
                .equals(Constants.SPECIFIED_PRIZE_NAME_TONGQIANMINGPAI)) {
            RoleBindBean rolebind = roleBindDao.getRoleBindBeanByOpenId(openId);
            String account = rolebind.getAccount();
            LOG.info("玩家{}获得铜钱铭牌，调用reciveGiftToGame进行发奖", account);
            reciveGiftToGame(result, commonService.getServerCode(), account);
        }
        return result;
    }

    /**
     * 
     * <p>
     * 发奖到游戏
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午6:59:58 描述
     *
     * @param resultBean
     * @param serverCode
     * @param account
     *            void
     */
    private void reciveGiftToGame(ResultBean<UserLotteryBean> resultBean,
            int serverCode, String account) {
        String prizeCodeToGame = "特殊铭牌奖励活动奖励铜钱铭牌(140902)";
        try {
            // 获取服务器信息
            ServerInfoBean serverInfo = callWebApiAgent
                    .getServerStatusFromWebApi(serverCode);
            // 发放物品
            CallWebServiceAgent.givePresents(2, account, prizeCodeToGame,
                "20250902235959", Constants.HD_NAME, serverInfo);
            LOG.info("{}调用service[reciveGiftToGame]发放铜钱铭牌结束，玩家账号是{}",
                new Object[] { Constants.HD_NAME, account });
        } catch (Exception e) {
            LOG.error(
                "invoke Service[LotteryPrizesBeanServiceImpl-reciveGiftToGame] catch a exception{}",
                Throwables.getStackTraceAsString(e));
            LOG.error(Constants.ERROR_LOG + "抽奖发放到游戏时失败。{}", resultBean);
        }
    }

    /**
     * 
     * <p>
     * 获取抽奖页面滚动中奖列表数据（200条）
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:10:38 描述
     *
     * @return List<LotteryPrizesBean>
     */
    public List<LotteryPrizesBean> getLotteryList() {
        // 缓存key
        String memKey = Constants.CACHE_PRIZES_SHOW;
        List<LotteryPrizesBean> resultData = memcache.get(memKey, List.class);
        if (resultData == null) {
            resultData = lotteryPrizesBeanDao.getLotteryList();
            if (resultData != null && resultData.size() > 0) {
                memcache.set(memKey, 60, resultData);
            }
        }
        return resultData;
    }

    /**
     * 
     * <p>
     * 根据openId获取当前玩家的中奖记录
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:05:32 描述
     *
     * @param openId
     * @return List<LotteryPrizesBean>
     */
    public List<LotteryPrizesBean> getLotteryListByOpenId(String openId) {
        return lotteryPrizesBeanDao.getLotteryListByOpenId(openId);
    }
}
