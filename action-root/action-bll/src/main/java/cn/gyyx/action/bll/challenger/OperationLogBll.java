/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午8:32:47
 * 版本号：v1.0
 * 本类主要用途叙述：后台操作日志Bll
 */

package cn.gyyx.action.bll.challenger;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.dao.challenger.OperationLogDao;

public class OperationLogBll {
	private OperationLogDao operationLogDao = new OperationLogDao();

	/***
	 * 增加控制台日志
	 * 
	 * @param operateBean
	 */
	public void addOperationLog(OperationLogBean operateBean, SqlSession session) {
		operationLogDao.addOperationLog(operateBean, session);
	}
	
	/***
	 * 查询同人活动作品审核通过日志数量
	 * 
	 * @param operateBean
	 */
	public int getCoserWorksCheckCount(int actionCode,String type,String userName, SqlSession session) {
		return operationLogDao.getCoserWorksCheckCount(actionCode,type,userName, session);
	}

}
