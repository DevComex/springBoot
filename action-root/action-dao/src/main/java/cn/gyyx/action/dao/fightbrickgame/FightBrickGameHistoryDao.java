package cn.gyyx.action.dao.fightbrickgame;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.fightbrickgame.FightBrickGameHistoryBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wechat.WeChatCountDao;

public class FightBrickGameHistoryDao extends MyBatisBaseDAO {

    private static final Logger logger = LoggerFactory
            .getLogger(WeChatCountDao.class);

    /**
     * 记录游戏得分
     */
    public Integer addScoreHistory(FightBrickGameHistoryBean model) {
        Integer number = 0;
        SqlSession session = getSession(true);
        try {
            IFightBrickGameHistoryMapper mapper = session
                    .getMapper(IFightBrickGameHistoryMapper.class);
            number = mapper.addScoreHistory(model);
            return number;
        } catch (Exception ex) {
            logger.warn("addScoreHistory" + ex.toString());
        } finally {
            session.close();
        }
        return number;
    }
}
