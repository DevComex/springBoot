/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午3:36:48
 * 版本号：v1.0
 * 本类主要用途叙述：用户中交信息的Dao
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.UserInfoBean;

public class NewUserInfoDao {
	/****
	 * 增加一条中奖记录信息
	 * 
	 * @param userInfoBean
	 * @param sqlSession
	 * @return
	 */
	public int addInfo(UserInfoBean userInfoBean, SqlSession sqlSession) {
		// TODO Auto-generated method stub

		IUserLotteryInfoNew ioInfo = sqlSession
				.getMapper(IUserLotteryInfoNew.class);
		int i = ioInfo.addInfo(userInfoBean);
		return i;

	}

	public List<String> getPresentNamesByUserIP(int actionCode, String ip, SqlSession session) {
		IUserLotteryInfoNew mapper = session.getMapper(IUserLotteryInfoNew.class);
		return mapper.getPresentNamesByUserIP(actionCode, ip);
	}

	/**
	 * 某个人在某个活动获得某个奖
	 */
	public List<UserInfoBean> getOneInActionOnePrizeInfoSqlsession(int actionCode,
			String userAccount, String prizeChinese, SqlSession sqlSession) {
		IUserLotteryInfoNew ioInfo = sqlSession
				.getMapper(IUserLotteryInfoNew.class);
		return ioInfo.getOneInActionOnePrizeInfo(actionCode, userAccount,
				prizeChinese);
	}

	public List<UserInfoBean> getAvailableByUserSqlsession(int actionCode, String userAccount, int available,
			SqlSession sqlSession) {
		IUserLotteryInfoNew ioInfo = sqlSession
				.getMapper(IUserLotteryInfoNew.class);
		return ioInfo.getAvailableByUserSqlsession(actionCode, userAccount,available);
	}
	
	/**
         * 更新发送物品日志 yangteng
         * @param userInfoBean
         * @param sqlSession
         * @return
         */
	public Integer updateSendPresentLog(UserInfoBean userInfoBean,SqlSession sqlSession){
		IUserLotteryInfoNew ioInfo = sqlSession
				.getMapper(IUserLotteryInfoNew.class);
		return ioInfo.updateSendPresentLog(userInfoBean);
	}
	
	/*
	 * @Override public List<UserInfoBean> getAllUserLotteryInfo(int actionCode)
	 * { // TODO Auto-generated method stub try (SqlSession sqlSession =
	 * getSession()) { IUserLotteryInfoNew ioInfo = sqlSession
	 * .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.getAllUserLotteryInfo(actionCode); } }
	 */

	/*	*//**
	 * 获取用户中奖信息的重载
	 */
	/*
	 * 
	 * @Override public List<UserInfoBean> getUserLotteryInfoByUserCode(int
	 * actionCode, String userAccount) { // TODO Auto-generated method stub try
	 * (SqlSession sqlSession = getSession()) { IUserLotteryInfoNew ioInfo =
	 * sqlSession .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.getUserLotteryInfoByUserCode(actionCode, userAccount); }
	 * 
	 * }
	 * 
	 * @Override public List<UserInfoBean> wishGetUserLotteryInfoByUserCode(int
	 * actionCode, String userAccount) { // TODO Auto-generated method stub try
	 * (SqlSession sqlSession = getSession()) { IUserLotteryInfoNew ioInfo =
	 * sqlSession .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.wishGetUserLotteryInfoByUserCode(actionCode, userAccount); } }
	 * 
	 * public int getNumOf(int actionCode, String prizeChinese) { try
	 * (SqlSession sqlSession = getSession()) { IUserLotteryInfoNew ioInfo =
	 * sqlSession .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.getNumOf(actionCode, prizeChinese); } } public Integer
	 * selectLogByDay(String date){ try (SqlSession sqlSession = getSession()) {
	 * IUserLotteryInfoNew ioInfo = sqlSession
	 * .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.selectLogByDay(date); } }
	 * 
	 * @Override public List<UserInfoBean> getLogByInfo(NewPageBean newPageBean)
	 * { try (SqlSession sqlSession = getSession()) { IUserLotteryInfoNew ioInfo
	 * = sqlSession .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.getLogByInfo(newPageBean); } }
	 * 
	 * @Override public Integer getCountByInfo(NewPageBean newPageBean) { try
	 * (SqlSession sqlSession = getSession()) { IUserLotteryInfoNew ioInfo =
	 * sqlSession .getMapper(IUserLotteryInfoNew.class); return
	 * ioInfo.getCountByInfo(newPageBean); } }
	 */

}
