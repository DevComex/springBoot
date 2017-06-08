package cn.gyyx.action.dao.lottery.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.mapper.IActionQualificationMapper;

public class ActionQualificationDAOImpl extends MyBatisBaseDAO implements IActionQualificationDAO {

	public Map<String, Integer> getLotteryCountAndMustWinCount(String userId, int activityId) {
		Map<String, Integer> result = null;
		
		ActionQualificationPO po = this.getData(userId, activityId);
		if (po != null) {
			result = new HashMap<String, Integer>();
			
			result.put("LotteryCount", po.getLotteryCount() != null ? po.getLotteryCount() : 0);
			result.put("MustWinCount", po.getMustWinCount() != null ? po.getMustWinCount() : 0);
		}
		
		return result;
	}
	
	public int getLotteryCount(String userId, int activityId) {
		ActionQualificationPO result = this.getData(userId, activityId);
		
		if (result != null && result.getLotteryCount() != null) {
			return result.getLotteryCount();
		}
		
		return 0;
	}
	
	public int getMustWinCount(String userId, int activityId) {
		ActionQualificationPO result = this.getData(userId, activityId);
		
		if (result != null && result.getLotteryCount() != null) {
			return result.getMustWinCount();
		}
		
		return 0;
	}
	
	public ActionQualificationPO getData(String userId, int activityId) {
		ActionQualificationPO result = null;
		
		try(SqlSession session = this.getSession()) {
			ActionQualificationPO parameters = new ActionQualificationPO();
			parameters.setUserId(userId);
			parameters.setActivityId(activityId);
			
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			
			result = mapper.selectByUseridAndActivityid(parameters);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAO.getLotteryCount => userid = " + userId + "; activityId = " + activityId, e);
		}
		
		return result;
	}
	
	/*
	 * 插入积分初始兑换信息
	 * */
	public ResultBean<Integer> insertLottery(ActionQualificationPO aqpo) {
		ResultBean<Integer> result =new ResultBean<Integer>(false,"",null);
		SqlSession session = null;
		try {
			session = this.getSession();	
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			int a=mapper.insertLottery(aqpo);
			result.setData(a);
			result.setIsSuccess(true);
			result.setMessage("插入成功");
			session.commit();
			return result;
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAO.", e);
		}
		
		return result;
		
	}
	
	/*
	 * 增加普通奖券机会
	 * */
	public ResultBean<Integer> updateLottery(ActionQualificationPO aqpo){
		ResultBean<Integer> result =new ResultBean<Integer>(false,"",null);
		SqlSession session = null;
		try {
			session = this.getSession();	
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			int a=mapper.updateLottery(aqpo);
			result.setIsSuccess(true);
			result.setMessage("更新普通奖券成功");
			session.commit();
			return result;
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAO.", e);
		}
		
		return result;
		
	}
	
	/*
	 * 增加必中奖券机会
	 * */
	public ResultBean<Integer> updateMustWin(ActionQualificationPO aqpo){
		ResultBean<Integer> result =new ResultBean<Integer>(false,"",null);
		SqlSession session = null;
		try {
			session = this.getSession();	
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			int a=mapper.updateMustWin(aqpo);
			result.setIsSuccess(true);
			result.setMessage("更新必中奖次数成功");
			session.commit();
			return result;
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAO.", e);
		}
		
		return result;
		
	}
	
	// 抽奖次数减一
	public int putLotteryCountMinusOne(ActionQualificationPO po, SqlSession session) throws Exception {
		int result = 0;
		
		try {
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			result = mapper.putLotteryCountMinusOne(po);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAOImpl.putLotteryCountMinusOne", e);
			throw new Exception(e.getCause());
		}
		
		return result;
	}
	
	// 必中次数减一
	public int putMustWinCountMinusOne(ActionQualificationPO po, SqlSession session) throws Exception {
		int result = 0;
		
		try {
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			result = mapper.putMustWinCountMinusOne(po);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAOImpl.putMustWinCountMinusOne", e);
			throw new Exception(e.getCause());
		}
		
		return result;
	}

	public int insert(ActionQualificationPO po, SqlSession session) {
		try {
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			
			return mapper.insertLottery(po);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAOImpl->insert->Cause:" + e.getCause());
			logger.error("ActionQualificationDAOImpl->insert->Message:" + e.getMessage());
			logger.error("ActionQualificationDAOImpl->insert->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}
	
	public int minusLotteryScore(ActionQualificationPO po, SqlSession session) {
		
		try {
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			
			return mapper.minusLotteryScore(po);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAOImpl->minusLotteryScore->Cause:" + e.getCause());
			logger.error("ActionQualificationDAOImpl->minusLotteryScore->Message:" + e.getMessage());
			logger.error("ActionQualificationDAOImpl->minusLotteryScore->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}
	
	public int addLotteryScore(ActionQualificationPO po, SqlSession session) {
		
		try {
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			return mapper.addLotteryScore(po);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAOImpl->addLotteryScore->Cause:" + e.getCause());
			logger.error("ActionQualificationDAOImpl->addLotteryScore->Message:" + e.getMessage());
			logger.error("ActionQualificationDAOImpl->addLotteryScore->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}
	
	//普通抽奖次数减一   by  某天(以创建时间为准)
	public int putLotteryCountMinusOneByDate(ActionQualificationPO po, SqlSession session) throws Exception {
		int result = 0;	
		try {
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			result = mapper.putLotteryCountMinusOneByDate(po);
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAOImpl.putLotteryCountMinusOneByDate", e);
			throw new Exception(e.getCause());
		}
		
		return result;
	}

	
	//初始化抽奖机会  通用
	public ResultBean<Integer> InitializeLottery(ActionQualificationPO po){
		ResultBean<Integer> result =new ResultBean<Integer>(false,"",null);
		SqlSession session = null;
		try {
			session = this.getSession();	
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
			int a=mapper.InitializeLottery(po);
			result.setData(a);
			result.setIsSuccess(true);
			result.setMessage("插入成功");
			session.commit();
			return result;
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAO.InitializeLottery", e);
		}finally {
			if(session!=null){
				session.close();	
			}
			
		}		
		return result;
		
	}
	
	//根据用户ID、活动ID、抽奖机会初始化时间 查询抽奖资格
	public ActionQualificationPO getDataByCreateAt(String userId, int activityId,Date createAt ) {
		ActionQualificationPO result = null;
		SqlSession session = null;
		
		try {
			session = this.getSession(true);
			
			ActionQualificationPO parameters = new ActionQualificationPO();
			parameters.setUserId(userId);
			parameters.setActivityId(activityId);
			parameters.setCreateAt(createAt);
			IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);		
			result = mapper.selectByUseridAndActivityidAndCreateDate(parameters);
			
		}
		catch(Exception e) {
			logger.error("ActionQualificationDAO.getLotteryCountByCreateAt => userid = " + userId + "; activityId = " + activityId+";CreateAt =" + createAt, e);
		}finally {
                    if (session != null) {
                        session.close();
                    }
                }
		
		return result;
	}

	public ActionQualificationPO getDataByCreateAtSession(String userId, int activityId, Date createAt,
			SqlSession session) {
		ActionQualificationPO parameters = new ActionQualificationPO();
		parameters.setUserId(userId);
		parameters.setActivityId(activityId);
		parameters.setCreateAt(createAt);
		IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
		return 	mapper.selectByUseridAndActivityidAndCreateDate(parameters);
	}

	public void InitializeLotterySession(ActionQualificationPO po, SqlSession session) {
		IActionQualificationMapper mapper = session.getMapper(IActionQualificationMapper.class);
		mapper.InitializeLottery(po);
	}
}
