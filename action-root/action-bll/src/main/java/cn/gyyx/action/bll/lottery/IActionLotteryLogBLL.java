package cn.gyyx.action.bll.lottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface IActionLotteryLogBLL {
	
	Logger logger = GYYXLoggerFactory.getLogger(IActionLotteryLogBLL.class);
	
	List<ActionLotteryLogPO> getDataList(ActionLotteryLogPO po);
	
	int putData(ActionLotteryLogPO po, SqlSession session) throws Exception; 
	
	int isExsits(ActionLotteryLogPO po);
	
	List<ActionLotteryLogPO> getDataExceptThankYou(ActionLotteryLogPO params);
	
	int getCountTodayByAccount(String account, int activityCode);
}
