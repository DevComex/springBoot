/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月16日下午4:05:30
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖信息记录的dao
 */

package cn.gyyx.action.dao.lottery;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.UserRecordBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class UserRecordDao implements IUserRecord {
	// 获取session对象
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 增加一条用户抽奖记录
	 */
	@Override
	public void addUserRecordBean(UserRecordBean userbean) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserRecord iDneUserLottery = sqlSession
					.getMapper(IUserRecord.class);
			iDneUserLottery.addUserRecordBean(userbean);
			sqlSession.commit();

		}

	}

	/**
	 * 得到用户的抽奖记录
	 */
	@Override
	public UserRecordBean getUserRecordBeanByTime(int userCode,
			Date lotteryTime, int actionCode, String type) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserRecord iDneUserLottery = sqlSession
					.getMapper(IUserRecord.class);
			return iDneUserLottery.getUserRecordBeanByTime(userCode,
					lotteryTime, actionCode, type);
		}
	}
    /**
     * 根据ip查询用户记录
     */
	@Override
	public UserRecordBean getUserRecordBeanByIp(String ip, int actionCode,
			String type) {
		try (SqlSession sqlSession = getSession()) {
			IUserRecord iDneUserLottery = sqlSession
					.getMapper(IUserRecord.class);
			return iDneUserLottery.getUserRecordBeanByIp(ip, actionCode, type);

		}
	}

}
