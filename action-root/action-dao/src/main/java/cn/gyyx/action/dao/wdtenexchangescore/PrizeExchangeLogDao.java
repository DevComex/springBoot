/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日下午1:15:05
 * 版本号：v1.0
 * 本类主要用途叙述：奖品日志的数据库交互层
 */

package cn.gyyx.action.dao.wdtenexchangescore;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.exchangescore.PrizeExchangeLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class PrizeExchangeLogDao {
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 根据奖品主键和活动主键
	 * 
	 * @param prizeCode
	 * @return
	 */
	public PrizeExchangeLogBean getTopOneByPrizeAndUserOrderByTime(
			int prizeCode, int actionCode, int userCode, SqlSession sqlSession) {
		IPrizeExchangeLog iPrizeExchangeLog = sqlSession
				.getMapper(IPrizeExchangeLog.class);
		return iPrizeExchangeLog.getTopOneByPrizeAndUserOrderByTime(prizeCode,
				actionCode, userCode);

	}

	/***
	 * 增加一条日志信息
	 * 
	 */
	public void addPrizeExchangeLog(PrizeExchangeLogBean prizeExchangeLogBean,
			SqlSession sqlSession) {
		IPrizeExchangeLog iPrizeExchangeLog = sqlSession
				.getMapper(IPrizeExchangeLog.class);
		iPrizeExchangeLog.addPrizeExchangeLog(prizeExchangeLogBean);
	}
	
	/***
	 * 增加一条日志信息
	 * 
	 */
	public void addPrizeExchangeLog(PrizeExchangeLogBean prizeExchangeLogBean) {
		try(SqlSession sqlSession=getSession()){
		IPrizeExchangeLog iPrizeExchangeLog = sqlSession
				.getMapper(IPrizeExchangeLog.class);
		iPrizeExchangeLog.addPrizeExchangeLog(prizeExchangeLogBean);
		sqlSession.commit(true);
		}
	}


	/***
	 * 
	 * 得到所有中奖信息
	 * 
	 * @return
	 */
	public List<PrizeExchangeLogBean> getAllPrizeExchangeLogBeans(
			SqlSession sqlSession) {
		IPrizeExchangeLog iPrizeExchangeLog = sqlSession
				.getMapper(IPrizeExchangeLog.class);
		return iPrizeExchangeLog.getAllPrizeExchangeLogBeans();

	}

	/**
	 * 得到用户的兑换信息
	 * 
	 * @param actionCode
	 * @param userCode
	 * @return
	 */
	public List<PrizeExchangeLogBean> getExchangeLogBeansByUserCode(
			int actionCode, int userCode, SqlSession sqlSession) {
		IPrizeExchangeLog iPrizeExchangeLog = sqlSession
				.getMapper(IPrizeExchangeLog.class);
		return iPrizeExchangeLog.getExchangeLogBeansByUserCode(actionCode,
				userCode);
	}

}
