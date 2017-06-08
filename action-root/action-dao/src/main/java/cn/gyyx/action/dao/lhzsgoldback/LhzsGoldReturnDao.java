package cn.gyyx.action.dao.lhzsgoldback;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnApplyBean;
import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnUserBean;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月16日 下午1:00:48
 * 描        述：
 */
public class LhzsGoldReturnDao {

	public LhzsGoldReturnUserBean getUserByAccount(String account,
			SqlSession session) {
		LhzsGoldReturnMapper mapper = session.getMapper(LhzsGoldReturnMapper.class);
		return mapper.getUserByAccount(account);
	}

	public int getUserApplyCountByAccount(String account, SqlSession session) {
		LhzsGoldReturnMapper mapper = session.getMapper(LhzsGoldReturnMapper.class);
		return mapper.getUserApplyCountByAccount(account);
	}

	public void insertUserApply(LhzsGoldReturnApplyBean lhzsGoldReturnApplyBean, SqlSession session) {
		LhzsGoldReturnMapper mapper = session.getMapper(LhzsGoldReturnMapper.class);
	    mapper.insertUserApply(lhzsGoldReturnApplyBean);
	}

}
