/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:42:17
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.ChallenterTeamBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamUserBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;

public interface IChallenterTeam {
	ChallenterTeamBean getUserTeamInfo(ChallenterTeamBean obj);

	ChallenterTeamBean getUserCreateTeam(int userId);

	ChallenterTeamBean getUserTeamInfo(int userId);

	int checkTeamNameExist(String teamName);

	int insert(ChallenterTeamBean challenterTeamBean);

	int teamListPagingCount(ChallenterTeamBean b);

	List<ChallenterTeamBean> teamListPaging(ChallenterTeamBean b);

	ChallenterTeamBean getTeam(int teamId);

	void updateTeamApplyCountAddOne(int teamId);

	List<ChallenterUserInfoBean> getTeamMemberNamesNotLeader(int teamId);

	List<ChallenterTeamBean> getTeamListData(ChallenterTeamBean bean);

	int getTeamListDataCount(ChallenterTeamBean bean);

	void reviewChallenterTeam(@Param("code") int code, @Param("state") String state);
}
