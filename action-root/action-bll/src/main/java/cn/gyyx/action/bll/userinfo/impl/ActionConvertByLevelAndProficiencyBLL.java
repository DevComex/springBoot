package cn.gyyx.action.bll.userinfo.impl;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.dao.userinfo.IActionUserInfoDAO;
import cn.gyyx.action.dao.userinfo.impl.DefaultActionUserInfoDAO;

public class ActionConvertByLevelAndProficiencyBLL extends ConvertByLevelAndProficiencyBLL {
	
	public BigDecimal convert(ActionUserInfoPO params) {
		BigDecimal result = BigDecimal.ZERO;
		
		logger.info("ActionConvertByLevelAndProficiencyBLL->convert->params=" + JSON.toJSONString(params));
		
		if (null != params
				&& null != params.getRoleLevel() && params.getRoleLevel() > 0
				&& null != params.getRoleProficiency() && params.getRoleProficiency() > 0) {
			BigDecimal proficiency = BigDecimal.valueOf(params.getRoleProficiency());
			BigDecimal level = BigDecimal.valueOf(params.getRoleLevel());
			
			result = this.calculate(level, proficiency);
		}
		
		logger.info("ActionConvertByLevelAndProficiencyBLL->convert->result=" + result);
		
		return result;
	}
}
