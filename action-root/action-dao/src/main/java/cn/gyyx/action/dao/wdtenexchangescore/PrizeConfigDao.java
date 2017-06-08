/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日上午11:23:46
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.wdtenexchangescore;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.exchangescore.PrizeConfigBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class PrizeConfigDao {

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 获取奖品主键
	 * 
	 * @param prizeCode
	 * @param sqlSession
	 * @return
	 */
	public PrizeConfigBean getPrizeConfigBeanByPrizeCode(int prizeCode) {
		try (SqlSession sqlSession = getSession()) {
		IPrizeConfig iPrizeConfig = sqlSession.getMapper(IPrizeConfig.class);
		return iPrizeConfig.getPrizeConfigBeanByPrizeCode(prizeCode);
		}
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
		
			IPrizeConfig iPrizeConfig = sqlSession
					.getMapper(IPrizeConfig.class);
			return iPrizeConfig
					.getPrizeConfigBeanByPrizeCodeWithLock(prizeCode);
		
	}

	/***
	 * /兑换奖品更改配置的信息
	 * 
	 * @param prizeCode
	 * @param sqlSession
	 * @return
	 */
	public void exchangePrize(int prizeCode, SqlSession sqlSession) {
		IPrizeConfig iPrizeConfig = sqlSession.getMapper(IPrizeConfig.class);
		iPrizeConfig.exchangePrize(prizeCode);
	}

	// 获取所有配置信息
	public List<PrizeConfigBean> getAllExchangePrizes(SqlSession sqlSession) {
		IPrizeConfig iPrizeConfig = sqlSession.getMapper(IPrizeConfig.class);
		return iPrizeConfig.getAllExchangePrizes();
	}

	// 更新中奖次数了
	public void setTimeByCode(int code, int num, SqlSession sqlSession) {
		IPrizeConfig iPrizeConfig = sqlSession.getMapper(IPrizeConfig.class);
		iPrizeConfig.setTimeByCode(code, num);
	}

}
