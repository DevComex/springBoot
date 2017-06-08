/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年12月9日上午10:51:50
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.service.wechatroulette;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.LotteryException.LotteryExpType;
import cn.gyyx.action.service.newLottery.NewLotteryConfig;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;       

public class NewlotteryService {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(NewlotteryService.class);
     private NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
    /**
     * 直接与数据库交互的抽奖
     * 
     * @param actionCode
     * @param userCode
     * @param type
     * @param userName
     * @param server
     * @param serverCode
     * @param ip
     * 
     * @return
     * @throws LotteryException
     */
    public ResultBean<UserLotteryBean> lotteryByDataBase(int actionCode,
            int userCode, String type, String userName, String server,
            int serverCode, String ip, PrizeBean noPrize, SqlSession sqlSession)
            throws LotteryException {
        
        ResultBean<UserLotteryBean> resultBean;
        // 日志
        logger.debug("actionCode", actionCode);
        logger.debug("userCode", userCode);
        logger.debug("type", type);
        logger.debug("userName", userName);
        logger.debug("server", server);
        logger.debug("serverCode", serverCode);
        logger.debug("ip", ip);
        logger.debug("PrizeBean", noPrize);
        // 开启一个session
        // 得到活动信息
        // 来自表 hd_config_tb
        // 配置活动的基本信息
        ContrParmBean controParm = new ContrParmBean();
        try {
            controParm = userLotteryBll.getContrParm(actionCode, sqlSession);
            logger.debug("controParm" + controParm.toString());

        } catch (Exception e) {
            // 抛出活动信息的错误
            logger.warn("" + e);
            throw new LotteryException(LotteryExpType.CONTROPARM_ERROR,
                    e.getMessage());
        }

        // 得到奖品的信息
        // 数据来自 表 action_prize_tb
        // 这是奖品信息的来源
        List<PrizeBean> listPrize = new ArrayList<>();
        try {
            listPrize = userLotteryBll.getPrize(actionCode, sqlSession);
            logger.debug("listPrize" + listPrize);

        } catch (Exception e) {
            logger.warn("" + e);
            throw new LotteryException(LotteryExpType.LISTPRIZE_ERROR,
                    e.getMessage());
        }

        NewLotteryConfig lotteryConfig = new NewLotteryConfig();
        // 判断用的是怎样的抽奖方法
        /**
         * 下面是概率方式 byChance 需要配置概率表 action_lottery_chance_tb
         * 
         */
        if ("byChance".equals(type)) {
            // 得到概率配置信息
            // 信息来自于表 action_lottery_chance_tb
            List<ChancePrizeBean> listInformation = new ArrayList<>();
            try {
                listInformation = userLotteryBll.getChancePrize(actionCode,
                    sqlSession);
            } catch (Exception e) {
                logger.warn("" + e);
                // 抛出异常
                throw new LotteryException(LotteryExpType.CHANCE_PRIZE_ERROR,
                        e.getMessage());
            }
            /**
             * 这里对抽奖内容进行限制
             * 
             * @author caishuai 此处照原版本新增部分
             */
            List<UserInfoBean> availableList = userLotteryBll
                    .getAvailableByUserSqlsession(actionCode, userName, 1,
                        sqlSession);
            if (availableList.size() > 0) {
                // 已中过大奖，只能中谢谢参与或铭牌，将其余的奖品从概率中移除
                for (PrizeBean prizeBean : listPrize) {
                    if (prizeBean.getIsAvailable() == 1) {
                        for (ChancePrizeBean chancePrizeBean : listInformation) {
                            if (chancePrizeBean.getPrizeCode() == prizeBean
                                    .getCode()) {
                                listInformation.remove(chancePrizeBean);
                                break;
                            }
                        }
                    }
                }
            }
            /**
             * @author caishuai 新增结束
             */
            // 得到中奖的信息
            // 这就是抽奖的主体
            // 想知道奖品怎么来的，你就点进去
            try {
                resultBean = lotteryConfig.config(userCode, controParm,
                    listInformation, type, listPrize, noPrize, sqlSession);
            } catch (Exception e) {
                logger.warn("" + e);
                // 抛出异常
                throw new LotteryException(LotteryExpType.LOTTERY_CHANCE_ERROR,
                        e.getMessage());
            }
            // 针对不同的奖品进行锁排队
            UserLotteryBean data = resultBean.getData();
            String prizeCode = (noPrize == null ? "_"
                    : noPrize.getCode() + "_");
            if (data != null) {
                prizeCode = data.getPrizeCode() + "_" + data.getCode();
            }
            String prizeLock = String.format("ACTIONV2_%s_LOTTERY_PRIZE_%s",
                actionCode, prizeCode).toLowerCase();
            logger.info("This is the lock [{}] of the action {} for prize {}",
                new Object[] { prizeLock, actionCode, prizeCode });
            try (DistributedLock lock = new DistributedLock(prizeLock)) {
                lock.weakLock(60, 30);
                // 奖品超过规定数量的处理
                resultBean = isNumTop(listInformation, resultBean, sqlSession);
                // 没中奖加入纪念奖的处理
                resultBean = getNoPrize(noPrize, resultBean);
            } catch (Exception re) {
                // 抛出异常
                logger.warn("" + re);
                resultBean = getNoPrize(noPrize, resultBean);
                throw new LotteryException(LotteryExpType.PRIZE_TOPNUM_ERROR,
                        re.getMessage());
            }
        }
        // 这个是按顺序抽奖
        // 如果没有什么特殊需求，就不要用这个方式了
        // 配置麻烦
        // 唯一的好处是你可以控制奖品发放
        // 但是，奖品很容易发不出去
        else if ("byOrder".equals(type)) {
            // 这个是获取order的列表配置信息
            // 数据来自于表 action_config_tb
            // 这里面的数据决定了 第几名 中什么奖
            List<OrderAndPrizeBean> listOrderPrizeBeans = new ArrayList<>();
            try {
                listOrderPrizeBeans = userLotteryBll
                        .getPrizeAndOrderInfo(actionCode, sqlSession);
            } catch (Exception e) {
                logger.warn("" + e);
                // 抛出异常
                throw new LotteryException(LotteryExpType.ORDER_PRIZE_ERROR,
                        e.getMessage());
            }

            logger.debug("listOrderPrizeBeans", listOrderPrizeBeans);
            // 得到中奖的信息
            try {
                resultBean = lotteryConfig.config(userCode, controParm,
                    listOrderPrizeBeans, type, listPrize, noPrize, sqlSession);
            } catch (Exception e) {
                logger.warn("" + e);
                // 抛出异常
                throw new LotteryException(LotteryExpType.LOTTERY_ORDER_ERROR,
                        e.getMessage());
            }

        }
        // 中奖类型就是规定的，如果你进入了else，就是乱输入的锅，配合下
        else {

            throw new LotteryException(LotteryExpType.TYPE_NULL_ERROR,
                    "no type no can ");

        }

        // 处理卡密类型的奖品，获取到卡号，存到了 prize_chinese 奖品中文名
        // 卡密的表 action_recharge_tb
        // 奖品的类型 得是 card
        try {
            resultBean = getCardId(actionCode, resultBean, sqlSession);
        } catch (Exception e) {
            logger.warn("" + e);
            // 获取卡密出现错误
            throw new LotteryException(LotteryExpType.CARD_ERROE,
                    e.getMessage());
        }

        // 这是对中奖后的判断，将中了奖的信息插入到日志表去
        // 日志表 hd_send_present_log
        // 这是抽奖的日志通用表
        // 需求方查询中奖信息也是从这个表里查询
        try {
            if (resultBean.getIsSuccess()) {
                UserInfoBean userInfo = new UserInfoBean();
                // 这是日志的参数设定
                userInfo.setAccount(userName);
                userInfo.setActionCode(actionCode);
                userInfo.setGameCode(controParm.getGameId());
                userInfo.setIp(ip);
                userInfo.setPresentName(resultBean.getData().getPrizeChinese());
                userInfo.setPresentType(resultBean.getData().getIsReal());
                userInfo.setServerCode(serverCode);
                userInfo.setServerName(server);
                userInfo.setSource(controParm.getActivityName());
                userInfo.setSourceCode(controParm.getCode());
                userInfo.setTime(new Date());
                /**
                 * @author caishuai
                 *         在这里增加了一个字段的存储，其余和原文件一致，目的是将奖品表的IsAvailable属性同步到日志表
                 */
                userInfo.setAvailable(resultBean.getData().getIsAvailable());
                // 更新用户中奖信息
                // 中了奖信息就放入到日志表里
                if (!"谢谢参与".equals(userInfo.getPresentName())) {
                    userLotteryBll.addUserLotteryInfo(userInfo, sqlSession);
                }
            }

        } catch (Exception e) {
            logger.warn("" + e);
            // 写入日志发生错误
            throw new LotteryException(LotteryExpType.PRESENT_LOG_ERROR,
                    e.getMessage());
        }

        return resultBean;
    }

  

    /****
     * 判断是否达到最高数量
     * 
     * @param listInformation
     *            prizeCode
     * @return
     */
    private ResultBean<UserLotteryBean> isNumTop(
            List<ChancePrizeBean> listInformation,
            ResultBean<UserLotteryBean> resultLottery, SqlSession sqlSession) {
        NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
        ResultBean<UserLotteryBean> resultLotterytInfo = new ResultBean<>();
        // 查询到所中奖品的信息
        for (int j = 0; j < listInformation.size(); j++) {
            // 查找到当前的奖品信息
            if (listInformation.get(j).getPrizeCode() == resultLottery.getData()
                    .getPrizeCode()) {
                // 如果是有限定数量的
                if (listInformation.get(j).getNumber() != -1) {
                    // 查过了限定数量
                    if (listInformation.get(j).getNumber() <= 0) {
                        resultLotterytInfo.setIsSuccess(false);
                        resultLotterytInfo.setMessage("未中奖");
                        resultLotterytInfo.setData(null);
                        return resultLotterytInfo;
                    } else {
                        // 减去数量
                        userLotteryBll.reduceTimeChancePrize(
                            listInformation.get(j).getActionCode(),
                            resultLottery.getData().getCode(),
                            resultLottery.getData().getPrizeCode(), sqlSession);
                        return resultLottery;
                    }

                }
                break;
            }

        }
        return resultLottery;
    }

    /****
     * 将noPrizeBean放入奖品， 纪念奖 noPrizeBean ，如果系统不中奖，统统中这个
     * 
     * @param noPrizeBean
     * @param resultLottery
     * @return
     */
    private ResultBean<UserLotteryBean> getNoPrize(PrizeBean noPrize,
            ResultBean<UserLotteryBean> resultLottery) {
        ResultBean<UserLotteryBean> resultBean = new ResultBean<>();
        if (resultLottery != null && !resultLottery.getIsSuccess()
                && noPrize != null) {

            UserLotteryBean userLotteryTempBean = new UserLotteryBean();
            // 设定中文名
            userLotteryTempBean.setPrizeChinese(noPrize.getChinese());
            // 设定英文名
            userLotteryTempBean.setPrizeEnglish(noPrize.getEnglish());
            // 设定前台所对应的符号
            userLotteryTempBean.setConfigCode(noPrize.getNum());
            // 设定属性
            userLotteryTempBean.setIsReal(noPrize.getIsReal());
            // 设定data
            resultBean.setData(userLotteryTempBean);
            // 设定message
            resultBean.setMessage(noPrize.getChinese());
            // 设定属性
            resultBean.setIsSuccess(true);
            return resultBean;
        }

        return resultLottery;
    }

    /****
     * 如果奖品是卡号，那么就去获得卡号
     * 
     * @param resultLottery
     * @param sqlSession
     * @return
     */
    private ResultBean<UserLotteryBean> getCardId(int actionCode,
            ResultBean<UserLotteryBean> resultLottery, SqlSession sqlSession) {
        ResultBean<UserLotteryBean> resultBean = new ResultBean<>();
        NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
        if (resultLottery.getIsSuccess() && resultLottery.getData() != null
                && "card".equals(resultLottery.getData().getIsReal())) {

            // 获取卡号信息
            String card = userLotteryBll.getCardCode(actionCode,
                resultLottery.getData().getPrizeEnglish(),
                resultLottery.getData().getUserCode(), sqlSession);
            resultBean = resultLottery;
            // 配置奖品信息
            resultBean.getData()
                    .setPrizeChinese(resultLottery.getData().getPrizeChinese());
            resultBean.getData().setCardCode(card);
            return resultBean;
        }

        return resultLottery;
    }
}
