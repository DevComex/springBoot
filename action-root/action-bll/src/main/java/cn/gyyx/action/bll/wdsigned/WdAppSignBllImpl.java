package cn.gyyx.action.bll.wdsigned;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedBean;
import cn.gyyx.action.beans.wdsigned.WdAppSignedLogBean;
import cn.gyyx.action.dao.wdsigned.IWdAppSignLogMapper;
import cn.gyyx.action.dao.wdsigned.IWdAppSignMapper;
import cn.gyyx.action.dao.wdsigned.WdAppSignDao;
import cn.gyyx.action.dao.wdsigned.WdAppSignLogDao;

public class WdAppSignBllImpl implements IWdAppSignBll {

	IWdAppSignMapper iWdAppSignMapper = new WdAppSignDao();

	@Override
	public WdAppSignedBean getSign(String account, int serverId, String batch) {
		return iWdAppSignMapper.getSign(account, serverId, batch);
	}

	@Override
	public int insert(WdAppSignedBean bean) {
		return iWdAppSignMapper.insert(bean);
	}

	@Override
	public int updateSign(int serialDay, int totalDay, String account, int serverId, String batch) {
		return iWdAppSignMapper.updateSign(serialDay, totalDay, account, serverId, batch);
	}

	@Override
	public int insert(WdAppSignedBean bean, SqlSession session) {
		return iWdAppSignMapper.insert(bean,session);
	}

	@Override
	public int updateSign(int serialDay, int totalDay, String account, int serverId, String batch, SqlSession session) {
		return iWdAppSignMapper.updateSign(serialDay, totalDay, account, serverId, batch,session);
	}

	@Override
	public WdAppSignedBean getSign(String account, int serverId, String batch, SqlSession session) {
		return iWdAppSignMapper.getSign(account, serverId, batch,session);
	}

}
