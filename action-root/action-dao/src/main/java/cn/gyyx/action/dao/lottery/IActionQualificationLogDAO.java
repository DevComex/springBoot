package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionQualificationLogPO;

public interface IActionQualificationLogDAO {

	List<ActionQualificationLogPO> selectDataToday(ActionQualificationLogPO params);
	
	int insert(ActionQualificationLogPO params, SqlSession session);
}
