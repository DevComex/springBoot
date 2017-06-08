
/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年12月9日上午10:51:50
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.service.wdxlsgpresent;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.bll.wechat.WeiXinRechargeBll;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.LotteryException.LotteryExpType;
import cn.gyyx.action.service.newLottery.NewLotteryConfig;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdXLSGlotteryService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdXLSGlotteryService.class);

	/**
	 * 直接与数据库交互的抽奖
	 * 
	 * @param actionCode
	 * @param userCode
	 * @param type
	 * @param openId
	 * @param server
	 * @param serverCode
	 * @param ip
	 * 
	 * @return
	 * @throws LotteryException
	 */
	public ResultBean<UserLotteryBean> lotteryByDataBase(int actionCode, String type, String openId, PrizeBean noPrize,
			SqlSession sqlSession) throws LotteryException {
		NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		// 日志
		logger.debug("actionCode{}", actionCode);
		logger.debug("type{}", type);
		logger.debug("openId{}", openId);
		// 开启一个session
		// 得到活动信息
		// 来自表 hd_config_tb
		// 配置活动的基本信息
		ContrParmBean controParm = new ContrParmBean();
		try {
			controParm = userLotteryBll.getContrParm(actionCode, sqlSession);
			logger.debug("controParm" + controParm.toString());

		} catch (Exception e) {
			// 抛出活动信息的错误
			logger.warn("controParm:获取活动信息异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			throw new LotteryException(LotteryExpType.CONTROPARM_ERROR, e.getMessage());
		}

		// 得到奖品的信息
		// 数据来自 表 action_prize_tb
		// 这是奖品信息的来源
		List<PrizeBean> listPrize = new ArrayList<>();
		try {
			listPrize = userLotteryBll.getPrize(actionCode, sqlSession);
			logger.debug("listPrize" + listPrize);

		} catch (Exception e) {
			logger.warn("userLotteryBll.getPrize:获取奖品信息异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			throw new LotteryException(LotteryExpType.LISTPRIZE_ERROR, e.getMessage());
		}

		NewLotteryConfig lotteryConfig = new NewLotteryConfig();
		// 判断用的是怎样的抽奖方法
		/**
		 * 下面是概率方式 byChance 需要配置概率表 action_lottery_chance_tb
		 * 
		 */
		if ("byChance".equals(type)) {
			// 得到概率配置信息
			// 信息来自于表 action_lottery_chance_tb
			List<ChancePrizeBean> listInformation = new ArrayList<>();
			try {
				listInformation = userLotteryBll.getChancePrize(actionCode, sqlSession);
			} catch (Exception e) {
				logger.warn("userLotteryBll.getChancePrize:获取概率配置信息异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
				// 抛出异常
				throw new LotteryException(LotteryExpType.CHANCE_PRIZE_ERROR, e.getMessage());
			}

			// 得到中奖的信息
			// 这就是抽奖的主体
			// 想知道奖品怎么来的，你就点进去
			try {
				resultBean = lotteryConfig.config(1, controParm, listInformation, type, listPrize, noPrize, sqlSession);
			} catch (Exception e) {
				logger.warn("lotteryConfig.config:抽奖出现异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
				// 抛出异常
				throw new LotteryException(LotteryExpType.LOTTERY_CHANCE_ERROR, e.getMessage());
			}

			// 奖品超过规定数量的处理
			try {
				resultBean = isNumTop(listInformation, resultBean, sqlSession);
			} catch (Exception e) {
				logger.error("isNumTop---error---:" + Throwables.getStackTraceAsString(e));
				// 抛出异常
				throw new LotteryException(LotteryExpType.PRIZE_TOPNUM_ERROR, e.getMessage());
			}

			if (resultBean.getIsSuccess()) {

				if (resultBean.getData().getIsReal().equals("card")) {
					WeiXinRechargeBll rechargeBll = new WeiXinRechargeBll();
					String cardCodeString = rechargeBll.getCardCode(actionCode, resultBean.getData().getPrizeEnglish(),
							openId,sqlSession);
					resultBean.setMessage(cardCodeString);
					resultBean.getData().setPrizeChinese(resultBean.getData().getPrizeChinese() + ":" + cardCodeString);
				}

			}

			return resultBean;
		} else {

			resultBean.setIsSuccess(false);
			resultBean.setMessage("抽奖类型错误");
			return resultBean;

		}

	}

	/****
	 * 判断是否达到最高数量
	 * 
	 * @param listInformation
	 *            prizeCode
	 * @return
	 */
	private ResultBean<UserLotteryBean> isNumTop(List<ChancePrizeBean> listInformation,
			ResultBean<UserLotteryBean> resultLottery, SqlSession sqlSession) {
		NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();
		ResultBean<UserLotteryBean> resultLotterytInfo = new ResultBean<>();
		// 查询到所中奖品的信息
		for (int j = 0; j < listInformation.size(); j++) {
			// 查找到当前的奖品信息
			if (listInformation.get(j).getPrizeCode() == resultLottery.getData().getPrizeCode()) {
				// 如果是有限定数量的
				if (listInformation.get(j).getNumber() != -1) {
					// 查过了限定数量
					if (listInformation.get(j).getNumber() <= 0) {
						resultLotterytInfo.setIsSuccess(false);
						resultLotterytInfo.setMessage("礼包已领完");
						resultLotterytInfo.setData(null);
						return resultLotterytInfo;
					} else {
						// 减去数量
						userLotteryBll.reduceTimeChancePrize(listInformation.get(j).getActionCode(),resultLottery.getData().getCode(),
								resultLottery.getData().getPrizeCode(), sqlSession);
						return resultLottery;
					}

				}
				break;
			}

		}
		return resultLottery;
	}

}