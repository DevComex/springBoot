/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月10日 上午11:26:33
 * @版本号：
 * @本类主要用途描述：积分记录接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;

public interface ICreditsMapper {

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: addCredits
	 * @Description: TODO 添加积分记录
	 * @param credits
	 * @return int
	 */
	public int addCredits(CreditsBean credits);

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getCredits
	 * @Description: TODO 查询所有积分记录
	 * @param page
	 * @return List<CreditsBean>
	 */
	public List<CreditsBean> getCredits(CreditsBean credit);

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getCreditsNum
	 * @Description: TODO 获取所有记录数
	 * @param credit
	 * @return int
	 */
	public int getCreditsNum(CreditsBean credit);

}
