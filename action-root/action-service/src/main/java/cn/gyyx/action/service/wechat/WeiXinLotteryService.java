/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:39:17
 * 版本号：v1.181
 * 本类主要用途叙述：中奖的service,可以直接调用只里面的方法去进行抽奖活动
 */

package cn.gyyx.action.service.wechat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.lottery.LotteryMemcacheBll;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.wechat.WeiXinRechargeBll;
import cn.gyyx.action.service.lottery.LotteryConfig;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WeiXinLotteryService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WeiXinLotteryService.class);

	/**
	 * 直接与数据库交互的抽奖
	 * 
	 * @param actionCode
	 * @param userCode
	 * @param type
	 * @param userName
	 * @param server
	 * @param serverCode
	 * @param ip
	 *            这个是按照名次抽奖
	 * @return
	 */
	public ResultBean<UserLotteryBean> lotteryByDataBase(int actionCode,
			int userCode, String type, String userName, String server,
			int serverCode, String ip, PrizeBean noPrize) {
		logger.debug("actionCode", actionCode);
		logger.debug("userCode", userCode);
		logger.debug("type", type);
		logger.debug("userName", userName);
		logger.debug("server", server);
		logger.debug("serverCode", serverCode);
		logger.debug("ip", ip);
		logger.debug("PrizeBean", noPrize);
		UserLotteryBll userLotteryBll = new UserLotteryBll();
		// 得到活动信息
		ContrParmBean controParm = userLotteryBll.getContrParm(actionCode);
		logger.debug("controParm", controParm);
		// 得到奖品的信息
		List<PrizeBean> listPrize = userLotteryBll.getPrize(actionCode);
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		LotteryConfig lotteryConfig = new LotteryConfig();
		// 根据方法要传不同的
		if (type.equals("byOrder")) {
			// 得到名次与奖品的对应
			List<OrderAndPrizeBean> listOrderPrizeBeans = userLotteryBll
					.getPrizeAndOrderInfo(actionCode);
			logger.debug("listOrderPrizeBeans", listOrderPrizeBeans);

			// 得到中奖的信息
			resultBean = lotteryConfig.config(userCode, controParm,
					listOrderPrizeBeans, type, listPrize, noPrize);
			logger.debug("resultBean", resultBean);
		} else if (type.equals("byChance")) {
			// 得到概率配置信息
			List<ChancePrizeBean> listInformation = userLotteryBll
					.getChancePrize(actionCode);
			// 得到中奖的信息
			resultBean = lotteryConfig.config(userCode, controParm,
					listInformation, type, listPrize, noPrize);
			logger.debug("resultBean", resultBean);
		}
		UserInfoBean userInfo = new UserInfoBean();
		// 如果用户中奖了
		if (resultBean.getIsSuccess()
				&& !resultBean.getData().getPrizeChinese().equals("谢谢参与")
				&& !resultBean.getMessage().equals("谢谢参与")) {
			if (resultBean.getData().getIsReal().equals("card")) {
				WeiXinRechargeBll rechargeBll = new WeiXinRechargeBll();

				String cardCodeString = rechargeBll.getCardCode(actionCode,
						resultBean.getData().getPrizeEnglish(), userName);
				resultBean.setMessage(cardCodeString);
				resultBean.getData().setPrizeChinese(
						resultBean.getData().getPrizeChinese() + ":"
								+ cardCodeString);
			}

			userInfo.setAccount(userName);
			userInfo.setActionCode(actionCode);
			userInfo.setGameCode(controParm.getGameId());
			userInfo.setIp(ip);
			userInfo.setPresentName(resultBean.getData().getPrizeChinese());
			userInfo.setPresentType(resultBean.getData().getIsReal());
			userInfo.setServerCode(serverCode);
			userInfo.setServerName(server);
			userInfo.setSource(controParm.getActivityName());
			userInfo.setSourceCode(controParm.getCode());
			userInfo.setTime(new Date());
			// 更新用户中奖信息
			userLotteryBll.addUserLotteryInfo(userInfo);
		}
		logger.debug("lotteryByDataBase", resultBean);
		return resultBean;

	}

	/**
	 * 
	 * @param actionCode
	 * @param userCode
	 * @param type
	 * @param userName
	 * @param server
	 * @param serverCode
	 * @param ip
	 * @return
	 */
	public ResultBean<UserLotteryBean> lotteryByMemcacheByOrder(int actionCode,
			int userCode, String type, String userName, String server,
			int serverCode, String ip, PrizeBean noPrize) {
		logger.debug("actionCode", actionCode);
		logger.debug("userCode", userCode);
		logger.debug("type", type);
		logger.debug("userName", userName);
		logger.debug("server", server);
		logger.debug("serverCode", serverCode);
		logger.debug("ip", ip);
		logger.debug("PrizeBean", noPrize);
		UserLotteryBll userLotteryBll = new UserLotteryBll();
		LotteryConfig lotteryConfig = new LotteryConfig();
		ContrParmBean controParm = new ContrParmBean();
		List<PrizeBean> listPrizeBean = new ArrayList<PrizeBean>();
		List<OrderAndPrizeBean> listOrderPrizeBean = new ArrayList<OrderAndPrizeBean>();
		LotteryMemcacheBll lotteryMemcacheBll = new LotteryMemcacheBll();
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		// 得到活动信息
		// 若果memcache中活动信息不为空
		if (lotteryMemcacheBll.getControParaMem(actionCode) != null) {
			controParm = lotteryMemcacheBll.getControParaMem(actionCode);
			logger.debug("controParm", controParm);
		}
		// 如果不存在就加上
		else {
			controParm = userLotteryBll.getContrParm(actionCode);
			logger.debug("controParm", controParm);
			lotteryMemcacheBll.setContrParmMem(controParm, actionCode);

		}

		// 得到活动信息
		// 若果memcache中活动信息不为空
		if (lotteryMemcacheBll.getPrizeMem(actionCode) != null) {
			listPrizeBean = lotteryMemcacheBll.getPrizeMem(actionCode);
			logger.debug("listPrizeBean", listPrizeBean);
		}
		// 如果不存在就加上
		else {
			listPrizeBean = userLotteryBll.getPrize(actionCode);
			logger.debug("listPrizeBean", listPrizeBean);
			lotteryMemcacheBll.setPrizeBeanMem(listPrizeBean, actionCode);

		}

		if (type.equals("byOrder")) {
			// 若果名次的对应信息
			// 若果存在
			if (lotteryMemcacheBll.getPrizeAndOrderInfoMem(actionCode) != null) {
				listOrderPrizeBean = lotteryMemcacheBll
						.getPrizeAndOrderInfoMem(actionCode);
				logger.debug("listOrderPrizeBean", listOrderPrizeBean);
			}
			// 如果不存在就加上
			else {
				listOrderPrizeBean = userLotteryBll
						.getPrizeAndOrderInfo(actionCode);
				lotteryMemcacheBll.setPrizeAndOrderInfonMem(listOrderPrizeBean,
						actionCode);
				logger.debug("listOrderPrizeBean", listOrderPrizeBean);
			}
			// 得到中奖的信息
			resultBean = lotteryConfig.config(userCode, controParm,
					listOrderPrizeBean, type, listPrizeBean, noPrize);
			logger.debug("resultBean", resultBean);
		} else if (type.equals("byChance")) {
			List<ChancePrizeBean> listInformation = new ArrayList<ChancePrizeBean>();
			if (lotteryMemcacheBll.getChancePrizeBeanMem(actionCode) != null) {
				listInformation = lotteryMemcacheBll
						.getChancePrizeBeanMem(actionCode);
				logger.debug("listInformation", listOrderPrizeBean);
			}
			// 如果不存在就加上
			else {
				listInformation = userLotteryBll.getChancePrize(actionCode);
				lotteryMemcacheBll.setPrizeChanceBeanMem(listInformation,
						actionCode);
				logger.debug("lotteryMemcacheBll", listOrderPrizeBean);
			}
			resultBean = lotteryConfig.config(userCode, controParm,
					listInformation, type, listPrizeBean, noPrize);
			logger.debug("resultBean", resultBean);

		}
		// 如果用户中奖了
		if (resultBean.getIsSuccess()
				&& !resultBean.getData().getPrizeChinese().equals("谢谢参与")
				&& !resultBean.getMessage().equals("谢谢参与")) {
			UserInfoBean userInfo = new UserInfoBean();

			userInfo.setAccount(userName);
			userInfo.setActionCode(actionCode);
			userInfo.setGameCode(controParm.getGameId());
			userInfo.setIp(ip);
			userInfo.setPresentName(resultBean.getData().getPrizeChinese());
			userInfo.setPresentType(resultBean.getData().getIsReal());
			userInfo.setServerCode(serverCode);
			userInfo.setServerName(server);
			userInfo.setSource(controParm.getActivityName());
			userInfo.setSourceCode(controParm.getCode());
			userInfo.setTime(new Date());
			// 更新用户中奖信息
			int infocode = userLotteryBll.addUserLotteryInfo(userInfo);
			logger.debug("infocode", infocode);
			// 如果中奖信息不等于空
			if (lotteryMemcacheBll.getUserLotteryMem(actionCode) != null) {
				List<UserInfoBean> listUserInfoBean = lotteryMemcacheBll
						.getUserLotteryMem(actionCode);
				logger.info("listUserInfoBean", listUserInfoBean);
				if (listUserInfoBean.size() > 0) {

					userInfo.setCode(listUserInfoBean.get(
							listUserInfoBean.size() - 1).getCode() + 1);
				} else {
					userInfo.setCode(1000);
				}
				List<UserInfoBean> arrayList = new ArrayList<UserInfoBean>(
						listUserInfoBean);
				// 更新用户中奖信息
				userInfo.setCode(infocode);
				logger.debug("userInfo", userInfo);
				arrayList.add(0, userInfo);
				lotteryMemcacheBll.setUserInfoBeanMem(arrayList, actionCode);
			}
			// 不存在
			else {
				List<UserInfoBean> listUserInfoBean = new ArrayList<UserInfoBean>();
				listUserInfoBean.add(userInfo);
				// 更新用户中奖信息
				userInfo.setCode(infocode);
				lotteryMemcacheBll.setUserInfoBeanMem(listUserInfoBean,
						actionCode);

			}
		}

		// 返回结果
		logger.debug("resultBean", resultBean);
		return resultBean;
	}

}
