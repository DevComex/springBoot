package cn.gyyx.action.dao.wdsigned;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedSendPrizeBean;

/**
 * 问道APP礼物
 * 
 * @ClassName: IWdAppSignLogMapper
 * @description IWdAppSignLogMapper
 * @author luozhenyu
 * @date 2016年11月16日
 */
public interface IWdAppSignSendPrizeMapper {

	/**
	 * 插入签到日志
	 * 
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedSendPrizeBean bean);

	/**
	 * 获取礼物表签到记录根据礼品
	 * 
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	WdAppSignedSendPrizeBean getSignSendPrizeByPrize(@Param(value = "account") String account,
			@Param(value = "serverId") int serverId, @Param(value = "gift") String gift,
			@Param(value = "batch") String batch);
	
	/**
	 * 获取礼物表签到记录根据礼品
	 * 
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	List<WdAppSignedSendPrizeBean> getSignSendPrizeByPrizes(@Param(value = "account") String account,
			@Param(value = "serverId") int serverId, @Param(value = "gifts") List<String> gift,
			@Param(value = "batch") String batch);
	
	
	/**
	 * 获取礼物表签到记录根据礼品
	 * 
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	WdAppSignedSendPrizeBean getSignSendPrizeByPrize(@Param(value = "account") String account,
			@Param(value = "serverId") int serverId, @Param(value = "gift") String gift,
			@Param(value = "batch") String batch,SqlSession session);
	/**
	 * 修改礼包发放状态
	 * 
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	int modifyPrizeStatus(@Param(value = "account") String account,
			@Param(value = "serverId") int serverId, @Param(value = "gift") String gift,
			@Param(value = "batch") String batch,@Param(value = "status") int status,
			@Param(value = "sendTime") Date sendTime);



	/**
	 * 根据奖品状态获取未领取奖品
	 * @param account
	 * @param serverName
	 * @param status
	 * @param batch
	 * @return
	 */
	WdAppSignedSendPrizeBean getUnclaimedPrizeByStatus(@Param(value = "account") String account,
			@Param(value = "serverName") String serverName,@Param(value = "status") int status,
			@Param(value = "batch") String batch);

	
	/**
	 * 获取礼物表签到记录根据礼品
	 * 
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	List<WdAppSignedSendPrizeBean> getSignSendPrize(@Param(value = "account") String account,
			@Param(value = "serverId") int serverId, @Param(value = "batch") String batch,
			@Param(value = "status") int status);

	int insert(WdAppSignedSendPrizeBean bean, SqlSession session);
}
