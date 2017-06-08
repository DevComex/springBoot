package cn.gyyx.action.bll.lottery.template.validate;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.dao.config.IHdConfigDAO;
import cn.gyyx.action.dao.config.Impl.HdConfigDAOImpl;

public class ActivityIsInProgressValidateBLL extends DefaultLotteryValidateBLL {

	private IHdConfigDAO hdConfigDAO = null;
	
	// 活动是否进行中
	@Override
	public boolean validate(LotteryValidateVO param) {
		hdConfigDAO = new HdConfigDAOImpl();
		
		// 获得活动配置信息
		ActionConfigPO actionPo = hdConfigDAO.getData(param.getActivityId());
		
		if (actionPo == null) {
			message = "无效活动！";
			return false;
		}
		
		Date now = Calendar.getInstance().getTime();
		
		if (actionPo.getHdStart() != null && actionPo.getHdStart().getTime() > now.getTime()) {
			message = "活动未开始！";
			return false;
		}
		
		if (actionPo.getHdEnd() != null && actionPo.getHdEnd().getTime() < now.getTime()) {
			message = "活动已结束！";
			return false;
		}
		
		return true;
	}
}
