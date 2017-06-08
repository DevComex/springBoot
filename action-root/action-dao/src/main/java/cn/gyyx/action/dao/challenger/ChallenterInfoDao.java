/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午1:36:26
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.challenger.ChallenterInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallenterInfoDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 增加控制台日志
	 * 
	 * @param operateBean
	 */
	public void addChallenterInfo(ChallenterInfoBean challenterInfoBean) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			iChallenterInfo.addChallenterInfo(challenterInfoBean);
			sqlSession.commit(true);
		}
	}

	/***
	 * 获取某个人合法的挑战信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ChallenterInfoBean getOneChallenterInfoWithoutUnPass(int userCode) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			return iChallenterInfo.getOneChallenterInfoWithoutUnPass(userCode);
		}

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
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			return iChallenterInfo.getStateChallenterInfo(pageNo, size, state);
		}
	}

	/***
	 * 获取某状态下挑战的总数
	 * 
	 * @param state
	 * @return
	 */
	public Integer getCountStateChallenterInfo(String state) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			return iChallenterInfo.getCountStateChallenterInfo(state);
		}
	}

	/**
	 * 增加被挑战次数
	 * 
	 * @param userCode
	 * @param sqlSession
	 */
	public void addChallengeTimes(int userCode, SqlSession sqlSession) {
		IChallenterInfo iChallenterInfo = sqlSession
				.getMapper(IChallenterInfo.class);
		iChallenterInfo.addChallengeTimes(userCode);
	}

	/**
	 * 获取某状态某人的挑战信息
	 */
	public ChallenterInfoBean getOneStatusChallenterInfo(int userCode,
			String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			return iChallenterInfo.getOneStatusChallenterInfo(userCode, status);
		}
	}

	/***
	 * 找到前五个被人揍得最多的玩家
	 * 
	 * @return
	 */
	public List<ChallenterInfoBean> getTopFiveChallenterInfo() {
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			return iChallenterInfo.getTopFiveChallenterInfo();
		}
	}

	/**
	 * 根据主键获取一条挑战信息
	 * 
	 * @param code
	 * @return
	 */
	public ChallenterInfoBean getOneChallenterInfo(int code) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterInfo iChallenterInfo = sqlSession
					.getMapper(IChallenterInfo.class);
			return iChallenterInfo.getOneChallenterInfo(code);
		}
	}

	/***
	 * 设定一个挑战的状态和时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer setOneStatusAndTime(int code, String status,
			SqlSession sqlSession) {
		IChallenterInfo iChallenterInfo = sqlSession
				.getMapper(IChallenterInfo.class);
		return iChallenterInfo.setOneStatusAndTime(code, status);
	}
	
	
	
	
	

}
