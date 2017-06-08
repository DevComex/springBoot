package cn.gyyx.action.dao.lottery.mapper;

import java.util.List;

import cn.gyyx.action.beans.lottery.po.ActionQualificationLogPO;

public interface IActionQualificationLogMapper {

	List<ActionQualificationLogPO> selectDataToday(ActionQualificationLogPO params);
	
	int insert(ActionQualificationLogPO params);
}
