package cn.gyyx.action.dao.userinfo.impl;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.userinfo.po.ActionGameInterimPO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.userinfo.IActionGameInterimDAO;
import cn.gyyx.action.dao.userinfo.mapper.IActionGameInterimMapper;

public class DefaultActionGameInterimDAO extends MyBatisBaseDAO implements IActionGameInterimDAO {

	public ActionGameInterimPO selectOne(ActionGameInterimPO params) {
		ActionGameInterimPO result = null;
		logger.info("DefaultActionGameInterimDAO->selectOne->params=" + JSON.toJSONString(params));
		
		try(SqlSession session = this.getSession()) {
			result = session.getMapper(IActionGameInterimMapper.class).selectOne(params);
		}
		catch(Exception e) {
			logger.error("DefaultActionGameInterimDAO->selectOne->Cause:" + e.getCause());
			logger.error("DefaultActionGameInterimDAO->selectOne->Message:" + e.getMessage());
			logger.error("DefaultActionGameInterimDAO->selectOne->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("DefaultActionGameInterimDAO->selectOne->result=" + JSON.toJSONString(result));
		
		return result;
	}
	
	public int insert(ActionGameInterimPO params, SqlSession session) {
		logger.info("DefaultActionGameInterimDAO->insert->params=" + JSON.toJSONString(params));
		
		try {
			return session.getMapper(IActionGameInterimMapper.class).insert(params);
		}
		catch(Exception e) {
			logger.error("DefaultActionGameInterimDAO->insert->Cause:" + e.getCause());
			logger.error("DefaultActionGameInterimDAO->insert->Message:" + e.getMessage());
			logger.error("DefaultActionGameInterimDAO->insert->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}
}
