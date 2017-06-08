package cn.gyyx.action.bll.userinfo.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.beans.userinfo.po.ActionGameInterimPO;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.bll.lottery.impl.ActionQualificationBLLImpl;
import cn.gyyx.action.bll.userinfo.IActionGameBLL;
import cn.gyyx.action.bll.userinfo.IActionUserInfoBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.userinfo.IActionGameInterimDAO;
import cn.gyyx.action.dao.userinfo.IActionUserInfoDAO;
import cn.gyyx.action.dao.userinfo.impl.DefaultActionGameInterimDAO;
import cn.gyyx.action.dao.userinfo.impl.DefaultActionUserInfoDAO;

public class ProficiencyConvertToPointBLL implements IActionGameBLL {

	private IActionUserInfoDAO actionUserInfoDAO = new DefaultActionUserInfoDAO();
	private IActionGameInterimDAO actionGameInterimDAO = new DefaultActionGameInterimDAO();
	
	@Override
	public boolean excute(int activityId, String userId, String ip) {
		
		// 根据用户ID获得报名信息
		ActionUserInfoPO userInfo = actionUserInfoDAO.selectOne(activityId, userId);
		if (null != userInfo && !StringUtils.isEmpty(userInfo.getUserId()) && !StringUtils.isEmpty(userInfo.getServerName()) 
				&& StringUtils.isEmpty(userInfo.getRoleId()) ) {
			// 根据用户ID、服务器名、角色ID查询游戏中角色等级和道行
			ActionGameInterimPO params = new ActionGameInterimPO();
			params.setUserId(userInfo.getUserId());
			params.setServerName(userInfo.getServerName());
			params.setRoleId(userInfo.getRoleId());
			
			ActionGameInterimPO gameInfo = actionGameInterimDAO.selectOne(params);
			if (null != gameInfo && null != gameInfo.getRoleProficiency() && gameInfo.getRoleProficiency() > 0
					&& null != gameInfo.getRoleLevel() && gameInfo.getRoleLevel() > 0) {
				//  获得游戏中角色等级、道行
				BigDecimal proficiencyInGame = BigDecimal.valueOf(gameInfo.getRoleProficiency() == null ? 0 : gameInfo.getRoleProficiency());
				BigDecimal levelInGame = BigDecimal.valueOf(gameInfo.getRoleLevel() == null ? 0 : gameInfo.getRoleLevel());
				BigDecimal scoreInGame = excuteScore(proficiencyInGame, levelInGame);
				
				// 根据游戏中等级、道行换算积分
				if (scoreInGame.compareTo(BigDecimal.ZERO) > 0) {
					// 根据活动保存的等级、道行换算积分
					BigDecimal proficiencyInAction = BigDecimal.valueOf(userInfo.getRoleProficiency() == null ? 0 : gameInfo.getRoleProficiency());
					BigDecimal levelInAction = BigDecimal.valueOf(userInfo.getRoleLevel() == null ? 0 : userInfo.getRoleLevel());
					BigDecimal scoreInAction = excuteScore(proficiencyInAction, levelInAction);
					
					BigDecimal score = scoreInGame.subtract(scoreInAction);
					if (score.compareTo(BigDecimal.ZERO) > 0) {
						SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
						
						try {
							// 积分大于零，则新增积分
							int addScore = Integer.parseInt(String.valueOf(score.longValue()));
							IActionQualificationBLL actionQualificationBLL = new ActionQualificationBLLImpl();
							if (actionQualificationBLL.addLotteryScore(activityId, userId, addScore, OperateScoreEnums.GameConvert.toString(), ip, session) < 1) {
								session.rollback();
								return false;
							}
							
							// 更新活动中的等级、道行
							userInfo.setRoleProficiency(Integer.valueOf(proficiencyInAction.toString()));
							userInfo.setRoleLevel(Integer.valueOf(levelInGame.toString()));
							
							IActionUserInfoBLL actionUserInfoBLL = new DefaultActionUserInfoBLL();
							if (actionUserInfoBLL.push(userInfo, session) < 1) {
								session.rollback();
								return false;
							}
							
							session.commit();
						}
						catch(Exception e) {
							if (null != session) session.rollback();
						}
						finally {
							if (null != session) session.close();
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private BigDecimal excuteScore(BigDecimal proficiency, BigDecimal level) {
		// 道标 = 等级 * 等级 * 等级 * 0.29 / 180
		// 积分 = 道行 / 360 / 标道 * 100
		return proficiency.divide(BigDecimal.valueOf(360))
				.divide(level.multiply(level)
						     .multiply(level)
							 .multiply(BigDecimal.valueOf(0.29))
							 .divide(BigDecimal.valueOf(180)))
				.multiply(BigDecimal.valueOf(100));
	}
}
