/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年12月11日下午1:37:13
 * 版本号：v1.0
 * 本类主要用途叙述：中奖的错误信息
 */

package cn.gyyx.action.service.newLottery;

import cn.gyyx.distribute.lock.DLockException;

public class LotteryException extends Exception {
	private static final long serialVersionUID = -7681279223486923701L;

	public static enum LotteryExpType {
		/**
		 * 储存日志信息错误
		 */
		PRESENT_LOG_ERROR,
		/**
		 * 获取卡号错误
		 */
		CARD_ERROE,
		/**
		 * 没有当前抽奖类型
		 */
		TYPE_NULL_ERROR,
		/**
		 * 活动基本信息获取失败
		 */
		CONTROPARM_ERROR,
		/**
		 * 
		 * 获取奖品信息错误
		 */
		LISTPRIZE_ERROR,

		/***
		 * 获取奖品与概率对应信息出错
		 */
		CHANCE_PRIZE_ERROR,

		/***
		 * 物品限定数量的错误
		 */
		PRIZE_TOPNUM_ERROR,
		/**
		 * 机会抽奖主体产生错误
		 */
		LOTTERY_CHANCE_ERROR,
		/***
		 * 奖品与名次对应信息获取错误
		 */
		ORDER_PRIZE_ERROR,

		/***
		 * 
		 */
		LOTTERY_ORDER_ERROR
	}

	private final LotteryExpType type;

	/**
	 * 叙述:构造一个DLockException异常<br />
	 * 
	 * @param type
	 *            异常具体类型
	 * @param message
	 *            异常信息
	 * @see DLockException
	 */
	public LotteryException(LotteryExpType type, String message) {
		super(message);
		this.type = type;
	}

	/**
	 * 叙述:获得具体的异常类型<br />
	 * 
	 * @return ExpType 具体异常类型(enum)
	 */
	public LotteryExpType getType() {
		return type;
	}

}
