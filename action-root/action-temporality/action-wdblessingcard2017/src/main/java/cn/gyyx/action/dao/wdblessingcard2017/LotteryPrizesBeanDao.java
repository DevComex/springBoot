/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：niushuai
 * @联系方式：niushuai@gyyx.cn
 * @创建时间：2017年3月13日 下午7:37:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdblessingcard2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdblessingcard2017.LotteryPrizesBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.ui.wdblessingcard2017.QueryBean;

/**
 * <p>
 * 中奖记录dao
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class LotteryPrizesBeanDao extends MyBatisBaseDAO {

    /**
     * <p>
     * 通过用户账户获得中奖信息
     * </p>
     *
     * @action niushuai 2017年3月11日 下午5:24:56 描述
     *
     * @param queryBean
     * @return List<LotteryPrizesBean>
     */
    public List<LotteryPrizesBean> getLotteryListByAccount(QueryBean queryBean) {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.getLotteryListByAccount(queryBean);
        }
    }

    /**
     * <p>
     * 获取最新的几条中奖记录
     * </p>
     *
     * @action niushuai 2017年3月12日 下午11:22:22 描述
     *
     * @param queryBean
     * @return List<LotteryPrizesBean>
     */
    public List<LotteryPrizesBean> getLotteryList(QueryBean queryBean) {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.getLotteryList(queryBean);
        }
    }

    /**
     * <p>
     * 统计某一用户已经抽到了某一活动的奖品数量 <br>
     * 相同账号或者相同ip都认为是同一用户
     * </p>
     *
     * @action niushuai 2017年3月14日 下午3:23:52 描述
     *
     * @param activityId
     * @param account
     * @param lotteryCodeList
     * @return int
     */
    public int getUserLotteryCount(int activityId, String account, String ip,
            List<Integer> prizeCodeList) {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.getUserLotteryCount(activityId, account, ip,
                prizeCodeList);
        }
    }

    /**
     * <p>
     * 统计用户中奖数量
     * </p>
     *
     * @action niushuai 2017年3月17日 下午2:06:08 描述
     *
     * @param account
     * @param prizeType
     * @return int
     */
    public int prizeCount(String account, String prizeType, Integer actionCode) {
        try (SqlSession sqlSession = this.getSession(true)) {
            LotteryPrizesBeanMapper mapper = sqlSession
                    .getMapper(LotteryPrizesBeanMapper.class);
            return mapper.prizeCount(account, prizeType, actionCode);
        }
    }

}
