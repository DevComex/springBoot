package cn.gyyx.action.bll.lottery.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;
import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.bll.lottery.ILotteryPrizesBLL;
import cn.gyyx.action.dao.config.IActionLotteryChanceDAO;
import cn.gyyx.action.dao.config.Impl.ActionLotteryChanceDAOImpl;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;
import cn.gyyx.distribute.lock.DistributedLock;

public class LotteryPrizesBLLImpl implements ILotteryPrizesBLL {

	private IActionLotteryLogDAO actionLotteryLogDAO = new ActionLotteryLogDAOImpl();
	private IActionLotteryChanceDAO actionLotteryChanceDAO = new ActionLotteryChanceDAOImpl();
	private IActionQualificationDAO actionQualificationDAO = new ActionQualificationDAOImpl();
	
	@Override
	public List<LotteryPrizesVO> getPrizesInfomations(int activityId, String userId) {
		List<LotteryPrizesVO> result = null;
		
		ActionLotteryLogPO po = new ActionLotteryLogPO();
		po.setActivityType("lottery");
		po.setActivityId(activityId);
		po.setUserId(userId);
		
		List<ActionLotteryLogPO> poList = actionLotteryLogDAO.getDataList(po);
		if (poList != null && poList.size() > 0) {
			result = new ArrayList<LotteryPrizesVO>();
			
			for (ActionLotteryLogPO item : poList) {
				LotteryPrizesVO vo = new LotteryPrizesVO();
				vo.setActivityId(item.getActivityId());
				vo.setUserId(item.getUserId());
				vo.setPrizeType(item.getPrizeType());
				vo.setPrizeCode(item.getPrizeCode());
				vo.setPrizeName(item.getPrizeName());
				vo.setPrizeNum(item.getPrizeNum());
				vo.setCardCode(item.getCardCode());
				
				result.add(vo);
			}
		}
		
		return result;
	}
	
	@Override
	public int putPrizesNumber(int activityId, int code, SqlSession session) throws Exception {
		int result = 0;
		
		String key = activityId + code + this.getClass().getName();
		try(DistributedLock lock = new DistributedLock(key)) {
			lock.weakLock(30,0);
			
			ActionLotteryChancePO params = new ActionLotteryChancePO();
			params.setActionCode(activityId);
			params.setPrizeCode(code);
			
			// 查询该奖品剩余数量
			ActionLotteryChancePO po = actionLotteryChanceDAO.getData(params);
			if (po == null) return -1;
			
			// -100代表不限制数量；其他代表限制数量
			if (po.getNumber() >= 0) {
				// 奖品数量大于0，则表示奖品有剩余
				if (po.getNumber() > 0) {
					// 更新数量
					result = actionLotteryChanceDAO.putNumber(po, session);
				}
			}
		}
		catch(Exception e) {
			logger.error("LotteryPrizesBLLImpl.putPrizesNumber => activityId=" + activityId + ";code=" + code, e);
			throw new Exception(e.getCause());
		}
		
		return result;
	}
	
	@Override
	public int putLotteryCountMinusOne(int activityId, String userId, SqlSession session) throws Exception {
		int result = 0;
		
		try(DistributedLock lock = new DistributedLock(activityId + "ActionQualification" + userId)) {
			lock.weakLock(30,0);
			
			ActionQualificationPO po = new ActionQualificationPO();
			po.setActivityId(activityId);
			po.setUserId(userId);
			
			result = actionQualificationDAO.putLotteryCountMinusOne(po, session);
		}
		catch(Exception e) {
			logger.error("LotteryPrizesBLLImpl.putLotteryCountMinusOne", e);
		}
		
		return result;
	}
	
	@Override
	public int putMustWinCountMinusOne(int activityId, String userId, SqlSession session) throws Exception {
		int result = 0;
		
		try(DistributedLock lock = new DistributedLock(activityId + "ActionQualification" + userId)) {
			lock.weakLock(30,0);
			
			ActionQualificationPO po = new ActionQualificationPO();
			po.setActivityId(activityId);
			po.setUserId(userId);
			
			result = actionQualificationDAO.putMustWinCountMinusOne(po, session);
		}
		catch(Exception e) {
			logger.error("LotteryPrizesBLLImpl.putLotteryCountMinusOne", e);
		}
		
		return result;
	}
	
    /**
     * 普通抽奖次数减一 by 某天(以创建时间为准)
     * 
     * @author caishuai 原有基础上改动过，去掉锁，调用时如有必要用外部去使用锁
     * @throws Exception 
     * @other 改动时没有其他程序调用该方法 2017-3-27 14:19:04
     * 
     */
    @Override
    public int putLotteryCountMinusOneByDate(int activityId, String userId,
            Date createAt,SqlSession session) throws Exception {
        int result = 0;
        ActionQualificationPO po = new ActionQualificationPO();
        po.setActivityId(activityId);
        po.setUserId(userId);
        po.setCreateAt(createAt);
        result = actionQualificationDAO.putLotteryCountMinusOneByDate(po,session);
        return result;
    }
	
	
}
