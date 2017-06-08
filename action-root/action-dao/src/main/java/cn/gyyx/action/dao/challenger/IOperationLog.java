/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午7:53:58
 * 版本号：v1.0
 * 本类主要用途叙述：后台操作日志数据库
 */



package cn.gyyx.action.dao.challenger;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.OperationLogBean;

public interface IOperationLog {
	/***
	 * 增加控制台日志
	 * @param operateBean
	 */
	public void addOperationLog(OperationLogBean operateBean);

	public int getCoserWorksCheckCount(@Param("actionCode") int actionCode, @Param("type") String type,
			@Param("userName")String userName);

}
