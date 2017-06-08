/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动账号相关接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtenyearfans.WdAccountInfoBean;

public interface IWdAccountInfoBean {
	public void insertWdAccountInfoBean(WdAccountInfoBean wdAccountInfoBean);
	public WdAccountInfoBean selectWdAccountInfoBeanByCode(@Param("code")int code);
	public void updateWdAccountInfoBean(WdAccountInfoBean wdAccountInfoBean);
	public WdAccountInfoBean selectWdAccountInfoBeanByAccountName(@Param("accountName")String accountName);
	public WdAccountInfoBean selectAccountInfoByAccountName(@Param("accountName")String accountName);
}