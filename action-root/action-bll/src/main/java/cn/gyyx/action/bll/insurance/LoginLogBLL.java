/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——登陆日志业务类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.insurance;

import cn.gyyx.action.beans.insurance.LoginLogBean;
import cn.gyyx.action.dao.insurance.LoginLogDAO;

public class LoginLogBLL {
	private LoginLogDAO loginLogDAO = new LoginLogDAO();
	/**
	 * 插入登陆日志
	 * @param loginLogBean
	 */
	public void addLoginLog(LoginLogBean loginLogBean){
		loginLogDAO.insertLoginLog(loginLogBean);
	}
	/**
	 * 获取上次的登陆日志
	 * @param countId
	 * @return
	 */
	public LoginLogBean getLastLogin(Integer countId){
		return loginLogDAO.selectLastLogin(countId);
	}
}
