/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午12:45:56
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;

public class NewPrizeAndOrderDao {

	/**
	 * 得到奖品名次对应信息的dao
	 */
	public List<OrderAndPrizeBean> getPrizeInfo(int actionCode,
			SqlSession sqlSession) {
		// TODO Auto-generated method stub
		IPrizeAndOrderNew iPrizeAndOrder = sqlSession
				.getMapper(IPrizeAndOrderNew.class);
		return iPrizeAndOrder.getPrizeInfo(actionCode);

	}
	
	

}
