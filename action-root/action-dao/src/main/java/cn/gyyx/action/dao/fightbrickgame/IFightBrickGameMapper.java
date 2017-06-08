package cn.gyyx.action.dao.fightbrickgame;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;

public interface IFightBrickGameMapper {

    /**
     * 记录游戏得分
     * 
     * @param model
     * @return
     */
    Integer addScore(FightBrickGameBean model);

    /**
     * 查询当天排行
     * 
     * @param isYesterday
     *            昨天排行
     * @return
     */
    List<FightBrickGameBean> getRank();

    /**
     * 查询用户当天的记录
     * 
     * @param openid
     * @return
     */
    FightBrickGameBean getScoreInfo(String openid);

    /**
     * 更新用户信息
     * 
     * @param model
     * @return
     */
    Integer update(FightBrickGameBean model);

    /**
     * 查询用户中奖列表
     * 
     * @return
     */
    List<FightBrickGameBean> getAwardList(@Param("activityId") Integer activityId, @Param("hdEnd") String hdEnd);

    /**
     * 查询历史最高得分
     * 
     * @param openid
     * @return
     */
    Integer getHighScore(String openid);

    /**
     * 查询排行
     * 
     * @param openid
     * @return
     */
    FightBrickGameBean getRankByOpenId(String openid);

    /**
     * 查询历史最高排名
     * 
     * @param openid
     * @return
     */
    Integer getHighTop(String openid);

    /**
     * oa后台查询列表
     * 
     * @param map
     * @return
     */
    List<FightBrickGameBean> getList(Map<String, Object> map);

    /**
     * oa后台查询总行数
     * 
     * @param map
     * @return
     */
    Integer getCount(Map<String, String> map);

    /**
     * 查询昨天排行
     * 
     * @return
     */
    List<FightBrickGameBean> getYesterdayRank();

    /**
     * 
     * <p>
     * 定时任务更新昨日排行
     * </p>
     *
     * @action yangteng 2017年3月25日 下午3:40:20 描述
     *
     * @return Integer
     */
    Integer updateYesterdayRank();
    
    /**
     * 
      * <p>
      *    查询活动结束后大礼包
      * </p>
      *
      * @action
      *    yangteng 2017年3月30日 下午8:27:22 描述
      *
      * @param activityId
      * @param hdEnd
      * @return List<FightBrickGameBean>
     */
    List<FightBrickGameBean> getGiftList(@Param("activityId") Integer activityId, @Param("hdEnd") String hdEnd);
    
    /**
     * 
      * <p>
      *    记录分享日志
      * </p>
      *
      * @action
      *    yangteng 2017年4月7日 下午1:24:02 描述
      *
      * @param activityId
      * @param openid
      * @param ip
      * @return Integer
     */
    Integer addShareLog(@Param("activityId") Integer activityId, @Param("openid") String openid, @Param("ip") String ip);
}
