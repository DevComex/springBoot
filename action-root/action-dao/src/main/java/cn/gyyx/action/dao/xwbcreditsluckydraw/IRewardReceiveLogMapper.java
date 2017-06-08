/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：签到奖励领取记录数据处理接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.RewardReceiveLogBean;

public interface IRewardReceiveLogMapper {
	/**
	 * 增加签到奖励领取记录
	 * @param rewardReceiveLogBean
	 */
	public void insertRewardReceiveLog(RewardReceiveLogBean rewardReceiveLogBean);
	/**
	 * 按条件查询奖励领取记录
	 * @param rewardReceiveLogBean
	 * @return
	 */
	public List<RewardReceiveLogBean> selectRewardReceiveLog(RewardReceiveLogBean rewardReceiveLogBean);
}
