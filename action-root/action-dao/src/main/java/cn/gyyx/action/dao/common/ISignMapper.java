package cn.gyyx.action.dao.common;

import cn.gyyx.action.beans.common.ActionCommonSignBean;

public interface ISignMapper {

	public ActionCommonSignBean getSign(ActionCommonSignBean sign);
	
	public int insertSign(ActionCommonSignBean sign);
	
	public int updateSign(ActionCommonSignBean sign);
	
	public ActionCommonSignBean getRecentSign(ActionCommonSignBean sign);

}
