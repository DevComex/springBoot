package cn.gyyx.action.bll.userinfo.impl;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.userinfo.po.ActionGameInterimPO;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.dao.userinfo.IActionGameInterimDAO;
import cn.gyyx.action.dao.userinfo.impl.DefaultActionGameInterimDAO;

public class GameConvertByLevelAndProficiencyBLL extends ConvertByLevelAndProficiencyBLL {

	private IActionGameInterimDAO actionGameInterimDAO = new DefaultActionGameInterimDAO();
	
	public BigDecimal convert(ActionUserInfoPO params) {
		BigDecimal result = BigDecimal.ZERO;
		
		logger.info("ActionConvertByLevelAndProficiencyBLL->convert->params=" + JSON.toJSONString(params));
		
		ActionGameInterimPO gameParams = new ActionGameInterimPO();
		gameParams.setUserId(params.getUserId());
		gameParams.setServerName(params.getServerName());
		gameParams.setRoleId(params.getRoleId());
		
		// 根据用户ID、服务器名、角色ID查询游戏中角色等级和道行
		ActionGameInterimPO gameInfo = actionGameInterimDAO.selectOne(gameParams);
		if (null != gameInfo 
				&& null != gameInfo.getRoleLevel() && gameInfo.getRoleLevel() > 0
				&& null != gameInfo.getRoleProficiency() && gameInfo.getRoleProficiency() > 0) {
			BigDecimal proficiency = BigDecimal.valueOf(gameInfo.getRoleProficiency());
			BigDecimal level= BigDecimal.valueOf(gameInfo.getRoleLevel());
			
			this.valuesMap.put("RoleLevel", gameInfo.getRoleLevel());
			this.valuesMap.put("RoleProficiency", gameInfo.getRoleProficiency());
			
			result = this.calculate(level, proficiency);
		}
		
		logger.info("ActionConvertByLevelAndProficiencyBLL->convert->result=" + result);
		
		return result;
	}
}
