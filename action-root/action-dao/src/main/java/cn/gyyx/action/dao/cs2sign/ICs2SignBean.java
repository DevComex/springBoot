package cn.gyyx.action.dao.cs2sign;

import cn.gyyx.action.beans.cs2sign.Cs2SignBean;

public interface ICs2SignBean {
	public void insertCs2SignBean(Cs2SignBean bean);
	public int selectCs2SignBeanTodayCount(String account);
}