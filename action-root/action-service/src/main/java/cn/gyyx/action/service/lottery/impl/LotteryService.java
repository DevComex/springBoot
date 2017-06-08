package cn.gyyx.action.service.lottery.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.beans.lottery.LotteryEnum.PrizesTypeEnum;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.config.IActionPrizeBLL;
import cn.gyyx.action.bll.config.impl.ActionPrizeBLLImpl;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.ILotteryBLL;
import cn.gyyx.action.bll.lottery.ILotteryPrizesBLL;
import cn.gyyx.action.bll.lottery.ILotteryProbabilityBLL;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryBLLImplByMustWin;
import cn.gyyx.action.bll.lottery.impl.LotteryPrizesBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryProbabilityBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryProbabilityBLLImplByAvailable;
import cn.gyyx.action.bll.lottery.impl.LotteryValidateBLLImplByCount;
import cn.gyyx.action.bll.lottery.impl.LotteryValidateBLLImplByMustWin;
import cn.gyyx.action.bll.lottery.impl.LotteryValidateBLLImplByTime;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.lottery.ILotterySendPrizesService;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LotteryService {

	private static final String MUST_WIN = "MustWin";
	private static Logger logger = GYYXLoggerFactory.getLogger(LotteryService.class);
	private String message;
	private LotteryPrizesVO thankYouPrizes;
	
	// 抽奖
	public LotteryPrizesVO getPrizesByMustWin(int activityId, String userId, String ip, boolean isMustWin) {
		LotteryPrizesVO result = this.getThankYouPrizes(activityId, userId);
		
		ILotteryValidateBLL validateByTime = new LotteryValidateBLLImplByTime();
		ILotteryValidateBLL validateByMustWin = new LotteryValidateBLLImplByMustWin();
		ILotteryValidateBLL validateByCount = new LotteryValidateBLLImplByCount();
		ILotteryBLL lottery = null;
		ILotteryPrizesBLL prizesBll = new LotteryPrizesBLLImpl();
		IActionLotteryLogBLL logBll = new ActionLotteryLogBLLImpl();
		
		// 防止用户重复提交
		DistributedLock lock = new DistributedLock(activityId + userId);
		
		SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
		
		ActionPrizePO prizes = null;
		try {
			try {
				lock.weakLock(30, 0);
				
				LotteryValidateVO param = new LotteryValidateVO();
				param.setActivityId(activityId);
				param.setUserId(userId);
				
				// 是否在抽奖活动时间范围内
				if (!validateByTime.validate(param)) {
					message = validateByTime.getMessage();
					if (session != null) session.rollback();
					return null;
				}
				
				// 是否使用必中券
				if (isMustWin == true) {
					// 必中券次数是否>0；是否使用过必中券。
					if (!validateByMustWin.validate(param)) {
						message = validateByMustWin.getMessage();
						if (session != null) session.rollback();
						return null;
					}
					
					boolean isWinning = false;  // 是否抽中有效奖品
					
					lottery = new LotteryBLLImplByMustWin();
					
					// 获得奖品
					IActionPrizeBLL actionPrizeBLL = new ActionPrizeBLLImpl();
					lottery.setPrizesList(actionPrizeBLL.getDataList(activityId));
					
					// 获得有效奖品概率
					ILotteryProbabilityBLL lotteryProbabilityBLL = new LotteryProbabilityBLLImplByAvailable();
					lottery.setProbability(lotteryProbabilityBLL.getDataList(activityId));
					
					// 抽奖
					prizes = lottery.getPrizes();
					
					if (prizes != null) {  // 有效奖品
						// 更新奖品数量
						if (prizesBll.putPrizesNumber(activityId, prizes.getCode(), session) > 0) {
							// 设置奖品
							isWinning = true;
							result = this.setPrizes(activityId, userId, prizes);
							result.setRemark(MUST_WIN);  // 设置必中标识
						}
						else {
							prizes = null;
						}
					}
					
					if (false == isWinning) {
						message = "再来一次！";
						if (session != null) session.rollback();
						return null;
					}
				}
				else {
					// 抽奖次数是否>0；
					if (!validateByCount.validate(param)) {
						message = validateByCount.getMessage();
						if (session != null) session.rollback();
						return null;
					}
					
					lottery = new LotteryBLLImpl();
					
					// 获得奖品
					IActionPrizeBLL actionPrizeBLL = new ActionPrizeBLLImpl();
					lottery.setPrizesList(actionPrizeBLL.getDataList(activityId));
					
					// 获得有效奖品概率
					ILotteryProbabilityBLL lotteryProbabilityBLL = new LotteryProbabilityBLLImpl();
					lottery.setProbability(lotteryProbabilityBLL.getDataList(activityId));
					
					// 抽奖
					prizes = lottery.getPrizes();
					
					if (prizes != null) {  // 中奖了
						boolean isUpdateNumber = true;
						
						if (prizes.getIsAvailable() == 1) {
							// 奖品为有效奖品，则判断是否中过有效奖品
	
							ActionLotteryLogPO logParams = new ActionLotteryLogPO();
							logParams.setActivityType("lottery");
							logParams.setActivityId(activityId);
							logParams.setUserId(userId);
							logParams.setIsAvailable(1);
							
							List<ActionLotteryLogPO> logList = logBll.getDataList(logParams);
							
							if (logList != null) {
								if (logList.size() > 1) {
									// 每人最多中两次有效奖品，则返回谢谢参与
									isUpdateNumber = false;
								}
								else if (logList.size() > 0) {
									// 中过一次有效奖品
									if (logList.get(0).getRemark() != null && logList.get(0).getRemark().equalsIgnoreCase(MUST_WIN)) {
										// 使用必中券，则能在中一次奖
										isUpdateNumber = true;
									}
									else {
										// 没有使用必中券中有效奖品，则返回谢谢参与
										isUpdateNumber = false;
									}
								}
								else {
									// 没有中过有效奖品
									isUpdateNumber = true;
								}
							}
						}
						
						if (isUpdateNumber == true) {
							// 更新奖品数量
							if (prizesBll.putPrizesNumber(activityId, prizes.getCode(), session) < 1) {
								// 设置奖品为谢谢参与
								prizes = null;
								result = this.getThankYouPrizes(activityId, userId);
							}
							else {
								// 设置奖品
								result = this.setPrizes(activityId, userId, prizes);
							}
						}
						else {
							// 设置奖品为谢谢参与
							prizes = null;
							result = this.getThankYouPrizes(activityId, userId);
						}
					}
				}
				
				// 是否为游戏虚拟物品。如果是，则发放到服务器
				if (prizes != null && prizes.getIsReal().equalsIgnoreCase(PrizesTypeEnum.GamePrizes.name())) {
					if (!StringUtils.isEmpty(prizes.getServicePrizesCode())) {
						ILotterySendPrizesService sendPrizes = new LotterySendPrizesToGame();
						
						if (false == sendPrizes.pushPrizes(userId, prizes.getServicePrizesCode())) {
							// 发生异常，改为谢谢参与
							result = this.getThankYouPrizes(activityId, userId);
						}
					}
				}
				
				// 获得卡号
				if (prizes != null && prizes.getIsReal().equalsIgnoreCase(PrizesTypeEnum.CardPrizes.name())) {
					NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
					result.setCardCode(userLotteryBll.getCardCode(activityId, result.getPrizeEnglish(), result.getUserCode(), session));
				}
				
				if (result.getRemark() != null && result.getRemark().equalsIgnoreCase(MUST_WIN)) {
					// 必中抽奖次数减一
					prizesBll.putMustWinCountMinusOne(activityId, userId, session);
				}
				else {
					// 抽奖次数减一
					prizesBll.putLotteryCountMinusOne(activityId, userId, session);
				}
				
				// 记录抽奖结果
				ActionLotteryLogPO logPo = this.setLog(activityId, userId, ip, result);
				logPo.setActivityType("lottery");
				logBll.putData(logPo, session);
				
				session.commit();
			}
			finally {
				lock.close();
			}
		}
		catch(Exception e) {
			if (session != null) session.rollback();
			logger.error("LotteryService.getPrizesByMustWin", e.getCause());
		}
		
		return result;
	}
	
	public String getMessage() {
		return message;
	}
	
	protected ActionLotteryLogPO setLog(int activityId, String userId, String ip, LotteryPrizesVO vo) {
		ActionLotteryLogPO result = new ActionLotteryLogPO();
		result.setActivityId(activityId);
		result.setUserId(userId);
		result.setServerId("2");
		result.setPrizeType(vo.getPrizeType());
		result.setPrizeCode(vo.getPrizeCode());
		result.setPrizeName(vo.getPrizeName());
		result.setPrizeNum(vo.getPrizeNum());
		result.setCardCode(vo.getCardCode());
		result.setWinningAt(new Date());
		result.setWinningIp(ip);
		result.setIsAvailable(vo.getIsAvailable());
		result.setRemark(vo.getRemark());
		
		return result;
	}
	
	protected LotteryPrizesVO setPrizes(int activityId, String userId, ActionPrizePO po) {
		LotteryPrizesVO result = new LotteryPrizesVO();
		
		if (po != null) {
			result.setActivityId(activityId);
			result.setUserId(userId);
			result.setPrizeType(po.getIsReal());
			result.setPrizeCode(po.getCode());
			result.setPrizeName(po.getChinese());
			result.setPrizeEnglish(po.getEnglish());
			result.setPrizeNum(po.getNum());
			result.setIsAvailable(po.getIsAvailable());
		}
		
		return result;
	}
	
	protected LotteryPrizesVO getThankYouPrizes(int activityId, String userId) {
		if (this.thankYouPrizes == null) {
			thankYouPrizes = new LotteryPrizesVO();
			thankYouPrizes.setActivityId(activityId);
			thankYouPrizes.setUserId(userId);
			thankYouPrizes.setPrizeType(PrizesTypeEnum.ThankYou.name());
			thankYouPrizes.setPrizeCode(0);
			thankYouPrizes.setPrizeName("谢谢参与");
			thankYouPrizes.setPrizeNum(7);
			thankYouPrizes.setIsAvailable(0);
		}
		
		return this.thankYouPrizes;
	}
}
