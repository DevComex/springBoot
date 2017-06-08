package cn.gyyx.action.bll.wdlight2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdlight2017.LightBean;
import cn.gyyx.action.dao.wdlight2017.LightDao;

public class LightBll {
	private LightDao lightDao = new LightDao();
	
	public List<LightBean> getLightAll () {
		return lightDao.getLightALl();
	}
	
	public List<LightBean> getLightAll (SqlSession sqlSession) {
		return lightDao.getLightALl(sqlSession);
	}
	
	public LightBean getLightByLevel(int level, SqlSession sqlSession) {
		return lightDao.getLightByLevel(level, sqlSession);
	}
	
	public void updateLight(LightBean lightBean, SqlSession sqlSession) {
		lightDao.updateLight(lightBean, sqlSession);
	}
	
	public void updateLight(LightBean lightBean) {
		lightDao.updateLight(lightBean);
	}
	
	// 获取灯应该的状态
	public int getCurrentLightType(int limitNum,int wishNum) {
		int halfNUm = limitNum / 2;
		if (wishNum < halfNUm) {
			return 1;
		} else if (wishNum >= halfNUm && wishNum < limitNum) {
			return 2;
		} else if (wishNum >= limitNum) {
			return 3;
		} else {
			return 0;
		}
	}
	
	
}
