/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午8:30:53
 * 版本号：v1.0
 * 本类主要用途叙述：后台操作日志的数据库
 */

package cn.gyyx.action.dao.challenger;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.OperationLogBean;

public class OperationLogDao {
	/***
	 * 增加控制台日志
	 * 
	 * @param operateBean
	 */
	public void addOperationLog(OperationLogBean operateBean, SqlSession session) {
		IOperationLog iOperationLog = session.getMapper(IOperationLog.class);
		iOperationLog.addOperationLog(operateBean);
	}

	public int getCoserWorksCheckCount(int actionCode, String type,
			String userName, SqlSession session) {
		IOperationLog iOperationLog = session.getMapper(IOperationLog.class);
		return iOperationLog.getCoserWorksCheckCount( actionCode,type,userName);
	}
}
