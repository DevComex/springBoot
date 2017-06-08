package cn.gyyx.action.dao.common;

import cn.gyyx.action.beans.common.ActionCommonSignLogBean;
import java.util.List;

public interface ISignLogMapper {
	public int insertSignLog(ActionCommonSignLogBean signLog);
	
	public List<ActionCommonSignLogBean> selectSignLog(ActionCommonSignLogBean signLog);
}
