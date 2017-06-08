package cn.gyyx.action.ui.fightbrickgame;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.fightbrickgame.Constants;
import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.bll.fightbrickgame.FightBrickGameBLL;
import cn.gyyx.distribute.lock.DistributedLock;

/**
 * 
 * <p>
 * 计算昨天的排名定时任务
 * </p>
 * 
 * @author yangteng
 * @since 0.0.1
 */
public class RankTask {
    private Logger logger = LoggerFactory.getLogger(RankTask.class);

    private FightBrickGameBLL fightBrickGameBll = new FightBrickGameBLL();

    public void start() {
        logger.info("计算昨天排名的定时任务开始");
        try (DistributedLock lock = new DistributedLock(
                Constants.DISTRIBUTED_LOCK_RANK_TASK)) {
            List<FightBrickGameBean> list = fightBrickGameBll
                    .getYesterdayRank();
            if (list.isEmpty()) {
                logger.info("查询昨天排行失败");
                return;
            }

            FightBrickGameBean model = list.get(list.size() - 1);
            Integer historyRank = model.getHistoryRank();
            if (historyRank != null) {
                logger.info("定时计算昨天排行已经执行,直接返回");
                return;
            }

            logger.info("更新昨日排行开始");
            Integer row = fightBrickGameBll.updateYesterdayRank();
            logger.info(row > 0 ? "更新昨日排行成功" : "更新昨日排行失败");
        } catch (Exception ex) {
            logger.error("定时计算昨天的排名任务,分布式锁失败", ex);
        }
        logger.info("计算昨天排名的定时任务结束");
    }
}
