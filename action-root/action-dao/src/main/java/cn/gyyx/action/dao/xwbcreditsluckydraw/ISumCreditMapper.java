/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月10日 上午10:49:38
 * @版本号：
 * @本类主要用途描述：总积分接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SumCreditBean;

public interface ISumCreditMapper {

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getSumCredit
	 * @Description: TODO 根据账号获取用户总积分
	 * @param account
	 * @return int
	 */
	public int getSumCredit(String account);

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: addSumCredit
	 * @Description: TODO 添加总积分记录新用户
	 * @param sumCredit
	 * @return int
	 */
	public int addSumCredit(SumCreditBean sumCredit);

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: setSumCredit
	 * @Description: TODO 修改积分数量
	 * @param sumCredit
	 * @return int
	 */
	public int setSumCredit(SumCreditBean sumCredit);
	public SumCreditBean getCreditByAccount(String account);
}
