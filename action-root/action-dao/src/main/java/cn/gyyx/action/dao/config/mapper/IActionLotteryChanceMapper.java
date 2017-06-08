package cn.gyyx.action.dao.config.mapper;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;

public interface IActionLotteryChanceMapper {
	
	List<ActionLotteryChancePO> getDataList(int activityId);
	
	List<ActionLotteryChancePO> getDataListByAvailable(int activityId);
	
	ActionLotteryChancePO getData(ActionLotteryChancePO po);
	
	int putNumber(ActionLotteryChancePO po);
}
