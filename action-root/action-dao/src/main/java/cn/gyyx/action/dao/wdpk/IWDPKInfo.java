package cn.gyyx.action.dao.wdpk;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdallpk.AllPKInfo;

public interface IWDPKInfo {
	public List<String> getAvailableCount();
	public boolean isAccountExist(@Param("account")String account);
	public void addWDPKInfoBean(AllPKInfo bean);
}
