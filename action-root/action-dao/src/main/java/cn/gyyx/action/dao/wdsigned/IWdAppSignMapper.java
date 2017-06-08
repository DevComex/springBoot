package cn.gyyx.action.dao.wdsigned;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;

/**
 * 问道APP签到表
 * 
 * @ClassName: IWdAppSignLogMapper
 * @description IWdAppSignLogMapper
 * @author luozhenyu
 * @date 2016年11月16日
 */
public interface IWdAppSignMapper {

	/**
	 * 插入签到记录
	 * 
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedBean bean);
	
	/**
	 * 插入签到记录
	 * 
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignedBean bean,SqlSession session);

	/**
	 * 获取账号签到信息
	 * 
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedBean getSign(@Param(value = "account") String account, @Param(value = "serverId") int serverId,
			@Param(value = "batch") String batch);
	
	/**
	 * 获取账号签到信息
	 * 
	 * @param account
	 * @param serverId
	 * @return
	 */
	WdAppSignedBean getSign(@Param(value = "account") String account, @Param(value = "serverId") int serverId,
			@Param(value = "batch") String batch,SqlSession session);

	int updateSign(@Param(value="serialDay")int serialDay,
			@Param(value="totalDay")int totalDay,@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch);
	
	
	int updateSign(@Param(value="serialDay")int serialDay,
			@Param(value="totalDay")int totalDay,@Param(value="account")String account,
			@Param(value="serverId")int serverId,@Param(value="batch")String batch,SqlSession session);
}
