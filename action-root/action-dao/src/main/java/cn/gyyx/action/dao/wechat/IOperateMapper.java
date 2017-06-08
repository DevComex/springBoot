package cn.gyyx.action.dao.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.OperateBean;

public interface IOperateMapper {
	/**
	 * 获取所有的操作信息
	 * @return
	 */
	public List<OperateBean> getOperateInfoByConfigCode(Integer configCode);
	/**
	 * 获取所有参与统计的操作信息
	 * @return
	 */
	public List<OperateBean> getOperateInfoByConfigCodeNoCount(Integer configCode);
	/**
	 * 根据操作类型查询数量
	 * @param operateType
	 * @return
	 */
	public Integer getCountByOperateType(OperateBean operateBean);
	/**
	 * 插入新操作
	 * @param operateBean
	 */
	public void addOperateInfo(OperateBean operateBean);
	/**
	 * 根据code查询操作
	 * @param code
	 * @return
	 */
	public OperateBean getOperateInfoByCode(Integer code);
	/**
	 * 修改操作
	 * @param operateBean
	 */
	public void updateOperateInfo(OperateBean operateBean);
	/**
	 * 删除操作
	 * @param code
	 */
	public void updateDeleteFlag(Integer code);
	/**
	 * 编辑参数
	 * @param operateBean
	 */
	public void updateWechatCustomPara(OperateBean operateBean);
	/**
	 * 修改拥挤状态
	 * @param operateBean
	 */
	public void updateOperateInCount(OperateBean operateBean);
	
	/**
	 * 修改详细统计状态
	 * @param operateBean
	 */
	public void updateOperateInDetail(OperateBean operateBean);
}
