package cn.gyyx.action.service.lottery;

import java.util.List;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;

public interface ILotteryMethod {
	//中奖方式的实现
	public <T> ResultBean<UserLotteryBean> getPrize(int userCode,ContrParmBean contrParm,List<T> list);
	

}
