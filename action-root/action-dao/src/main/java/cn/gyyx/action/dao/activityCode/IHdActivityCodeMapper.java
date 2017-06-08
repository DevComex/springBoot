package cn.gyyx.action.dao.activityCode;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.activityCode.PresentMesBean;

public interface IHdActivityCodeMapper {
	/**
	 * 获取中奖信息
	 * @param activityCode
	 * @return
	 */
	public PresentMesBean getPresent(@Param("activityCode")int activityCode);
	/**
	 * 删除一份奖品
	 * @param code
	 * @return
	 */
	public int deleteActivityCode(@Param("code")int code);
	/**
	 * 获取剩余激活码
	 * @param actionCode
	 * @return
	 */
	public Integer getReceiveCount(@Param("actionCode")String actionCode );
}
