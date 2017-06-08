package cn.gyyx.action.bll.cs2sign;

import java.util.Date;
import java.util.List;

import cn.gyyx.action.beans.cs2sign.Cs2BindInfoBean;
import cn.gyyx.action.beans.cs2sign.Cs2SignBean;
import cn.gyyx.action.beans.cs2sign.Cs2SignInfoBean;
import cn.gyyx.action.dao.cs2sign.Cs2BindInfoDAO;
import cn.gyyx.action.dao.cs2sign.Cs2SignDAO;
import cn.gyyx.action.dao.cs2sign.Cs2SignInfoDAO;

public class Cs2signBLL {
	private Cs2SignInfoDAO signInfoDao = new Cs2SignInfoDAO();
	private Cs2BindInfoDAO bindInfoBll = new Cs2BindInfoDAO();
	private Cs2SignDAO signDao = new Cs2SignDAO();
	public int selectCs2SignInfoCountByAccount(String account) {
		return signInfoDao.selectCs2SignInfoCountByAccount(account);
	}

	public boolean insertCs2SignInfoBean(Cs2SignInfoBean info) {
		return signInfoDao.insertCs2SignInfoBean(info);
	}

	public Cs2SignInfoBean selectCs2SignInfoBeanByAccount(String account) {
		return signInfoDao.selectCs2SignInfoBeanByAccount(account);
	}
	
	public int selectCs2BindInfoCountByServerAndChar(String server,String character) {
		return bindInfoBll.selectCs2BindInfoCountByServerAndChar(server, character);
	}
	
	public boolean insertCs2BindInfoBean(Cs2BindInfoBean cs2BindInfoBean) {
		return bindInfoBll.insertCs2BindInfoBean(cs2BindInfoBean);
	}
	
	public List<Cs2BindInfoBean> selectCs2BindInfoBeanByAccount(String account) {
		return bindInfoBll.selectCs2BindInfoBeanByAccount(account);
	}
	
	public boolean isSignToday(String account) {
		return signDao.selectCs2SignBeanTodayCount(account) != 0;
	}
	
	public void insert(Cs2SignBean cs2SignBean) {
		signDao.insertCs2SignBean(cs2SignBean);
	}
	
	public void updateCs2SignInfo(Date date,int continuity,String account) {
		signInfoDao.updateCs2SignInfo(date, continuity, account);
	}
}
