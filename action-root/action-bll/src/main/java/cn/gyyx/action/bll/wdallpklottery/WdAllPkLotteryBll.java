package cn.gyyx.action.bll.wdallpklottery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.dao.wdpkforecast.WdPkLotteryDao;

public class WdAllPkLotteryBll {

	private WdPkLotteryDao wdPkLotteryDao = new WdPkLotteryDao();

	// 查询当前用户中奖信息
	public List<ActionLotteryLogPO> selectUserLotteryInfoBYactioncode(ActionLotteryLogPO po) {

		return wdPkLotteryDao.selectUserLotteryInfoBYactioncode(po);

	}

	public List<Map<String, String>> getLotteryCountGroupByDate(
			int activityCode) {
		return wdPkLotteryDao.getLotteryCountGroupByDate(activityCode);
	}

	public Map<String, String> covertMapFromListByDate(
			List<Map<String, String>> loginLog) {
		Map<String,String> s = new HashMap<>();
		if(loginLog != null){
			for(Map<String,String> d : loginLog){
				String a = d.get("lotteryTime");
				String b = d.get("lotteryCount");
				s.put(a, b+"");
			}
		}
		return s;
	}
}
