package cn.gyyx.action.dao.backstage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.backstage.LimitBean;
import cn.gyyx.action.beans.backstage.LimitPeopleBean;

public interface ILimitPeopleMapper {
	/**
	 * 添加某活动下的人员
	 * @param LimitPeopleBean
	 * @return
	 */
	public void insertByActionCode(LimitPeopleBean limitPeopleBean);
	/**
	 * 删除某活动下的人员
	 * @param LimitPeopleBean
	 * @return
	 */
	public void deleteLimitPeopleBean(@Param("limitCode") int limitCode);
	/**
	 * 查询某活动下的人员
	 * @param LimitPeopleBean
	 * @return
	 */
	public List<LimitPeopleBean> selectByActionCode(@Param("limitCode") int limitCode);
}
