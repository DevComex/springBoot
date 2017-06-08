package cn.gyyx.action.bll.wdsigned;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedBean;

/**
 * 问道APP签到记录
 * @ClassName: IWdAppSignBll
 * @description IWdAppSignBll
 * @author luozhenyu
 * @date 2016年11月17日
 */
public interface IWdAppSignBll {
	/**
	 * 插入签到日志
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedBean bean); 
	
	/**
	 * 插入签到日志
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedBean bean,SqlSession session); 
	
	/**
	 * 获取今天签到信息
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedBean getSign(String account,int serverId,String batch);
	
	
	/**
	 * 获取今天签到信息
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedBean getSign(String account,int serverId,String batch,SqlSession session);
	
	
	/**
	 * 修改签到记录
	 * @param serialDay
	 * @param totalDay
	 * @param account
	 * @param serverId
	 * @param batch
	 * @return
	 */
	int updateSign(int serialDay,int totalDay,String account,int serverId,String batch);
	
	
	/**
	 * 修改签到记录
	 * @param serialDay
	 * @param totalDay
	 * @param account
	 * @param serverId
	 * @param batch
	 * @return
	 */
	int updateSign(int serialDay,int totalDay,String account,int serverId,String batch,SqlSession session);
	

}
