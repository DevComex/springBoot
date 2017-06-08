package cn.gyyx.action.dao.config.mapper;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionPrizePO;

public interface IActionPrizeMapper {
	
	List<ActionPrizePO> getDataList(int activityId);
}
