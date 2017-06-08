package cn.gyyx.action.dao.mobile.website;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.mobile.website.MobileRegisteBean;
import cn.gyyx.action.beans.mobile.website.MobileWebsiteRegisteLog;

public interface IRegisteMapper {
	/**
	 * 插入注册信息
	 * @param para
	 * @return
	 */
	public Integer insertRegiste(MobileRegisteBean para);
	public Integer isExist(@Param("phone")String phone);
	
	public Integer insertRegisteLog(MobileWebsiteRegisteLog para);
}
