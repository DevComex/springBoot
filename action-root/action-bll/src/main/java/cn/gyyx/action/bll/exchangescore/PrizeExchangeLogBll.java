/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日下午1:18:25
 * 版本号：v1.0
 * 本类主要用途叙述：奖品兑换日志的业务层
 */

package cn.gyyx.action.bll.exchangescore;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.exchangescore.PrizeExchangeLogBean;
import cn.gyyx.action.dao.wdtenexchangescore.PrizeExchangeLogDao;

public class PrizeExchangeLogBll {
	private PrizeExchangeLogDao prizeExchangeDao = new PrizeExchangeLogDao();

	/**
	 * 根据奖品主键和活动主键
	 * 
	 * @param prizeCode
	 * @return
	 */
	public PrizeExchangeLogBean getTopOneByPrizeAndUserOrderByTime(
			int prizeCode, int actionCode, int userCode, SqlSession sqlSession) {
		return prizeExchangeDao.getTopOneByPrizeAndUserOrderByTime(prizeCode,
				actionCode, userCode, sqlSession);
	}

	/***
	 * 增加一条日志信息
	 * 
	 */
	public void addPrizeExchangeLog(PrizeExchangeLogBean prizeExchangeLogBean,
			SqlSession sqlSession) {
		prizeExchangeDao.addPrizeExchangeLog(prizeExchangeLogBean, sqlSession);
	}
	/***
	 * 增加一条日志信息
	 * 
	 */
	public void addPrizeExchangeLog(PrizeExchangeLogBean prizeExchangeLogBean) {
		prizeExchangeDao.addPrizeExchangeLog(prizeExchangeLogBean);
	}

	/***
	 * 
	 * 得到所有中奖信息
	 * 
	 * @return
	 */
	public List<PrizeExchangeLogBean> getAllPrizeExchangeLogBeans(
			SqlSession sqlSession) {
		return prizeExchangeDao.getAllPrizeExchangeLogBeans(sqlSession);
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
		return prizeExchangeDao.getExchangeLogBeansByUserCode(actionCode,
				userCode, sqlSession);
	}
}
