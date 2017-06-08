/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：LhzsGoldBack
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年04月13日
 * @版本号：
 * @本类主要用途描述：玩家充值记录调用接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.lhzsgoldback;

import cn.gyyx.action.beans.lhzsgoldback.RechangeBean;

public interface IRechangeMapper {

	/**
	 * @Title findByAccount
	 * @Description 根据用户账号查询用户充值记录
	 * @author fanjiaqi
	 * @param account
	 * @return
	 */
	public RechangeBean findByAccount(String account);
}
