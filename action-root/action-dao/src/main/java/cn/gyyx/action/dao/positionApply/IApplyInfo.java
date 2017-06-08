/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月17日上午10:06:01
 * 版本号：v1.0
 * 本类主要用途叙述：职位申请信息的数据库接口
 */

package cn.gyyx.action.dao.positionApply;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.positionApply.ApplyInfoBean;

public interface IApplyInfo {
	/***
	 * 添加职位申请信息
	 * 
	 * @param applyInfoBean
	 */
	public void addInfo(ApplyInfoBean applyInfoBean);

	/***
	 * 查询所有职位种类
	 */
	public List<String> getApplyInfoPostion();

	/***
	 * 查询出所有的申请信息
	 */
	public List<ApplyInfoBean> getAllApplyInfoBeans();

	/**
	 * 根据主键查询申请信息
	 */
	public ApplyInfoBean getApplyInfoByCode(@Param("code") int code);
	/**
	 * 根据主键删除一条信息
	 */
	public void removeApplyInfoByCode(@Param("code") int code);
	
	/**
	 * 根据电话的姓名查询
	 * 
	 * 
	 */
	public ApplyInfoBean getApplyInfoByNameAndPhone(@Param("name") String name,@Param("phone") String phone,@Param("position") String position);
}
