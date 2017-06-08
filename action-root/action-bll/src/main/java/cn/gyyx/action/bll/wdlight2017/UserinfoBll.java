package cn.gyyx.action.bll.wdlight2017;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wdlight2017.UserinfoBean;
import cn.gyyx.action.dao.wd10yearcoser.ResourceBeanMapper;
import cn.gyyx.action.dao.wdlight2017.UserinfoBeanMapper;
import cn.gyyx.action.dao.wdlight2017.UserinfoDao;

public class UserinfoBll {
	private UserinfoDao userinfoDao = new UserinfoDao();
	
	public UserinfoBean getUserinfoBeanByUserId (int userId) {
		return userinfoDao.getUserinfoBeanByUserId(userId);
	}
	
	public UserinfoBean getUserinfoBeanByRoleCode (String roleCode) {
		return userinfoDao.getUserinfoBeanByRoleCode(roleCode);
	}
	
	public UserinfoBean getUserinfoBeanByUserId (int userId, SqlSession sqlSession) {
		return userinfoDao.getUserinfoBeanByUserId(userId,sqlSession);
	}
	
	public void addUserinfoBean(UserinfoBean userinfoBean) {
		userinfoDao.addUserinfoBean(userinfoBean);
	}
	
	public void updateUserinfoBean(UserinfoBean userinfoBean, SqlSession sqlSession) {
		userinfoDao.updateUserinfoBean(userinfoBean,sqlSession);
	}
	
		
}
