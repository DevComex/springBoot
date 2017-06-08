package cn.gyyx.action.dao.wechat;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.wechat.LotteryChanceSupplyBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class LotteryChanceSupplyDao {

	private static final Logger logger = LoggerFactory
			.getLogger(LotteryChanceSupplyDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取补充总数
	 * @param chanceCode
	 * @return
	 */
	public int getSupplyCount(int chanceCode){
		SqlSession session = getSession();
		int count = 0;
		try {
			ILotteryChanceSupplyMapper mapper = session
					.getMapper(ILotteryChanceSupplyMapper.class);
			count = mapper.getSupplyCount(chanceCode);
		} catch (Exception e) {
			logger.warn("getSupplyCount" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	
	/**
	 * 增加奖品补充信息
	 * @param lotteryChanceSupplyBean
	 */
	public void addPrizeSupply(LotteryChanceSupplyBean lotteryChanceSupplyBean){
		SqlSession session = getSession();
		try {
			ILotteryChanceSupplyMapper mapper = session
					.getMapper(ILotteryChanceSupplyMapper.class);
			mapper.addPrizeSupply(lotteryChanceSupplyBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addPrizeSupply" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 获取最新补充
	 * @param chanceCode
	 * @return
	 */
	public LotteryChanceSupplyBean getTopSupply(int chanceCode){
		SqlSession session = getSession();
		LotteryChanceSupplyBean lotteryChanceSupplyBean = new LotteryChanceSupplyBean();
		try {
			ILotteryChanceSupplyMapper mapper = session
					.getMapper(ILotteryChanceSupplyMapper.class);
			lotteryChanceSupplyBean = mapper.getTopSupply(chanceCode);
		} catch (Exception e) {
			logger.warn("getTopSupply" + e.toString());
		} finally {
			session.close();
		}
		return lotteryChanceSupplyBean;
	}
}
