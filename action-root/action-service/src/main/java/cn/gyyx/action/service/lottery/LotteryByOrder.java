package cn.gyyx.action.service.lottery;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.lottery.LotteryByOrderBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LotteryByOrder implements ILotteryMethod {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryByOrder.class);

	/**
	 * 得到用户所中的奖
	 */
	public <T> ResultBean<UserLotteryBean> getPrize(int userCode,
			ContrParmBean contrParm, List<T> list) {
		logger.debug("userCode", userCode);
		logger.debug("contrParm", contrParm);
		logger.debug("list", list);
		// TODO Auto-generated method stub
		UserLotteryBean userLotteryBean = new UserLotteryBean();
		LotteryByOrderBll lotteryByOrderBll = new LotteryByOrderBll();
		List<OrderAndPrizeBean> list2 = new ArrayList<OrderAndPrizeBean>();
		ResultBean<UserLotteryBean> resultLotterytInfo = new ResultBean<UserLotteryBean>();
		ResultBean<Integer> resultOrder = new ResultBean<Integer>();
		// 将list转化为名次奖品对应表
		if (list != null) {
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					list2.add((OrderAndPrizeBean) list.get(i));
				}

			}
			logger.debug("List<OrderAndPrizeBean>", list2);
			resultLotterytInfo.setIsSuccess(false);

			resultOrder = lotteryByOrderBll.getOrderByUserCode(userCode,
					contrParm.getCode());
			resultLotterytInfo.setMessage(resultOrder.getMessage());
			// 如果名次获得是成功的
			if (resultOrder.getIsSuccess()) {
				// 查找是否存在中奖信息
				for (int i = 0; i < list2.size(); i++) {
					// 如果该名次有奖
					if (resultOrder.getData() == list2.get(i).getOrder()) {
						// 如果奖品的信息不是空
						userLotteryBean.setPrizeCode(list2.get(i)
								.getPrizeCode());
						userLotteryBean.setUserCode(userCode);
						resultLotterytInfo.setIsSuccess(true);
						resultLotterytInfo.setData(userLotteryBean);
						break;

					}
					// 没有抽中奖
					else {
						resultLotterytInfo.setIsSuccess(false);
						resultLotterytInfo.setMessage("谢谢参与");

					}

				}
			}
		}
		logger.debug("resultLotterytInfo", resultLotterytInfo);
		return resultLotterytInfo;
	}

}
