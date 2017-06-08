package cn.gyyx.action.bll.lottery.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.dao.config.IHdConfigDAO;
import cn.gyyx.action.dao.config.Impl.HdConfigDAOImpl;

public class LotteryValidateBLLImplByTime implements ILotteryValidateBLL {
	private String message;
	private IHdConfigDAO hdConfigDAO = new HdConfigDAOImpl();
	
	// 活动是否在有效期内
	@Override
	public boolean validate(LotteryValidateVO param) {
		// 获得活动配置信息
		ActionConfigPO actionPo = hdConfigDAO.getData(param.getActivityId());
		if (actionPo == null) {
			message = "无效活动！";
			return false;
		}
		
		Date now = new Date();
		
		if (actionPo.getHdStart() != null && actionPo.getHdStart().getTime() > now.getTime()) {
			message = "活动未开始！";
			return false;
		}
		
		if (actionPo.getHdEnd() != null && this.getEndTime(param.getActivityId(), actionPo.getHdEnd().getTime()) < now.getTime()) {
			message = "活动已结束！";
			return false;
		}
		
		return true;
	}
	
	// 获得成功/失败信息
	@Override
	public String getMessage() {
		return message;
	}
	
	protected long getEndTime(int activityId, long endTime) {
		long result = 0;
		
		try {
			switch(activityId) {
				case 395:
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					result = sdf.parse("2016-10-10 23:59:59").getTime();
				default:
					result = endTime;
			}
		}
		catch(Exception e) {
			logger.error("LotteryValidateBLLImplByTime.getEndTime", e);
		}
		
		return result;
	}
}
