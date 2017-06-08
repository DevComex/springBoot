package cn.gyyx.action.bll.lottery;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;

public interface ILotteryProbabilityBLL {

	List<ActionLotteryChancePO> getDataList(int activityId);
}
