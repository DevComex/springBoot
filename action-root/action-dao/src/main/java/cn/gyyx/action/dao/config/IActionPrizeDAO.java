package cn.gyyx.action.dao.config;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionPrizePO;

public interface IActionPrizeDAO {

	List<ActionPrizePO> getDataList(int activityId);
}
