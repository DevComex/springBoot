package cn.gyyx.action.dao.backstage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.backstage.LimitBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;


public interface ILimitMapper {
	/**
	 * 增加权限
	 * @param limitBean
	 * @return
	 */
	public Integer insertLimitBean(LimitBean limitBean);
	/**
	 * 编辑权限
	 * @param limitBean
	 * @return
	 */
	public void updateLimitBean(LimitBean limitBean);
	/**
	 * 删除权限
	 * @param limitBean
	 * @return
	 */
	public void deleteLimitBean(@Param("code") int code);
	/**
	 * 权限列表
	 * @param limitBean
	 * @return
	 */
	public List<LimitBean> selectLimitBeanAll(NewPageBean newPageBean);
	public Integer selectLimitBeanAllCount();
	public LimitBean selectLimitBeanByCode(@Param("code") int code);
	/**
	 * 查询用户具有权限的活动
	 * @param limitBean
	 * @return
	 */
	public List<LimitBean> selectLimitBeanByUser(NewPageBean newPageBean);
	public Integer selectLimitBeanByUserCount(@Param("personId") int personId);
}
