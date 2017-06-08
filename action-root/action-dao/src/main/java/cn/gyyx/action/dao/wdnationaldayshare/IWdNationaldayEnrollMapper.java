package cn.gyyx.action.dao.wdnationaldayshare;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;

public interface IWdNationaldayEnrollMapper {
	
	/**
	 * 根据账号查询报名信息
	 */
	public WdNationaldayEnrollBean getEnrollInfoByAccount(String account);
	
	/**
	 * 新增
	 */
	public int insert(WdNationaldayEnrollBean bean);
	
	/**
	 * 增加分数
	 */
	public int increaseScore(@Param("account")String account ,@Param("increaseScore")int increaseScore);

	/**
	 * @param openId
	 * @return
	 */
	public WdNationaldayEnrollBean getEnrollInfoFromDatabaseByOpenId(
			@Param("openId") String openId);
	
}
