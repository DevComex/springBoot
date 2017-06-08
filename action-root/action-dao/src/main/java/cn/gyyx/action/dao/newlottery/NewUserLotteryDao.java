/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月5日下午1:35:48
 * 版本号：v1.0
 * 本类主要用途叙述：中将配置的Dao层
 */

package cn.gyyx.action.dao.newlottery;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

public class NewUserLotteryDao {

	/**
	 * 抽奖获得名次的重载
	 */
	public void getOrderByUserCode(HashMap<String, Integer> map,
			SqlSession sqlSession) {
		// TODO Auto-generated method stub

		IUserLotteryByOrderNew iUserLotteryByOrder = sqlSession
				.getMapper(IUserLotteryByOrderNew.class);
		iUserLotteryByOrder.getOrderByUserCode(map);

	}

}
