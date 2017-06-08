/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日上午11:28:58
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖信息配置的DAO层实现
 */



package cn.gyyx.action.dao.wdno1pet;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdno1pet.LotteryConfigBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class LotterDAO implements ILotter {

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
	 * 向中奖信息配置中增加记录
	 */

	@Override
	public void insertLotter(LotteryConfigBean lConfig) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			ILotter iLotter = sqlSession.getMapper(ILotter.class);

			iLotter.insertLotter(lConfig);
			sqlSession.commit();
		}

	}

	/**
	 * 得到中奖配置表中的条数
	 */
	@Override
	public int selectConutLottery() {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			ILotter iLotter = sqlSession.getMapper(ILotter.class);

			int num = iLotter.selectConutLottery();
			return num;
		}

	}

	/**
	 * 清空中奖配置表
	 */
	@Override
	public void deleteLotteryConfig() {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			ILotter iLotter = sqlSession.getMapper(ILotter.class);

			iLotter.deleteLotteryConfig();
			sqlSession.commit();
		}

	}

	@Override
	/**
	 * 根据名次查询中奖信息
	 */
	public LotteryConfigBean selectLotteryConfigByOrder(int lcOrder) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			ILotter iLotter = sqlSession.getMapper(ILotter.class);

			LotteryConfigBean lBean = iLotter.selectLotteryConfigByOrder(lcOrder);
			return lBean;
			
		}
	}

}
