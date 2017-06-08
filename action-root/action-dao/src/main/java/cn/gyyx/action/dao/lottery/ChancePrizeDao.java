/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月9日下午3:44:34
 * 版本号：v1.0
 * 本类主要用途叙述：按概率抽奖配置信息的Dao层
 */



package cn.gyyx.action.dao.lottery;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ChancePrizeDao implements IChancePrize {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChancePrizeDao.class);
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
	/**
	 * 得到按概率抽奖奖品配置信息
	 */
	@Override
	public List<ChancePrizeBean> getChancePrize(int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IChancePrize iChancePrize=sqlSession.getMapper(IChancePrize.class);
			return iChancePrize.getChancePrize(actionCode);
					
				}
	
	}
	public Integer updateChancePrizeBean(ChancePrizeBean chancePrizeBean){
		Integer result = null;
		SqlSession session = getSession();
		try {
			IChancePrize iChancePrize = session
					.getMapper(IChancePrize.class);
			result = iChancePrize.updateChancePrizeBean(chancePrizeBean);
			session.commit();
		} catch (Exception e) {
			
		} finally {
			session.close();
		}
		return result;
	}
	public ChancePrizeBean getChancePrizeByprizeCode(Integer prizeCode){
		ChancePrizeBean result = null;
		SqlSession session = getSession();
		try {
			IChancePrize iChancePrize = session
					.getMapper(IChancePrize.class);
			result = iChancePrize.getChancePrizeByprizeCode(prizeCode);
		} catch (Exception e) {
			logger.warn("getChancePrizeByprizeCode"+e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	
	    /**
	     * 根据奖品编号获取奖品的概率和数量
	     */
	    public ChancePrizeBean getPrizeProbabilityAndNumberByPrizeCode(Integer prizeCode) {
	        ChancePrizeBean result = null;
	        SqlSession session = getSession();
	        try {
	            IChancePrize iChancePrize = session.getMapper(IChancePrize.class);
	            result = iChancePrize.getPrizeProbabilityAndNumberByPrizeCode(prizeCode);
	        } catch (Exception e) {
	            logger.warn("getChancePrizeByprizeCode" + e.toString());
	        } finally {
	            session.close();
	        }
	        return result;
	    }
	    
	    /**
	     * 根据奖品编号获取该奖品对应的所有奖池
	     */
	    public List<ChancePrizeBean> getChancePrizesByPrizeCode(int prizeCode) {
	        List<ChancePrizeBean> result = null;
	        SqlSession session = getSession();
	        try {
	            IChancePrize iChancePrize = session.getMapper(IChancePrize.class);
	            result = iChancePrize.getChancePrizesByPrizeCode(prizeCode);
	        } catch (Exception e) {
	            logger.error("getChancePrizesByprizeCode" + e.toString());
	        } finally {
	            session.close();
	        }
	        return result;
	    }
	    
	@Override
	public Integer insertPrizeChange(ChancePrizeBean chancePrizeBean) {
		Integer result = null;
		SqlSession session = getSession();
		try {
			IChancePrize iChancePrize = session
					.getMapper(IChancePrize.class);
			result = iChancePrize.insertPrizeChange(chancePrizeBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("insertPrizeChange"+e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	

}
