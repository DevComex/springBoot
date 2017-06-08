/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/5 11:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wish11th;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wish11th.Constants;
import cn.gyyx.action.beans.wish11th.Wish11thLightBean;
import cn.gyyx.action.beans.wish11th.Wish11thLotteryPrizesBean;
import cn.gyyx.action.beans.wish11th.Wish11thRoleBindBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishBean;
import cn.gyyx.action.beans.wish11th.Wish11thWishInfoBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wish11th.Wish11thLightBll;
import cn.gyyx.action.bll.wish11th.Wish11thUserBll;
import cn.gyyx.action.bll.wish11th.Wish11thWishBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.wish11th.Wish11thLotteryPrizesBeanDao;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DLockException.ExpType;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 许愿相关服务类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thWishService {

    // 许愿内容相关业务逻辑层
    private Wish11thWishBll wishBll = new Wish11thWishBll();
    // 用户相关业务逻辑层
    private Wish11thUserBll userBll = new Wish11thUserBll();
    // 许愿蛋糕相关业务逻辑层
    private Wish11thLightBll lightBll = new Wish11thLightBll();
    // 抽奖业务类
    private NewlotteryService lottery = new NewlotteryService();
    // 日志访问类
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(Wish11thWishService.class);

    // 抽奖数据访问
    private static final Wish11thLotteryPrizesBeanDao lotteryPrizesBeanDao = new Wish11thLotteryPrizesBeanDao();

    // 发奖服务
    private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    // 抽奖日志数据访问
    private static final IActionLotteryLogDAO actionLotteryLogDao = new ActionLotteryLogDAOImpl();

    // 缓存
    private MemcacheUtil memcacheUtil = new MemcacheUtil();
    private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
            .getMemcache();

    // 实例化事务
    private static SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession();
    }

    /**
     * <p>
     * 获取指定层的祝愿信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 上午11:38:33 描述
     *
     * @param level
     * @return ResultBean<List<WishBean>>
     */
    public ResultBean<List<Map<String, Object>>> getWishsBylevel(int level,
            int status) {
        ResultBean<List<Map<String, Object>>> resultBean = new ResultBean<>();
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            String num = "0";
            // 层级许愿列表缓存key
            String levelWishKey = Constants.ACTIVITY_ENGLISHNAME
                    + "_WishList_level" + level + "_" + status;
            // 缓存获取层级许愿列表
            list = memcachedClientAdapter.get(levelWishKey, List.class);
            if (null == list) {
                List<Wish11thWishBean> wishList = wishBll.getTopWishsBylevel(50,
                    level, status);
                if (null != wishList) {
                    list = new ArrayList<>();
                    for (Wish11thWishBean wish : wishList) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("roleName", wish.getRoleName());
                        data.put("content", wish.getContent());
                        list.add(data);
                    }
                    memcachedClientAdapter.set(levelWishKey, 180, list);
                }
            }

            // 缓存获取层级许愿人数Key
            String countLevelWishKey = Constants.ACTIVITY_ENGLISHNAME
                    + "_WishCount_level" + level + "_" + status;
            // 缓存获取层级许愿人数
            num = memcachedClientAdapter.get(countLevelWishKey, String.class);
            if (null == num) {
                // 获取当前层数所有许愿通过的人数
                num = wishBll.getWishUserCountByLevel(level,
                    Constants.WISH11TH_ALLSTATUS) + "";
                memcachedClientAdapter.set(countLevelWishKey, 180, num + "");
            }
            resultBean.setData(list);
            resultBean.setTotal(Integer.parseInt(num));
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取第" + level + "层许愿数据成功");
        } catch (Exception e) {
            logger.error(
                Constants.ERROR_LOG + "[getWishsBylevel]获取指定层的祝愿信息产生异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取第" + level + "层许愿数据失败");
        }
        return resultBean;
    }

    /**
     * <p>
     * 用户许愿
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午2:40:04 描述
     *
     * @param userId
     *            用户ID
     * @param level
     *            当前层
     * @param ip
     *            IP
     * @param content
     *            许愿内容
     * @return ResultBean<UserLotteryBean>
     */
    public ResultBean<UserLotteryBean> userWish(int userId, int level,
            String ip, String content) {
        Wish11thLightBean currentLight = null;
        Wish11thRoleBindBean userBindInfo = null;
        ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();

        DistributedLock userLock = new DistributedLock(
                Constants.ACTIVITY_ENGLISHNAME + "_" + Constants.ACTIVITY_TYPE
                        + "_" + userId + "_" + Constants.ACTIVITY_ID);
        try {
            logger.info("进入userLock,抽奖开始,userId:{}", new Object[] { userId });
            userLock.weakLock(70, 60);
            // 获取用户绑定信息
            userBindInfo = userBll.getUserByUserID(userId);
            if (null == userBindInfo) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("您还没有绑定角色！");
                resultBean.setStateCode(-5);
                return resultBean;
            }
            // 根据层级 获取对应的抽奖actioncode
            int lotteryActionCode = Constants.Wish11thMapperActionCode
                    .getActionCode(level);
            // 获取当前的层级信息
            currentLight = lightBll.getLightByLevel(level);
            int lightStatus = currentLight.getLightType();
            // 获取该用户在改层许愿需要消耗积分
            int needScore = this.consumeScoreRules(userId, level, lightStatus);
            int consumeScore = userBindInfo.getConsumeScore();
            int totalConsumeScore = needScore + consumeScore;
            // 获取用户剩余积分
            int score = userBindInfo.getScore();
            // 判断积分是否充足
            if (totalConsumeScore > score) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("您的当前积分不足！");
                resultBean.setStateCode(-4);
                return resultBean;
            }
            // 用户进行抽奖
            resultBean = this.lottery(userId, ip, lotteryActionCode,
                userBindInfo);
            if (!resultBean.getIsSuccess()) {
                return resultBean;
            }
            
            // 更新用户积分消耗信息
            userBll.updateUserConsumeScore(userBindInfo.getCode(),
                totalConsumeScore);
            logger.debug(String.format("更新用户【%s】积分消耗信息", userId));
            
            // 保存用户愿望
            Wish11thWishBean wishBean = new Wish11thWishBean();
            wishBean.setContent(content);
            wishBean.setCreateTime(new Date());
            wishBean.setLevel(level);
            wishBean.setPrizeCode(resultBean.getData().getPrizeCode());
            wishBean.setPrizeName(resultBean.getData().getPrizeChinese());
            wishBean.setPresenttype(resultBean.getData().getIsReal());
            wishBean.setRoleName(userBindInfo.getRoleName());
            wishBean.setServerId(userBindInfo.getServerId());
            wishBean.setServerName(userBindInfo.getServerName());
            wishBean.setStatus(0); // -1-未通过 0-待审核 1-审核通过
            wishBean.setUserId(userId);
            wishBll.addWish(wishBean);
            logger.info("用户-{}愿望保存完毕，开始计算当前层的点亮状态", new Object[] { userId });

            // 获取当前层许愿人数
            int num = wishBll.getWishUserCountByLevel(level,
                Constants.WISH11TH_ALLSTATUS);
            // 根据当前层设置的点亮人数和实际的许愿人数来设置当前层的点亮状态
            this.updateLightState(currentLight, num);
            logger.info("当前灯的点亮状态设置完毕userid:{},开始发奖到游戏",
                new Object[] { userId });
        } catch (DLockException e) {
            logger.error(
                Constants.ERROR_LOG + "[userWish-userLottery]用户许愿抽奖方法产生异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("当前抽奖人数较多,请稍后重试！");
            resultBean.setStateCode(-100);
            return resultBean;
        } finally {
            try {
                if (userLock != null)
                    userLock.close();
            } catch (DLockException e) {
                logger.error(Constants.ERROR_LOG + "关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        // 游戏发奖
        if (resultBean.getIsSuccess()
                && resultBean.getData().getIsReal().equals("noRealPrize")
                && userBindInfo != null) {
            receiveGiftToGame(resultBean, userBindInfo.getServerId(),
                userBindInfo.getAccount());
        }
        logger.info("发奖到游戏已完成,userId:{}", new Object[] { userId });
        return resultBean;
    }

    /**
     * <p>
     * 判断用户或ip是否中过实物奖或银元宝
     * </p>
     *
     * @action tanjunkai 2017年4月14日 上午1:48:32 描述
     *
     * @param userId
     *            用户ID
     * @param ip
     *            当前ip
     * @return Boolean
     */
    public ResultBean<UserLotteryBean> lottery(int userId, String ip,
            int lotteryActionCode, Wish11thRoleBindBean userBindInfo) {

        if ("".equals(ip) || null == ip)
            ip = "0.0.0.0";
        ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
        DistributedLock ipLock = new DistributedLock(
                Constants.ACTIVITY_ENGLISHNAME + "_" + Constants.ACTIVITY_TYPE
                        + "_" + ip + "_" + Constants.ACTIVITY_ID);
        ExpType lockExceptiopType = null;
        try {
            ipLock.weakLock(68, 50);
            // 获取默认奖品
            try(SqlSession sqlSession = getSession()) {
                PrizeBean defaultPrize = getDefaultPrizeBean(Constants.ACTIVITY_ID);
                // 获取当前ip或账号中实物奖或银元宝的数量
                int count = lotteryPrizesBeanDao
                        .getCountForRealOrSilver(userId + "", ip);
                if (count == 0)// 未中过,进行抽奖
                {
                    // 开始抽奖
                    resultBean = lottery.lotteryByDataBase(lotteryActionCode,
                        userBindInfo.getUserId(), "byChance",
                        userBindInfo.getAccount(), userBindInfo.getServerName(),
                        userBindInfo.getServerId(), ip, defaultPrize,
                        sqlSession);
                    if (!resultBean.getIsSuccess()) {
                        resultBean.setIsSuccess(false);
                        resultBean.setMessage("当前抽奖人数较多,请稍后重试");
                        return resultBean;
                    }
                } else {
                    // 当前用户或ip已经中过实物奖品，之后只能中纪念奖了
                    UserLotteryBean bean = new UserLotteryBean();
                    bean.setUserCode(userBindInfo.getUserId());
                    bean.setPrizeCode(defaultPrize.getCode());
                    bean.setPrizeChinese(defaultPrize.getChinese());
                    bean.setPrizeEnglish(defaultPrize.getEnglish());
                    bean.setConfigCode(defaultPrize.getNum());
                    bean.setIsReal(defaultPrize.getIsReal());
                    bean.setConfigCode(defaultPrize.getNum());
                    resultBean.setProperties(true, bean.getPrizeChinese(),
                        bean);
                }
                int addLog = this.addLotteryLog(ip, lotteryActionCode,
                    userBindInfo, resultBean, sqlSession);
                if (addLog > 0)
                    sqlSession.commit();
            } catch (Exception e) {
                logger.error(Constants.ERROR_LOG + "[lottery]用户许愿抽奖方法产生异常：{}",
                    Throwables.getStackTraceAsString(e));
                resultBean.setIsSuccess(false);
                resultBean.setMessage("当前抽奖人数较多,请稍后重试！");
                resultBean.setStateCode(-100);
                return resultBean;
            } 
        } catch (DLockException e) {
            lockExceptiopType = e.getType();
            logger.error(
                Constants.ERROR_LOG + "[userWish-ipLottery]用户许愿抽奖方法产生异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("当前抽奖人数较多,请稍后重试！");
            resultBean.setStateCode(-100);
        } finally {
            try {
                if (ipLock != null && lockExceptiopType == null)
                    ipLock.close();
            } catch (DLockException e) {
                logger.error(Constants.ERROR_LOG + "ipLock关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
                resultBean.setIsSuccess(false);
                resultBean.setMessage("当前抽奖人数较多,请稍后重试！");
                resultBean.setStateCode(-100);
            }
        }
        return resultBean;
    }

    /**
     * <p>
     * 添加抽奖日志
     * </p>
     *
     * @action tanjunkai 2017年4月14日 上午3:22:11 描述
     *
     * @param ip
     * @param lotteryActionCode
     * @param userBindInfo
     * @param resultBean
     * @param sqlSession
     * @return int
     */
    public int addLotteryLog(String ip, int lotteryActionCode,
            Wish11thRoleBindBean userBindInfo,
            ResultBean<UserLotteryBean> resultBean, SqlSession sqlSession) {
        try {
            // 记录抽奖结果到抽奖记录日志表【action_lottery_log_tb】
            ActionLotteryLogPO log = new ActionLotteryLogPO();
            log.setActivityId(lotteryActionCode);
            log.setUserId(userBindInfo.getUserId().toString());
            log.setGameId(Constants.GAMEID + "");
            log.setServerId(userBindInfo.getServerId().toString());
            log.setPrizeType(resultBean.getData().getIsReal());
            log.setPrizeCode(resultBean.getData().getPrizeCode());
            log.setPrizeName(resultBean.getData().getPrizeChinese());
            log.setPrizeNum(1); // 奖品数量
            log.setCardCode(resultBean.getData().getCardCode());
            log.setRemark(Constants.ACTIVITY_NAME);
            log.setWinningAt(new Date());
            log.setWinningIp(ip);
            log.setIsAvailable(resultBean.getData().getIsAvailable());
            log.setActivityType(Constants.ACTIVITY_TYPE);
            return actionLotteryLogDao.putData(log, sqlSession);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * <p>
     * 计算用户在当前层许愿需要消耗的积分
     * </p>
     *
     * @action tanjunkai 2017年4月6日 下午2:13:45 描述
     *
     * @param userId
     *            用户ID
     * @param level
     *            当前许愿层
     * @return int 需要消耗的积分
     */
    public int calculateConsumeScore(int userId, int level) {
        Wish11thLightBean curLight = lightBll.getLightByLevel(level);
        if (null != curLight) {
            return this.consumeScoreRules(userId, level,
                curLight.getLightType());
        }
        return 1000;
    }

    /**
     * <p>
     * 积分消耗规则
     * </p>
     *
     * @action tanjunkai 2017年4月6日 下午1:04:10 描述
     *
     * @param userId
     *            用户ID
     * @param level
     *            层级
     * @return int 需要消耗的积分
     */
    private int consumeScoreRules(int userId, int level, int lightStatus) {
        // 获取用户在该层第几次抽奖
        int wishTimes = wishBll.getUserWishNumByLevel(userId, level) + 1;
        int consumeScore = 1000;
        // 如何该层全部点亮,消耗积分为2×2的n次方
        if (lightStatus == 3) {
            consumeScore = (int) Math.pow(2, level)
                    * (int) Math.pow(2, wishTimes);
        } else {// 如果该层为点两中,消耗积分为2×2的n-1次方
            consumeScore = (int) Math.pow(2, level)
                    * (int) Math.pow(2, wishTimes - 1);
        }
        return consumeScore;
    }

    /**
     * <p>
     * 获取纪念奖
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:03:05 描述
     *
     * @param actionCode
     * @return PrizeBean
     */
    private PrizeBean getDefaultPrizeBean(int actionCode) {
        logger.debug(String.format("--活动获取【%d】发出纪念奖--", actionCode));
        PrizeBean prizeBean = new PrizeBean();
        prizeBean.setActionCode(actionCode);
        prizeBean.setChinese("铭牌(限时)");
        prizeBean.setCode(0);
        prizeBean.setIsReal("noRealPrize");
        prizeBean.setEnglish("title");
        prizeBean.setNum(1);
        return prizeBean;
    }

    /**
     * <p>
     * 发奖到游戏里
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午3:55:37 描述
     *
     * @param resultBean
     * @param serverCode
     * @param account
     * @param sqlSession
     * @return ResultBean<UserLotteryBean>
     */
    private ResultBean<UserLotteryBean> receiveGiftToGame(
            ResultBean<UserLotteryBean> resultBean, int serverCode,
            String account) {
        String prizeEnglish = resultBean.getData().getPrizeEnglish().trim();
        String nameString = "";
        if ("nameplate".equals(prizeEnglish)) {
            nameString = "特殊铭牌奖励活动奖励灯笼铭牌(140902)";
        } else if ("title".equals(prizeEnglish)) {
            nameString = "2017年官网活动奖励圆梦2017称谓(161128)";
        } else if ("3000Ag".equals(prizeEnglish)) {
            nameString = "2017年官网活动奖励3000银元宝礼包(161128)";
        } else if ("5000Ag".equals(prizeEnglish)) {
            nameString = "2017年官网活动奖励5000银元宝礼包(161128)";
        } else if ("8000Ag".equals(prizeEnglish)) {
            nameString = "2017年官网活动奖励8000银元宝礼包(161128)";
        }
        if (StringUtils.isNotBlank(nameString)) {
            boolean is = reciveGift(account, nameString, serverCode);
            // 发送成功
            if (is) {
                logger.debug(
                    String.format("--问道周年许愿活动用户【%s】获取获取虚拟奖品【%s】success--",
                        account, nameString));
                return resultBean;
            } else {
                // 发送失败
                logger.debug(
                    String.format("--问道周年许愿活动用户【%s】获取获取虚拟奖品【%s】 fail--",
                        account, nameString));
                return new ResultBean<>(false, "发送到游戏失败！", null);
            }
        } else {
            return resultBean;
        }
    }

    /**
     * <p>
     * 发奖
     * </p>
     *
     * @action Administrator 2017年4月5日 下午3:57:10 描述
     *
     * @param account
     * @param giftPackage
     * @param serverCode
     * @return boolean
     */
    private boolean reciveGift(String account, String giftPackage,
            int serverCode) {
        boolean is = true;
        try {
            // 获取服务器信息
            ServerInfoBean serverInfo = callWebApiAgent
                    .getServerStatusFromWebApi(serverCode);
            // 发放物品
            CallWebServiceAgent.givePresents(2, account, giftPackage,
                "20250902235959", "问道周年许愿蛋糕", serverInfo);
        } catch (Exception e) {
            logger.error(
                Constants.ERROR_LOG + "[reciveGift]调用游戏接口发放游戏礼包到游戏产生异常：{}",
                Throwables.getStackTraceAsString(e));
            is = false;
        }
        return is;
    }

    /**
     * <p>
     * 更新灯的状态
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午4:06:38 描述
     *
     * @param lightlist
     *            所有层列表
     * @param currentLight
     *            当前层
     * @param lightType
     *            亮灯的级别
     * @param wishNum
     *            当前层许愿的人数 void
     */
    private void updateLightState(Wish11thLightBean currentLight, int wishNum) {
        // 查找对应层数的人数限制
        int limitNum = currentLight.getLimitNum();
        int lightStatus = currentLight.getLightType();
        // 计算出当前层应该得状态
        int actualLightStatus = lightBll.getCurrentLightType(limitNum, wishNum);
        // 更新数据
        if (lightStatus != actualLightStatus
                && actualLightStatus > lightStatus) {
            lightBll.updateLightType(currentLight.getCode(),
                currentLight.getActionCode(), actualLightStatus);
        }
    }

    /**
     * <p>
     * 获取用户所有许愿列表
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午5:40:11 描述
     *
     * @param userId
     *            用户ID
     * @return ResultBean<List<Wish11thWishBean>>
     */
    public ResultBean<List<Wish11thWishBean>> getMyAllWish(int userId) {
        ResultBean<List<Wish11thWishBean>> resultBean = new ResultBean<>();
        try {
            List<Wish11thWishBean> list = wishBll.getMyWishAll(userId);
            resultBean.setData(list);
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取用户所有许愿数据成功");
        } catch (Exception e) {
            logger.error(
                Constants.ERROR_LOG + "[getMyAllWish]获取用户所有许愿列表产生异常：{}",
                Throwables.getStackTraceAsString(e));
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取用户所有许愿数据失败");
        }
        return resultBean;
    }

    /**
     * <p>
     * 获取所有许愿获奖信息
     * </p>
     *
     * @action tanjunkai 2017年4月5日 下午7:06:38 描述
     *
     * @param num
     *            前num条
     * @return ResultBean<List<Wish11thWishBean>>
     */
    public ResultBean<List<Map<String, Object>>> findWishAll(int num) {
        ResultBean<List<Map<String, Object>>> resultBean = new ResultBean<>();
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            // 层级许愿列表缓存key
            String prizeWishKey = Constants.ACTIVITY_ENGLISHNAME + "_PrizeWish";
            list = memcachedClientAdapter.get(prizeWishKey, List.class);
            if (null == list) {
                List<Wish11thWishInfoBean> wishList = wishBll.findWishAll(num);
                if (null != wishList) {
                    list = new ArrayList<>();
                    for (Wish11thWishInfoBean wish : wishList) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("account", wish.getAccount());
                        data.put("prizeName", wish.getPrizeName());
                        data.put("createTime", wish.getCreateTime());
                        list.add(data);
                    }
                    memcachedClientAdapter.set(prizeWishKey, 180, list);
                }
            }
            String numTotal = "0";
            String prizeWishCountKey = Constants.ACTIVITY_ENGLISHNAME
                    + "_PrizeWishCount";
            numTotal = memcachedClientAdapter.get(prizeWishCountKey,
                String.class);
            if (null == numTotal) {
                numTotal = wishBll.getAllWishCount() + "";
                memcachedClientAdapter.set(prizeWishCountKey, 180,
                    numTotal + "");
            }
            resultBean.setTotal(Integer.parseInt(numTotal));
            resultBean.setData(list);
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取所有许愿数据成功");
        } catch (Exception e) {
            logger.warn("findWishAll:", e);
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取所有许愿数据失败");
        }
        return resultBean;
    }
}
