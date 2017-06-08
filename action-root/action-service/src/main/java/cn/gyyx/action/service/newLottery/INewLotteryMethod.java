package cn.gyyx.action.service.newLottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;

public interface INewLotteryMethod {
	// 中奖方式的实现
	public <T> ResultBean<UserLotteryBean> getPrize(int userCode,
			ContrParmBean contrParm, List<T> list, SqlSession session);

}
