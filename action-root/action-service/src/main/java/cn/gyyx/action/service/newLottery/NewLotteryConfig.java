/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月4日下午1:56:25
 * 版本号：v1.0
 * 本类主要用途叙述：中奖处理
 */

package cn.gyyx.action.service.newLottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NewLotteryConfig {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NewLotteryConfig.class);

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
			List<PrizeBean> listPrize, PrizeBean noPrize, SqlSession sqlSession) {
		logger.debug("listPrize", listPrize);
		logger.debug("userCode", userCode);
		logger.debug("type", type);
		logger.debug("listInformation", listInformation);
		logger.debug("contrParm", contrParm);
		logger.debug("PrizeBean", noPrize);
		ResultBean<UserLotteryBean> resultLotterytInfo = new ResultBean<>();
		// 中奖的实现接口
		INewLotteryMethod iLotteryMethod;
		// 如果是按照顺序中将
		// 不同的方式实现不同的接口
		// 实现按顺序抽奖的接口
		if ("byOrder".equals(type)) {
			iLotteryMethod = new LotteryByOrderNew();
		}
		// 按照概率中奖
		// 实现按概率抽奖的接口
		else if ("byChance".equals(type)) {
			iLotteryMethod = new LotteryByChanceNew();
		}
		// 没有该抽奖方式
		else {
			resultLotterytInfo.setIsSuccess(false);
			resultLotterytInfo.setMessage("没有有效的抽奖方式");
			logger.debug("resultLotterytInfo", resultLotterytInfo);
			return resultLotterytInfo;
		}
		// 得到中奖信息
		// 深入抽奖从此处进入
		resultLotterytInfo = iLotteryMethod.getPrize(userCode, contrParm,
				listInformation, sqlSession);
		logger.debug("resultLotterytInfo", resultLotterytInfo);

		// 如果中得了奖
		if (resultLotterytInfo.getIsSuccess()) {
			if (listPrize != null) {
				for (int i = 0; i < listPrize.size(); i++) {
					// 找到当前所中的奖品信息
					if (resultLotterytInfo.getData().getPrizeCode() == listPrize
							.get(i).getCode()) {
						resultLotterytInfo.getData().setPrizeChinese(
								listPrize.get(i).getChinese());// 设定中文名
						resultLotterytInfo.getData().setPrizeEnglish(
								listPrize.get(i).getEnglish());// 设定英文名
						resultLotterytInfo.getData().setConfigCode(
								listPrize.get(i).getNum());// 设定前台所用的数
						resultLotterytInfo.getData().setIsReal(
								listPrize.get(i).getIsReal());
						resultLotterytInfo.getData().setIsAvailable(
								listPrize.get(i).getIsAvailable());
						resultLotterytInfo.setMessage(listPrize.get(i)
								.getChinese());
						resultLotterytInfo.getData().setUserCode(userCode);
						resultLotterytInfo.setIsSuccess(true);
					}
				}
			}
			// 如果没中奖
		} else {
			resultLotterytInfo.setIsSuccess(false);
			resultLotterytInfo.setMessage("谢谢参与");
			resultLotterytInfo.setData(null);

		}
		logger.debug("resultLotterytInfo config", resultLotterytInfo);
		return resultLotterytInfo;

	}
}
