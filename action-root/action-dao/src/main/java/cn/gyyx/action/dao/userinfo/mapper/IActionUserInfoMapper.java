package cn.gyyx.action.dao.userinfo.mapper;

import java.util.List;

import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;

public interface IActionUserInfoMapper {

	List<ActionUserInfoPO> select(ActionUserInfoPO params);
	
	List<UserInfoAndQualificationVO> selectUserInfo(ActionUserInfoPO params);
	
	int insert(ActionUserInfoPO params);
	
	int update(ActionUserInfoPO params);
}
