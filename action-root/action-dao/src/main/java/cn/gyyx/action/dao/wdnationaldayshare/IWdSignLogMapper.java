package cn.gyyx.action.dao.wdnationaldayshare;

import cn.gyyx.action.beans.wdnationalday.WdSignLogBean;

public interface IWdSignLogMapper {

	//插入刚到达的step
	public int insertsendlog(WdSignLogBean wdSignLogBean);
	//查询已到达的step
	public Integer selectstep(WdSignLogBean wdSignLogBean);
	
}
