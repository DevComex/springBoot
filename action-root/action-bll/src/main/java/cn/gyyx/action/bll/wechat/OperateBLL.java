/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:操作业务逻辑
************************************************/
package cn.gyyx.action.bll.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.OperateBean;
import cn.gyyx.action.dao.wechat.OperateDao;

public class OperateBLL {

	private OperateDao operateDao = new OperateDao();
	
	/**
	 * 获取所有的操作信息
	 * @return
	 */
	public List<OperateBean> getOperateInfoByConfigCode(Integer configCode) {
		return operateDao.getOperateInfoByConfigCode(configCode);
	}
	
	/**
	 * 获取所有参与统计的操作信息
	 * @return
	 */
	public List<OperateBean> getOperateInfoByConfigCodeNoCount(Integer configCode) {
		return operateDao.getOperateInfoByConfigCodeNoCount(configCode);
	}
	
	/**
	 * 根据操作类型查询数量
	 * @param operateType
	 * @return
	 */
	public Integer getCountByOperateType(OperateBean operateBean){
		return operateDao.getCountByOperateType(operateBean);
	}
	
	/**
	 * 插入新操作
	 * @param operateBean
	 */
	public void addOperateInfo(OperateBean operateBean){
		operateDao.addOperateInfo(operateBean);
	}
	
	/**
	 * 根据code查询操作
	 * @param code
	 * @return
	 */
	public OperateBean getOperateInfoByCode(Integer code){
		return operateDao.getOperateInfoByCode(code);
	}
	
	/**
	 * 修改操作
	 * @param operateBean
	 */
	public void updateOperateInfo(OperateBean operateBean){
		operateDao.updateOperateInfo(operateBean);
	}
	
	/**
	 * 删除操作
	 * @param code
	 */
	public void updateDeleteFlag(Integer code){
		operateDao.updateDeleteFlag(code);
	}
	
	/**
	 * 编辑参数
	 * @param operateBean
	 */
	public void updateWechatCustomPara(OperateBean operateBean){
		operateDao.updateWechatCustomPara(operateBean);
	}
	
	/**
	 * 修改统计状态
	 * @param operateBean
	 */
	public void updateOperateInCount(OperateBean operateBean){
		operateDao.updateOperateInCount(operateBean);
	}
	
	/**
	 * 修改详细统计状态
	 * @param operateBean
	 */
	public void updateOperateInDetail(OperateBean operateBean){
		operateDao.updateOperateInDetail(operateBean);
	}
}
