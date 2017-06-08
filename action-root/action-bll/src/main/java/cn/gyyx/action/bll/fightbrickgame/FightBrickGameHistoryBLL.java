package cn.gyyx.action.bll.fightbrickgame;

import cn.gyyx.action.beans.fightbrickgame.FightBrickGameHistoryBean;
import cn.gyyx.action.dao.fightbrickgame.FightBrickGameHistoryDao;

public class FightBrickGameHistoryBLL {

    private FightBrickGameHistoryDao fightBrickGameHistoryDao = new FightBrickGameHistoryDao();

    /**
     * 记录游戏得分
     * 
     * @param model
     * @return
     */
    public Integer addScoreHistory(FightBrickGameHistoryBean model) {
        return fightBrickGameHistoryDao.addScoreHistory(model);
    }
}
