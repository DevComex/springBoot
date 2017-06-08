package cn.gyyx.action.bll.config.impl;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.bll.config.IActionPrizeBLL;
import cn.gyyx.action.dao.config.IActionPrizeDAO;
import cn.gyyx.action.dao.config.Impl.ActionPrizeDAOImpl;

public class ActionPrizeBLLImpl implements IActionPrizeBLL {

	private IActionPrizeDAO actionPrizeDAO = new ActionPrizeDAOImpl();
	
	@Override
	public List<ActionPrizePO> getDataList(int activityId) {
		return actionPrizeDAO.getDataList(activityId);
	}
}
