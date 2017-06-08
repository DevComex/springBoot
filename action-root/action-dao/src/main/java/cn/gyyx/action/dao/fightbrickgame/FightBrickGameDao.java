package cn.gyyx.action.dao.fightbrickgame;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.wechat.WeChatCountDao;

/**
 * 
 * <p>
 * 微信H5小游戏数据访问层
 * </p>
 * 
 * @author Administrator
 * @since 0.0.1
 */
public class FightBrickGameDao extends MyBatisBaseDAO {

    private static final Logger logger = LoggerFactory
            .getLogger(WeChatCountDao.class);

    /**
     * 记录游戏得分
     */
    public Integer addScore(FightBrickGameBean model) {
        Integer number = 0;
        SqlSession session = this.getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number = mapper.addScore(model);
            return number;
        } catch (Exception ex) {
            logger.error("addScore,异常信息", ex);
        } finally {
            session.close();
        }
        return number;
    }

    /**
     * 查询排行榜
     */
    public List<FightBrickGameBean> getRank() {

        List<FightBrickGameBean> list = null;

        SqlSession session = this.getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            list = mapper.getRank();
        } catch (Exception ex) {
            logger.error("getRank,异常信息", ex);
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * 查询玩家当天是有记录
     * 
     * @param openid
     * @return
     */
    public FightBrickGameBean getScoreInfo(String openid) {

        FightBrickGameBean model = null;

        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            model = mapper.getScoreInfo(openid);
        } catch (Exception ex) {
            logger.error("getScoreInfo,异常信息", ex);
        } finally {
            session.close();
        }
        return model;
    }

    /**
     * 更新玩家得分信息
     * 
     * @param model
     * @return
     */
    public Integer update(FightBrickGameBean model) {
        Integer number = 0;
        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number = mapper.update(model);
        } catch (Exception ex) {
            logger.error("update,异常信息", ex);
        } finally {
            session.close();
        }
        return number;
    }

    /**
     * 查询用户中奖列表
     */
    public List<FightBrickGameBean> getAwardList(Integer activityId, String hdEnd) {
        List<FightBrickGameBean> list = null;

        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            list = mapper.getAwardList(activityId, hdEnd);
        } catch (Exception ex) {
            logger.error("getAwardList,异常信息", ex);
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * 查询历史最高得分
     */
    public Integer getHighScore(String openid) {
        Integer number = 0;
        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number = mapper.getHighScore(openid);
        } catch (Exception ex) {
            logger.error("getHighScore,异常信息", ex);
        } finally {
            session.close();
        }
        return number;
    }

    /**
     * 查询排行
     */
    public FightBrickGameBean getRankByOpenId(String openid) {
        FightBrickGameBean model = null;

        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            model = mapper.getRankByOpenId(openid);
        } catch (Exception ex) {
            logger.error("getRankByOpenId,异常信息", ex);
        } finally {
            session.close();
        }
        return model;
    }

    /**
     * 
     * <p>
     * 根据openid查询历史最高排行
     * </p>
     *
     * @action yangteng 2017年3月24日 下午8:25:53 描述
     *
     * @param openid
     * @return Integer
     */
    public Integer getHignRank(String openid) {
        Integer number = 0;
        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number = mapper.getHighTop(openid);
        } catch (Exception ex) {
            logger.error("getHignRank,异常信息", ex);
        } finally {
            session.close();
        }
        return number;
    }

    /**
     * 
     * <p>
     * oa后台H5小游戏的列表
     * </p>
     *
     * @action yangteng 2017年3月24日 下午8:25:09 描述
     *
     * @param map
     * @return List<FightBrickGameBean>
     */
    public List<FightBrickGameBean> getList(Map<String, Object> map) {
        List<FightBrickGameBean> list = null;

        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            list = mapper.getList(map);
        } catch (Exception ex) {
            logger.error("getList,异常信息", ex);
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * 
     * <p>
     * oa后台H5小游戏的数据总条数
     * </p>
     *
     * @action yangteng 2017年3月24日 下午8:24:22 描述
     *
     * @param map
     * @return Integer
     */
    public Integer getCount(Map<String, String> map) {
        Integer number = 0;
        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number = mapper.getCount(map);
        } catch (Exception ex) {
            logger.error("getCount,异常信息", ex);
        } finally {
            session.close();
        }
        return number;
    }

    /**
     * 查询昨天排行榜
     */
    public List<FightBrickGameBean> getYesterdayRank() {

        List<FightBrickGameBean> list = null;

        SqlSession session = this.getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            list = mapper.getYesterdayRank();
        } catch (Exception ex) {
            logger.error("getYesterdayRank,异常信息", ex);
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * 
     * <p>
     * 定时任务更新昨日排行
     * </p>
     *
     * @action yangteng 2017年3月25日 下午3:39:27 描述
     *
     * @return Integer
     */
    public Integer updateYesterdayRank() {
        Integer number = 0;
        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number = mapper.updateYesterdayRank();
            return number;
        } catch (Exception ex) {
            logger.error("updateYesterdayRank,异常信息", ex);
        } finally {
            session.close();
        }
        return number;
    }

    /**
     * 
      * <p>
      *    查询活动结束后大礼包
      * </p>
      *
      * @action
      *    yangteng 2017年3月30日 下午8:25:47 描述
      *
      * @param activityId
      * @param hdEnd void
     */
    public List<FightBrickGameBean> getGiftList(Integer activityId, String hdEnd) {
        List<FightBrickGameBean> list = null;

        SqlSession session = getSession(true);
        try {
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            list = mapper.getGiftList(activityId, hdEnd);
        } catch (Exception ex) {
            logger.error("getGiftList,异常信息", ex);
        } finally {
            session.close();
        }
        return list;
        
    }

    /**
     * 
      * <p>
      *    记录分享日志
      * </p>
      *
      * @action
      *    yangteng 2017年4月7日 下午1:21:46 描述
      *
      * @param activityId
      * @param openid
      * @param ip
      * @return Integer
     */
    public Integer addShareLog(Integer activityId, String openid, String ip) {
        Integer number=0;
        SqlSession session = getSession(true);
        try{
            IFightBrickGameMapper mapper = session
                    .getMapper(IFightBrickGameMapper.class);
            number=mapper.addShareLog(activityId, openid, ip);
        }catch(Exception ex){
            logger.error("addShareLog,异常信息", ex);
        }finally{
            session.close();
        }
        return number;
    }
}
