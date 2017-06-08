package cn.gyyx.action.dao.userinfo.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.userinfo.IActionUserInfoDAO;
import cn.gyyx.action.dao.userinfo.mapper.IActionUserInfoMapper;

public class DefaultActionUserInfoDAO extends MyBatisBaseDAO implements IActionUserInfoDAO {

	@Override
	public ActionUserInfoPO selectOne(int activityId, String userId) {
		try(SqlSession session = this.getSession()) {
			logger.info("ActionUserInfoDAOImpl->selectOne->activityId=" + activityId + ";userId=" + userId);
			ActionUserInfoPO params = new ActionUserInfoPO();
			params.setActivityId(activityId);
			params.setUserId(userId);
			
			List<ActionUserInfoPO> poList = session.getMapper(IActionUserInfoMapper.class).select(params);
			logger.info("ActionUserInfoDAOImpl->selectOne->poList=" + JSON.toJSONString(poList));
			if (null != poList && poList.size() > 0) {
				return poList.get(0);
			}
		}
		catch(Exception e) {
			logger.error("ActionUserInfoDAOImpl->selectOne->Cause:" + e.getCause());
			logger.error("ActionUserInfoDAOImpl->selectOne->Message:" + e.getMessage());
			logger.error("ActionUserInfoDAOImpl->selectOne->StackTrace:" + e.getStackTrace());
		}
		
		return null;
	}
	
	@Override
	public UserInfoAndQualificationVO selectUserInfo(int activityId, String userId) {
		try(SqlSession session = this.getSession()) {
			logger.info("ActionUserInfoDAOImpl->selectUserInfo->activityId=" + activityId + ";userId=" + userId);
			ActionUserInfoPO params = new ActionUserInfoPO();
			params.setActivityId(activityId);
			params.setUserId(userId);
			
			List<UserInfoAndQualificationVO> poList = session.getMapper(IActionUserInfoMapper.class).selectUserInfo(params);
			logger.info("ActionUserInfoDAOImpl->selectUserInfo->poList=" + JSON.toJSONString(poList));
			if (null != poList && poList.size() > 0) {
				return poList.get(0);
			}
		}
		catch(Exception e) {
			logger.error("ActionUserInfoDAOImpl->selectUserInfo->Cause:" + e.getCause());
			logger.error("ActionUserInfoDAOImpl->selectUserInfo->Message:" + e.getMessage());
			logger.error("ActionUserInfoDAOImpl->selectUserInfo->StackTrace:" + e.getStackTrace());
		}
		
		return null;
	}
	
	@Override
	public int insert(ActionUserInfoPO params, SqlSession session) {
		try {
			logger.info("ActionUserInfoDAOImpl->insert->params=" + JSON.toJSONString(params));
			return session.getMapper(IActionUserInfoMapper.class).insert(params);
		}
		catch(Exception e) {
			logger.error("ActionUserInfoDAOImpl->insert->Cause:" + e.getCause());
			logger.error("ActionUserInfoDAOImpl->insert->Message:" + e.getMessage());
			logger.error("ActionUserInfoDAOImpl->insert->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}

	@Override
	public int update(ActionUserInfoPO params, SqlSession session) {
		try {
			logger.info("ActionUserInfoDAOImpl->insert->params=" + JSON.toJSONString(params));
			return session.getMapper(IActionUserInfoMapper.class).update(params);
		}
		catch(Exception e) {
			logger.error("ActionUserInfoDAOImpl->insert->Cause:" + e.getCause());
			logger.error("ActionUserInfoDAOImpl->insert->Message:" + e.getMessage());
			logger.error("ActionUserInfoDAOImpl->insert->StackTrace:" + e.getStackTrace());
		}
		
		return -1;
	}
}
