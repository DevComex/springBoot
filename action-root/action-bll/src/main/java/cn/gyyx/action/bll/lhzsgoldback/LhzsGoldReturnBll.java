package cn.gyyx.action.bll.lhzsgoldback;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnApplyBean;
import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnUserBean;
import cn.gyyx.action.dao.lhzsgoldback.LhzsGoldReturnDao;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月16日 下午12:58:55
 * 描        述：
 */
public class LhzsGoldReturnBll {
	private LhzsGoldReturnDao lhzsGoldReturnDao = new LhzsGoldReturnDao();

	public LhzsGoldReturnUserBean getUserByAccount(String account,
			SqlSession session) {
		return lhzsGoldReturnDao.getUserByAccount(account,session);
	}

	public int getUserApplyCountByAccount(String account, SqlSession session) {
		return lhzsGoldReturnDao.getUserApplyCountByAccount(account,session);
	}

	public void insertUserApply(LhzsGoldReturnApplyBean lhzsGoldReturnApplyBean, SqlSession session) {
		lhzsGoldReturnDao.insertUserApply(lhzsGoldReturnApplyBean,session);
	}

}
