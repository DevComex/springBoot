package cn.gyyx.action.dao.lottery.mapper;

import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;

public interface IActionQualificationMapper {

	// 根据用户ID、活动ID查询抽奖资格
	ActionQualificationPO selectByUseridAndActivityid(ActionQualificationPO po);
	
	//第一次获得兑奖机会插入相关信息 ,问道国庆微信活动使用
	int insertLottery (ActionQualificationPO po);
	
	//增加普通奖劵机会
	int updateLottery(ActionQualificationPO po);
	
	//增加必中奖券机会
	int updateMustWin(ActionQualificationPO po);
	
	// 抽奖次数减一
	int putLotteryCountMinusOne(ActionQualificationPO po);
	
	// 必中次数减一
	int putMustWinCountMinusOne(ActionQualificationPO po);

	// 减少抽奖积分
	int minusLotteryScore(ActionQualificationPO po);
	
	// 增加抽奖积分
	int addLotteryScore(ActionQualificationPO po);

	//初始化抽奖机会 通用
	int InitializeLottery(ActionQualificationPO po);
	
	//普通抽奖次数减一   by  某天(以创建时间为准)
	int putLotteryCountMinusOneByDate(ActionQualificationPO po);
	
	//根据用户ID、活动ID、抽奖机会初始化时间 查询抽奖资格
	ActionQualificationPO selectByUseridAndActivityidAndCreateDate(ActionQualificationPO po);
}