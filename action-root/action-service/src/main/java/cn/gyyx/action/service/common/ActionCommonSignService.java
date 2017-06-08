package cn.gyyx.action.service.common;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionCommonSignBean;
import cn.gyyx.action.beans.common.ActionCommonSignLogBean;
import cn.gyyx.action.bll.common.ActionCommonSignBll;
import cn.gyyx.action.bll.common.ActionCommonSignLogBll;
import cn.gyyx.action.bll.jswswxsign.DateTools;

public class ActionCommonSignService {
	private ActionCommonSignBll signBLL=new ActionCommonSignBll();
	private ActionCommonSignLogBll signLogBLL =new ActionCommonSignLogBll();
	/**
	 * 签到
	 */
	public ResultBean<HashMap<String,String>> sign(int actionCode,String account,SqlSession session) {
		ResultBean<HashMap<String,String>> res = new ResultBean<HashMap<String,String>>();
		HashMap<String,String> signMap = new HashMap<String,String>();
		res.setProperties(false, "操作失败！", signMap);
		if (StringUtils.isBlank(account)) {
			res.setMessage("缺少必要参数！");
			return res;
		}
		Date curTime = new Date();
		//1.获得用户最后签到的日期,判断用户是否签到过并且今日是否签到
		int continuousCount = 0;
		ActionCommonSignBean recentSign = signBLL.getRecentSign(actionCode,account,session);
		if(recentSign == null){
			//没有签到过
			continuousCount = 1;
			recentSign = new ActionCommonSignBean();
			recentSign.setLastSignDate(curTime);
			recentSign.setAccount(account);
			recentSign.setActionCode(actionCode);
			recentSign.setContinuousCount(continuousCount);
			recentSign.setSignNumber(1);  
			signBLL.insert(recentSign,session);
		}else{
			//有签到记录，且是今日的签到记录
			Date recentSignTime = recentSign.getLastSignDate();
			if(DateTools.formatDate(recentSignTime).equals(DateTools.formatDate(curTime))){
				res.setMessage("今日您已签到，请明日再来哟！");
				return res;	
			}
			//是昨天的话
			if(DateTools.formatDate(recentSignTime).equals(DateTools.formatDate(DateTools.getPreviousDate(curTime)))){
				continuousCount = recentSign.getContinuousCount()+ 1;
			}else{
				continuousCount = 1;
			}
			recentSign.setSignNumber(1);  
			recentSign.setContinuousCount(continuousCount);
			recentSign.setLastSignDate(curTime);
			signBLL.update(recentSign,session);
		}
		//2.签到明细插入一条日志
		ActionCommonSignLogBean signLog = new ActionCommonSignLogBean();
		signLog.setAccount(account);
		signLog.setSid(recentSign.getCode());
		signLog.setSignTime(curTime);
		signLog.setActionCode(actionCode);
		signLogBLL.insert(signLog,session);
		res.setIsSuccess(true);
		res.setMessage("签到成功！");
		signMap.put("continuousCount", continuousCount + "");
		res.setData(signMap);
		return res;
	}
	/**
	 * 获取签到信息
	 */
	public ActionCommonSignBean getSignInfo(int actionCode,String account){
		ActionCommonSignBean recentSign = signBLL.getRecentSign(actionCode,account);
		return recentSign;
	}
}
