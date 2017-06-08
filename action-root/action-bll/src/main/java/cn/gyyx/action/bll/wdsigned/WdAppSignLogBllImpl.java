package cn.gyyx.action.bll.wdsigned;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;
import cn.gyyx.action.dao.wdsigned.IWdAppSignLogMapper;
import cn.gyyx.action.dao.wdsigned.WdAppSignLogDao;

public class WdAppSignLogBllImpl implements IWdAppSignLogBll {

	IWdAppSignLogMapper iWdAppSignLogMapper;
	
	@Override
	public int insert(WdAppSignedLogBean bean) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return iWdAppSignLogMapper.insert(bean);
	}

	@Override
	public WdAppSignedLogBean getTodaySignLog(String account, int serverId, String batch,String today) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return	iWdAppSignLogMapper.getTodaySignLog(account, serverId, batch,today);
	}

	@Override
	public List<String> getSignHistoryDate(String account, int serverId, String batch) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return iWdAppSignLogMapper.getSignHistoryDate(account, serverId, batch);
	}

	@Override
	public int getSignCountByAccount(String account, int serverId, String batch) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return iWdAppSignLogMapper.getSignCountByAccount(account, serverId, batch);
	}

	@Override
	public int insert(WdAppSignedLogBean bean, SqlSession session) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return iWdAppSignLogMapper.insert(bean,session);
	}

	@Override
	public int getSignCountByAccount(String account, int serverId, String batch, SqlSession session) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return iWdAppSignLogMapper.getSignCountByAccount(account, serverId, batch,session);
	}

	@Override
	public WdAppSignedLogBean getTodaySignLog(String account, int serverId, String batch, String today,
			SqlSession session) {
		iWdAppSignLogMapper=new WdAppSignLogDao();
		return	iWdAppSignLogMapper.getTodaySignLog(account, serverId, batch,today,session);
	}

}
