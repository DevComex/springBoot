/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：laixiancai
  * @联系方式：laixiancai@gyyx.cn
  * @创建时间：2017年4月10日 下午6:10:36
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wdrankinglist2017;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import cn.gyyx.action.beans.wdrankinglist2017.LotteryPrizesBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 抽奖相关Dao
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class LotteryPrizesBeanDao extends MyBatisBaseDAO {
    /**
     * 
     * <p>
     * 方法说明
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午6:12:49 描述
     *
     * @param activityId
     * @param account
     * @param prizeCodeList
     * @return int
     */
    public int getUserLotteryCount(int activityId, String account,
            List<Integer> prizeCodeList) {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.getUserLotteryCount(activityId, account,
                prizeCodeList);
        }
    }

    /**
     * 
     * <p>
     * 获取抽奖页面滚动中奖列表数据（200条）
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:05:39 描述
     *
     * @return List<LotteryPrizesBean>
     */
    public List<LotteryPrizesBean> getLotteryList() {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.getLotteryList();
        }
    }

    /**
     * 
     * <p>
     * 根据openId获取当前玩家的中奖记录
     * </p>
     *
     * @action laixiancai 2017年4月11日 上午9:05:32 描述
     *
     * @param openId
     * @return List<LotteryPrizesBean>
     */
    public List<LotteryPrizesBean> getLotteryListByOpenId(String openId) {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.getLotteryListByOpenId(openId);
        }
    }
}
