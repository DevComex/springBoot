package cn.gyyx.action.service.oa.sdsg.activityCode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.ActivityCodeChannelBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.InsertActivtionCodeBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.UserMesBean;
import cn.gyyx.action.bll.oa.sdsg.activityCode.SDSGOAActivityCode;

public class SDSGOAActivityCodeService {
	
	private SDSGOAActivityCode oaActivityCodeBLL = new SDSGOAActivityCode();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private long day = 24*60*60*1000;
	/**
	 * 激活-产品
	 */
	public ResultBean<String> getActivationProduct(Date nowDate) {
		String startTime="",endTime="";
		ResultBean<String> result = new ResultBean<String>();
		try{
			startTime=format.format(new Date(this.startTime(nowDate)));
			endTime=format.format(new Date(this.endTime(nowDate)));
		}catch(Exception e){
			result.setProperties(false, "error", "查询失败！！！");
			return result;
		}
		result = oaActivityCodeBLL.getActivationProduct(startTime, endTime);
		return result;
	}
	/**
	 * 激活-市场
	 */
	public ResultBean<String> getActivationMarket(Date nowDate) {
		String startTime="",endTime="";
		ResultBean<String> result = new ResultBean<String>();
		try{
			startTime=format.format(new Date(this.startTime(nowDate)));
			endTime=format.format(new Date(this.endTime(nowDate)));
		}catch(Exception e){
			result.setProperties(false, "error", "查询失败！！！");
			return result;
		}
		result = oaActivityCodeBLL.getActivationMarket(startTime, endTime);
		return result;
	}
	/**
	 * 发放-产品
	 */
	public ResultBean<String> getSendMarket(Date nowDate) {
		String startTime="",endTime="";
		ResultBean<String> result = new ResultBean<String>();
		try{
			startTime=format.format(new Date(this.startTime(nowDate)));
			endTime=format.format(new Date(this.endTime(nowDate)));
		}catch(Exception e){
			result.setProperties(false, "error", "查询失败！！！");
			return result;
		}
		result = oaActivityCodeBLL.getSendMarket(startTime, endTime);
		return result;
	}
	public ResultBean<String> insertActivationCode(int num,int channel,String channelStr) {
		InsertActivtionCodeBean para = new InsertActivtionCodeBean(num,channel,channelStr,"神道三国封测激活码",0,28,"神道三国对接测试",405,"");
		ResultBean<String> resultBean = new ResultBean<String>();
		Map<String,Object> result = oaActivityCodeBLL.insertActivationCode(para);
		if(result.get("errorMes").equals("")){
			resultBean.setProperties(false, "error", "添加失败！！");
		}else if(result.get("errorMes").equals("error")){
			resultBean.setProperties(false, "error", "添加失败！！！");
		}else if(result.get("errorMes").equals("success")){
			resultBean.setProperties(true, "success", "添加成功！！");
		}else{
			resultBean.setProperties(false, "error", "添加失败！！！！");
		}
		return resultBean;
	}
	public ResultBean<List<ActivityCodeChannelBean>> searchActivityChannel() {
		ResultBean<List<ActivityCodeChannelBean>> result = new ResultBean<List<ActivityCodeChannelBean>>();
		List<ActivityCodeChannelBean> resultList = oaActivityCodeBLL.searchActivityChannel();
		result.setProperties(true, "success", resultList);
		return result;
	}
	public ResultBean<List<UserMesBean>> getActivityMarketDetailed(Date nowDate){
		String startTime="",endTime="";
		ResultBean<List<UserMesBean>> result = new ResultBean<List<UserMesBean>>();
		try{
			startTime=format.format(new Date(this.startTime(nowDate)));
			endTime=format.format(new Date(this.endTime(nowDate)));
		}catch(Exception e){
			result.setProperties(false, "error", null);
			return result;
		}
		List<UserMesBean> resultList = oaActivityCodeBLL.getActivityMarketDetailed(startTime, endTime);
		result.setProperties(true, "success", resultList);
		return result;
	}
	/**
	 * 调用接口 获取用户信息
	 * @param nowDate
	 * @return
	 */
	public ResultBean<List<UserMesBean>> getActivityMarketDetailedAll(Date nowDate){
		String startTime="",endTime="";
		ResultBean<List<UserMesBean>> result = new ResultBean<List<UserMesBean>>();
		try{
			startTime=format.format(new Date(this.startTime(nowDate)));
			endTime=format.format(new Date(this.endTime(nowDate)));
		}catch(Exception e){
			result.setProperties(false, "error", null);
			return result;
		}
		List<UserMesBean> resultList = oaActivityCodeBLL.getActivityMarketDetailed(startTime, endTime);
		result.setProperties(true, "success", resultList);
		return result;
	}
	/**
	 * 查询激活市场详细信息
	 * @param nowDate
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	public ResultBean<List<UserMesBean>> getActivityMarketDetailedToCode(Date nowDate, int startCode, int endCode) {
		String startTime="",endTime="";
		ResultBean<List<UserMesBean>> result = new ResultBean<List<UserMesBean>>();
		try{
			startTime=format.format(new Date(this.startTime(nowDate)));
			endTime=format.format(new Date(this.endTime(nowDate)));
		}catch(Exception e){
			result.setProperties(false, "error", null);
			return result;
		}
		List<UserMesBean> resultBean = oaActivityCodeBLL.getActivityMarketDetailedToCode(startTime, endTime, startCode, endCode);
		result.setProperties(true, startCode+";"+(endCode-1-startCode)+";"+format.format(nowDate), resultBean);
		return result;
	}
	public long startTime(Date nowDate){
		long nowLong = nowDate.getTime();
		long result = nowLong-nowLong%day-(8*60*60*1000);
		return result;
	}
	
	public long endTime(Date nowDate){
		long result = startTime(nowDate)+day;
		return result;
	}
}
