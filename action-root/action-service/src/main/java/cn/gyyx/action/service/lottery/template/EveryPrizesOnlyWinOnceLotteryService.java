package cn.gyyx.action.service.lottery.template;

import java.lang.management.ManagementFactory;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfLotteryVO;
import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;
import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.beans.enums.PrizesTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.config.IActionPrizeBLL;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.ActionPrizeBLLImpl;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.bll.lottery.ILotteryBLL;
import cn.gyyx.action.bll.lottery.ILotteryPrizesBLL;
import cn.gyyx.action.bll.lottery.ILotteryProbabilityBLL;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.lottery.impl.ActionQualificationBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryPrizesBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryProbabilityBLLImpl;
import cn.gyyx.action.bll.lottery.template.validate.ActivityIsInProgressValidateBLL;
import cn.gyyx.action.bll.lottery.template.validate.EachPrizesOnlyWinOnceValidateBLL;
import cn.gyyx.action.bll.lottery.template.validate.UserScoreMeetsConditionValidateBLL;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.bll.userinfo.IUserInfoBLL;
import cn.gyyx.action.bll.userinfo.impl.UserInfoBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.lottery.ILotterySendPrizesService;
import cn.gyyx.action.service.lottery.impl.LotterySendPrizesToGame;
import cn.gyyx.distribute.lock.DistributedLock;

public class EveryPrizesOnlyWinOnceLotteryService extends DefaultLotteryService {

	private ILotteryValidateBLL lotteryValidateBLL = null;
	private ILotteryBLL lottery = null;
	private IActionQualificationBLL qualificationBLL = null;
	private IActionPrizesAddressBLL prizesAddressBLL= null;
	private ILotteryPrizesBLL lotteryPrizesBLL = null;
	private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
	
	@Override
	public ResultBean<ActionPrizesAddressOfLotteryVO> getPrizes(int activityId, String userId, String ip) {
		ResultBean<ActionPrizesAddressOfLotteryVO> result = new ResultBean<ActionPrizesAddressOfLotteryVO>();
		result.setIsSuccess(false);
		
		logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->start.");
		
		// 防止用户重复提交
		String key = activityId + "-" + userId + "-" + this.getClass().getName();
		try (DistributedLock lock = new DistributedLock(key)) {
			lock.weakLock(30, 0);
			
			logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->activityId=" + activityId +";userId=" + userId + ";ip=" + ip);
			
			LotteryValidateVO validateParams = new LotteryValidateVO();
			validateParams.setActivityType(ActivityTypeEnums.Lottery.toString());
			validateParams.setActivityId(activityId);
			validateParams.setUserId(userId);
			
			// 是否在活动期间内
			int hdState = hdConfigBLL.getState(activityId);
			if (1 != hdState) {
				logger.info("ActionExchangeServiceImpl->exchange->activation is not in progress.");
				result.setStateCode(hdState);
				result.setMessage(hdConfigBLL.getMessage());
				return result;
			}
			
			// 获得抽奖积分
			IHdConfigBLL configBLL = new DefaultHdConfigBLL();
			int score = configBLL.getLotteryScore(activityId);
			if (score < 1) {
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->config is not exsits.");
				result.setMessage("活动参数不能为空！");
				return result;
			}
			
			// 用户积分是否够抽奖
			lotteryValidateBLL = new UserScoreMeetsConditionValidateBLL();
			if (false ==lotteryValidateBLL.validate(validateParams)) {
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->user score is not inadequate.");
				result.setMessage(lotteryValidateBLL.getMessage());
				return result;
			}
			
			lottery = new LotteryBLLImpl(); 
			
			// 获得奖品
			IActionPrizeBLL actionPrizeBLL = new ActionPrizeBLLImpl();
			List<ActionPrizePO> prizesList = actionPrizeBLL.getDataList(activityId);
			if (null == prizesList || prizesList.size() < 1) {
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->prizesList is null.");
				result.setMessage("奖品列表不能为空！");
				return result;
			}
			lottery.setPrizesList(prizesList);
			logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->prizesList=" + JSON.toJSONString(prizesList));
			
			
			// 获得奖品概率
			ILotteryProbabilityBLL lotteryProbabilityBLL = new LotteryProbabilityBLLImpl();
			List<ActionLotteryChancePO> lotteryChanceList = lotteryProbabilityBLL.getDataList(activityId);
			if (null == lotteryChanceList || lotteryChanceList.size() < 1) {
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->lotteryChanceList is null.");
				result.setMessage("奖品概率不能为空！");
				return result;
			}
			lottery.setProbability(lotteryChanceList);
			logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->lotteryChanceList=" + JSON.toJSONString(lotteryChanceList));
			
			// 抽奖
			ActionPrizePO prizes = lottery.getPrizes();
			logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->prizes=" + JSON.toJSONString(prizes));
			if (null != prizes && null != prizes.getIsAvailable() && 1 == prizes.getIsAvailable()) {  // 中了有效奖品，（一般情况下，除了谢谢参与都是有效奖品）
				// 设置参数
				validateParams.setPrizeType(prizes.getIsReal());
				validateParams.setPrizeName(prizes.getChinese());
				
				lotteryValidateBLL = new EachPrizesOnlyWinOnceValidateBLL();
				if (false == lotteryValidateBLL.validate(validateParams)) {
					// 该奖品中过一次，则返回谢谢参与
					prizes = this.getThankYouPrizes(activityId, prizesList);
				}
			}
			else {
				prizes = this.getThankYouPrizes(activityId, prizesList);
			}
			
			SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
			
			try {
				// 扣除积分
				qualificationBLL = new ActionQualificationBLLImpl();
				if (qualificationBLL.minusLotteryScore(activityId, userId, score, OperateScoreEnums.Lottery.toString(), ip, session) < 1) {
					logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->update user score faild.");
					result.setMessage("更新用户积分失败！");
					return result;
				}
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->update user score success.");
				
				// 扣除奖品数量
				lotteryPrizesBLL = new LotteryPrizesBLLImpl();
				// 扣除奖品数量，除了谢谢参与
				if (!prizes.getIsReal().equalsIgnoreCase(PrizesTypeEnums.ThankYou.toString()) &&
						lotteryPrizesBLL.putPrizesNumber(activityId, prizes.getCode(), session) < 1) {
					logger.info("EveryPrizesOnlyWinOnceLotteryService->putPrizesNumber->update prizes number faild.");
					result.setMessage("更新用户积分失败！");
					return result;
				}
				logger.info("EveryPrizesOnlyWinOnceLotteryService->putPrizesNumber->update prizes number success.");
				
				// 发放奖品到游戏中
				if (null != prizes && prizes.getIsReal().equalsIgnoreCase(PrizesTypeEnums.GamePrizes.toString())) {
					if (!StringUtils.isEmpty(prizes.getServicePrizesCode())) {
						ILotterySendPrizesService sendPrizes = new LotterySendPrizesToGame();
						
						if (false == sendPrizes.pushPrizes(userId, prizes.getServicePrizesCode())) {
							logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->send prizes to game faild.");
							// 发生异常，改为谢谢参与
							prizes = this.getThankYouPrizes(activityId, prizesList);
						}
					}
				}
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->send prizes to game success.");
				
				// 虚拟物品
				String cardCode = StringUtils.EMPTY;
				if (null != prizes && prizes.getIsReal().equalsIgnoreCase(PrizesTypeEnums.VirtualPrizes.toString())) {
					NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
					// 已使用的虚拟物品为1，0为未使用
					cardCode = userLotteryBll.getCardCode(activityId, prizes.getEnglish(), userId.hashCode(), session);
					logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->VirtualPrizes=" + cardCode);
				}
				
				// 记录抽奖结果
				IActionLotteryLogBLL logBLL = new ActionLotteryLogBLLImpl();
				logBLL.putData(this.getActionLotteryLogPO(ActivityTypeEnums.Lottery.toString(), userId, cardCode, ip, prizes), session);
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->IActionLotteryLogBLL success.");
				
				session.commit();
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->session commit.");
				
				// 查询填写地址
				prizesAddressBLL = new ActionPrizesAddressBLLImpl();
				ActionPrizesAddressPO addressResult = prizesAddressBLL.get(activityId, userId, ActivityTypeEnums.Lottery.toString());
				logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->addressResult=" + JSON.toJSONString(addressResult));
				
				ActionPrizesAddressOfLotteryVO lotteryVO = null;
				if (null != addressResult) {
					logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->addressResult=" + JSON.toJSONString(addressResult));
					lotteryVO = JSON.parseObject(JSON.toJSONString(addressResult), ActionPrizesAddressOfLotteryVO.class);
				}
				else {
					lotteryVO = new ActionPrizesAddressOfLotteryVO();
				}
				
				lotteryVO.setPrizeType(prizes.getIsReal());
				lotteryVO.setPrizeCode(prizes.getCode());
				lotteryVO.setPrizeName(prizes.getChinese());
				lotteryVO.setPrizeNum(prizes.getNum());
				lotteryVO.setCardCode(cardCode);
				
				result.setData(lotteryVO);
				result.setIsSuccess(true);
			}
			catch(Exception sqle) {
				if (session != null) session.rollback();
				logger.error("EveryPrizesOnlyWinOnceLotteryService->getPrizes->Cause=" + sqle.getCause());
				logger.error("EveryPrizesOnlyWinOnceLotteryService->getPrizes->Message=" + sqle.getMessage());
				logger.error("EveryPrizesOnlyWinOnceLotteryService->getPrizes->StackTrace=" + sqle.getStackTrace());
			}
			finally {
				if (session != null) session.close();
			}
		}
		catch(Exception e) {
			logger.error("EveryPrizesOnlyWinOnceLotteryService->getPrizes->lock->Cause=" + e.getCause());
			logger.error("EveryPrizesOnlyWinOnceLotteryService->getPrizes->lock->Message=" + e.getMessage());
			logger.error("EveryPrizesOnlyWinOnceLotteryService->getPrizes->lock->StackTrace=" + e.getStackTrace());
		}
		
		logger.info("EveryPrizesOnlyWinOnceLotteryService->getPrizes->result=" + JSON.toJSONString(result));
		
		return result;
	}
}
