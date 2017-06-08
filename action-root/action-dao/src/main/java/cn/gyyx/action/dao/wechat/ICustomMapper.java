package cn.gyyx.action.dao.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.CustomBean;

public interface ICustomMapper {
	/**
	 * 获取操作的所有自定义参数
	 * @return
	 */
	public List<CustomBean> getAllCustomInfoByOperate(Integer operateCode);
	
	/**
	 * 获取统计的所有自定义参数
	 * @return
	 */
	public List<CustomBean> getAllCustomInfoByConfigCode(Integer configCode);
	
	/**
	 * 获取自定义参数
	 * @return
	 */
	public CustomBean getCustomInfoByCode(Integer code);
	/**
	 * 根据自定义参数获取数量
	 * @param customType
	 * @return
	 */
	public Integer getCustomCountByCustomType(String customType);
	
	/**
	 * 增加参数
	 * @param customBean
	 */
	public void addCustomInfo(CustomBean customBean);
	/**
	 * 修改参数
	 * @param customBean
	 */
	public void updateCustomInfo(CustomBean customBean);
	/**
	 * 删除参数
	 * @param code
	 */
	public void deleteCustomInfo(Integer code);
}
