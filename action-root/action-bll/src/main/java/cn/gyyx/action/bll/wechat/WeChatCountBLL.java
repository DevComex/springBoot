/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:统计业务逻辑
************************************************/
package cn.gyyx.action.bll.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.WeChatCountBean;
import cn.gyyx.action.dao.wechat.WeChatCountDao;

public class WeChatCountBLL {

	private WeChatCountDao weChatCountDao = new WeChatCountDao();

	public void accessCount(WeChatCountBean weChatCountBean) {
		weChatCountDao.addAccessCount(weChatCountBean);
	}

	public Integer countByType(String countType) {
		return weChatCountDao.selectCountByType(countType);
	}

	public List<WeChatCountBean> selectCountWithPM() {
		return weChatCountDao.selectCountWithPM();
	}
	
	public Integer getDayCountByType(String countType,String nickName,String dateStr) {
		return weChatCountDao.selectDayCountByType(countType,nickName,dateStr);
	}
}
