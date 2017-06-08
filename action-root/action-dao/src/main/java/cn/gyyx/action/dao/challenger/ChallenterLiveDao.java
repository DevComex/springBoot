/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午3:12:49
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.challenger.ChallenterLiveBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallenterLiveDao {

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 增加直播信息
	 * 
	 * @param challenterLiveBean
	 */
	public void addChallenterLive(ChallenterLiveBean challenterLiveBean) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterLive iChallenterLive = sqlSession
					.getMapper(IChallenterLive.class);
			iChallenterLive.addChallenterLive(challenterLiveBean);
			sqlSession.commit(true);
		}
	}

	/***
	 * 得到一个人发布直播的次数 不带状态查询
	 * 
	 * @param userCode
	 * @param begin
	 * @param end
	 * @return
	 */
	public Integer getOneCountLiveIndayNoState(int userCode, String begin, String end) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterLive iChallenterLive = sqlSession
					.getMapper(IChallenterLive.class);
			return iChallenterLive.getOneCountLiveIndayNoState(userCode, begin, end);
		}
	}
	public Integer getOneCountLiveInday(int userCode, String begin, String end,SqlSession sqlSession) {
			IChallenterLive iChallenterLive = sqlSession
					.getMapper(IChallenterLive.class);
			return iChallenterLive.getOneCountLiveInday(userCode, begin, end);
	}

	/**
	 * 根据状态获取前多少条信息
	 * 
	 * @param size
	 * @param status
	 * @return
	 */
	public List<ChallenterLiveBean> getTopNumChallenterLiveBean(int size,
			String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterLive iChallenterLive = sqlSession
					.getMapper(IChallenterLive.class);
			return iChallenterLive.getTopNumChallenterLiveBean(size, status);
		}
	}

	/***
	 * 根据主键获取一条直播信息
	 * 
	 * @param code
	 * @return
	 */
	public ChallenterLiveBean getOneCodeChallenterLiveBean(int code,
			SqlSession sqlSession) {
		IChallenterLive iChallenterLive = sqlSession
				.getMapper(IChallenterLive.class);
		return iChallenterLive.getOneCodeChallenterLiveBean(code);
	}

	/**
	 * 更新状态与时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer setStateAndTime(int code, String status,
			SqlSession sqlSession) {
		IChallenterLive iChallenterLive = sqlSession
				.getMapper(IChallenterLive.class);
		return iChallenterLive.setStateAndTime(code, status);
	}

	/**
	 * 分页获取待审核直播信息
	 * 
	 * @param size
	 * @param status
	 * @return
	 */
	public List<ChallenterLiveBean> getPageOncheckChallenterLiveBean(int size,
			int pageNo, String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterLive iChallenterLive = sqlSession
					.getMapper(IChallenterLive.class);
			return iChallenterLive.getPageOncheckChallenterLiveBean(size,
					pageNo, status);
		}
	}

	/**
	 * 更新状态与时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer getAllCountOncheckChallenterLiveBean() {
		try (SqlSession sqlSession = getSession()) {
			IChallenterLive iChallenterLive = sqlSession
					.getMapper(IChallenterLive.class);
			return iChallenterLive.getAllCountOncheckChallenterLiveBean();
		}
	}
}
