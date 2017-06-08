package cn.gyyx.action.dao.lottery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;

public interface IActionLotteryLogMapper {

	List<ActionLotteryLogPO> getDataList(ActionLotteryLogPO po);
	
	int putData(ActionLotteryLogPO po); 
	
	Integer selectCount(ActionLotteryLogPO po);
	
	List<ActionLotteryLogPO> selectExceptThankYou(ActionLotteryLogPO po);
	
	int getCountTodayByAccount(@Param("account")String account,@Param("activityCode") int activityCode);
}
