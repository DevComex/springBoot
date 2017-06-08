package cn.gyyx.action.service.userinfo;

import java.util.Map;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;
import cn.gyyx.action.service.ILoggerService;

public interface IActionUserInfoService extends ILoggerService {

	ResultBean<Map<String, Boolean>> getState(int activityId, String userId);
	
	ResultBean<UserInfoAndQualificationVO> getUserInfo(int activityId, String userId);
	
	ResultBean<String> signUp(ActionUserInfoPO params);
	
	ResultBean<String> bindGameRole(ActionUserInfoPO params);
}
