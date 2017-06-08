/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午12:43:43
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;

public interface IPrizeAndOrderNew {
	/**
	 * 得到奖品与名次的对应信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public List<OrderAndPrizeBean> getPrizeInfo(
			@Param("actionCode") int actionCode);

	/**
	 * 插入奖品配置
	 * */
	public void addActionConfig(OrderAndPrizeBean o);
}
