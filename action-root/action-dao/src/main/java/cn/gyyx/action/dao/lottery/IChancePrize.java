/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月9日下午3:40:29
 * 版本号：v1.0
 * 本类主要用途叙述：得到概率抽奖奖品的配置信息
 */



package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;

public interface IChancePrize {
	/**
	 * 得到按概率抽奖的中奖配置信息
	 * @return
	 */
	public List<ChancePrizeBean> getChancePrize(@Param("actionCode") int actionCode);
	public Integer updateChancePrizeBean(ChancePrizeBean chancePrizeBean);
	public ChancePrizeBean getChancePrizeByprizeCode(Integer prizeCode);
	
	  /**
         * 
          * <p>
          *    方法说明
          * </p>
          *
          * @action
          *    根据奖品编号获取奖品的概率和数量
          *
          * @param prizeCode
          * @return ChancePrizeBean
         */
        public ChancePrizeBean getPrizeProbabilityAndNumberByPrizeCode(Integer prizeCode);
        
        /**
         * 
          * <p>
          *    方法说明
          * </p>
          *
          * @action
          *    根据奖品编号获取该奖品对应的所有奖池
          *
          * @param prizeCode
          * @return List<ChancePrizeBean>
         */
        public List<ChancePrizeBean> getChancePrizesByPrizeCode(@Param("prizeCode") int prizeCode);
	public Integer insertPrizeChange(ChancePrizeBean chancePrizeBean);
}
