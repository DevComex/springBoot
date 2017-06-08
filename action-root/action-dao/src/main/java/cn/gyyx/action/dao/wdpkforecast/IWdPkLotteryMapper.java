package cn.gyyx.action.dao.wdpkforecast;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.wdpkforecast.WdPkAllLotteryInfo;
import cn.gyyx.action.beans.wdpkforecast.WdVoteSurplusBean;

public interface IWdPkLotteryMapper {
	// 更新用户抽奖机会

	public void updatelotteryTime(@Param("lottery_time") int lottery_time, @Param("user_code") int user_code,
			@Param("action_code") int action_code);

	// 查询用户大奖池抽奖机会
	public List<QualificationBean> selectBiglotteryByTypeAndWin(@Param("actionCode") int actionCode,
			@Param("type") int type, @Param("win") int win);

	// 查询用户小奖池抽奖机会
	public List<QualificationBean> selectSamllLotteryStatusByTypeAndWin(@Param("actionCode") int actionCode,
			@Param("win") int win, @Param("type") int type);

	// 按抽奖规则查询用户是否有抽有效奖机会

	public List<ActionLotteryLogPO> selectIsAvailable(ActionLotteryLogPO po);

	// 插入中奖日志

	public void insertLotteryLog(ActionLotteryLogPO po);

	// 查询用户所有中奖信息
	public List<ActionLotteryLogPO> selectUserLotteryInfo(@Param("userId") String userId);

	// 查询所有用户中奖信息

	public List<WdPkAllLotteryInfo> selectAllUserLotteryInfo(int type);

	// 查询用户大奖池余票
	public List<QualificationBean> selectBig(@Param("actionCode") int actionCode, @Param("type") int type,
			@Param("win") int win);

	// 查询用户小奖池余票
	public List<QualificationBean> selectSamll(@Param("actionCode") int actionCode, @Param("win") int win,
			@Param("type") int type);

	// 插入余票
	public void insertVoteSurplus(WdVoteSurplusBean VSB);

	public List<WdVoteSurplusBean> selectSurplus(WdVoteSurplusBean VSB);


	// 查询当前用户中奖信息 BY actioncode

	public List<ActionLotteryLogPO> selectUserLotteryInfoBYactioncode(ActionLotteryLogPO po);
	
	// 插入中奖日志

    public void insertAllLotteryLog(ActionLotteryLogPO po);

	public List<Map<String, String>> getLotteryCountGroupByDate(
			@Param("actionCode")int actionCode);
}
