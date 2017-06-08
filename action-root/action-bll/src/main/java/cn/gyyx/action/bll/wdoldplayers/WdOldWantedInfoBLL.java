package cn.gyyx.action.bll.wdoldplayers;

import java.util.List;

import cn.gyyx.action.beans.wdoldplayers.NewPageBean;
import cn.gyyx.action.beans.wdoldplayers.PageBean;
import cn.gyyx.action.beans.wdoldplayers.WdOldWantedInfoBean;
import cn.gyyx.action.dao.wdoldplayers.WdOldWantedInfoDAO;

public class WdOldWantedInfoBLL {
	private WdOldWantedInfoDAO wdOldWantedInfoDAO = new WdOldWantedInfoDAO();
	public void insertWdOldWantedInfoBean(WdOldWantedInfoBean wdOldWantedInfoBean) {
		wdOldWantedInfoDAO.insertWdOldWantedInfoBean(wdOldWantedInfoBean);
	}
	public WdOldWantedInfoBean getWdOldWantedByCode(int code){
		return wdOldWantedInfoDAO.selectWdOldWantedInfoBeanByCode(code);
	}
	public List<WdOldWantedInfoBean> selectWdOldWantedInfoBeanByRole(WdOldWantedInfoBean bean){
		return wdOldWantedInfoDAO.selectWdOldWantedInfoBeanByRole(bean);
		
	}
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByMe(WdOldWantedInfoBean bean){
		return wdOldWantedInfoDAO.getWdOldWantedInfoBeanByMe(bean);
	}
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByAccount(String myAccount){
		return wdOldWantedInfoDAO.getWdOldWantedInfoBeanByAccount(myAccount);
		
	}
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByWanted(WdOldWantedInfoBean bean){
		return wdOldWantedInfoDAO.getWdOldWantedInfoBeanByWanted(bean);
	}
	public List<WdOldWantedInfoBean> getWantedInfoByPage(PageBean<WdOldWantedInfoBean> pageBean){
		return wdOldWantedInfoDAO.getWantedInfoByPage(pageBean);
		
	}
	public int getWantedCount(PageBean<WdOldWantedInfoBean> pageBean){
		return wdOldWantedInfoDAO.getWantedCount(pageBean);
	}
	public void updateWdOldWantedInfoBean(WdOldWantedInfoBean wdOldWantedInfoBean) {
		wdOldWantedInfoDAO.updateWdOldWantedInfoBean(wdOldWantedInfoBean);
	}
	public void updatecheckFlag(WdOldWantedInfoBean bean){
		wdOldWantedInfoDAO.updatecheckFlag(bean);
	}
	public List<WdOldWantedInfoBean> selectWdOldWantedByPage(NewPageBean<WdOldWantedInfoBean> newPageBean){
		return wdOldWantedInfoDAO.selectWdOldWantedByPage(newPageBean);
	}
	public int selectCount(NewPageBean<WdOldWantedInfoBean> newPageBean){
		return wdOldWantedInfoDAO.selectCount(newPageBean);
		
	}
}
