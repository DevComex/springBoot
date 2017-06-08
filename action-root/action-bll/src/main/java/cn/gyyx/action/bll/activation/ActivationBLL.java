package cn.gyyx.action.bll.activation;

import cn.gyyx.action.dao.activation.ActivationDAO;

/** 
* 类:ActivationBLL<br />
* 叙述:Activation Code WEB API BLL<br />
* 注释日期:2015年11月26日<br />
* @author lizepu
*/
public class ActivationBLL {
	ActivationDAO dao = new ActivationDAO();
	public int validateActCode(int gameId, String activationCode) {
		return  dao.validateActCode(gameId, activationCode);
	}

	public int useActivateCode(int serverId, int userId, int gameId,
			String activationCode) {
		return dao.useActivateCode(serverId, userId, gameId, activationCode);
	}
}
