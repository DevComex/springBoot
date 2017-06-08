package cn.gyyx.action.bll.fightbrickgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.dao.fightbrickgame.FightBrickGameDao;

/**
 * 微信H5小游戏业务逻辑层
 * <p>
 * FightBrickGameBLL描述
 * </p>
 * 
 * @author yangteng
 * @since 0.0.1
 */
public class FightBrickGameBLL {

    private FightBrickGameDao fightBrickGameDao = new FightBrickGameDao();

    /**
     * 记录游戏得分
     * 
     * @param model
     * @return
     */
    public Integer addScore(FightBrickGameBean model) {
        return fightBrickGameDao.addScore(model);
    }

    /**
     * 查询排行200
     * 
     * @return
     */
    public List<FightBrickGameBean> getRank() {
        List<FightBrickGameBean> list = fightBrickGameDao.getRank();
        if (list == null) {
            return new ArrayList<FightBrickGameBean>();
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
        return fightBrickGameDao.getScoreInfo(openid);
    }

    /**
     * 更新用户得分信息
     * 
     * @param model
     * @return
     */
    public Integer update(FightBrickGameBean model) {
        return fightBrickGameDao.update(model);
    }

    /**
     * 查询中奖列表
     * 
     * @return
     */
    public List<FightBrickGameBean> getAwardList(Integer activityId, String hdEnd) {
        List<FightBrickGameBean> list = fightBrickGameDao
                .getAwardList(activityId, hdEnd);
        if (list == null) {
            return new ArrayList<FightBrickGameBean>();
        }        
        return list;
    }

    /**
     * 查询最新得分，当天最高得分，当天排行
     * 
     * @param openid
     */
    public FightBrickGameBean getRank(String openid) {
        return fightBrickGameDao.getRankByOpenId(openid);
    }

    /**
     * 查询临时抽奖资格的分数
     * 
     * @return
     */
    public Integer getTempQualification() {
        List<FightBrickGameBean> list = fightBrickGameDao.getRank();
        if (list == null||list.isEmpty()) {
            return 0;
        }
        //超过200人返回第200个人的分数,否则直接返回0
        return list.size() >= 200 ? list.get(list.size() - 1).getHighScore() : 0;
    }

    /**
     * 查询历史最高得分
     * 
     * @param openid
     * @return
     */
    public Integer getHighScore(String openid) {
        return fightBrickGameDao.getHighScore(openid);
    }

    /**
     * 查询历史最高排名
     * 
     * @param openid
     * @return
     */
    public Integer getHignRank(String openid) {
        return fightBrickGameDao.getHignRank(openid);
    }

    /**
     * oa后台h5小游戏列表
     * 
     * @param keyWord
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     */
    public List<FightBrickGameBean> getList(String keyWord, String beginTime,
            String endTime, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyWord);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        map.put("startSize", (pageIndex - 1) * pageSize);
        map.put("endSize", pageIndex * pageSize);

        List<FightBrickGameBean> list = fightBrickGameDao.getList(map);
        if (list == null) {
            return new ArrayList<FightBrickGameBean>();
        }
        return list;
    }

    /**
     * oa后台h5小游戏总条数
     * 
     * @param keyWord
     * @param beginTime
     * @param endTime
     */
    public Integer getCount(String keyWord, String beginTime, String endTime) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("keyword", keyWord);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);

        return fightBrickGameDao.getCount(map);
    }

    /**
     * 查询排行200
     * 
     * @param isYesterday
     *            昨天
     * @return
     */
    public List<FightBrickGameBean> getYesterdayRank() {
        List<FightBrickGameBean> list = fightBrickGameDao.getYesterdayRank();
        if (list == null) {
            return new ArrayList<FightBrickGameBean>();
        }
        return list;
    }

    /**
     * 
     * <p>
     * 定时任务更新昨日排行
     * </p>
     *
     * @action yangteng 2017年3月25日 下午3:38:25 描述
     *
     * @return Integer
     */
    public Integer updateYesterdayRank() {
        return fightBrickGameDao.updateYesterdayRank();
    }
    
    /**
     * 
      * <p>
      *    查询活动结束后发送的大礼包
      * </p>
      *
      * @action
      *    Administrator 2017年3月30日 下午9:11:45 描述
      *
      * @param activityId
      * @param hdEnd
      * @return List<FightBrickGameBean>
     */
    public List<FightBrickGameBean> getGiftList(Integer activityId, String hdEnd) {
        List<FightBrickGameBean> list = fightBrickGameDao.getGiftList(activityId, hdEnd);                
        if (list == null||list.isEmpty()) {
            return new ArrayList<FightBrickGameBean>();
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
      *    yangteng 2017年4月7日 下午1:20:47 描述
      *
      * @param activityId
      * @param openid
      * @param ip
      * @return Integer
     */
    public Integer addShareLog(Integer activityId, String openid, String ip) {
        return fightBrickGameDao.addShareLog(activityId, openid, ip);
    }
}
