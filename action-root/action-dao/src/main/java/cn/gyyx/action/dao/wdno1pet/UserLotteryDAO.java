/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日下午1:59:41
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖资格的DAO层实现
 */

package cn.gyyx.action.dao.wdno1pet;

import java.io.IOException;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdno1pet.UserLotteryBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class UserLotteryDAO implements IUserLottery {

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
	 * 查询具有抽奖资格的总人数
	 */
	@Override
	public int selectNumLucky() {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLottery iUserLottery = sqlSession
					.getMapper(IUserLottery.class);

			int num = iUserLottery.selectNumLucky();
			return num;

		}
	}

	/**
	 * 向用户的中奖信息表中插入记录
	 */
	@Override
	public void insertUserLottery(UserLotteryBean userLotteryBean ) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLottery iUserLottery = sqlSession
					.getMapper(IUserLottery.class);
			 iUserLottery.insertUserLottery(userLotteryBean);
			 sqlSession.commit();

		}
		
	}
/**
 * 插入用户的地址等信息
 */
	@Override
	public void updateUserAdress(String info,int userCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLottery iUserLottery = sqlSession
					.getMapper(IUserLottery.class);
			 iUserLottery.updateUserAdress(info,userCode);
			 sqlSession.commit();

		}
		
	}

	/**
	 * 通过用户id查找用户中奖信息
	 */
@Override
public UserLotteryBean selectUserLotteryByUserCode(int userCode) {
	// TODO Auto-generated method stub
	try (SqlSession sqlSession = getSession()) {
		IUserLottery iUserLottery = sqlSession
				.getMapper(IUserLottery.class);

		UserLotteryBean userLotteryBean = iUserLottery.selectUserLotteryByUserCode(userCode);
		return userLotteryBean;
		

	}
}

/**
 * 更新用户的中奖信息
 */
	@Override
	public void updateUserLotteryStatusAndType(int userCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLottery iUserLottery = sqlSession
					.getMapper(IUserLottery.class);

			 iUserLottery.updateUserLotteryStatusAndType(userCode);
			 sqlSession.commit();
		}
		
	}

@Override
public void updateUserLotteryStatus(String lotteryStatus,int userCode) {
	// TODO Auto-generated method stub
	try (SqlSession sqlSession = getSession()) {
		IUserLottery iUserLottery = sqlSession
				.getMapper(IUserLottery.class);

		 iUserLottery.updateUserLotteryStatus(lotteryStatus, userCode);
		 sqlSession.commit();
	}
	
}
/**
 * 更新用户的中奖信息
 */
@Override
public void updateLotteryType(HashMap<String, Integer> map) {
	// TODO Auto-generated method stub
	

	
	try (SqlSession sqlSession = getSession()) {
		IUserLottery iUserLottery = sqlSession
				.getMapper(IUserLottery.class);

		 iUserLottery.updateLotteryType(map);
		 sqlSession.commit();
	}
}
/**
 * 向中奖信息表中插入记录
 */
@Override
public void insertUser(UserLotteryBean uLotteryBean) {
	// TODO Auto-generated method stub
	try (SqlSession sqlSession = getSession()) {
		IUserLottery iUserLottery = sqlSession
				.getMapper(IUserLottery.class);

		 iUserLottery.insertUser( uLotteryBean);
		 sqlSession.commit();
	}
	
}

}
