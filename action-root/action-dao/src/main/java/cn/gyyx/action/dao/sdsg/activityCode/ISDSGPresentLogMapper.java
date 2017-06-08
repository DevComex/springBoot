package cn.gyyx.action.dao.sdsg.activityCode;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.sdsg.activityCode.ActionConfigBean;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;

public interface ISDSGPresentLogMapper {
	/**
	 * 获取当天抽奖次数
	 * @param actioinCode
	 * @param account
	 * @param endTime
	 * @param startTime
	 * @return
	 */
	public int presentCountDay(int actioinCode,String account,String endTime,String startTime);
	/**
	 * 判断是否获奖
	 * @param actioinCode
	 * @param account
	 * @return
	 */
	public PresentLogBean hasPrize(int actioinCode,String account,String presentType);
	
	public Integer isActivation(int gameId,int userId);
	/**
	 * 
	 * @param actionCode 活动编号
	 * @param account 账号
	 * @param serverID 区服ID
	 * @param serverName 区服名
	 * @return
	 */
	public void updateActivation(int actionCode,String account,String presentType,String serverID,String serverName,String activeCode);
	
	public int isExist(int actionCode,String account,String presentType);
	
	public void insertPrizeLog(String account,int serverId,String serverName,String presentName,String drawTime,String drawIp);
	
	public void updateGameActivityCode(int gameId,String activityCode,String serverID,String accountName,Date date,Integer userId);
	
	public int isExistActiveCode(@Param("activeCode")String activeCode);
	
	public ActionConfigBean getActionMsg(@Param("code")int code);
	public void updateGameActivityCodeStatus( String cardCode);
	public int isRightActiveCode(@Param("activeCode")String activeCode); 
}
