/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdblessingcard2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年3月14日 下午5:50:33
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wdblessingcard2017.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wdblessingcard2017.LotteryPrizesBeanDao;
import cn.gyyx.action.service.wdblessingcard2017.PrizeService;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
  * <p>
  *   PrizeServiceImpl描述
  * </p>
  *  
  * @author niushuai
  * @since 0.0.1
  */
public class PrizeServiceImpl implements PrizeService {

    GYYXLogger logger = GYYXLoggerFactory.getLogger(PrizeServiceImpl.class);
    
    NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();    
    LotteryPrizesBeanDao lotteryPrizesBeanDao = new LotteryPrizesBeanDao();
    /* (non-Javadoc)
     * @see cn.gyyx.action.service.wdblessingcard2017.PrizeService#getPrizes(int)
     */
    @Override
    public List<PrizeBean> getPrizes(int actionCode) {
        return userLotteryBll.getPrize(actionCode, getSession());
    }

    protected SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        logger.debug("Open a mybatis sqlsession with no auto-commit.");
        return sqlSessionFactory.openSession();
    }

    /* (non-Javadoc)
     * @see cn.gyyx.action.service.wdblessingcard2017.PrizeService#prizeCount(java.lang.String, java.lang.String)
     */
    @Override
    public int prizeCount(String account, String prizeType, int actionCode) {
        
        return lotteryPrizesBeanDao.prizeCount(account, prizeType, actionCode);
    }
}
