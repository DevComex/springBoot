/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2016年-02月-18日
       Note:服务器关闭状态拼装
************************************************/
package cn.gyyx.action.service.xuanwuba;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.ServerStatusBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.ServerStatusBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ServerStatusService {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerStatusService.class);
	private ServerStatusBll serverStatusBll = new ServerStatusBll();
	
	/**
	 * 每次访问是判断时间修改失效状态
	 */
	public void onlineCheck(){
		List<ServerStatusBean> queryList = serverStatusBll.getAllOpenServerStatusInfo();
		Date date = new Date();
		for (ServerStatusBean serverStatusBean : queryList) {
			if(serverStatusBean.getEndTime().getTime() <= date.getTime()){
				serverStatusBean.setCloseStatus(true);
			}
			serverStatusBll.modifyServerStatusInfo(serverStatusBean);
		}
	}
	
	/**
	 * 判断当前是否是停服状态
	 * @return
	 */
	public boolean checkServerStatusIsOpen(){
		List<ServerStatusBean> queryList = serverStatusBll.getAllOpenServerStatusInfo();
		Date date = new Date();
		boolean flag = true;
		for (ServerStatusBean serverStatusBean : queryList) {
			if(date.after(serverStatusBean.getStartTime()) && date.before(serverStatusBean.getEndTime())){
				flag = false;
			}
		}
		return flag;
	}
}
