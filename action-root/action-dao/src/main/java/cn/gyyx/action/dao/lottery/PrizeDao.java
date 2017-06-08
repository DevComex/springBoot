/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:06:15
 * 版本号：v1.0
 * 本类主要用途叙述：奖品的数据库Dao
 * 
 */



package cn.gyyx.action.dao.lottery;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.backstage.ILimitMapper;
import cn.gyyx.action.dao.backstage.LimitDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class PrizeDao implements IPrize {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PrizeDao.class);

	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	@Override
	/**
	 * 获得奖品信息
	 */
	public List<PrizeBean> getPrize(int actionCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IPrize prize =sqlSession.getMapper(IPrize.class);
			return prize.getPrize(actionCode);
		}
		
	}
	public PrizeBean getPrizeByCode(@Param("code") int code){
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IPrize prize =sqlSession.getMapper(IPrize.class);
			return prize.getPrizeByCode(code);
		}
	}
	public Integer updatePrizeBean(PrizeBean prizeBean){
		Integer result = null;
		SqlSession session = getSession();
		try {
			IPrize prize = session
					.getMapper(IPrize.class);
			result = prize.updatePrizeBean(prizeBean);
			session.commit();
		} catch (Exception e) {
			
		} finally {
			session.close();
		}
		return result;
	}
	public Integer updatePrizeBeanAll(PrizeBean prizeBean){
		Integer result = null;
		SqlSession session = getSession();
		try {
			IPrize prize = session
					.getMapper(IPrize.class);
			result = prize.updatePrizeBeanAll(prizeBean);
			session.commit();
		} catch (Exception e) {
			logger.error("updatePrizeBeanAll",e);
		} finally {
			session.close();
		}
		return result;
	}
	@Override
	public List<PrizeBean> getPrizeAll(int actionCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IPrize prize =sqlSession.getMapper(IPrize.class);
			return prize.getPrizeAll(actionCode);
		}
	}
	@Override
	public Integer insertPrize(PrizeBean prizeBean) {
		SqlSession session = getSession();
		Integer result = 0;
		try{
			IPrize mapper = session.getMapper(IPrize.class);
			result = mapper.insertPrize(prizeBean);
			session.commit();
		}catch(Exception e){
			logger.warn("insertPrize"+e.toString());
		}finally{
			session.close();
		}
		return result; 
	}
	@Override
	public PrizeBean getPrizeByNum(PrizeBean prizeBean) {
		try (SqlSession sqlSession = getSession()) {
			IPrize prize =sqlSession.getMapper(IPrize.class);
			return prize.getPrizeByNum(prizeBean);
		}
	}
}
