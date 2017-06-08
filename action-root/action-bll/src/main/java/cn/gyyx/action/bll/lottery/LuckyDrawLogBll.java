/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午4:36:16
 * 版本号：v1.0
 * 本类主要用途叙述：抽奖分数来源日志BLL
 */

package cn.gyyx.action.bll.lottery;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.dao.lottery.LuckyDrawLogDao;

public class LuckyDrawLogBll {
	private LuckyDrawLogDao logDao = new LuckyDrawLogDao();

	/***
	 * 增加一个抽奖来源日志
	 * 
	 * @param logBean
	 */
	public void addLuckyDrawLog(LuckyDrawLogBean logBean, SqlSession sqlSession) {
		logDao.addLuckyDrawLog(logBean, sqlSession);
	}

	/***
	 * 获取一段时间内某人某一类型增加抽奖次的记录
	 * 
	 */
	public LuckyDrawLogBean getOneSourceluckyDrawLogInTime(int userCode,
			String begin, String end, String source) {
		return logDao.getOneSourceluckyDrawLogInTime(userCode, begin, end,
				source);
	}

	public int getCountByAccountAndSourceInAction(String username,
			int actionCode, String source, SqlSession session) {
		return logDao.getCountByAccountAndSourceInAction(username, actionCode, source,
				session);
	}

	public int getCountTodayByAccount(String account, int activityCode,
			String source) {
		return logDao.getCountTodayByAccount(account, activityCode, source);
	}

}
