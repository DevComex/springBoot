package cn.gyyx.action.dao.wdnationaldayshare;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdnationalday.WdNationaldaySignLogBean;

public interface IWdNationaldaySignLogMapper {


	/**
	 * 根据获取当天的签到日志
	 */
	public WdNationaldaySignLogBean getTodaySignLog(@Param("account") String account,@Param("today") String today);
	
	/**
	 * 新增
	 */
	public int insert(WdNationaldaySignLogBean bean);
	
	/**
	 * 获取账号历史签到日志
	 */
	public List<String> getSignHistoryDate(@Param("account") String account);

	/**
	 * 获取累计签到次数
	 */
	public int getSignCountByAccount(@Param("account") String account);
	
	
	
}
