package cn.gyyx.action.dao.userinfo;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.userinfo.po.ActionGameInterimPO;

public interface IActionGameInterimDAO {

	ActionGameInterimPO selectOne(ActionGameInterimPO params);
	
	int insert(ActionGameInterimPO params, SqlSession session);
}
