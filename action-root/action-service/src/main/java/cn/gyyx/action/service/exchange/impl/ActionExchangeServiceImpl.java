package cn.gyyx.action.service.exchange.impl;

import java.util.Calendar;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.beans.enums.PrizesTypeEnums;
import cn.gyyx.action.beans.exchange.po.ActionExchangePO;
import cn.gyyx.action.beans.exchange.vo.ActionExchangeVO;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.exchange.IActionExchangeBLL;
import cn.gyyx.action.bll.exchange.IActionExchangeValidateBLL;
import cn.gyyx.action.bll.exchange.impl.ActionExchangeBLLImpl;
import cn.gyyx.action.bll.exchange.impl.ActionExchangeValidateBLLImplByCondition;
import cn.gyyx.action.bll.exchange.impl.ActionExchangeValidateBLLImplByCount;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.lottery.impl.ActionQualificationBLLImpl;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.exchange.IActionExchangeService;
import cn.gyyx.distribute.lock.DistributedLock;

public class ActionExchangeServiceImpl implements IActionExchangeService {
	
	private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
	private IActionExchangeBLL actionExchangeBLL = new ActionExchangeBLLImpl();
	private IActionPrizesAddressBLL actionPrizesAddressBLL = new ActionPrizesAddressBLLImpl();
	private IActionLotteryLogBLL actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
	private IActionQualificationBLL qualificationBLL = new ActionQualificationBLLImpl();
	
	// 兑换奖品
	@Override
	public ResultBean<ActionPrizesAddressPO> exchange(ActionExchangeVO params) {
		ResultBean<ActionPrizesAddressPO> result = new ResultBean<ActionPrizesAddressPO>();
		result.setStateCode(0);
		result.setIsSuccess(false);
		
		logger.info("ActionExchangeServiceImpl->exchange->start.");
		
		// 防止同一用户重复提交
		String key = params.getActivityId() + "-" + params.getUserId() + "-" + this.getClass().getName();
		try(DistributedLock lock = new DistributedLock(key)) {
			lock.weakLock(30, 0);
			
			// 是否在活动期间内
			int hdState = hdConfigBLL.getState(params.getActivityId());
			if (1 != hdState) {
				logger.info("ActionExchangeServiceImpl->exchange->activation is not in progress.");
				result.setStateCode(hdState);
				result.setMessage(hdConfigBLL.getMessage());
				return result;
			}
			
			// 是否兑换过奖品
			ActionLotteryLogPO logParams = new ActionLotteryLogPO();
			logParams.setActivityType(ActivityTypeEnums.Exchange.toString());
			logParams.setActivityId(params.getActivityId());
			logParams.setUserId(params.getUserId());
			logParams.setPrizeType(PrizesTypeEnums.Invitation.toString());
			
			if (actionLotteryLogBLL.isExsits(logParams) > 0) {
				logger.info("ActionExchangeServiceImpl->exchange->exchange is exsits.");
				result.setStateCode(3);
				result.setMessage(hdConfigBLL.getMessage());
				return result;
			}
			
			// 获得兑换奖品信息
			ActionExchangePO poParams = new ActionExchangePO();
			poParams.setActivityId(params.getActivityId());
			poParams.setItemsId(params.getItemsId());
			
			ActionExchangePO poResult = actionExchangeBLL.getOne(poParams);
			if (null == poResult) {
				logger.info("ActionExchangeServiceImpl->exchange->ActionExchangePO'data not exsits.params=" + JSON.toJSONString(poParams));
				return result;
			}
			logger.info("ActionExchangeServiceImpl->exchange->ActionExchangePO result=" + JSON.toJSONString(poResult));
			
			// 获得兑换积分
			int exchangeScore = poResult.getExchangeCondition();
			if (exchangeScore < 1) {
				logger.info("ActionExchangeServiceImpl->exchange->put->exchangeScore=" + exchangeScore);
				result.setStateCode(-1);
				result.setMessage("奖品兑换失败！");
				return result;
			}
			
			// 获得用户积分
			int userScore = qualificationBLL.getScore(params.getActivityId(), params.getUserId());
			logger.info("ActionExchangeServiceImpl->exchange->userScore=" + userScore);
			if (userScore < exchangeScore) {
				logger.info("ActionExchangeServiceImpl->exchange->point low.params=" + JSON.toJSONString(params));
				result.setStateCode(1);
				result.setMessage("您的积分不够兑换！");
				return result;
			}
			else {
				params.setUserPoint(userScore);
			}
			
			// 用户积分是否满足兑换条件
			IActionExchangeValidateBLL validateCondition = new ActionExchangeValidateBLLImplByCondition(poResult);
			if (!validateCondition.validate(params)) {
				logger.info("ActionExchangeServiceImpl->exchange->point validate faild.params=" + JSON.toJSONString(params));
				result.setStateCode(1);
				result.setMessage(validateCondition.getMessage());
				return result;
			}
			logger.info("ActionExchangeServiceImpl->exchange->point validate pass.params=" + JSON.toJSONString(params));
			
			// 奖品数量是否大于零
			IActionExchangeValidateBLL validateCount = new ActionExchangeValidateBLLImplByCount(poResult);
			if (!validateCount.validate(params)) {
				logger.info("ActionExchangeServiceImpl->exchange->count validate faild.params=" + JSON.toJSONString(params));
				result.setStateCode(2);
				result.setMessage(validateCount.getMessage());
				return result;
			}
			logger.info("ActionExchangeServiceImpl->exchange->count validate pass.params=" + JSON.toJSONString(params));
				
			SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
			
			try {
				// 兑换奖品数量减1
				int putState = actionExchangeBLL.put(poResult, session);
				if (0 == putState) {
					logger.info("ActionExchangeServiceImpl->exchange->put->number is gone. putState=0");
					// 奖品已经兑换完
					session.rollback();
					result.setStateCode(2);
					result.setMessage("奖品已经兑完了！");
					return result;
				}
				else if (putState < 0) {
					// 奖品兑换失败
					logger.info("ActionExchangeServiceImpl->exchange->put->faild. putState=<0");
					session.rollback();
					result.setStateCode(-1);
					result.setMessage("奖品兑换失败！");
					return result;
				}
				
				// 扣除用户相应积分
				int minusState = qualificationBLL.minusLotteryScore(params.getActivityId(), params.getUserId(), exchangeScore, OperateScoreEnums.Exchange.toString(), params.getIp(), session);
				if (minusState < 0) {
					logger.info("ActionExchangeServiceImpl->exchange->put->minus lotteryScore is faild.");
					session.rollback();
					result.setStateCode(-1);
					result.setMessage("奖品兑换失败！");
					return result;
				}
				else if (0 == minusState) {
					logger.info("ActionExchangeServiceImpl->exchange->put->user score is low.");
					session.rollback();
					result.setStateCode(-1);
					result.setMessage("您的积分不足！");
					return result;
				}

				// 保存兑换记录
				ActionLotteryLogPO po = new ActionLotteryLogPO();
				po.setActivityType(ActivityTypeEnums.Exchange.toString());
				po.setActivityId(params.getActivityId());
				po.setUserId(params.getUserId());
				po.setServerId("2");
				po.setPrizeType(PrizesTypeEnums.Invitation.toString());
				po.setPrizeCode(0);
				po.setPrizeName(poResult.getItemsName());
				po.setWinningAt(Calendar.getInstance().getTime());
				po.setWinningIp(params.getIp());
				
				if (actionLotteryLogBLL.putData(po, session) < 1) {
					// 保存日志失败
					logger.info("ActionExchangeServiceImpl->exchange->log->faild. < 1");
					session.rollback();
					result.setStateCode(-1);
					result.setMessage("奖品兑换失败！");
					return result;
				}
				
				session.commit();
				
				// 查询地址信息
				int activityId = params.getActivityId();
				ActionPrizesAddressPO addressResult = actionPrizesAddressBLL.get(activityId, params.getUserId(), ActivityTypeEnums.Exchange.toString());
				if (null != addressResult) {
					result.setData(addressResult);
				}
				
				result.setIsSuccess(true);
			}
			catch(Exception e) {
				if (session != null) session.rollback();
				logger.error("ActionExchangeServiceImpl->exchange->sql->errorCause:" + e.getCause());
				logger.error("ActionExchangeServiceImpl->exchange->sql->errorMessage:" + e.getMessage());
				logger.error("ActionExchangeServiceImpl->exchange->sql->StackTrace:" + e.getStackTrace());
			}
			finally {
				if (session != null) session.close();
			}
		}
		catch(Exception e) {
			logger.error("ActionExchangeServiceImpl->exchange->errorCause:" + e.getCause());
			logger.error("ActionExchangeServiceImpl->exchange->errorMessage:" + e.getMessage());
			logger.error("ActionExchangeServiceImpl->exchange->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionExchangeServiceImpl->exchange->done." + JSON.toJSONString(result));
		
		return result;
	}
	
	// 获得用户积分
	protected int getUserPoint(int activityId, String userId) {
		// TODO:获得用户积分
		return 1000;
	}
}
