package cn.gyyx.action.dao.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.WeChatCountBean;

public interface IWeChatCountMapper {
	public void addAccessCount(WeChatCountBean weChatCountBean);

	public Integer selectCountByType(String countType);

	public List<WeChatCountBean> selectCountWithPM();
	
	public Integer selectDayCountByType(String countType,String nickName,String dateStr);
}
