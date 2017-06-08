package cn.gyyx.action.dao.sdsg.oa.activityCode;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.oa.lhzs.activityCode.ActivityCodeChannelBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.UserMesBean;

public interface ISDSGSendPresentLogMapper {
	/**
	 * 激活-产品
	 * @return
	 */
	public Integer getActivationProduct(String startTime,String endTime);
	/**
	 * 激活-市场 
	 * @return
	 */
	public Integer getActivationMarket(String startTime,String endTime);
	/**
	 * 发放-产品 （发放未激活）
	 * @return
	 */
	public Integer getSendMarket(String startTime,String endTime);
	/**
	 * 发放-产品 （当天发放激活）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getSendMarket2(String startTime,String endTime);
	/**
	 * 添加激活码
	 * @param para
	 * @return
	 */
	public void insertActivationCode(Map<String,Object>para);
	
	public List<ActivityCodeChannelBean> searchActivityChannel();
	/**
	 * 获取 激活-市场 详细信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserMesBean> getActivityMarketDetailed(String startTime,String endTime);
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserMesBean> getActivityMarketDetailedToCode(String startTime,String endTime,int startCode,int endCode);
	
}
