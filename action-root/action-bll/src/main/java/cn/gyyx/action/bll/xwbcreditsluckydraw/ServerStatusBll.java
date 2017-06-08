/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2016年-02月-18日
       Note:服务器关闭状态业务
************************************************/
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.ServerStatusBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.ServerStatusDAO;

public class ServerStatusBll {
	private ServerStatusDAO serverStatusDAO = new ServerStatusDAO();
	
	/**
	 * 增加服务器关闭信息
	 * @param serverStatusBean
	 */
	public void addServerStatusInfo(ServerStatusBean serverStatusBean){
		serverStatusDAO.addServerStatusInfo(serverStatusBean);
	}
	
	/**
	 * 获取所有的停服信息
	 * @return
	 */
	public List<ServerStatusBean> getAllServerStatusInfoByPage(int page){
		return serverStatusDAO.getAllServerStatusInfoByPage(page);
	}
	
	public List<ServerStatusBean> getAllServerStatusInfo(){
		return serverStatusDAO.getAllServerStatusInfo();
	}
	
	/**
	 * 获取所有开启的停服信息
	 * @return
	 */
	public List<ServerStatusBean> getAllOpenServerStatusInfo(){
		return serverStatusDAO.getAllOpenServerStatusInfo();
	}
	
	/**
	 * 根据code获取停服信息
	 * @param code
	 * @return
	 */
	public ServerStatusBean getServerStatusInfoByCode(int code){
		return serverStatusDAO.getServerStatusInfoByCode(code);
	}
	
	/**
	 * 修改服务器关闭信息
	 * @param serverStatusBean
	 */
	public void modifyServerStatusInfo(ServerStatusBean serverStatusBean){
		serverStatusDAO.modifyServerStatusInfo(serverStatusBean);
	}
	
}

