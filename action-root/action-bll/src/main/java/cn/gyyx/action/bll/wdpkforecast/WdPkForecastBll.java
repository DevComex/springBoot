package cn.gyyx.action.bll.wdpkforecast;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteLogBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteTeamsBean;
import cn.gyyx.action.beans.wdpkforecast.WdVoteOpportunityBean;
import cn.gyyx.action.beans.wdpkforecast.WdVoteSurplusBean;
import cn.gyyx.action.dao.wdpkforecast.WdPkForeCastDao;
import cn.gyyx.action.dao.wdpkforecast.WdPkLotteryDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdPkForecastBll {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdPkForecastBll.class);
	private WdPkLotteryDao wdPkLotteryDao =new WdPkLotteryDao();
	private WdPkForeCastDao wdPkForeCastDao = new WdPkForeCastDao();

	// 获取获取预测队伍信息
	public ResultBean<List<WdPkVoteTeamsBean>> getALLVoteTeams(int type) {
		ResultBean<List<WdPkVoteTeamsBean>> result = new ResultBean<List<WdPkVoteTeamsBean>>(false, "未知错误", null);

		List<WdPkVoteTeamsBean> wdPkVoteTeamsBean = wdPkForeCastDao.selectVoteTeamByType(type);
		if (wdPkVoteTeamsBean == null || wdPkVoteTeamsBean.size() == 0) {
			result.setIsSuccess(false);
			result.setMessage("PK预测队伍信息暂未出炉,请稍后再试");

		}
		result.setIsSuccess(true);
		result.setMessage("已获取预测队伍信息");
		result.setData(wdPkVoteTeamsBean);
		return result;
	}

	// 查询剩余可用票数
	public Integer getVoteTimes(int userId, int actionCode, int type) {

		return wdPkForeCastDao.selectVoteTimesByUserIdAndType(userId, actionCode, type);

	}

	// 查询WdVoteOpportunityBean 是否存在
	public WdVoteOpportunityBean getVoteOpportunityBean(int userId, int actionCode, int type) {

		return wdPkForeCastDao.selectVoteOpportunityBeanByUserIdAndType(userId, actionCode, type);

	}

	// 给用户投票机会
	public void insertVoteOpportunity(WdVoteOpportunityBean wdVoteOpportunityBean) {

		wdPkForeCastDao.insertVoteOpportunity(wdVoteOpportunityBean);

	}

	// 减少用户可投票数
	public void reduceVoteTimes(WdVoteOpportunityBean wdVoteOpportunityBean, SqlSession sqlSession) {

		wdPkForeCastDao.reduceVoteTimes(wdVoteOpportunityBean, sqlSession);
	}

	// 用户投票
	public void insertVoteLog(WdPkVoteLogBean wdPkVoteLogBean, SqlSession sqlSession) {

		wdPkForeCastDao.insertVoteLog(wdPkVoteLogBean, sqlSession);

	}
	// 查询用户投票状态

	public List<WdPkVoteLogBean> selectVoteStatus(String account) {

		return wdPkForeCastDao.selectVoteStatus(account);

	}
	// 查询用户已投几个区组

	public Integer selectVoteExistedTimes(int userId, int type) {

		return wdPkForeCastDao.selectVoteExistedTimes(userId, type);
	}

	public ResultBean<List<WdPkVoteLogBean>> selectVoteExisted(WdPkVoteLogBean wdPkVoteLogBean) {
		ResultBean<List<WdPkVoteLogBean>> result = new ResultBean<List<WdPkVoteLogBean>>(false, "未知错误", null);

		if (wdPkForeCastDao.selectVoteExisted(wdPkVoteLogBean) == null
				|| wdPkForeCastDao.selectVoteExisted(wdPkVoteLogBean).size() == 0) {

			result.setIsSuccess(false);
			result.setMessage("用户还未投过当前区组");
			return result;
		} else {
			result.setIsSuccess(true);
			result.setMessage("用户已投过当前区组");
			result.setData(wdPkForeCastDao.selectVoteExisted(wdPkVoteLogBean));
			return result;

		}
	}

	/********************************** OA后台使用 *******************************************/
	public List<QualificationBean> getBigLottery(int actionCode, int type, int win) {

		return wdPkLotteryDao.selectBiglotteryByTypeAndWin(actionCode, type, win);

	}

	public List<QualificationBean> getSmallLottery(int actionCode, int type, int win) {

		return wdPkLotteryDao.selectSamllLotteryStatusByTypeAndWin(actionCode, type, win);

	}
	
	public void insertLottery(int lottery_time,int user_code,int action_code){
		
		wdPkForeCastDao.insertLottery(lottery_time, user_code, action_code);
	}
	
	public  QualificationBean selectLottery(int user_code,int action_code){
		
		return	wdPkForeCastDao.selectLottery(user_code, action_code);
		
	}
	
	public List<QualificationBean> getBig(int actionCode, int type, int win) {

		return wdPkLotteryDao.selectBig(actionCode, type, win);

	}

	public List<QualificationBean> getSmall(int actionCode, int type, int win) {

		return wdPkLotteryDao.selectSamll(actionCode, type, win);

	}
	
	public void insertVoteSurplus (WdVoteSurplusBean VSB){
		
		wdPkLotteryDao.insertVoteSurplus(VSB);
		
	}
	
	//阶段结束后更新抽奖机会
	
	public void updatelotteryTime(int lottery_time,int user_code,int action_code){
		wdPkLotteryDao.updatelotteryTime(lottery_time, user_code, action_code);	
	}
	
	public List<WdVoteSurplusBean>selectSurplus(WdVoteSurplusBean VSB ){
		
		return wdPkLotteryDao.selectSurplus(VSB);
		
	}
	
}
