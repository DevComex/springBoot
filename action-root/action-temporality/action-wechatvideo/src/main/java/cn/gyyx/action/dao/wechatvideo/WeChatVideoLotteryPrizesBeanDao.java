/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/17 17:22
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechatvideo;

import cn.gyyx.action.dao.MyBatisBaseDAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

/**
 * <p>
 * 抽奖记录数据访问
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoLotteryPrizesBeanDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 统计某一用户已经抽到了某一活动的奖品数量
     * </p>
     *
     * @action tanjunkai 2017年3月17日 下午5:46:04 描述
     *
     * @param activityId
     *            活动ID
     * @param userId
     *            用户ID
     * @param prizeCodeList
     *            奖品码列表
     * @return int
     */
    public int getUserLotteryCount(int activityId, int userId,
            List<Integer> prizeCodeList) {
        try (SqlSession sqlSession = this.getSession(true)) {
            WeChatVideoLotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(WeChatVideoLotteryPrizesBeanMapper.class);
            return mapper.getUserLotteryCount(activityId, userId,
                prizeCodeList);
        }
    }
}
