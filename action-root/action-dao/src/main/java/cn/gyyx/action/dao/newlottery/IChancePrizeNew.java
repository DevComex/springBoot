/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月9日下午3:40:29
 * 版本号：v1.0
 * 本类主要用途叙述：得到概率抽奖奖品的配置信息
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;

public interface IChancePrizeNew {
	/**
	 * 得到按概率抽奖的中奖配置信息 数据来源 action_lottery_chance_tb
	 * 
	 * @return
	 */
	public List<ChancePrizeBean> getChancePrize(
			@Param("actionCode") int actionCode);

        /**
         * 
          * <p>
          *    方法说明
          * </p>
          *
          * @action
          *     减少奖品的限制
          *
          * @param prizeCode
          * @param prizePoolCode
          * @param actionCode void
         */
        public void reduceTimeChancePrize(@Param("prizeCode") int prizeCode,@Param("prizePoolCode") int prizePoolCode,
                @Param("actionCode") int actionCode);

	public Integer updateChancePrizeBean(ChancePrizeBean chancePrizeBean);

	public ChancePrizeBean getChancePrizeByprizeCode(Integer prizeCode);
}
