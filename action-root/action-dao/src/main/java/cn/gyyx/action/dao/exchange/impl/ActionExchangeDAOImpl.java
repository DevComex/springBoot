package cn.gyyx.action.dao.exchange.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.exchange.IActionExchangeDAO;
import cn.gyyx.action.dao.exchange.mapper.IActionExchangeMapper;

public class ActionExchangeDAOImpl extends MyBatisBaseDAO implements IActionExchangeDAO {
	
	@Override
	public List<ActionExchangePO> get(ActionExchangePO params) {
		logger.info("ActionExchangeDAOImpl->get->start.");
		
		try(SqlSession session = this.getSession()) {
			logger.info("ActionExchangeDAOImpl->get->params=" + JSON.toJSONString(params));
			
			IActionExchangeMapper mapper = session.getMapper(IActionExchangeMapper.class);
			
			List<ActionExchangePO> poList = mapper.select(params);
			if (poList != null && poList.size() > 0) {
				logger.info("ActionExchangeDAOImpl->get->result=" + JSON.toJSONString(poList));
				return poList;
			}
		}
		catch(Exception e) {
			logger.error("ActionExchangeDAOImpl->get->errorCause:" + e.getCause());
			logger.error("ActionExchangeDAOImpl->get->errorMessage:" + e.getMessage());
			logger.error("ActionExchangeDAOImpl->get->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionExchangeDAOImpl->get->result=null");
		
		return null;
	}
	
	@Override
	public int put(ActionExchangePO params, SqlSession session) {
		logger.info("ActionExchangeDAOImpl->put->start.");
		
		try {
			logger.info("ActionExchangeDAOImpl->put->params=" + JSON.toJSONString(params));
			
			IActionExchangeMapper mapper = session.getMapper(IActionExchangeMapper.class);
			
			return mapper.updateCount(params);
		}
		catch(Exception e) {
			logger.error("ActionExchangeDAOImpl->put->errorCause:" + e.getCause());
			logger.error("ActionExchangeDAOImpl->put->errorMessage:" + e.getMessage());
			logger.error("ActionExchangeDAOImpl->put->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionExchangeDAOImpl->put->result=-1");
		
		return -1;
	}
}
