/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午4:31:53
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.lottery;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class LuckyDrawLogDao {

	/***
	 * 增加一个抽奖来源日志
	 * 
	 * @param logBean
	 */
	public void addLuckyDrawLog(LuckyDrawLogBean logBean, SqlSession sqlSession) {
		ILuckyDrawLog iLuckyDrawLog = sqlSession.getMapper(ILuckyDrawLog.class);
		iLuckyDrawLog.addLuckyDrawLog(logBean);
	}

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 获取一段时间内某人某一类型增加抽奖次的记录
	 * 
	 */
	public LuckyDrawLogBean getOneSourceluckyDrawLogInTime(int userCode,
			String begin, String end, String source) {
		try (SqlSession sqlSession = getSession()) {
			ILuckyDrawLog iLuckyDrawLog = sqlSession
					.getMapper(ILuckyDrawLog.class);
			return iLuckyDrawLog.getOneSourceluckyDrawLogInTime(userCode,
					begin, end, source);

		}
	}

	public int getCountByAccountAndSourceInAction(String username,
			int actionCode, String source, SqlSession session) {
		ILuckyDrawLog iLuckyDrawLog = session
				.getMapper(ILuckyDrawLog.class);
		return iLuckyDrawLog.getCountByAccountAndSourceInAction(username,actionCode,source);
	}
	
	public int getCountTodayByAccount(String account, int activityCode,
			String source) {
		try (SqlSession sqlSession = getSession()) {
			ILuckyDrawLog iLuckyDrawLog = sqlSession.getMapper(ILuckyDrawLog.class);
			return iLuckyDrawLog.getCountTodayByAccount(account,activityCode, source);
		}
	}
	
	
}
