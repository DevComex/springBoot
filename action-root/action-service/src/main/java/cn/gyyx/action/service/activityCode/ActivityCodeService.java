package cn.gyyx.action.service.activityCode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activityCode.ConfigBean.ACTION;
import cn.gyyx.action.beans.activityCode.ConfigBean.IMPORTTYPE;
import cn.gyyx.action.beans.activityCode.ConfigBean.JUDGE;
import cn.gyyx.action.beans.activityCode.ConfigBean.RESULTTYPE;
import cn.gyyx.action.beans.activityCode.PresentLogParaBean;
import cn.gyyx.action.beans.activityCode.PresentMesBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.bll.activityCode.HdActivityCodeBLL;
import cn.gyyx.action.bll.activityCode.SendPresentLogBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.core.Ip;

public class ActivityCodeService {
	private SendPresentLogBLL sendPresentLogBLL = new SendPresentLogBLL();
	private HdActivityCodeBLL hdActivityCodeBLL = new HdActivityCodeBLL();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	public ResultBean<String> submitMessage(HttpServletRequest request,String actionCode,String message,IMPORTTYPE type,ACTION actionType){
		ResultBean<String> result = new ResultBean<String>();
		ResultBean<Date> isExpired = isExpired(actionCode);
		if(isExpired.getIsSuccess()){
			switch(type){
			case defaultType:{
				result = submitDufaultType(actionCode, message, request, isExpired.getData(), actionType);
				break;
			}
			case phone:{
				result = submitPhone(actionCode,message,request,isExpired.getData(),actionType);
				break;
			}
			case email:{
				result = submitEmail(actionCode,message,request,isExpired.getData(),actionType);
				break;
			}
			}
		}else{
			result.setProperties(false,RESULTTYPE.expired.name(),isExpired.getMessage());
		}
		return result;
	}
	public ResultBean<String> submitDufaultType(String activityCode,String message,HttpServletRequest request,Date nowDate,ACTION actionType){
		ResultBean<String> result = new ResultBean<String>();
		PresentLogParaBean paraBean = new PresentLogParaBean(activityCode,message);
		Map<String,String> isExist = isExist(paraBean.getActionCodeInt(),message);
		if(isExist.get("isExist").equals(JUDGE.trueStr.getVariable())){
			result.setProperties(false, RESULTTYPE.existed.name(), isExist.get("account")+"您好！;"+"您的礼包码是:"+isExist.get("presentName"));
		}else{
			result = operation(request,paraBean,nowDate,actionType);
		}
		return result;
	}
	public ResultBean<String> submitPhone(String activityCode,String phoneNum,HttpServletRequest request,Date nowDate,ACTION actionType){
		ResultBean<String> result = new ResultBean<String>();
		if(phoneNum.matches("^[1][358][0-9]{9}$")){    //判断手机正则 
			result = submitDufaultType(activityCode, phoneNum, request, nowDate, actionType);
		}else{
			result.setProperties(false, RESULTTYPE.phoneWrong.name(), "不好意思，你的手机号输入有误，请重新输入。");
		}
		return result;
	}
	public ResultBean<String> submitEmail(String activityCode,String phoneNum,HttpServletRequest request,Date nowDate,ACTION actionType){
		ResultBean<String> result = new ResultBean<String>();
		if(phoneNum.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$/")){    //判断邮箱正则 
			result = submitDufaultType(activityCode, phoneNum, request, nowDate, actionType);
		}else{
			result.setProperties(false, RESULTTYPE.emailWrong.name(), "不好意思，你的邮箱输入有误，请重新输入。");
		}
		return result;
	}
	
	/**
	 * 判断活动是否过期
	 * @param actionCode
	 * @return
	 */
	public ResultBean<Date> isExpired(String actionCode){
		ResultBean<Date> result = new ResultBean<Date>();
		Date nowDate = new Date();
		ContrParmBean controParm = new UserLotteryBll().getContrParm(Integer.parseInt(actionCode));
		if(controParm==null){
			result.setProperties(false, "活动还未开始！！", nowDate);
		}else{
			if(nowDate.getTime()<controParm.getActivityStart().getTime()){    
				result.setProperties(false, "活动还未开始！！", nowDate);
			}else if(nowDate.getTime()>controParm.getActivityEnd().getTime()){
				result.setProperties(false, "活动已结束！！", nowDate);
			}else{
				result.setProperties(true, "活动正在进行中，多谢您的参与！！", nowDate);
			}
		}
		return result;
	}
	/**
	 * 判断该活动ID下是否有searchPara的中奖信息
	 * @param activityId
	 * @param searchPara
	 * @return
	 */
	public Map<String,String> isExist(int activityId, String searchPara) {
		return sendPresentLogBLL.isExist(activityId, searchPara);
	}
	/**
	 * 判断相同同一活动相同IP获奖个数
	 * @param ip
	 * @param activityId
	 * @return
	 */
	public boolean SameIPNum(String ip, int activityId,int count) {
		return sendPresentLogBLL.SameIPNum(ip, activityId,count);
	}
	/**
	 * 获取奖品，并插入获奖日志
	 * @param request
	 * @param presentLog
	 * @param nowDate
	 * @return
	 */
	public ResultBean<String> operation(HttpServletRequest request,PresentLogParaBean presentLog,Date nowDate,ACTION actionType){
		ResultBean<String> result = new ResultBean<String>();
		presentLog.setIp(Ip.getCurrent(request).asString());    //获取当前IP
//		presentLog.setIp("192.168.6.195");
		PresentMesBean present = hdActivityCodeBLL.getPresent(Integer.parseInt(presentLog.getActionCode()));
		if(present==null){
			result.setProperties(false, RESULTTYPE.noPrizes.name(), "亲,礼包都已经抢光啦！！");
		}else{
			result.setProperties(true, RESULTTYPE.getPrizes.name(), presentLog.getAccount()+"您好！;您的礼包码是:"+present.getPresentMes());
			PresentLogBean para = new PresentLogBean(
					 presentLog.getActionCodeInt()
					,presentLog.getAccount()
					,actionType.getSource()
					,actionType.getSourceCode()
					,actionType.getPresentType()
					,present.getPresentMes()
					,format.format(nowDate)
					,presentLog.getIp()); 
			sendPresentLogBLL.insertWinningMes(para);
		}
		return result;
	}
}
