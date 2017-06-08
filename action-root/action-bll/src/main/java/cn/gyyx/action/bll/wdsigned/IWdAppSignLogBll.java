package cn.gyyx.action.bll.wdsigned;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;

/**
 * 问道app签到日志
 * @ClassName: IWdAppSignLogBll
 * @description IWdAppSignLogBll
 * @author luozhenyu
 * @date 2016年11月17日
 */
public interface IWdAppSignLogBll {
	/**
	 * 插入签到日志
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedLogBean bean); 
	
	
	/**
	 * 插入签到日志
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedLogBean bean,SqlSession session); 
	/**
	 * 获取今天签到信息
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedLogBean getTodaySignLog(String account,int serverId,String batch,String today);
	
	
	/**
	 * 获取今天签到信息
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedLogBean getTodaySignLog(String account,int serverId,String batch,String today,SqlSession session);
	/**
	 * 获取历史签到信息
	 * @param account
	 *   @param serverId
	 * @return
	 */
	List<String> getSignHistoryDate(String account,int serverId,String batch);
	
	/**
	 * 获取累计签到次数
	 * @param account
	 * @param serverId
	 * @return
	 */
	int getSignCountByAccount(String account,int serverId,String batch);
	
	/**
	 * 获取累计签到次数
	 * @param account
	 * @param serverId
	 * @return
	 */
	int getSignCountByAccount(String account,int serverId,String batch,SqlSession session);

}
