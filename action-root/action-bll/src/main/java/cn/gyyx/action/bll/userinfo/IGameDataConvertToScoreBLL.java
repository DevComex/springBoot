package cn.gyyx.action.bll.userinfo;

import java.math.BigDecimal;
import java.util.Map;

import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;

public interface IGameDataConvertToScoreBLL {

	BigDecimal convert(ActionUserInfoPO params);
	
	Map<String, Object> getValue();
}
