package cn.gyyx.action.bll.lottery;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface IActionPrizesAddressBLL {
	
	Logger logger = GYYXLoggerFactory.getLogger(IActionPrizesAddressBLL.class);
	
	// 根据活动ID、用户code、用户ID获得地址信息
	LotteryPrizesVO getAddress(int activityId, int userCode, String userId);
	
	// 如果没有地址，则创建地址；否则更新。
	boolean putAddress(LotteryPrizesVO vo, SqlSession session) throws Exception;
	
	// 根据活动ID，用户ID，类型查询地址信息
	ActionPrizesAddressPO get(int activityId, String userId, String activityType);
	
	// 如果没有地址，则创建地址；否则更新。
	boolean post(ActionPrizesAddressPO params, SqlSession session) throws Exception;
}
