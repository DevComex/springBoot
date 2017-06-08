package cn.gyyx.action.bll.wdpkforecast;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.wdpkforecast.WdPkAllLotteryInfo;
import cn.gyyx.action.dao.wdpkforecast.WdPkLotteryDao;

public class WdPkLotteryBll {

	private WdPkLotteryDao wdPkLotteryDao = new WdPkLotteryDao();

	public ResultBean<List<ActionLotteryLogPO>> checkIsAvailable(ActionLotteryLogPO po, SqlSession sqlSession) {

		ResultBean<List<ActionLotteryLogPO>> result = new ResultBean<List<ActionLotteryLogPO>>(false, "未知错误", null);

		List<ActionLotteryLogPO> list = wdPkLotteryDao.selectIsAvailable(po, sqlSession);
		if (list == null || list.size() == 0) {
			result.setMessage("该用户/该IP还未获得有效奖品");
			result.setIsSuccess(true);
			result.setData(list);
			return result;
		} else {

			result.setIsSuccess(false);
			result.setMessage("该用户/该IP已获得有效奖品");
			result.setData(list);
			return result;
		}

	}

	public void insertLotteryLog(ActionLotteryLogPO po, SqlSession sqlSession) {

		wdPkLotteryDao.insertLotteryLog(po, sqlSession);

	}

	public List<ActionLotteryLogPO> selectUserLotteryInfo(String userId) {

		return wdPkLotteryDao.selectUserLotteryInfo(userId);
	}

	public List<WdPkAllLotteryInfo>selectAllUserLotteryInfo(int type){
		
		
		return wdPkLotteryDao.selectAllUserLotteryInfo(type);
		
	}
	
	public void insertALLLotteryLog(ActionLotteryLogPO po, SqlSession sqlSession) {

		wdPkLotteryDao.insertAllLotteryLog(po, sqlSession);

	}
	
}
