/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午6:07:04
 * 版本号：v1.0
 * 本类主要用途叙述：报名信息的bll
 */

package cn.gyyx.action.bll.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.dao.challenger.ChallenterUserInfoDao;

public class ChallenterUserInfoBll {
	private ChallenterUserInfoDao challenterUserInfoDao = new ChallenterUserInfoDao();

	/***
	 * 更改用户报名信息状态
	 * 
	 * @param userCode
	 * @param status
	 * @param sqlSession
	 */
	public Integer setUserInfoState(int userCode, String status,
			SqlSession sqlSession) {
		return challenterUserInfoDao.setUserInfoState(userCode, status,
				sqlSession);
	}

	/***
	 * 根据状态主键获取某人的报名信息
	 * 
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ChallenterUserInfoBean getCodeChallenterUserInfo(int code,
			String status) {
		return challenterUserInfoDao.getCodeChallenterUserInfo(code, status);
	}

	/***
	 * 根据状态获取报名信息
	 * 
	 * @param status
	 * @return
	 */
	public List<ChallenterUserInfoBean> getStatusChallenterUserInfo(int pageNo,
			int pageSize, String status) {
		return challenterUserInfoDao.getStatusChallenterUserInfo(pageNo,
				pageSize, status);
	}

	/***
	 * 根据状态查询数量
	 */
	public Integer getCountStatusChallenterUserInfo(String status) {
		return challenterUserInfoDao.getCountStatusChallenterUserInfo(status);
	}

	/***
	 * 根据状态获取某人的报名信息
	 * 
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ChallenterUserInfoBean getOneChallenterUserInfo(int userCode,
			String status) {
		return challenterUserInfoDao.getOneChallenterUserInfo(userCode, status);
	}

	/**
	 * 根据条件获取信息 一条
	 */
	public ChallenterUserInfoBean getUserInfoBean(
			ChallenterUserInfoBean userInfoBean) {
		return challenterUserInfoDao.getUserInfoBean(userInfoBean);
	}

	/**
	 * 根据条件获取数量
	 */
	public int getUserInfoCount(ChallenterUserInfoBean userInfoBean) {
		return challenterUserInfoDao.getUserInfoCount(userInfoBean);
	}

	/**
	 * 插入
	 */
	public int insert(ChallenterUserInfoBean userInfoBean) {
		return challenterUserInfoDao.insert(userInfoBean);
	}

	/**
	 * 得到用户最后一条中奖信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ChallenterUserInfoBean getLastOneChallenterUserInfo(int userCode) {
		return challenterUserInfoDao.getLastOneChallenterUserInfo(userCode);
	}

	/**
	 * 根据ids查询list 以后我的代码是每个业务一个sql语句,不做通用的了，目的：业务明确,通过的不明确
	 * 
	 * @param userIds
	 * @return
	 */
	public List<ChallenterUserInfoBean> getUserInfoListByIds(
			List<Integer> userIds) {
		return challenterUserInfoDao.getUserInfoListByIds(userIds);
	}

	public List<ChallenterUserInfoBean> getTeamMemberUserInfoListByTeamIds(
			List<Integer> teamIds) {
		return challenterUserInfoDao
				.getTeamMemberUserInfoListByTeamIds(teamIds);
	}
}
