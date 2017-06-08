package cn.gyyx.action.bll.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.dao.wd10yearcoser.UserfavoriteDao;

public class UserfavoriteBll {
	private UserfavoriteDao userfavoriteDao = new UserfavoriteDao();

	/***
	 * 添加资源
	 * 
	 * @param resource
	 */
	public void addUserfavorite(UserfavoriteBean userfavorite) throws Exception {
		userfavoriteDao.addFavorite(userfavorite);
	}
	
	/***
	 * 添加资源
	 * 
	 * @param resource
	 */
	public void addUserfavorite(UserfavoriteBean userfavorite, SqlSession session) throws Exception {
		userfavoriteDao.addFavorite(userfavorite, session);
	}
	
	/***
	 * 查找资源点赞量
	 * 
	 * @param resource
	 */
	public int countFavoriteNumBySourceCode(int sourceCode, SqlSession session) throws Exception {
		return userfavoriteDao.countFavoriteNumBySourceCode(sourceCode, session);
	}
	
	/***
	 * 查找资源点赞量
	 * 
	 * @param resource
	 */
	public int countFavoriteNumByResourceCode(int resourceCode, SqlSession session) throws Exception {
		return userfavoriteDao.countFavoriteNumByResourceCode(resourceCode, session);
	}
	
	/***
	 * 根据查找条件查找用户资源列表
	 * 
	 * @param code
	 */
	public List<UserfavoriteBean> findFavoriteByUser(int userCode){
		return userfavoriteDao.findFavoriteByUser(userCode);
	}
	
	/***
	 * 根据查找条件查找官方资源列表
	 * 
	 * @param code
	 */
	public List<UserfavoriteBean> findOfficialFavoriteByUser(int userCode){
		return userfavoriteDao.findOfficialFavoriteByUser(userCode);
	}
	
	/***
	 * 根据查找条件查找资源列表
	 * 
	 * @param code
	 */
	public List<UserfavoriteBean> findFavoriteByResource(int resourceCode,int userId,boolean flag){
		return userfavoriteDao.findFavoriteByResource(resourceCode,userId,flag);
	}
	
}
