package cn.gyyx.action.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.common.ActionUserLoginLog;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年11月28日 下午7:15:25
 * 描        述：
 */
public interface IActionUserLoginLogMapper {

	void insert(ActionUserLoginLog bean);

	ActionUserLoginLog getBeanByLoginId(@Param("loginId")String loginId);

	int getListPagingCount(ActionUserLoginLog bean);

	List<ActionUserLoginLog> getListPaging(ActionUserLoginLog bean);

	List<Map<String, String>> getLoginCountGroupByDate(@Param("activityCode")int activityCode);

}
