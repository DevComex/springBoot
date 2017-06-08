package cn.gyyx.action.bll.wdlight2017;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdlight2017.LightOaBean;
import cn.gyyx.action.beans.wdlight2017.ValidWishBean;
import cn.gyyx.action.beans.wdlight2017.WishBean;
import cn.gyyx.action.dao.wdlight2017.WishDao;

public class WishBll {
	private WishDao wishDao = new WishDao();
	
	public List<WishBean> getWishsBylevel (int level) {
		return wishDao.getWishsBylevel(level);
	}
	
	public List<ValidWishBean> getValidWishBean (int pageNum,int page,int status){
		return wishDao.getValidWishBean(pageNum, page, status);
	}
	
	public int modifyWishStatusByCode(int status,int code){
		return wishDao.modifyWishStatusByCode(status, code);
	}
	
	public int getUserWishNumByLevel(int userId, int level) {
		return wishDao.getUserWishNumByLevel(userId, level);
	}
	
	public void addWishBean (WishBean wishBean, SqlSession sqlSession) {
		wishDao.addWishBean(wishBean, sqlSession);
	}
	
	public List<WishBean> getMyWishAll (int userId) {
		return wishDao.getMyWishAll(userId);
	}
	
	public int getWishCount(int status){
		return wishDao.getWishCount(status);
	}
	
	public List<WishBean> findWishAll (int num) {
		return wishDao.findWishAll(num);
	}
	
	public int modifyLightLimitNum(int limitNum, int level){
		return wishDao.modifyLightLimitNum(limitNum, level);
	}
	
	public List<LightOaBean> getLightLevel(){
		return wishDao.getLightLevel();
	}
	public Map<Integer,Integer> getLightPeople(){
		return wishDao.getLightPeople();
	}
	public int getWishNumByLevel(int level) {
		return wishDao.getWishNumByLevel(level);
	}
	
	public int getWishCountByStatus(int status) {
		return wishDao.getWishCountByStatus(status);
	}
	
	public int getWishUserCountByLevel(int level) {
		return wishDao.getWishUserCountByLevel(level);
	}
	
	public int getWishUserCountByLevel(int level, SqlSession sqlSession) {
		return wishDao.getWishUserCountByLevel(level, sqlSession);
	}
	
	
	
}
