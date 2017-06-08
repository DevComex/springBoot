package cn.gyyx.action.dao.config.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.config.IActionPrizeDAO;
import cn.gyyx.action.dao.config.mapper.IActionPrizeMapper;

public class ActionPrizeDAOImpl extends MyBatisBaseDAO implements IActionPrizeDAO {

	@Override
	public List<ActionPrizePO> getDataList(int activityId) {
		List<ActionPrizePO> result = null;
		SqlSession session = null;
		
		try {
			session = this.getSession();
			
			IActionPrizeMapper mapper = session.getMapper(IActionPrizeMapper.class);
			
			result = mapper.getDataList(activityId);
		}
		catch(Exception e) {
			logger.error("ActionPrizeDAOImpl.getDataList => activityId=" + activityId, e);
		}
		
		return result;
	}
}
