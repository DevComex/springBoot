package cn.gyyx.action.dao.wdoldplayers;

import java.util.List;

import cn.gyyx.action.beans.wdoldplayers.NewPageBean;
import cn.gyyx.action.beans.wdoldplayers.PageBean;
import cn.gyyx.action.beans.wdoldplayers.WdOldWantedInfoBean;

import org.apache.ibatis.annotations.Param;

public interface IWdOldWantedInfoBean {
	public void insertWdOldWantedInfoBean(WdOldWantedInfoBean bean);
	public WdOldWantedInfoBean selectWdOldWantedInfoBeanByCode(@Param("code")int code);
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByAccount(@Param("myAccount")String myAccount);
	public void updateWdOldWantedInfoBean(WdOldWantedInfoBean bean);
	public List<WdOldWantedInfoBean> selectWdOldWantedInfoBeanByRole(WdOldWantedInfoBean bean);
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByMe(WdOldWantedInfoBean bean);
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByWanted(WdOldWantedInfoBean bean);
	public List<WdOldWantedInfoBean> getWantedInfoByPage(PageBean<WdOldWantedInfoBean> pageBean);
	public int getWantedCount(PageBean<WdOldWantedInfoBean> pageBean);
	public List<WdOldWantedInfoBean> selectWdOldWantedByPage(NewPageBean<WdOldWantedInfoBean> newPageBean);
	public int selectCount(NewPageBean<WdOldWantedInfoBean> newPageBean);
	public void updatecheckFlag(WdOldWantedInfoBean bean);
}