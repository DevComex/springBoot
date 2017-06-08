package cn.gyyx.action.bll.wdpk;

import cn.gyyx.action.beans.wdallpk.AllPKInfo;
import cn.gyyx.action.dao.wdpk.WDPKInfoDAO;

public class WDPKBll {
	private WDPKInfoDAO dao = new WDPKInfoDAO();
	public boolean isAccountAvailable(String account) {
		return dao.isAccountAvailable(account);
	}
	public boolean isAccountExists(String account) {
		return dao.isAccountExists(account);
	}
	public void addWDPKInfoBean(AllPKInfo bean) {
		dao.addWDPKInfoBean(bean);
	}
}
