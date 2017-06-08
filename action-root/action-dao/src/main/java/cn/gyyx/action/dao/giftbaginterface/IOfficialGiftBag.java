/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月18日上午10:37:30
 * 版本号：v1.0
 * 本类主要用途叙述：官方新手卡
 */



package cn.gyyx.action.dao.giftbaginterface;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.giftinterface.OfficialGiftBagBean;

public interface IOfficialGiftBag {
	public List<OfficialGiftBagBean> getOfficialGiftBagBean(@Param("begin") Date begin,
			@Param("end") Date end,	@Param("serverName") String serverName);

}
