/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动积分相关接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountScoreBean;

public interface IWdAccountScoreBean {
	public void insertWdAccountScoreBean(WdAccountScoreBean bean);
	public WdAccountScoreBean selectWdAccountScoreBeanByCode(@Param("code")int code);
	public void updateWdAccountScoreBean(WdAccountScoreBean bean);
	public WdAccountScoreBean selectWdAccountScoreBeanByAccount(@Param("accountName")String accountName);
	public WdAccountScoreBean selectWdAccountScoreBeanByAccountName(@Param("accountName")String accountName);
}