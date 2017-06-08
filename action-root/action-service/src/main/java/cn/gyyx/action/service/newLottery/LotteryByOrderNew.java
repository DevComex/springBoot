package cn.gyyx.action.service.newLottery;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LotteryByOrderNew implements INewLotteryMethod {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryByOrderNew.class);
	private NewUserLotteryBll userLotteryBll = new NewUserLotteryBll();

	
	/**
	 * 得到用户所中的奖
	 */
	@Override
	public <T> ResultBean<UserLotteryBean> getPrize(int userCode,
			ContrParmBean contrParm, List<T> list, SqlSession sqlSession) {
		logger.debug("userCode", userCode);
		logger.debug("contrParm", contrParm);
		logger.debug("list", list);
		UserLotteryBean userLotteryBean = new UserLotteryBean();
		List<OrderAndPrizeBean> list2 = new ArrayList<>();
		ResultBean<UserLotteryBean> resultLotterytInfo = new ResultBean<>();
		ResultBean<Integer> resultOrder = new ResultBean<>();
		// 将list转化为名次奖品对应表
		if (list != null) {
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					list2.add((OrderAndPrizeBean) list.get(i));
				}

			}
			logger.debug("List<OrderAndPrizeBean>", list2);
			resultLotterytInfo.setIsSuccess(false);
			// 以上都是扯淡，没必要深究
			// 从这开始才是抽奖的主体
			resultOrder = userLotteryBll.getOrderByUserCode(userCode,
					contrParm.getCode(), sqlSession);
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
