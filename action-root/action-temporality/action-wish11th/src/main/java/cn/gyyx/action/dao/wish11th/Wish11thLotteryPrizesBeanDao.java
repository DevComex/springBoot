/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/6 15:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wish11th;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wish11th.Wish11thLotteryPrizesBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 问道许愿活动抽奖日志数据访问类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thLotteryPrizesBeanDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 获取用户或ip抽取的奖品列表
     * </p>
     *
     * @action tanjunkai 2017年4月6日 下午8:34:40 描述
     *
     * @param userId
     *            用户ID
     * @param ip
     *            IP
     * @param actionCodeList
     *            活动code
     * @return int
     */
    public List<Wish11thLotteryPrizesBean> getUserIPPrizeList(
            String userId, String ip, List<Integer> actionCodeList) {
        try (SqlSession sqlSession = this.getSession(true)) {
            Wish11thLotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(Wish11thLotteryPrizesBeanMapper.class);
            return mapper.getUserIPPrizeList(userId, ip, actionCodeList);
        }
    }
    
    /**
      * <p>
      *    获取当前用户或ip中实物奖或银元宝的数量
      * </p>
      *
      * @action
      *    tanjunkai 2017年4月14日 上午1:49:34 描述
      *
      * @param userId
      * @param ip
      * @param actionCodeList
      * @return int
      */
    public int getCountForRealOrSilver(String userId, String ip)
    {
        try (SqlSession sqlSession = this.getSession(true)) {
            Wish11thLotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(Wish11thLotteryPrizesBeanMapper.class);
            return mapper.getCountForRealOrSilver(userId, ip);
        }
    }
}
