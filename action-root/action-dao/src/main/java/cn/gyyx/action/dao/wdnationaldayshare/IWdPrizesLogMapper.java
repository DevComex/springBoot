package cn.gyyx.action.dao.wdnationaldayshare;

import cn.gyyx.action.beans.wdnationalday.WdPrizesLogBean;

public interface IWdPrizesLogMapper {
    //查询奖品剩余数量
	public int selectPrizesNum(WdPrizesLogBean wdPrizesLogBean);
	//减少奖品数量
	public int reducePrizesNum(WdPrizesLogBean wdPrizesLogBean);
}
