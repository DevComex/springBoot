/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/20 15:02
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wechatvideo;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.wechatvideo.Constants;
import cn.gyyx.action.beans.wechatvideo.WeChatVideoRoleBindBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.PrizeDao;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 问道周年视频祝福活动奖品相关服务
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoPrizeService {
    GYYXLogger logger = GYYXLoggerFactory
            .getLogger(WeChatVideoPrizeService.class);

    // 奖品数据访问
    private static final PrizeDao prizeDao = new PrizeDao();

    // 抽奖日志数据访问
    private static final IActionLotteryLogDAO actionLotteryLogDao = new ActionLotteryLogDAOImpl();

    /**
     * <p>
     * 获取活动的所有奖品
     * </p>
     *
     * @action tanjunkai 2017年3月22日 下午1:55:22 描述
     *
     * @param actionCode
     *            活动标号
     * @return List<PrizeBean> 奖品列表
     */
    public List<PrizeBean> getPrizes(int actionCode) {
        return prizeDao.getPrize(actionCode);
    }

    /**
     * <p>
     * 获取事务
     * </p>
     *
     * @action tanjunkai 2017年3月22日 下午1:55:40 描述
     *
     * @return SqlSession
     */
    protected SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with no auto-commit.");
        return sqlSessionFactory.openSession();
    }

    /**
     * <p>
     * 获取我的中奖记录
     * </p>
     *
     * @action tanjunkai 2017年3月22日 下午2:09:27 描述
     *
     * @param roleBindInfo
     *            角色绑定信息
     * @return List<ActionLotteryLogPO>
     */
    public List<ActionLotteryLogPO> getMyPrize(
            WeChatVideoRoleBindBean roleBindInfo) {
        ActionLotteryLogPO params = new ActionLotteryLogPO();
        params.setUserId(roleBindInfo.getUserId().toString());
        params.setActivityId(Constants.ACTIVITY_ID);
        params.setActivityType(Constants.ACTIVITY_TYPE);
        List<ActionLotteryLogPO> prizeList = actionLotteryLogDao
                .selectExceptThankYou(params);
        List<ActionLotteryLogPO> myPrizeList = new ArrayList<ActionLotteryLogPO>();
        for (ActionLotteryLogPO prize : prizeList) {
            if (!prize.getPrizeName().equals(Constants.PRIZE_THANKS_NAME)) {
                myPrizeList.add(prize);
            }
        }
        return myPrizeList;
    }
}
