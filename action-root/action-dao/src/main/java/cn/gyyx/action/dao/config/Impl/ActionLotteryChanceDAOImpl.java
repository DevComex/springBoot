package cn.gyyx.action.dao.config.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.config.IActionLotteryChanceDAO;
import cn.gyyx.action.dao.config.mapper.IActionLotteryChanceMapper;

public class ActionLotteryChanceDAOImpl extends MyBatisBaseDAO implements IActionLotteryChanceDAO {

	@Override
	public List<ActionLotteryChancePO> getDataList(int activityId) {
		List<ActionLotteryChancePO> result = null;
		SqlSession session = null;
		
		try {
			session = this.getSession();
			
			IActionLotteryChanceMapper mapper = session.getMapper(IActionLotteryChanceMapper.class);
			
			result = mapper.getDataList(activityId);
		}
		catch(Exception e) {
			logger.error("ActionLotteryChanceDAO.getDataList => activityId" + activityId, e);
		}
		
		return result;
	}
	
	@Override
	public List<ActionLotteryChancePO> getDataListByAvailable(int activityId) {
		List<ActionLotteryChancePO> result = null;
		SqlSession session = null;
		
		try {
			session = this.getSession();
			
			IActionLotteryChanceMapper mapper = session.getMapper(IActionLotteryChanceMapper.class);
			
			result = mapper.getDataListByAvailable(activityId);
		}
		catch(Exception e) {
			logger.error("ActionLotteryChanceDAO.getDataList => activityId" + activityId, e);
		}
		
		return result;
	}
	
	@Override
	public ActionLotteryChancePO getData(ActionLotteryChancePO po) {
		ActionLotteryChancePO result = null;
		SqlSession session = null;
		
		try {
			session = this.getSession();
			
			IActionLotteryChanceMapper mapper = session.getMapper(IActionLotteryChanceMapper.class);

			result = mapper.getData(po);
		}
		catch(Exception e) {
			logger.error("ActionLotteryChanceDAO.getDataList", e);
		}
		
		return result;
	}
	
	@Override
	public int putNumber(ActionLotteryChancePO po, SqlSession session) throws Exception {
		int result = 0;
		
		try {
			IActionLotteryChanceMapper mapper = session.getMapper(IActionLotteryChanceMapper.class);

			result = mapper.putNumber(po);
		}
		catch(Exception e) {
			logger.error("ActionLotteryChanceDAO.getDataList", e);
			throw new Exception(e.getCause());
		}
		
		return result;
	}
}
