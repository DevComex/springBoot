package cn.gyyx.action.service.userinfo.impl;

import java.math.BigDecimal;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.bll.lottery.impl.ActionQualificationBLLImpl;
import cn.gyyx.action.bll.userinfo.IActionUserInfoBLL;
import cn.gyyx.action.bll.userinfo.IGameDataConvertToScoreBLL;
import cn.gyyx.action.bll.userinfo.impl.ActionConvertByLevelAndProficiencyBLL;
import cn.gyyx.action.bll.userinfo.impl.DefaultActionUserInfoBLL;
import cn.gyyx.action.bll.userinfo.impl.GameConvertByLevelAndProficiencyBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.userinfo.IGameDataConvertService;
import cn.gyyx.distribute.lock.DistributedLock;

public class ConvertByLevelAndProficiencyService implements IGameDataConvertService {

	private IActionUserInfoBLL actionUserInfoBLL = new DefaultActionUserInfoBLL();
	
	public boolean excute(int activityId, String userId, String ip) {
		
		// 防止用户重复提交
		String key = activityId + "-" + userId + "-" + this.getClass().getName();
		try(DistributedLock lock = new DistributedLock(key)) {
			lock.weakLock(30, 0);
			
			// 获得报名信息
			ActionUserInfoPO userInfo = actionUserInfoBLL.get(activityId, userId); 
			if (null != userInfo) {
				IGameDataConvertToScoreBLL gameConvert = new GameConvertByLevelAndProficiencyBLL();
				
				// 根据游戏中的等级、道行计算积分
				BigDecimal scoreInGame = gameConvert.convert(userInfo);
				if (scoreInGame.compareTo(BigDecimal.ZERO) < 1) {
					return false;
				}
				
				// 用户根据游戏数据已经取得的积分
				IGameDataConvertToScoreBLL actionConvert = new ActionConvertByLevelAndProficiencyBLL();
				
				// 计算出增加的积分
				BigDecimal score = scoreInGame.subtract(actionConvert.convert(userInfo));
				if (score.compareTo(BigDecimal.ZERO) > 0) {
					int updateScore = Integer.parseInt(String.valueOf(score.longValue()));
					SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
					
					try {
						// 更新积分
						IActionQualificationBLL actionQualificationBLL = new ActionQualificationBLLImpl();
						if (actionQualificationBLL.addLotteryScore(activityId, userId, updateScore, OperateScoreEnums.GameConvert.toString(), ip, session) > 0) {
							// 更新用户等级和道行
							ActionUserInfoPO userInfoParams = new ActionUserInfoPO();
							userInfoParams.setActivityId(activityId);
							userInfoParams.setUserId(userId);
							
							Integer roleLevel = Integer.valueOf(gameConvert.getValue().get("RoleLevel").toString());
							Integer roleProficiency = Integer.valueOf(gameConvert.getValue().get("RoleProficiency").toString());
							
							userInfoParams.setRoleLevel(roleLevel);
							userInfoParams.setRoleProficiency(roleProficiency);;
							
							if (actionUserInfoBLL.push(userInfoParams, session) > 0) {
								session.commit();
								return true;
							}
						}
						else {
							session.rollback();
						}
					}
					catch(Exception sqle) {
						if (null != session) session.rollback();
						logger.error("ConvertByLevelAndProficiencyService->excute->sql->errorCause:" + sqle.getCause());
						logger.error("ConvertByLevelAndProficiencyService->excute->sql->errorMessage:" + sqle.getMessage());
						logger.error("ConvertByLevelAndProficiencyService->excute->sql->StackTrace:" + sqle.getStackTrace());
					}
					finally {
						if (null != session) session.close();
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("ConvertByLevelAndProficiencyService->excute->errorCause:" + e.getCause());
			logger.error("ConvertByLevelAndProficiencyService->excute->errorMessage:" + e.getMessage());
			logger.error("ConvertByLevelAndProficiencyService->excute->StackTrace:" + e.getStackTrace());
		}
		
		return false;
	}
}
