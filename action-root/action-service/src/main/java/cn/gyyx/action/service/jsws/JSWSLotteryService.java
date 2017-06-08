package cn.gyyx.action.service.jsws;

import javax.servlet.http.HttpServletRequest;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activityCode.ConfigBean;
import cn.gyyx.action.beans.activityCode.ConfigBean.ACTION;
import cn.gyyx.action.service.activityCode.ActivityCodeService;

public class JSWSLotteryService extends ActivityCodeService{
	
	public ResultBean<String> getActivityCodePhone(HttpServletRequest request,String actionCode,String message,ACTION actionType){
		return super.submitMessage(request, actionCode, message, ConfigBean.IMPORTTYPE.phone, actionType );
	}
	public ResultBean<String> loginActivityCode(HttpServletRequest request,String actionCode,String message,ACTION actionType){
		return super.submitMessage(request, actionCode, message, ConfigBean.IMPORTTYPE.defaultType, actionType );
	}
}
