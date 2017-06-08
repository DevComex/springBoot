package cn.gyyx.action.dao.wdoldplayers;

import java.util.List;

import cn.gyyx.action.beans.wdoldplayers.NewPageBean;
import cn.gyyx.action.beans.wdoldplayers.PageBean;
import cn.gyyx.action.beans.wdoldplayers.WdOldWantedInfoBean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.dao.MyBatisConnectionFactory;

import org.apache.ibatis.session.SqlSession;

public class WdOldWantedInfoDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	public void insertWdOldWantedInfoBean(WdOldWantedInfoBean wdOldWantedInfoBean) {
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			mapper.insertWdOldWantedInfoBean(wdOldWantedInfoBean);
			session.commit();
		}
	}
	public WdOldWantedInfoBean selectWdOldWantedInfoBeanByCode(int code) {
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.selectWdOldWantedInfoBeanByCode(code);
		}
	}
	public void updateWdOldWantedInfoBean(WdOldWantedInfoBean wdOldWantedInfoBean) {
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			mapper.updateWdOldWantedInfoBean(wdOldWantedInfoBean);
			session.commit();
		}
	}
	public List<WdOldWantedInfoBean> selectWdOldWantedInfoBeanByRole(WdOldWantedInfoBean bean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.selectWdOldWantedInfoBeanByRole(bean);
		}
	}
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByAccount(String myAccount){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.getWdOldWantedInfoBeanByAccount(myAccount);
		}
	}
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByWanted(WdOldWantedInfoBean bean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.getWdOldWantedInfoBeanByWanted(bean);
		}
	}
	public List<WdOldWantedInfoBean> getWdOldWantedInfoBeanByMe(WdOldWantedInfoBean bean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.getWdOldWantedInfoBeanByMe(bean);
		}
	}
	public List<WdOldWantedInfoBean> getWantedInfoByPage(PageBean<WdOldWantedInfoBean> pageBean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.getWantedInfoByPage(pageBean);
		}
	}
	public int getWantedCount(PageBean<WdOldWantedInfoBean> pageBean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.getWantedCount(pageBean);
		}
	}
	public void updatecheckFlag(WdOldWantedInfoBean bean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			mapper.updatecheckFlag(bean);
			session.commit();
		}
	}
	public List<WdOldWantedInfoBean> selectWdOldWantedByPage(NewPageBean<WdOldWantedInfoBean> newPageBean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.selectWdOldWantedByPage(newPageBean);
		}
	}
	public int selectCount(NewPageBean<WdOldWantedInfoBean> newPageBean){
		try(SqlSession session = factory.openSession()) {
			IWdOldWantedInfoBean mapper = session.getMapper(IWdOldWantedInfoBean.class);
			return mapper.selectCount(newPageBean);
		}
	}
}