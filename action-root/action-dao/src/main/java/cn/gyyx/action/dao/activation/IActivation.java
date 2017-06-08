package cn.gyyx.action.dao.activation;

import org.apache.ibatis.annotations.Param;

/** 
* 类:IActivation<br />
* 叙述:给action-module中WEBAPI接口<br />
* 注释日期:2015年11月26日<br />
* @author lizepu
*/
public interface IActivation {
	/** 
	* 叙述:检测激活码是否存在<br />
	* @param gameId 游戏ID
	* @param activationCode 激活码
	* @return int 指定限制查询的满足数量
	*/
	public int validateActCode(@Param("gameId")int gameId,@Param("activationCode")String activationCode);
	/** 
	* 叙述:使用激活码<br />
	* @param serverId 服务器ID
	* @param userId 用户ID
	* @param gameId 游戏ID
	* @param activationCode 激活码
	* @return int 成功更新的行数
	*/
	public int useActivateCode(@Param("serverId")int serverId,@Param("userId")int userId,@Param("gameId")int gameId,@Param("activationCode")String activationCode);
}
