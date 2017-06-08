package cn.gyyx.action.dao.userinfo;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;

public interface IActionUserInfoDAO {

	ActionUserInfoPO selectOne(int activityId, String userId);
	
	UserInfoAndQualificationVO selectUserInfo(int activityId, String userId);
	
	int insert(ActionUserInfoPO params, SqlSession session);
	
	int update(ActionUserInfoPO params, SqlSession session);
}
