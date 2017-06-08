package cn.gyyx.action.bll.userinfo.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.bll.userinfo.IGameDataConvertToScoreBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public abstract class ConvertByLevelAndProficiencyBLL implements IGameDataConvertToScoreBLL {

	protected static Logger logger = GYYXLoggerFactory.getLogger(ConvertByLevelAndProficiencyBLL.class);
	protected Integer roleLevelInGame;
	protected Integer roleProficiencyInGame;
	protected Map<String, Object> valuesMap = new HashMap<String, Object>();
	
	public Map<String, Object> getValue() {
		return valuesMap;
	}
	
	protected BigDecimal calculate(BigDecimal level, BigDecimal proficiency) {
		// 道标 = 等级 * 等级 * 等级 * 0.29 / 180
		// 积分 = 道行 / 360 / 标道 * 100
		BigDecimal result = BigDecimal.ZERO;
		
		result = level.multiply(level).multiply(level).multiply(BigDecimal.valueOf(0.29)).divide(BigDecimal.valueOf(180), 2, BigDecimal.ROUND_HALF_UP);
		result = proficiency.divide(BigDecimal.valueOf(360), 2, BigDecimal.ROUND_HALF_UP).divide(result, 2, BigDecimal.ROUND_HALF_UP);
		result = result.multiply(BigDecimal.valueOf(100));
		
		return result;
	}
}
