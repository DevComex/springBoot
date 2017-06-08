package cn.gyyx.action.bll.wdsigned;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdsigned.WdAppSignedSendPrizeBean;
import cn.gyyx.action.dao.wdsigned.IWdAppSignSendPrizeMapper;
import cn.gyyx.action.dao.wdsigned.WdAppSignSendPrizeDao;

public class WdAppSignSendPrizeBllImpl implements IWdAppSignSendPrizeBll {

	IWdAppSignSendPrizeMapper iWdAppSignMapper = new WdAppSignSendPrizeDao();

	@Override
	public int insert(WdAppSignedSendPrizeBean bean) {
		return  iWdAppSignMapper.insert(bean);
		
	}

	@Override
	public WdAppSignedSendPrizeBean getSignSendPrizeByPrize(String account, int serverId, String gift, String batch) {
		return  iWdAppSignMapper.getSignSendPrizeByPrize(account,serverId,gift,batch);
	}
    
	@Override
	public int modifyPrizeStatus(String account, int serverId, String gift, String batch,int status,Date sendTime) {
		return  iWdAppSignMapper.modifyPrizeStatus(account,serverId,gift,batch,status,sendTime);
	}

	@Override
	public WdAppSignedSendPrizeBean getUnclaimedPrizeByStatus(String account,String serverName,int status,String batch) {
		
		return  iWdAppSignMapper.getUnclaimedPrizeByStatus(account,serverName,status,batch);
	}

	@Override
	public List<WdAppSignedSendPrizeBean> getSignSendPrize(String account, int serverId, String batch, int status) {
		return iWdAppSignMapper.getSignSendPrize(account, serverId, batch, status);
	}

	@Override
	public int insert(WdAppSignedSendPrizeBean bean, SqlSession session) {
		return  iWdAppSignMapper.insert(bean,session);
	}

	@Override
	public WdAppSignedSendPrizeBean getSignSendPrizeByPrize(String account, int serverId, String gift, String batch,
			SqlSession session) {
		return  iWdAppSignMapper.getSignSendPrizeByPrize(account,serverId,gift,batch,session);
	}
	@Override
	public List<WdAppSignedSendPrizeBean> getSignSendPrizeByPrizes(String account, int serverId, List<String> gift,
			String batch) {
		return  iWdAppSignMapper.getSignSendPrizeByPrizes(account,serverId,gift,batch);
	}


}
