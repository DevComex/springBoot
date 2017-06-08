/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午1:45:35
 * 版本号：v1.0
 * 本类主要用途叙述：挑战的Bll
 */

package cn.gyyx.action.bll.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.ChallenterInfoBean;
import cn.gyyx.action.dao.challenger.ChallenterInfoDao;

public class ChallenterInfoBll {
	private ChallenterInfoDao challenterInfoDao = new ChallenterInfoDao();
	
	//private simple

	/***
	 * 增加控制台日志
	 * 
	 * @param operateBean
	 */
	public void addChallenterInfo(ChallenterInfoBean challenterInfoBean) {
		challenterInfoDao.addChallenterInfo(challenterInfoBean);
	}

	/***
	 * 获取某个人合法的挑战信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ChallenterInfoBean getOneChallenterInfoWithoutUnPass(int userCode) {
		return challenterInfoDao.getOneChallenterInfoWithoutUnPass(userCode);
	}

	/***
	 * 页码状态获取挑战信息
	 * 
	 * @param pageNo
	 * @param size
	 * @param state
	 * @return
	 */
	public List<ChallenterInfoBean> getStateChallenterInfo(int pageNo,
			int size, String state) {
		return challenterInfoDao.getStateChallenterInfo(pageNo, size, state);
	}

	/***
	 * 获取某状态下挑战的总数
	 * 
	 * @param state
	 * @return
	 */
	public Integer getCountStateChallenterInfo(String state) {
		return challenterInfoDao.getCountStateChallenterInfo(state);
	}

	/**
	 * 增加被挑战次数
	 * 
	 * @param userCode
	 * @param sqlSession
	 */
	public void addChallengeTimes(int userCode, SqlSession sqlSession) {
		challenterInfoDao.addChallengeTimes(userCode, sqlSession);
	}

	/**
	 * 获取某状态某人的挑战信息
	 */
	public ChallenterInfoBean getOneStatusChallenterInfo(int userCode,
			String status) {
		return challenterInfoDao.getOneStatusChallenterInfo(userCode, status);
	}

	/***
	 * 找到前五个被人揍得最多的玩家
	 * 
	 * @return
	 */
	public List<ChallenterInfoBean> getTopFiveChallenterInfo() {
		return challenterInfoDao.getTopFiveChallenterInfo();
	}

	/**
	 * 根据主键获取一条挑战信息
	 * 
	 * @param code
	 * @return
	 */
	public ChallenterInfoBean getOneChallenterInfo(int code) {
		return challenterInfoDao.getOneChallenterInfo(code);
	}

	/***
	 * 设定一个挑战的状态和时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer setOneStatusAndTime(int code, String status,
			SqlSession sqlSession) {
		return challenterInfoDao.setOneStatusAndTime(code, status, sqlSession);
	}
}
