package cn.gyyx.action.service.lottery.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.bll.wdnationalday.WdNationaldayEnrollBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.lottery.ILotterySendPrizesService;

public class LotterySendPrizesToGame implements ILotterySendPrizesService {

	private String message;
	
	@Override
	public boolean pushPrizes(String userId, String prizesCode) {
		logger.info("LotterySendPrizesToGame.pushPrizes => userId=" + userId + ";prizesCode=" + prizesCode);
		
		try {
			// 游戏中奖品代码不为空，则发放奖品
			WdNationaldayEnrollBll wdNationaldayEnrollBll = new WdNationaldayEnrollBll();
			WdNationaldayEnrollBean bean = wdNationaldayEnrollBll.getUserInfoByOpenId(userId);
			
			logger.info("LotterySendPrizesToGame.pushPrizes => WdNationaldayEnrollBean=" + JSON.toJSONString(bean));
			
			//先获得服务器
			CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	        ServerInfoBean serverInfo = callWebApiAgent.getServerStatusFromWebApi(bean.getServerId());
	        
	        logger.info("LotterySendPrizesToGame.pushPrizes => ServerInfoBean=" + JSON.toJSONString(serverInfo));
	        
	        Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	        c.add(Calendar.YEAR, 1);
	        java.util.Date newdate = c.getTime();
	        
	        ProcessResult sendRes = CallWebServiceAgent.givePresents(2, bean.getAccount(), prizesCode, formatter.format(newdate), "", serverInfo);
			
	        logger.info("LotterySendPrizesToGame.pushPrizes => ProcessResult=" + JSON.toJSONString(sendRes));
	        
	        if(sendRes != null) {
	        	// 物品发送接口调用成功
	        	if(sendRes.getErrorCode() != 0) {
	        		// 发送物品失败
	        		message = "发放物品到游戏失败！";
	        		logger.info("LotterySendPrizesToGame.pushPrizes => " + message);
					return false;
	        	}
	        	else {
	        		logger.info("LotterySendPrizesToGame.pushPrizes => 发送物品成功");
	        		return true;
	        	}
	        }
	        else {
	        	// 物品发送接口调用失败
	    		message = "物品发送接口调用失败！";
	    		logger.info("LotterySendPrizesToGame.pushPrizes => " + message);
				return false;
	        }
		}
		catch(Exception e) {
			logger.error("LotterySendPrizesToGame.pushPrizes", e.getCause());
		}
		
		return true;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
