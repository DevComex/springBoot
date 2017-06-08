/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家兑换光宇币日志记录数据访问层Mapper
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.online;

import cn.gyyx.action.beans.online.OnlineChangeLogBean;

public interface IOnlineChangeLogMapper {
	/**
	 * 按账号查找用户申请记录
	 * @param account
	 * @return
	 */
	public OnlineChangeLogBean selectonlineChangeLog(String account);
	/**
	 * 增加用户申请记录
	 * @param onlineChangeLogBean
	 * @return
	 */
	public int addOnlineChangeLog(OnlineChangeLogBean onlineChangeLogBean);
}
