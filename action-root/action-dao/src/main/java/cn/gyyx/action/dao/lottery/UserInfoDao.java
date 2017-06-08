/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午3:36:48
 * 版本号：v1.0
 * 本类主要用途叙述：用户中交信息的Dao
 */

package cn.gyyx.action.dao.lottery;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class UserInfoDao implements IUserLotteryInfo {
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
	public List<UserInfoBean> getAllUserLotteryInfo(int actionCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getAllUserLotteryInfo(actionCode);
		}
	}

	@Override
	public int addInfo(UserInfoBean userInfoBean) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			int i = ioInfo.addInfo(userInfoBean);
			sqlSession.commit();
			return i;

		}
	}

	/**
	 * 获取用户中奖信息的重载
	 */

	@Override
	public List<UserInfoBean> getUserLotteryInfoByUserCode(int actionCode,
			String userAccount) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getUserLotteryInfoByUserCode(actionCode, userAccount);
		}

	}

	@Override
	public List<UserInfoBean> wishGetUserLotteryInfoByUserCode(int actionCode,
			String userAccount) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.wishGetUserLotteryInfoByUserCode(actionCode,
					userAccount);
		}
	}
	
	@Override
	public UserInfoBean getUserLotteryInfoByActionCodeAndUserCodeAndPresentName(
			int actionCode, String userAccount, String presentName) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getUserLotteryInfoByActionCodeAndUserCodeAndPresentName(
					actionCode, userAccount, presentName);
		}
	}

	
	public UserInfoBean getUserLotteryInfoByActionCodeAndUserCodeAndDrawTime(
			int actionCode,String userAccount,String drawTime){
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getUserLotteryInfoByActionCodeAndUserCodeAndDrawTime(
					actionCode, userAccount, drawTime);
		}
	}

	@Override
	public List<UserInfoBean> getSendPresentLogs(int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getSendPresentLogs(actionCode);
		}
	}

	public int getNumOf(int actionCode, String prizeChinese) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getNumOf(actionCode, prizeChinese);
		}
	}
	public  Integer selectLogByDay(String date){
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.selectLogByDay(date);
		}
	}

	@Override
	public List<UserInfoBean> getLogByInfo(NewPageBean newPageBean) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getLogByInfo(newPageBean);
		}
	}

	@Override
	public Integer getCountByInfo(NewPageBean newPageBean) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getCountByInfo(newPageBean);
		}
	}

	@Override
	public List<UserInfoBean> wishGetUserLotteryInfoByAvailable(int actionCode,
			String userAccount) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.wishGetUserLotteryInfoByAvailable(actionCode, userAccount);
		}
	}

	@Override
	public void updateAvailable(int code) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			 ioInfo.updateAvailable(code);
			 sqlSession.commit();
		}
		
	}


	@Override
	public List<UserInfoBean> getLotteryList4Wd2017SeekTeam(int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getLotteryList4Wd2017SeekTeam(actionCode);
		}
	}

	public  List<UserInfoBean> selectOtherPrizes(int actionCode, String userAccount, String presentType) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.selectOtherPrizes(actionCode,userAccount,presentType);
			 
		}
	}

	public List<UserInfoBean> selectOneTypePrizes(int actionCode, String userAccount, String presentType) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.selectOneTypePrizes(actionCode,userAccount,presentType);
			 
		}
	}

	public List<UserInfoBean>selectTop50AvaPrizes(int actionCode, int available) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.selectTop50AvaPrizes(actionCode, available);
			 
		}
	}

	@Override
	public List<UserInfoBean> getLotteryList4Wd161(int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			IUserLotteryInfo ioInfo = sqlSession
					.getMapper(IUserLotteryInfo.class);
			return ioInfo.getLotteryList4Wd161(actionCode);
		}
	}


}
