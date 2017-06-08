/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月4日下午1:56:25
 * 版本号：v1.0
 * 本类主要用途叙述：中奖处理
 */

package cn.gyyx.action.service.lottery;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LotteryConfig {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryConfig.class);
	private UserLotteryBll userLotteryBll = new UserLotteryBll();

	/**
	 * 得到转盘抽奖的信息
	 * 
	 * @param <T>
	 * 
	 * @param userCode
	 * @param list
	 * @param contrParm
	 * @return
	 */
	public <T> ResultBean<UserLotteryBean> config(int userCode,
			ContrParmBean contrParm, List<T> listInformation, String type,
			List<PrizeBean> listPrize, PrizeBean noPrize) {
		logger.debug("listPrize", listPrize);
		logger.debug("userCode", userCode);
		logger.debug("type", type);
		logger.debug("listInformation", listInformation);
		logger.debug("contrParm", contrParm);
		logger.debug("PrizeBean", noPrize);
		ResultBean<UserLotteryBean> resultLotterytInfo = new ResultBean<UserLotteryBean>();
		// 中奖的实现接口
		ILotteryMethod iLotteryMethod;
		// 如果是按照顺序中将
		if (type.equals("byOrder")) {
			iLotteryMethod = new LotteryByOrder();
		}
		// 按照概率中奖
		else if (type.equals("byChance")) {
			iLotteryMethod = new LotteryByChance();
		}
		// 没有该抽奖方式
		else {
			resultLotterytInfo.setIsSuccess(false);
			resultLotterytInfo.setMessage("没有有效的抽奖方式");
			logger.debug("resultLotterytInfo", resultLotterytInfo);
			return resultLotterytInfo;
		}
		
		// 得到中奖信息
		resultLotterytInfo = iLotteryMethod.getPrize(userCode, contrParm, listInformation);
		logger.debug("resultLotterytInfo", resultLotterytInfo);

		// 如果中得了奖
		if (resultLotterytInfo.getIsSuccess()) {
			if (listPrize != null) {
				for (int i = 0; i < listPrize.size(); i++) {
					// 找到当前所中的奖品信息
					if (resultLotterytInfo.getData().getPrizeCode() == listPrize.get(i).getCode()) {
						resultLotterytInfo.getData().setPrizeChinese(listPrize.get(i).getChinese());// 设定中文名
						resultLotterytInfo.getData().setPrizeEnglish(listPrize.get(i).getEnglish());// 设定英文名
						resultLotterytInfo.getData().setConfigCode(listPrize.get(i).getNum());// 设定前台所用的数
						resultLotterytInfo.getData().setIsReal(listPrize.get(i).getIsReal());
						resultLotterytInfo.setMessage(listPrize.get(i).getChinese());
						resultLotterytInfo.setIsSuccess(true);
						
						// 如果是按概率抽奖的方法
						if (type.equals("byChance")) {
							List<ChancePrizeBean> list2 = new ArrayList<ChancePrizeBean>();
							// 站换城概率奖品配置信息
							if (listInformation.size() > 0) {
								for (int j = 0; j < listInformation.size(); j++) {
									list2.add((ChancePrizeBean) listInformation
											.get(j));
								}
							}
							
							// 得到当前奖品的限制信息
							for (int j = 0; j < list2.size(); j++) {
								if (list2.get(j).getPrizeCode() == resultLotterytInfo.getData().getPrizeCode()) {
									// 如果是有限定数量的
									if (list2.get(j).getNumber() != 0) {
										// 得到已发放奖品数量
										int num = userLotteryBll.gettNumOfPrize(contrParm.getCode(), resultLotterytInfo.getData().getPrizeChinese());
										
										// 查过了限定数量
										if (num >= list2.get(j).getNumber()) {
											// 进行武将判断
											if (noPrize != null) {
												resultLotterytInfo.setData(new UserLotteryBean());
												resultLotterytInfo.getData().setPrizeChinese(noPrize.getChinese());// 设定中文名
												resultLotterytInfo.getData().setPrizeEnglish(noPrize.getEnglish());// 设定英文名
												resultLotterytInfo.getData().setConfigCode(noPrize.getNum());// 设定前台所用的数
												resultLotterytInfo.getData().setIsReal(noPrize.getIsReal());
												resultLotterytInfo.setMessage(noPrize.getChinese());
												resultLotterytInfo.setIsSuccess(true);
											} 
											else {
												UserLotteryBean userLotteryBean = new UserLotteryBean();
												resultLotterytInfo.setData(userLotteryBean);
												resultLotterytInfo.setIsSuccess(false);
											}
										}
									}
								}
							}
						}

						break;
						// 如果是概率的方法

					}

				}

			}
		} else {
			// 给未中奖用户配置的信息不为空
			if (noPrize != null) {
				resultLotterytInfo.setData(new UserLotteryBean());
				resultLotterytInfo.getData().setPrizeChinese(
						noPrize.getChinese());// 设定中文名
				resultLotterytInfo.getData().setPrizeEnglish(
						noPrize.getEnglish());// 设定英文名
				resultLotterytInfo.getData().setConfigCode(noPrize.getNum());// 设定前台所用的数
				resultLotterytInfo.getData().setIsReal(noPrize.getIsReal());
				resultLotterytInfo.setMessage(noPrize.getChinese());
				resultLotterytInfo.setIsSuccess(true);

			} else {
				UserLotteryBean userLotteryBean = new UserLotteryBean();
				userLotteryBean.setConfigCode(7);
				resultLotterytInfo.setData(userLotteryBean);
				resultLotterytInfo.setIsSuccess(false);
			}
		}
		logger.debug("resultLotterytInfo config", resultLotterytInfo);
		return resultLotterytInfo;

	}
}
