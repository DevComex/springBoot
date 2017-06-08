package cn.gyyx.action.dao.wdsigned;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;
/**
 * 问道APP签到日志表
 * @ClassName: IWdAppSignLogMapper
 * @description IWdAppSignLogMapper
 * @author luozhenyu
 * @date 2016年11月16日
 */
public interface IWdAppSignLogMapper {
	
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
	WdAppSignedLogBean getTodaySignLog(@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch
			,@Param(value="today")String today);
	
	
	/**
	 * 获取今天签到信息
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedLogBean getTodaySignLog(@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch
			,@Param(value="today")String today,SqlSession session);
	
	/**
	 * 获取历史签到信息
	 * @param account
	 *   @param serverId
	 * @return
	 */
	List<String> getSignHistoryDate(@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch);
	
	/**
	 * 获取累计签到次数
	 * @param account
	 * @param serverId
	 * @return
	 */
	int getSignCountByAccount(@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch);
	
	/**
	 * 获取累计签到次数
	 * @param account
	 * @param serverId
	 * @return
	 */
	int getSignCountByAccount(@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch,SqlSession session);

}
