/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/24 21:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wechatvideo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.bll.wechatvideo.WeChatVideoUpLoadLogBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.wechatvideo.WeChatVideoRoleBindDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 视频审核服务
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoService {
    private static final Logger LOGGER = GYYXLoggerFactory
            .getLogger(WeChatVideoUpLoadLogBll.class);
    private static final SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
            .getSqlActionDBV2SessionFactory();
    WeChatVideoRoleBindDao weChatVideoRoleBindDao = new WeChatVideoRoleBindDao();

    /**
     * <p>
     * OA后台审核自动给用户添加2000银元宝奖励日志的判断
     * </p>
     *
     * @action tanjunkai 2017年3月25日 下午12:44:14 描述
     *
     * @param userId
     *            void
     */
    public void auditAddSilerJudge(int userId) {
        // 判断是否已增加过2000银元宝的奖励，已增加过就不在增加
        if (this.isAddSiler(userId))
            return;
        // 如果没有设置2000银元宝的奖励则不加
        ActionLotteryLogPO prizeLog = this.generateSilverInfo(userId);
        if (prizeLog == null)
            return;
        // 新增2000银元宝奖励(该方法内部已判断如果增加过则不添加)
        this.Add2000SilverPrizeLog(prizeLog, sqlSessionFactory.openSession());
    }

    /**
     * <p>
     * 判断是否已增加过2000银元宝的奖励
     * </p>
     *
     * @action tanjunaki 2017年3月24日 下午7:33:44 描述
     *
     * @param userId
     * @return Boolean
     */
    private Boolean isAddSiler(int userId) {
        Boolean flag = false;
        WeChatVideoRoleBindBean roleBindInfo = new WeChatVideoRoleBindDao()
                .selectByUserId(userId);
        LOGGER.info("{}_判断用户{}是否增加过2000银元宝开始：", new Object[] {
                Constants.ACTIVITY_NAME, roleBindInfo.getAccount() });
        List<ActionLotteryLogPO> allPrizeLogList = new WeChatVideoPrizeService()
                .getMyPrize(roleBindInfo);
        for (ActionLotteryLogPO prizeLog : allPrizeLogList) {
            if (prizeLog.getPrizeName().equals(Constants.PRIZE_2000SILVER)) {
                flag = true;
                break;
            }
        }
        LOGGER.info("{}_判断用户{}是否增加过2000银元宝结果{}：", new Object[] {
                Constants.ACTIVITY_NAME, roleBindInfo.getAccount(), flag });
        return flag;
    }

    /**
     * <p>
     * 获取2000银元宝方法
     * </p>
     *
     * @action tanjunkai 2017年3月24日 下午6:44:14 描述
     *
     * @param userId
     * @return ActionLotteryLogPO
     */
    private ActionLotteryLogPO generateSilverInfo(int userId) {
        WeChatVideoRoleBindBean userInfo = weChatVideoRoleBindDao
                .selectByUserId(userId);
        PrizeBean silver = null;
        List<PrizeBean> prizes = new WeChatVideoPrizeService()
                .getPrizes(Constants.ACTIVITY_ID);
        for (PrizeBean prize : prizes) {
            if (Constants.PRIZE_2000SILVER.equals(prize.getChinese())) {
                silver = prize;
                break;
            }
        }
        ActionLotteryLogPO log = null;
        if (silver != null) {
            log = new ActionLotteryLogPO();
        } else {
            return null;
        }
        log.setActivityId(Constants.ACTIVITY_ID);
        log.setUserId(userInfo.getUserId().toString());
        log.setGameId(Constants.GAMEID + "");
        log.setServerId(userInfo.getServerCode().toString());
        log.setPrizeType(silver.getIsReal());
        log.setPrizeCode(silver.getCode());
        log.setPrizeName(silver.getChinese());
        log.setPrizeNum(1); // 奖品数量
        log.setWinningAt(new Date());
        log.setIsAvailable(silver.getIsAvailable());
        log.setActivityType(Constants.ACTIVITY_TYPE);
        log.setRemark("后台审核自动增加2000银元宝");
        return log;
    }

    /**
     * <p>
     * 新增2000银元宝奖励日志
     * </p>
     *
     * @action tanjunkai 2017年3月24日 下午7:02:14 描述
     *
     * @param log
     * @param session
     *            void
     */
    private void Add2000SilverPrizeLog(ActionLotteryLogPO log,
            SqlSession session) {
        try {
            LOGGER.info("{}_给用户{}增加2000银元宝开始：",
                new Object[] { Constants.ACTIVITY_NAME, log.getUserId() });
            new ActionLotteryLogDAOImpl().putData(log, session);
            session.commit();
            LOGGER.info("{}_给用户{}增加2000银元宝结果{}：", new Object[] {
                    Constants.ACTIVITY_NAME, log.getUserId(), "成功添加" });
        } catch (Exception e) {
            LOGGER.error("OA审核后新增2000银元宝奖励日志产生异常{}",
                Throwables.getStackTraceAsString(e));
            session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }
}
