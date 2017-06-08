package cn.gyyx.action.dao.lottery.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.lottery.po.ActionQualificationLogPO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.lottery.IActionQualificationLogDAO;
import cn.gyyx.action.dao.lottery.mapper.IActionQualificationLogMapper;

public class ActionQualificationLogDAOImpl extends MyBatisBaseDAO implements IActionQualificationLogDAO {

	@Override
	public List<ActionQualificationLogPO> selectDataToday(ActionQualificationLogPO params) {
		try(SqlSession session = this.getSession()) {
			logger.info("ActionQualificationLogDAOImpl->select->params=" + JSON.toJSONString(params));
			
			return session.getMapper(IActionQualificationLogMapper.class).selectDataToday(params);
		}
		catch(Exception e) {
			logger.error("ActionQualificationLogDAOImpl->select->Cause:" + e.getCause());
			logger.error("ActionQualificationLogDAOImpl->select->Message:" + e.getMessage());
			logger.error("ActionQualificationLogDAOImpl->select->StackTrace:" + e.getStackTrace());
		}
		
		return null;
	}
	
	@Override
	public int insert(ActionQualificationLogPO params, SqlSession session) {
		try {
			logger.info("ActionQualificationLogDAOImpl->insert->params=" + JSON.toJSONString(params));
			
			return session.getMapper(IActionQualificationLogMapper.class).insert(params);
		}
		catch(Exception e) {
			logger.error("ActionQualificationLogDAOImpl->insert->Cause:" + e.getCause());
			logger.error("ActionQualificationLogDAOImpl->insert->Message:" + e.getMessage());
			logger.error("ActionQualificationLogDAOImpl->insert->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}
}
