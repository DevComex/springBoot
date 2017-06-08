/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月17日 上午9:50:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wechatroulette;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionWXjifenChangeLogBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.action.beans.wechatroulette.Constant;
import cn.gyyx.action.bll.lottery.AddressBLL;
import cn.gyyx.action.bll.lottery.ILotteryPrizesBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.lottery.impl.LotteryPrizesBLLImpl;
import cn.gyyx.action.bll.wdpkforecast.WdRoleBindBll;
import cn.gyyx.action.bll.wechatroulette.RouletteBindBll;
import cn.gyyx.action.bll.wechatroulette.WeChatScoreBll;
import cn.gyyx.action.bll.wechatroulette.WeChatScoreBllImpl;
import cn.gyyx.action.cache.MemcacheUtil;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WechatRouletteService {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(WechatRouletteService.class);
    private WeChatScoreBll weChatScoreBll = new WeChatScoreBllImpl();

    private AddressBLL addressBll = new AddressBLL();
    private WdRoleBindBll wdRoleBindBll = new WdRoleBindBll();
    private ILotteryPrizesBLL prizesBll = new LotteryPrizesBLLImpl();
    private UserLotteryBll userLotteryBll = new UserLotteryBll();
    private NewlotteryService lottery = new NewlotteryService();
    private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    private XMemcachedClientAdapter memcachedClientAdapter = MemcacheUtil
            .getActionMemcache();
    private RouletteBindBll rouletteBindBll = new RouletteBindBll();

    private SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession(false);
    }

    /**
     * 
     * <p>
     * 绑定角色
     * </p>
     *
     * @action caishuai 2017年3月20日 下午4:46:29 描述
     *
     * @param wdPkRoleBindBean
     *            角色信息
     * @return ResultBean<WdPkRoleBindBean>
     */
    public ResultBean<WdPkRoleBindBean> addrole(
            WdPkRoleBindBean wdPkRoleBindBean) {
        ResultBean<WdPkRoleBindBean> result = new ResultBean<WdPkRoleBindBean>(
                false, "绑定失败", null);
        String lockKey = "WeChatRoulette_" + "addrole_openid"
                + wdPkRoleBindBean.getAccount() + "_actionCode"
                + wdPkRoleBindBean.getActionCode();
        DistributedLock lock = new DistributedLock(lockKey.toLowerCase());
        StopWatch sw = new StopWatch();
        try {
            lock.weakLock(20, 3);

            sw.start();

            WdPkRoleBindBean pkRoleBindBean = wdRoleBindBll
                    .getBindByAccountOrRoleName(
                        wdPkRoleBindBean.getActionCode(),
                        wdPkRoleBindBean.getAccount(),
                        wdPkRoleBindBean.getRoleName());
            logger.info("actionCode:{}_新增角色前检查角色是否已绑定结果checkrole:{}",
                Constant.ACTION_CODE, pkRoleBindBean);
            if (pkRoleBindBean == null) {
                String memkey = "WeChatRoulette_roleBind_actionCode"
                        + wdPkRoleBindBean.getActionCode() + "_openId"
                        + wdPkRoleBindBean.getAccount();
                // 微信号,账号未被绑定
                wdRoleBindBll.insertWdPkRoleBindBean(wdPkRoleBindBean);
                result = new ResultBean<WdPkRoleBindBean>(true, "绑定成功",
                        wdPkRoleBindBean);
                ObjectMapper mapper = new ObjectMapper();
                String value = mapper.writeValueAsString(wdPkRoleBindBean);
                // 这里用这张之前的表openid作为account字段，account作为rolename字段
                // 缓存设为15天时间，写入缓存
                memcachedClientAdapter.set(memkey.toLowerCase(),
                    this.getUntilNDayEndSeconds(Constant.Fifteen_Days), value);

                return result;

            } else {
                if (StringUtils.equals(wdPkRoleBindBean.getAccount(),
                    pkRoleBindBean.getAccount())) {
                    // 微信号被绑定了
                    result.setIsSuccess(false);
                    result.setMessage("您已经绑定过账号了");
                    result.setData(pkRoleBindBean);
                    return result;
                }
                if (StringUtils.equals(wdPkRoleBindBean.getRoleName(),
                    pkRoleBindBean.getRoleName())) {
                    // 账号被绑定了
                    result.setIsSuccess(false);
                    result.setMessage("该账号已被其他微信号绑定");
                    result.setData(pkRoleBindBean);
                    return result;
                }
                return result;

            }
        } catch (Exception e) {
            logger.error("actionCode:{},绑定角色失败,异常堆栈{}", Constant.ACTION_CODE,
                Throwables.getStackTraceAsString(e));
            return result;
        } finally {
            sw.stop();
            logger.info("绑定角色锁消耗时间：{}", sw.getTotalTimeMillis());
            try {
                if (lock != null)
                    lock.close();
            } catch (DLockException e) {
                logger.error("綁定角色，关闭分布式锁失败！异常：{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
    }

    /**
     * 
     * <p>
     * 获取用户每日抽奖上限次数
     * </p>
     *
     * @action caishuai 2017年3月20日 下午5:19:03 描述
     *
     * @param openId
     *            openID
     * @param actionCode
     *            活动code
     * @return Map<String,Object>
     */
    public int getScore(String openId, int actionCode) {
        int lotteryTimes = -1;
        WdPkRoleBindBean role = this.getUserInfoByOpenId(openId, actionCode);
        logger.info("actionCode:{}-获取用户openId：{}每日抽奖上限次数,检查是否绑定结果role:{}",
            new Object[] { actionCode, openId, role });
        if (role != null) {
            Date date = new Date();
            SqlSession session = null;
            String lockKey = "actioncode" + actionCode + "ActionLotteryTime"
                    + openId;
            DistributedLock lock = new DistributedLock(lockKey.toLowerCase());
            StopWatch sw = new StopWatch();
            try {
                lock.weakLock(20, 2);
                sw.start();

                // 查询今日是否首次登陆 是否有抽奖机会;
                ActionQualificationPO qualification = rouletteBindBll
                        .getQualification(openId, actionCode, date);
                logger.info("actionCode:{}_openId：{}查询的今日抽奖资格{}",
                    new Object[] { actionCode, openId, qualification });
                if (qualification == null) {
                    session = getSession();
                    ActionQualificationPO defaultPo = new ActionQualificationPO();
                    defaultPo.setActivityId(role.getActionCode());
                    defaultPo.setCreateAt(date);
                    defaultPo.setLotteryCount(Constant.MAX_TIMES);
                    defaultPo.setMustWinCount(0);
                    defaultPo.setModifyAt(date);
                    defaultPo.setUserId(openId);
                    rouletteBindBll.InitializeLottery(defaultPo, session);
                    lotteryTimes = Constant.MAX_TIMES;
                    logger.info(
                        "actionCode:{}_openId：{}初始化今日抽奖次数success,当前抽奖次数为默认次数{}",
                        new Object[] { actionCode, openId, lotteryTimes, });
                    session.commit(true);
                } else {
                    lotteryTimes = qualification.getLotteryCount();
                }

            } catch (Exception e) {
                lotteryTimes = -1;
                logger.error("actionCode:{}_openId：{}初始化抽奖次数失败，抽奖次数置为{},错误{}",
                    new Object[] { actionCode, openId, lotteryTimes,
                            Throwables.getStackTraceAsString(e) });
                if (session != null) {
                    session.rollback();
                }
            } finally {
                sw.stop();
                logger.info("初始化今日抽奖次数锁消耗时间：{}", sw.getTotalTimeMillis());
                if (session != null) {
                    session.close();
                }
                try {
                    if (lock != null)
                        lock.close();
                } catch (DLockException e) {
                    logger.error("actionCode:{}_openId：{},关闭分布式锁失败！异常：{}",
                        new Object[] { actionCode, openId,
                                Throwables.getStackTraceAsString(e) });
                }
            }

        } else {
            lotteryTimes = -2;
            logger.warn("actionCode:{}_初始化今日抽奖次数,没有获取到角色，返回抽奖次数错误码{}",
                actionCode, lotteryTimes);
        }

        return lotteryTimes;

    }

    /**
     * 
     * <p>
     * 微信大转盘抽奖
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:25:28 描述
     *
     * @param actionCode
     *            活动code
     * @param ip
     *            ip
     * @param openId
     *            openid
     * @return ResultBean<UserLotteryBean>
     */
    public ResultBean<UserLotteryBean> getLottery(int actionCode, String ip,
            String openId) {
        Date nowDate = new Date();
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>(
                false, "", null);
        // 查询是否绑定了区服；
        WdPkRoleBindBean role = this.getUserInfoByOpenId(openId, actionCode);
        logger.info("actionCode:{}_抽奖时检查 {}是否绑定了区服role:{}",
            new Object[] { actionCode, openId, role });
        if (role == null) {
            return new ResultBean<>(false, "请您先绑定区服信息，再进行抽奖", null);
        }
        // 将绑定信息转换为抽奖参数
        int userCode = role.getUserId();
        String account = role.getAccount();
        String userName = role.getRoleName();
        int serverCode = role.getServerId();
        String serverName = role.getServerName();
        String lockKey = "roulette_lottery_getlottery_actioncode" + actionCode
                + "_openid" + openId;
        SqlSession sqlSession = null;
        StopWatch sw = new StopWatch();
        try (DistributedLock lock = new DistributedLock(
                lockKey.toLowerCase())) {
            lock.weakLock(65, 30);
            sw.start();
            // 判定抽奖资格
            ResultBean<UserLotteryBean> lotteryCheck = this.lotteryCheck(openId,
                actionCode);
            if (!lotteryCheck.getIsSuccess()) {
                return lotteryCheck;
            }
            // 抽奖，调用的是改动后的抽奖
            sqlSession = getSession();
            result = lottery.lotteryByDataBase(actionCode, userCode, "byChance",
                userName, serverName, serverCode, ip,
                getNoPrizeBean(actionCode), sqlSession);
            // 修改抽奖次数
            prizesBll.putLotteryCountMinusOneByDate(actionCode, account,
                nowDate, sqlSession);
            // 减少用户积分
            weChatScoreBll.reduceScoreByOpenIdAndTypeBySession(openId,
                Constant.WeChat_Type, Constant.Rate, sqlSession);
            // 记录积分减少日志
            ActionWXjifenChangeLogBean jfchangeBean = new ActionWXjifenChangeLogBean();
            jfchangeBean.setAction_code(actionCode);
            jfchangeBean.setChangeTime(nowDate);
            jfchangeBean.setChangeType("reduce");
            jfchangeBean.setJifen(Constant.Rate);
            jfchangeBean.setOpenid(openId);
            jfchangeBean.setRemark(Constant.WeChat_Type);
            rouletteBindBll.insertReduceJfLog(jfchangeBean, sqlSession);
            sqlSession.commit(true);
        } catch (DLockException e) {
            logger.error("actionCode:{}_openid_{}抽奖出现异常,错误堆栈:{}", new Object[] {
                    actionCode, openId, Throwables.getStackTraceAsString(e) });
            return new ResultBean<>(false, "操作太快了，请休息一会再试", null);
        } catch (Exception e) {
            logger.error("actionCode:{}_openid_{}抽奖出现异常,错误堆栈:{}", new Object[] {
                    actionCode, openId, Throwables.getStackTraceAsString(e) });
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            return new ResultBean<>(false, "操作太快了，请过一会再试", null);
        } finally {
            sw.stop();
            logger.info("抽奖锁消耗时间：{}", sw.getTotalTimeMillis());
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        // 判定是否发奖并发放奖品
        sendPresent(actionCode, role, ip, result);
        return result;

    }

    /**
     * 
     * <p>
     * 获取用户中奖记录
     * </p>
     *
     * @action caishuai 2017年3月21日 下午4:18:05 描述
     *
     * @return List<UserInfoBean>
     */
    public List<UserInfoBean> getLotteryLogByUser(String openId,
            int actionCode) {
        // 获取openid对应的绑定的角色账户信息
        WdPkRoleBindBean role = this.getUserInfoByOpenId(openId, actionCode);
        logger.info("actionCode:{}_openid_{}获取中奖纪录检查是否绑定role:{}",
            new Object[] { actionCode, openId, role });
        if (role == null) {
            return new ArrayList<>();
        }
        // 得到该用户获得的奖品
        List<UserInfoBean> list = userLotteryBll
                .getUserLotteryByUserCode(actionCode, role.getRoleName());
        return list;
    }

    /**
     * 
     * <p>
     * 新增地址
     * </p>
     *
     * @action caishuai 2017年3月22日 上午11:45:50 描述
     *
     * @param openId
     * @param actionCode
     * @param address
     * @return ResultBean<Integer>
     */
    public ResultBean<Integer> setAddress(String openId, int actionCode,
            AddressBean address) {
        WdPkRoleBindBean role = this.getUserInfoByOpenId(openId, actionCode);
        logger.info("actioncode:{},openId:{},新增地址检查是否绑定role:{}",
            new Object[] { actionCode, openId, role });
        if (role == null) {
            return new ResultBean<>(false, "您尚未绑定过区服，不能填写地址", null);
        }
        // 查询用户是否中过实物奖，没中过不给填地址；
        List<UserInfoBean> list = userLotteryBll.selectOneTypePrizes(actionCode,
            role.getRoleName(), "realPrize");
        logger.info("actioncode:{},openId:{},新增地址检查是否中实物奖realprizeList:{}",
            new Object[] { actionCode, openId, list });
        if (list.size() < 1) {
            return new ResultBean<>(false, "您尚未中奖，不可填写地址信息", null);
        }

        address.setActionCode(actionCode);
        address.setUserAccount(role.getRoleName());
        address.setUserCode(role.getUserId());
        // 查询用户是否已填写过地址
        String lockKey = "roulette_lottery_addaddress_actioncode" + actionCode
                + "_openid" + openId;
        DistributedLock lock = new DistributedLock(lockKey.toLowerCase());
        StopWatch sw = new StopWatch();
        try {
            lock.weakLock(7, 5);
            sw.start();
            ResultBean<AddressBean> resultAddress = addressBll
                    .getAddress(role.getUserId(), actionCode);
            logger.info("actioncode:{},openId:{},新增地址检查是否填写过地址oldAddress:{}",
                new Object[] { actionCode, openId, resultAddress.getData() });
            if (resultAddress.getData() == null) {
                ResultBean<Integer> result = addressBll.addAddress(address);
                if (result.getIsSuccess()) {
                    result.setMessage("地址添加成功");
                    return result;
                } else {
                    result.setMessage("填写地址失败，请重试");
                    return result;
                }
            } else {
                return new ResultBean<>(false, "您已填写过地址", null);
            }
        } catch (Exception e) {
            logger.error("actioncode:{},openId:{},添加地址失败error:{}",
                new Object[] { actionCode, openId,
                        Throwables.getStackTraceAsString(e) });
            return new ResultBean<>(false, "填写地址失败，请重试", null);
        } finally {
            sw.stop();
            logger.info("填写地址锁消耗时间：{}", sw.getTotalTimeMillis());
            try {
                if (lock != null)
                    lock.close();
            } catch (DLockException e) {
                logger.error("actionCode:{}_openId：{},关闭分布式锁失败！异常：{}",
                    new Object[] { actionCode, openId,
                            Throwables.getStackTraceAsString(e) });
            }
        }

    }

    /**
     * 
     * <p>
     * 通过openId获取用户信息
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:23:30 描述
     *
     * @param openId
     *            openid
     * @param actionCode
     *            活动code
     * @return WdPkRoleBindBean 绑定信息实体类
     */
    public WdPkRoleBindBean getUserInfoByOpenId(String openId, int actionCode) {
        WdPkRoleBindBean res = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String memkey = "WeChatRoulette_roleBind_actionCode" + actionCode
                    + "_openId" + openId;
            String value = memcachedClientAdapter.get(memkey.toLowerCase(),
                String.class);
            logger.info("actioncode:{},openId:{}_memcache获取角色绑定信息为:{}",
                new Object[] { actionCode, openId, value });
            if (StringUtils.isEmpty(value)) {
                // 数据库获取
                res = wdRoleBindBll.getRoleBindInfoByAccount(openId,
                    actionCode);
                if (res != null) {
                    // 加入缓存
                    value = mapper.writeValueAsString(res);
                    memcachedClientAdapter.set(memkey.toLowerCase(),
                        this.getUntilNDayEndSeconds(Constant.Fifteen_Days),
                        value);
                }
            } else {
                JSONObject parse = JSON.parseObject(value);
                res = JSON.toJavaObject(parse, WdPkRoleBindBean.class);
            }
        } catch (Exception e) {
            logger.error("actioncode:{},openId:{}获取绑定信息失败error:{}",
                new Object[] { actionCode, openId,
                        Throwables.getStackTraceAsString(e) });
        }

        return res;
    }

    /**
     * 
     * <p>
     * 纪念奖
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:41:43 描述
     *
     * @param actionCode
     * @return PrizeBean
     */
    private PrizeBean getNoPrizeBean(int actionCode) {
        PrizeBean prizeBean = new PrizeBean();
        prizeBean.setActionCode(actionCode);
        prizeBean.setChinese("谢谢参与");
        prizeBean.setEnglish("thankyou");
        prizeBean.setNum(2);
        prizeBean.setIsAvailable(0);
        prizeBean.setIsReal("noRealPrize");
        prizeBean.setPrizeCode(Constant.Thanks_Code);

        return prizeBean;

    }

    /**
     * 
     * <p>
     * 发放奖品
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:41:52 描述
     *
     * @param serverCode
     * @param userName
     * @param chineseName
     * @param giftPackage
     * @return ProcessResult
     */
    public ProcessResult sendPrice(int serverCode, String userName,
            String chineseName, String giftPackage) {
        ServerInfoBean serverInfo = callWebApiAgent
                .getServerStatusFromWebApi(serverCode);
        // 发放物品
        ProcessResult processResult = null;
        processResult = CallWebServiceAgent.givePresents(Constant.Game_Id,
            userName, giftPackage, "20250902235959", chineseName, serverInfo);
        return processResult;
    }

    /**
     * 
     * <p>
     * 得到距离N天结束秒数
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:25:13 描述
     *
     * @param n
     * @return Integer
     */
    private Integer getUntilNDayEndSeconds(int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, n);
            Date tempDate;
            tempDate = sdf.parse(sdf.format(calendar.getTime()));
            return (int) ((tempDate.getTime() - date.getTime()) / 1000);
        } catch (ParseException e) {
            logger.error("wechat_roulette_getUntilDayEndSeconds error",
                Throwables.getStackTraceAsString(e));
            return 60 * 60 * 24;
        }
    }

    /**
     * 
     * <p>
     * 判定抽奖时每日抽奖次数和积分是否充足
     * </p>
     *
     * @action caishuai 2017年3月24日 下午5:57:01 描述
     *
     * @param openId
     * @param actionCode
     * @param sqlSession
     * @return ResultBean<UserLotteryBean>
     */
    private ResultBean<UserLotteryBean> lotteryCheck(String openId,
            int actionCode) {
        // 查询抽奖机会
        ActionQualificationPO qualification = rouletteBindBll
                .getQualification(openId, actionCode, new Date());
        logger.info("actionCode:{}_openId：{}抽奖时查询的今日抽奖资格{}",
            new Object[] { actionCode, openId, qualification });
        if (qualification == null) {
            return new ResultBean<>(false, "未能获取到抽奖机会，请刷新后再试", null);
        }
        if (qualification.getLotteryCount() == null
                || qualification.getLotteryCount() <= 0) {
            return new ResultBean<>(false, "您的抽奖次数已经用完了哦，请明天再来~微信签到可以获取积分哦",
                    null);
        }
        // 查询积分，session
        int score = weChatScoreBll.selectScoreByOpenIdAndType(openId,
            Constant.WeChat_Type);
        logger.info("actionCode:{}_抽奖时检查 {}  当前积分 {}",
            new Object[] { actionCode, openId, score });
        if (score < 5) {
            return new ResultBean<>(false, "积分不足，请前去问道公众号签到获取积分", null);
        }
        return new ResultBean<>(true, "有抽奖资格", null);
    }

    /**
     * 
     * <p>
     * 抽奖后判断是否发放奖品，以及奖品发放
     * </p>
     *
     * @action caishuai 2017年3月24日 下午6:17:39 描述
     *
     * @param actionCode
     * @param role
     * @param ip
     * @param result
     *            void
     */
    private void sendPresent(int actionCode, WdPkRoleBindBean role, String ip,
            ResultBean<UserLotteryBean> result) {
        // 发奖
        if (result.getData().getPrizeChinese().equals(Constant.Send_Gift)) {
            logger.info("actionCode:{}_openid_{}_检查发放{}奖品的用户名-userName：{}",
                new Object[] { actionCode, role.getAccount(),
                        Constant.Send_Gift, role.getRoleName() });
            String message = "";
            try {
                ProcessResult processResult = sendPrice(role.getServerId(),
                    role.getRoleName(), Constant.Send_Gift,
                    Constant.Send_Gift_Package);

                if (processResult.getErrorCode() != 0) {
                    // 保存错误日志-result
                    message = "send error:" + processResult.getErrorCode();
                }
            } catch (Exception e) {
                // 保存错误日志-result
                logger.error(
                    "actionCode:{}_openid_{}_发放{}奖品的用户名-userName：{}发生异常:{}",
                    new Object[] { actionCode, role.getAccount(),
                            Constant.Send_Gift, role.getRoleName(),
                            Throwables.getStackTraceAsString(e) });
                message = "exception:" + e.getMessage();
            }
            if (!StringUtils.isEmpty(message)) {
                // 写入错误日志
                UserInfoBean userInfo = new UserInfoBean();
                // 这是日志的参数设定
                userInfo.setAccount(role.getRoleName());
                userInfo.setActionCode(actionCode);
                userInfo.setGameCode(Constant.Game_Id);
                userInfo.setIp(ip);
                userInfo.setPresentName(result.getData().getPrizeChinese());
                userInfo.setPresentType(result.getData().getIsReal());
                userInfo.setServerCode(role.getServerId());
                userInfo.setServerName(role.getServerName());
                userInfo.setSource(message);
                userInfo.setSourceCode(actionCode);
                userInfo.setTime(new Date());
                rouletteBindBll.addErrorInfo(userInfo);
            }
        }
    }
}
