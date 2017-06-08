package cn.gyyx.action.service.common;

import java.util.Date;
import java.util.List;

import org.apache.wink.client.ClientResponse;

import cn.gyyx.action.beans.common.ActionUserLoginLog;
import cn.gyyx.action.beans.novicecard.ActiveLog;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.common.ActionUserLoginLogBLL;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.core.DataFormat;
import cn.gyyx.core.auth.UserInfo;

public class ActionCommonService {
	ActionUserLoginLogBLL actionUserLoginLogBLL = new ActionUserLoginLogBLL();
	
	/**
	 * 检测失败
	 */
	public static final String RES_FAIL = "RES_FAIL";
	/**
	 * 用户不存在
	 */
	public static final String RES_USER_NOTEXIST = "RES_USER_NOTEXIST";
	/**
	 * 用户已在此服务器激活
	 */
	public static final String RES_USER_ACTIVITED = "RES_USER_ACTIVITED";
	/**
	 * 用户未在此服务器激活
	 */
	public static final String RES_USER_NOTACTIVITE = "RES_USER_NOTACTIVITE";

	public String checkUserActivitionOnServer(String account, int game,
			int serverCode) {
		try {
			// 调用webapi
			CallWebApiAgent cwaa = new CallWebApiAgent();
			ClientResponse response = cwaa.getActivatelogFromWebApi(account,
					game);
			String statusType = response.getStatusType().toString();
			if("OK".equals(statusType)) {
				// 接收返回响应体
				String result = response.getEntity(String.class);
				if (!result.equals("[]")) {
					ActiveLog[] activeLogArray = DataFormat.jsonToObj(result,
							ActiveLog[].class);
					for (ActiveLog log : activeLogArray) {
						if (log.getServerCode() == serverCode) {
							return RES_USER_ACTIVITED;
						}
					}
				}
				return RES_USER_NOTACTIVITE;
			}
			return RES_USER_NOTEXIST;
		} catch (Exception e) {
			return RES_FAIL;
		}
	}

	public void insertLoginLog(UserInfo userInfo,int activityCode) {
		if(userInfo != null){
			ActionUserLoginLog log = actionUserLoginLogBLL.getBeanByLoginId(userInfo.getLoginID());
			if(log == null){
				log = new ActionUserLoginLog();
				log.setAccount(userInfo.getAccount()+"");
				log.setUserId(userInfo.getUserId()+"");
				log.setActivityCode(activityCode);
				log.setLoginTime(new Date());
				log.setLoginIp(userInfo.getLoginIP());
				log.setLoginId(userInfo.getLoginID());
				
				actionUserLoginLogBLL.insert(log);
			}
		}
	}

	public ResultBeanWithPage<List<ActionUserLoginLog>> loginLogDataListForOa(
			ActionUserLoginLog bean) {
		List<ActionUserLoginLog> list = actionUserLoginLogBLL.getListPaging(bean);
		int count = actionUserLoginLogBLL.getListPagingCount(bean);
		for(ActionUserLoginLog g :list){
			g.setLoginTimeStr(DateTools.formatDate(g.getLoginTime(),"yyyy-MM-dd HH:mm:ss"));
		}
		return new ResultBeanWithPage<>(true,"获取成功",list,count);
	}
}
