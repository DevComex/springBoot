package cn.gyyx.action.bll.lottery.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.IActionPrizesAddressDAO;
import cn.gyyx.action.dao.lottery.impl.ActionPrizesAddressDAOImpl;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;

public class ActionPrizesAddressBLLImpl implements IActionPrizesAddressBLL {

	private IActionPrizesAddressDAO actionPrizesAddressDAO = new ActionPrizesAddressDAOImpl();
	
	@Override
	public LotteryPrizesVO getAddress(int activityId, int userCode, String userId) {
		LotteryPrizesVO result = null;
		
		ActionPrizesAddressPO param = new ActionPrizesAddressPO();
		param.setActivityId(activityId);
		param.setUserCode(userCode);
		param.setUserId(userId);
		
		List<ActionPrizesAddressPO> poList = actionPrizesAddressDAO.getDataList(param);
		
		if (poList != null && poList.size() > 0) {
			// 每人每个活动最多只有一个地址。如果有多个地址，则取最新的。
			if (poList.size() > 1) {  
			/*	Collections.sort(poList, new Comparator<ActionPrizesAddressPO>() {
					@Override
					public int compare(ActionPrizesAddressPO arg0, ActionPrizesAddressPO arg1) {
						return arg1.getModifyAt().compareTo(arg0.getModifyAt());
					}
				});*/
			}
			
			result = new LotteryPrizesVO();
			result.setUserName(poList.get(0).getUserName());
			result.setUserPhone(poList.get(0).getUserPhone());
			result.setUserAddress(poList.get(0).getUserAddress());
		}
		
		return result;
	}
	
	@Override
	public boolean putAddress(LotteryPrizesVO vo, SqlSession session) throws Exception {
		boolean result = false;
		
		ActionPrizesAddressPO param = new ActionPrizesAddressPO();
		param.setActivityId(vo.getActivityId());
		param.setUserCode(vo.getUserCode());
		param.setUserId(vo.getUserId());
		
		// 查询数据库中是否存在
		List<ActionPrizesAddressPO> poList = actionPrizesAddressDAO.getDataList(param);
		
		param.setUserName(vo.getUserName());
		param.setUserPhone(vo.getUserPhone());
		param.setUserEmail("");
		param.setUserAddress(vo.getUserAddress());
		
		try {
			DistributedLock lock = new DistributedLock(vo.getActivityId() + vo.getUserId() + "address");
			
			try {
				lock.weakLock(30, 0);  // 开启分布式锁
				
				Date now = new Date();
				if (poList == null || poList.size() == 0) {
					// 创建新纪录
					param.setCreateAt(now);
					param.setModifyAt(now);
					
					actionPrizesAddressDAO.postData(param, session);
				}
				else {
					// 更新记录
					param.setModifyAt(now);
					
					actionPrizesAddressDAO.putData(param, session);
				}
				
				result = true;
			}
			finally {
				lock.release();
			}
		}
		catch(DLockException dle) {
			logger.error("ActionPrizesAddressBLLImpl.putAddress", dle);
		}
		
		return result;
	}
	
	// 根据活动ID，用户ID，类型查询地址信息
	public ActionPrizesAddressPO get(int activityId, String userId, String activityType) {
		logger.info("ActionPrizesAddressBLLImpl->get->start.");
		
		ActionPrizesAddressPO params = new ActionPrizesAddressPO();
		params.setActivityId(activityId);
		params.setUserId(userId);
		params.setActivityType(activityType);
		
		logger.info("ActionPrizesAddressBLLImpl->get->params=" + JSON.toJSONString(params));
		
		List<ActionPrizesAddressPO> addressList = actionPrizesAddressDAO.get(params);
		
		logger.info("ActionPrizesAddressBLLImpl->get->addressList=" + JSON.toJSONString(addressList));
		
		if (null != addressList && addressList.size() > 0) {
			if (addressList.size() > 1) {
				/*Collections.sort(addressList, new Comparator<ActionPrizesAddressPO>() {
					@Override
					public int compare(ActionPrizesAddressPO arg0, ActionPrizesAddressPO arg1) {
						return arg1.getModifyAt().compareTo(arg0.getModifyAt());
					}
				});*/
			}
			
			return addressList.get(0);
		}
		
		return null;
	}
	
	// 如果没有地址，则创建地址；否则更新。
	public boolean post(ActionPrizesAddressPO params, SqlSession session) throws Exception {
		boolean result = false;
		
		// 查询数据库中是否存在
		List<ActionPrizesAddressPO> poList = actionPrizesAddressDAO.get(params);
		
		try {
			Date now = Calendar.getInstance().getTime();
			
			if (null == poList || 0 == poList.size()) {
				// 创建新纪录
				params.setCreateAt(now);
				params.setModifyAt(now);
				
				if (actionPrizesAddressDAO.post(params, session) > 0) {
					result = true;
				}
			}
			else {
				// 更新记录
				params.setModifyAt(now);
				
				if (actionPrizesAddressDAO.put(params, session) > 0) {
					result = true;
				}
			}
		}
		catch(DLockException dle) {
			logger.error("ActionPrizesAddressDAOImpl->put->Cause:" + dle.getCause());
			logger.error("ActionPrizesAddressDAOImpl->put->Message:" + dle.getMessage());
			logger.error("ActionPrizesAddressDAOImpl->put->StackTrace:" + dle.getStackTrace());
		}
		
		return result;
	}
	
	/**
	 * 获取用户中奖纪录加地址
	 * @param currentPage 
	 * @param i 
	 * @return
	 */
	public List<ActionPrizesAddressPO> getUserAddress(int pageSize, int currentPage){
		return actionPrizesAddressDAO.getUserAddressPaging(pageSize, currentPage);
	}

	public List<ActionPrizesAddressPO> getUserAddress() {
		
		 return actionPrizesAddressDAO.getUserAddress();
	}
	/**
	 * 获取全部记录条数
	 * @return
	 */
	public Integer getCount(){
		return actionPrizesAddressDAO.getCount();
	}
}
