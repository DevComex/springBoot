/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月4日下午3:13:21
 * 版本号：v1.0
 * 本类主要用途叙述：通过概率中奖
 */

package cn.gyyx.action.service.newLottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;

public class LotteryByChanceNew implements INewLotteryMethod {

	/**
	 * 按照概率抽奖
	 */

	@Override
	public <T> ResultBean<UserLotteryBean> getPrize(int userCode,
			ContrParmBean contrParm, List<T> list,SqlSession sqlSession) {
		// 将list转化为概率奖品对应表
		List<ChancePrizeBean> list2 = new ArrayList<>();
		ResultBean<UserLotteryBean> resultBean = new ResultBean<>();
		UserLotteryBean userLotteryBean = new UserLotteryBean();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				list2.add((ChancePrizeBean) list.get(i));
			}

		}
		// 获得当前奖品的顺序号码
		int index = lottery(list2);
		if (index != -1) {
			for (int i = 0; i < list2.size(); i++)
				// 是这个顺序f
				if (i == index) {
					// 中奖信息
					userLotteryBean.setPrizeCode(list2.get(index)
							.getPrizeCode());
					userLotteryBean.setCode(list2.get(index).getCode());
					// 消息
					resultBean.setData(userLotteryBean);
					resultBean.setMessage("获奖成功");
					resultBean.setIsSuccess(true);
					break;
				} else {
					resultBean.setMessage("没有配置奖品");
					resultBean.setIsSuccess(false);
				}

		}

		return resultBean;
	}

	/**
	 * 按概率抽奖
	 * 
	 * @param list
	 * @return 位置
	 */
	public int lottery(List<ChancePrizeBean> list) {
		if (list == null || list.isEmpty()) {
			return -1;
		}

		int size = list.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (int i = 0; i < list.size(); i++) {
			sumRate += list.get(i).getProbability();
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<>(size);
		Double tempSumRate = 0d;
		for (int i = 0; i < list.size(); i++) {
			tempSumRate += list.get(i).getProbability();
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}
}
