package cn.gyyx.action.bll.wdsigned;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedSendPrizeBean;

/**
 * 问道APP签到礼物
 * 
 * @ClassName: IWdAppSignBll
 * @description IWdAppSignBll
 * @author luozhenyu
 * @date 2016年11月17日
 */
public interface IWdAppSignSendPrizeBll {
	/**
	 * 插入签到日志
	 * 
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedSendPrizeBean bean);
	
	/**
	 * 插入签到日志
	 * 
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedSendPrizeBean bean,SqlSession session);

	/**
	 * 获取该奖品的签到记录
	 * 
	 * @return
	 */
	WdAppSignedSendPrizeBean getSignSendPrizeByPrize(String account, int serverId, String gift, String batch);
	
	/**
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	List<WdAppSignedSendPrizeBean> getSignSendPrizeByPrizes(String account, int serverId, List<String> gift, String batch);
	
	/**
	 * 获取该奖品的签到记录
	 * 
	 * @return
	 */
	WdAppSignedSendPrizeBean getSignSendPrizeByPrize(String account, int serverId, String gift, String batch,SqlSession session);

	/**
	 * 修改领取礼包装状态
	 * 
	 * @return
	 */
	int modifyPrizeStatus(String account, int serverId, String gift, String batch,int status,Date sendTime);

	/**
	 * 获取未领取的奖品
	 * 
	 * @return
	 */
	WdAppSignedSendPrizeBean getUnclaimedPrizeByStatus(String account, String serverName, int status, String batch);

	/**
	 * 获取礼物表签到记录根据礼品
	 * 
	 * @param account
	 * @param serverId
	 * @param gift
	 * @param batch
	 * @return
	 */
	List<WdAppSignedSendPrizeBean> getSignSendPrize(String account, int serverId, String batch, int status);
}
