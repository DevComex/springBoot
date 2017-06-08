/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:自定义参数业务逻辑
************************************************/
package cn.gyyx.action.bll.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.CustomBean;
import cn.gyyx.action.dao.wechat.CustomDao;

public class CustomBLL {

	private CustomDao customDao = new CustomDao();
	
	/**
	 *  获取操作的所有自定义参数
	 * @return
	 */
	public List<CustomBean> getAllCustomInfoByOperate(Integer operateCode) {
		return customDao.getAllCustomInfoByOperate(operateCode);
	}
	
	/**
	 * 获取统计的所有自定义参数
	 * @return
	 */
	public List<CustomBean> getAllCustomInfoByConfigCode(Integer configCode) {
		return customDao.getAllCustomInfoByConfigCode(configCode);
	}
	
	/**
	 * 获取自定义参数
	 * @return
	 */
	public CustomBean getCustomInfoByCode(Integer code){
		return customDao.getCustomInfoByCode(code);
	}
	
	/**
	 * 根据自定义参数获取数量
	 * @param customType
	 * @return
	 */
	public Integer getCustomCountByCustomType(String customType){
		return customDao.getCustomCountByCustomType(customType);
	}
	
	/**
	 * 增加参数
	 * @param customBean
	 */
	public void addCustomInfo(CustomBean customBean){
		customDao.addCustomInfo(customBean);
	}
	
	/**
	 * 修改参数
	 * @param customBean
	 */
	public void updateCustomInfo(CustomBean customBean){
		customDao.updateCustomInfo(customBean);
	}
	
	/**
	 * 删除参数
	 * @param code
	 */
	public void deleteCustomInfo(Integer code){
		customDao.deleteCustomInfo(code);
	}
}
