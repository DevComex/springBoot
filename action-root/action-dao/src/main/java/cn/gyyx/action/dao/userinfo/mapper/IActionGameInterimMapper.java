package cn.gyyx.action.dao.userinfo.mapper;

import cn.gyyx.action.beans.userinfo.po.ActionGameInterimPO;

public interface IActionGameInterimMapper {

	ActionGameInterimPO selectOne(ActionGameInterimPO params);
	
	int insert(ActionGameInterimPO params);
}
