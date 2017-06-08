package cn.gyyx.action.dao.fightbrickgame;

import cn.gyyx.action.beans.fightbrickgame.FightBrickGameHistoryBean;

public interface IFightBrickGameHistoryMapper {

    /**
     * 记录游戏得分
     * 
     * @param model
     * @return
     */
    Integer addScoreHistory(FightBrickGameHistoryBean model);
}
