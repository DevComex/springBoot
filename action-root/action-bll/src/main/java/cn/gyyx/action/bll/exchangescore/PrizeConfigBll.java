/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日上午11:33:12
 * 版本号：v1.0
 * 本类主要用途叙述：奖品配置的业务层
 */

package cn.gyyx.action.bll.exchangescore;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.exchangescore.PrizeConfigBean;
import cn.gyyx.action.dao.wdtenexchangescore.PrizeConfigDao;

public class PrizeConfigBll {
	private PrizeConfigDao prizeConfigDao = new PrizeConfigDao();

	/***
	 * 获取奖品主键
	 * 
	 * @param prizeCode
	 * @param sqlSession
	 * @return
	 */
	public PrizeConfigBean getPrizeConfigBeanByPrizeCode(int prizeCode) {
		return prizeConfigDao.getPrizeConfigBeanByPrizeCode(prizeCode);

	}

	/***
	 * /兑换奖品更改配置的信息
	 * 
	 * @param prizeCode
	 * @param sqlSession
	 * @return
	 */
	public void exchangePrize(int prizeCode, SqlSession sqlSession) {
		prizeConfigDao.exchangePrize(prizeCode, sqlSession);
	}

	// 获取所有配置信息
	public List<PrizeConfigBean> getAllExchangePrizes(SqlSession sqlSession) {
		return prizeConfigDao.getAllExchangePrizes(sqlSession);
	}

	// 更新中奖次数了
	public void setTimeByCode(int code, int num, SqlSession sqlSession) {
		prizeConfigDao.setTimeByCode(code, num, sqlSession);

	}

	/***
	 * 获取奖品主键
	 * 
	 * @param prizeCode
	 * @param sqlSession
	 * @return
	 */
	public PrizeConfigBean getPrizeConfigBeanByPrizeCodeWithLock(int prizeCode,
			SqlSession sqlSession) {
		return prizeConfigDao.getPrizeConfigBeanByPrizeCodeWithLock(prizeCode,
				 sqlSession);
	}
}
