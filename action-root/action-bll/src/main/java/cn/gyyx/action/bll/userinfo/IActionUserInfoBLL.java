package cn.gyyx.action.bll.userinfo;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;

public interface IActionUserInfoBLL {

	ActionUserInfoPO get(int activityId, String userId);
	
	UserInfoAndQualificationVO getUserInfo(int activityId, String userId);
	
	int put(ActionUserInfoPO params, SqlSession session);
	
	int push(ActionUserInfoPO params, SqlSession session);
}
