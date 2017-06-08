/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家元宝信息数据访问层Mapper
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.online;

import cn.gyyx.action.beans.online.OnlineChangeBean;

public interface IOnlineChangeMapper {
	/**
	 * 查找账号是否存在
	 * @param account
	 * @return
	 */
	public OnlineChangeBean selectAccount(String account);
}
