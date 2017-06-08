package cn.gyyx.action.bll.activityCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.activityCode.ConfigBean.JUDGE;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.dao.activityCode.SendPresentLogDAO;

public class SendPresentLogBLL  {
	private SendPresentLogDAO sendPresentLogDAO = new SendPresentLogDAO();
	/**
	 * 
	 * @param ip
	 * @param activityId
	 * @return
	 */
	public boolean SameIPNum(String ip, int activityId,int count) {
		boolean result = false;
		int resultCount = sendPresentLogDAO.SameIPNum(ip, activityId);
		if(resultCount<count){
			result = true;
		}
		return result;
	}
	/**
	 * 判断是否存在
	 * @param activityId
	 * @param searchPara
	 * @return
	 */
	public Map<String,String> isExist(int activityId, String searchPara) {
		Map<String,String> result = new HashMap<String,String>();
		List<PresentLogBean> para = sendPresentLogDAO.isExist(activityId, searchPara);
		if(para.size()>0){
			result.put("isExist", JUDGE.trueStr.getVariable());
			result.put("account", para.get(0).getAccount());
			result.put("presentName", para.get(0).getPresentName());
		}else{
			result.put("isExist", JUDGE.falseStr.getVariable());
		}
		return result;
//		if(count>0){
//			result=true;
//		}
//		return result;
	}
	/**
	 * 添加获奖信息
	 * @param para
	 * @return
	 */
	public boolean insertWinningMes(PresentLogBean para) {
		return sendPresentLogDAO.insertWinningMes(para);
	}
}
