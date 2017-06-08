package cn.gyyx.action.service.lhcj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activityCode.ConfigBean;
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
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LHCJLotteryService {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(LHCJLotteryService.class);
	private SendPresentLogBLL sendPresentLogBLL = new SendPresentLogBLL();
	private HdActivityCodeBLL hdActivityCodeBLL = new HdActivityCodeBLL();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	public ResultBean<String> getActivityCode(HttpServletRequest request,String actionCode,String phoneNum,IMPORTTYPE type){
		ResultBean<String> result = new ResultBean<String>();
		ContrParmBean controParm = new UserLotteryBll().getContrParm(Integer.parseInt(actionCode));    //传入活动编号活动编号
		Date nowDate = new Date();
		if(nowDate.getTime()<controParm.getActivityStart().getTime()){    //判断活动是否过期，没过期进入以下操作
			result.setProperties(false, "notstart", "活动还未开始！！");
			return result;
		}else if(nowDate.getTime()>controParm.getActivityEnd().getTime()){
			result.setProperties(false, "overdue", "活动已结束！！");
		}else{
			switch(type){
			case phone :{
				result = submitPhone(actionCode,phoneNum,request,nowDate);
			}
			}
		}
		return result;
	}
	/**
	 * 输入手机号
	 * @param loginIP
	 * @param phoneNum
	 * @param result
	 * @return
	 */
	private ResultBean<String> submitPhone(String activityCode,String phoneNum,HttpServletRequest request,Date nowDate){
		ResultBean<String> result = new ResultBean<String>();
		PresentLogParaBean paraBean = new PresentLogParaBean(activityCode,phoneNum);
		if(phoneNum.matches("^[1][358][0-9]{9}$")){    //判断手机正则 
			result = operationPhone(request,paraBean,nowDate);
		}else{
			result.setProperties(false, RESULTTYPE.phoneWrong.name(), "不好意思，您的手机号输入有误，请重新输入。");
		}
		return result;
	}
	/**
	 * 判断每个IP最多只能输入5个不同的手机号
	 * 判断每个手机号只能领取一个奖
	 */
	private ResultBean<String> operationPhone(HttpServletRequest request,PresentLogParaBean presentLog,Date nowDate){
		ResultBean<String> result = new ResultBean<String>();
		presentLog.setIp(Ip.getCurrent(request).asString());    //获取当前IP
//		presentLog.setIp("192.168.6.194");
		Map<String,String> isExist = sendPresentLogBLL.isExist(Integer.parseInt(presentLog.getActionCode()), presentLog.getAccount());
		if(isExist.get("isExist").equals(JUDGE.trueStr.getVariable())){    //判断输入的是否已经存在...
			result.setProperties(false, RESULTTYPE.existed.name(), isExist.get("presentName"));
		}else{
			boolean isSatisfy = sendPresentLogBLL.SameIPNum(presentLog.getIp(), Integer.parseInt(presentLog.getActionCode()),5);
			if(isSatisfy){    //判断，这个IP是否已经输入5个手机号了
				//从奖品表中查出奖品
				PresentMesBean present = hdActivityCodeBLL.getPresent(Integer.parseInt(presentLog.getActionCode()));
				if(present==null){
					result.setProperties(false, "phone", "亲,奖品都已经抢光啦！！");
				}else{
					result.setProperties(true, "phone", present.getPresentMes());
					PresentLogBean para = new PresentLogBean(
							 presentLog.getActionCodeInt()
							,presentLog.getAccount()
							,ConfigBean.ACTION.LHCJ.getSource()
							,ConfigBean.ACTION.LHCJ.getSourceCode()
							,ConfigBean.ACTION.LHCJ.getPresentType()
							,present.getPresentMes()
							,format.format(nowDate)
							,presentLog.getIp()); 
					sendPresentLogBLL.insertWinningMes(para);
				}
			}else{
				result.setProperties(false, RESULTTYPE.overIP.name(), "亲，您已领取太多次，礼包更新后再来试下吧！");
			}
		}
		return result;
	}
}
