package cn.gyyx.action.bll.lottery.template.validate;

import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.userinfo.IUserInfoBLL;
import cn.gyyx.action.bll.userinfo.impl.UserInfoBLL;

public class UserScoreMeetsConditionValidateBLL extends DefaultLotteryValidateBLL {
	
	private IUserInfoBLL userInfoBLL = null;
	
	// 用户积分是否满足抽奖条件
	@Override
	public boolean validate(LotteryValidateVO param) {
		
		// 获得活动配置信息
		IHdConfigBLL configBLL = new DefaultHdConfigBLL();
		
		// 获得用户积分
		userInfoBLL = new UserInfoBLL();
		if (userInfoBLL.getScore(param.getActivityId(), param.getUserId()) < configBLL.getLotteryScore(param.getActivityId())) {
			message = "积分不足！";
			return false;
		}
		
		return true;
	}
}
