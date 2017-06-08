/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午7:06:09
 * 版本号：v1.0
 * 本类主要用途叙述：公用数据的表
 */

package cn.gyyx.action.dao.challenger;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.SameDataBean;

public interface ISameData {

	/**
	 * 插入简单数据
	 * 
	 * @param sameDataBean
	 */
	public void addSameData(@Param("type") String type,
			@Param("content") String content,
			@Param("actionCode") int actionCode);

	/**
	 * 根据类型更改数据
	 * 
	 * @param type
	 * @param content
	 */
	public void setSameDate(@Param("type") String type,
			@Param("content") String content,
			@Param("actionCode") int actionCode);

	/***
	 * 获取这个类型的簡單数据
	 * 
	 * @param type
	 * @return
	 */
	public SameDataBean getSameDataBean(@Param("type") String type,
			@Param("actionCode") int actionCode);
	
	public void increaseSameDate(@Param("type") String type,@Param("activityCode") int activityCode,
			@Param("increaseScore") double increaseScore);

}
