package cn.gyyx.action.bll.config;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionPrizePO;

public interface IActionPrizeBLL {
	
	List<ActionPrizePO> getDataList(int activityId);
}
