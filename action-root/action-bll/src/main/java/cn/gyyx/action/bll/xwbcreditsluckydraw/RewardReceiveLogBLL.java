/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：签到奖励领取记录业务处理类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.RewardReceiveLogBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.RewardReceiveLogDAO;

public class RewardReceiveLogBLL{
	RewardReceiveLogDAO rewardReceiveLogDAO = new RewardReceiveLogDAO();
	private TimeBLL timeBLL = new TimeBLL();
	/**
	 * 增加签到奖励领取记录
	 * @param rewardReceiveLogBean
	 */
	public boolean addRewardReceiveLog(RewardReceiveLogBean rewardReceiveLogBean){
		return rewardReceiveLogDAO.insertRewardReceiveLog(rewardReceiveLogBean);
	}
	/**
	 * 查询当月本人领取奖励记录
	 * @param rewardReceiveLogBean
	 * @return
	 */
	public List<RewardReceiveLogBean> getRewardReceiveLog(RewardReceiveLogBean rewardReceiveLogBean){
		
		return rewardReceiveLogDAO.selectRewardReceiveLog(rewardReceiveLogBean);
	}
	
}
