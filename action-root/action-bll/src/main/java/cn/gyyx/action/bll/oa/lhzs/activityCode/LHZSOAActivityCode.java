package cn.gyyx.action.bll.oa.lhzs.activityCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.ActivityCodeChannelBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.InsertActivtionCodeBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.UserMesBean;
import cn.gyyx.action.dao.lhzs.oa.activityCode.LHZSendPresentLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LHZSOAActivityCode {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(LHZSOAActivityCode.class);
	private LHZSendPresentLogDAO presentLogDAO = new LHZSendPresentLogDAO();
	/**
	 * 激活-产品
	 */
	public ResultBean<String> getActivationProduct(String startTime,String endTime) {
		ResultBean<String> result = new ResultBean<String>();
		Integer resultInt = presentLogDAO.getActivationProduct(startTime, endTime);
		if(resultInt==-1){
			result.setProperties(false, "error", "查询出错！！");
		}else if(resultInt==null){
			result.setProperties(true,startTime.split(" ")[0] , "0");
		}else{
			result.setProperties(true, startTime.split(" ")[0], resultInt.toString());
		}
		return result;
	}
	/**
	 * 激活-市场
	 */
	public ResultBean<String> getActivationMarket(String startTime,String endTime) {
		ResultBean<String> result = new ResultBean<String>();
		Integer resultInt = presentLogDAO.getActivationMarket(startTime, endTime);
		if(resultInt==-1){
			result.setProperties(false, "error", "查询出错！！");
		}else if(resultInt==null){
			result.setProperties(true,startTime.split(" ")[0], "0");
		}else{
			result.setProperties(true, startTime.split(" ")[0], resultInt.toString());
		}
		return result;
	}
	/**
	 * 发放-产品
	 */
	public ResultBean<String> getSendMarket(String startTime,String endTime) {
		ResultBean<String> result = new ResultBean<String>();
		Integer resultInt = presentLogDAO.getSendMarket(startTime, endTime);
		Integer resultInt2 = presentLogDAO.getSendMarket2(startTime, endTime);
		if(resultInt==-1||resultInt2==-1){
			result.setProperties(false, "error", "查询出错！！");
		}else if(resultInt==null||resultInt2==null){
			result.setProperties(true,startTime.split(" ")[0], "0");
		}else{
			result.setProperties(true, startTime.split(" ")[0], String.valueOf(resultInt+resultInt2));
		}
		return result;
	}
	/**
	 * 添加
	 * @param para
	 */
	public Map<String,Object> insertActivationCode(InsertActivtionCodeBean para) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("num", para.getNum());
		paraMap.put("channel", para.getChannel());
		paraMap.put("channelStr", para.getChannelStr());
		paraMap.put("cardType", para.getCardType());
		paraMap.put("status", para.getStatus());
		paraMap.put("gameId", para.getGameId());
		paraMap.put("desc", para.getDesc());
		paraMap.put("actionCode", para.getActionCode());
		paraMap.put("errorMes", para.getErrorMes());
		presentLogDAO.insertActivationCode(paraMap);
		return paraMap;
	}
	public List<ActivityCodeChannelBean> searchActivityChannel() {
		return presentLogDAO.searchActivityChannel();
	}
	/**
	 * 直接输出UserID
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserMesBean> getActivityMarketDetailed(String startTime,String endTime){
		return presentLogDAO.getActivityMarketDetailed(startTime,endTime);
	}
	/**
	 * 转换成用户账号
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserMesBean> getActivityMarketDetailedAll(String startTime,String endTime){
		return presentLogDAO.getActivityMarketDetailed(startTime,endTime);
	}
	/**
	 * 查询激活市场 详细信息
	 * @param startTime
	 * @param endTime
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	public List<UserMesBean> getActivityMarketDetailedToCode(String startTime,
			String endTime, int startCode, int endCode) {
		return presentLogDAO.getActivityMarketDetailedToCode(startTime, endTime, startCode, endCode);
	}
}
