/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月9日下午3:44:34
 * 版本号：v1.0
 * 本类主要用途叙述：按概率抽奖配置信息的Dao层
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;

public class NewChancePrizeDao {

	/**
	 * 得到按概率抽奖奖品配置信息
	 */
	public List<ChancePrizeBean> getChancePrize(int actionCode,
			SqlSession sqlSession) {

		IChancePrizeNew iChancePrize = sqlSession
				.getMapper(IChancePrizeNew.class);
		return iChancePrize.getChancePrize(actionCode);

	}

        /**
         * 
          * <p>
          *    方法说明
          * </p>
          *
          * @action
          *    减少抽奖次数
          *
          * @param actionCode
          * @param prizePoolCode
          * @param prizeCode
          * @param sqlSession void
         */
        public void reduceTimeChancePrize(int actionCode, int prizePoolCode, int prizeCode,
                        SqlSession sqlSession) {
                IChancePrizeNew iChancePrize = sqlSession
                                .getMapper(IChancePrizeNew.class);
                iChancePrize.reduceTimeChancePrize(prizeCode, prizePoolCode, actionCode);
        }

	/*
	 * public Integer updateChancePrizeBean(ChancePrizeBean chancePrizeBean){
	 * Integer result = null; SqlSession session = getSession(); try {
	 * IChancePrizeNew iChancePrize = session .getMapper(IChancePrizeNew.class);
	 * result = iChancePrize.updateChancePrizeBean(chancePrizeBean);
	 * session.commit(); } catch (Exception e) {
	 * 
	 * } finally { session.close(); } return result; } public ChancePrizeBean
	 * getChancePrizeByprizeCode(Integer prizeCode){ ChancePrizeBean result =
	 * null; SqlSession session = getSession(); try { IChancePrizeNew
	 * iChancePrize = session .getMapper(IChancePrizeNew.class); result =
	 * iChancePrize.getChancePrizeByprizeCode(prizeCode); } catch (Exception e)
	 * {
	 * 
	 * } finally { session.close(); } return result; }
	 */

}
