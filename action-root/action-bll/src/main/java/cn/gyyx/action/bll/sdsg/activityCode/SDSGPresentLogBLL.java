package cn.gyyx.action.bll.sdsg.activityCode;

import java.util.Date;

import cn.gyyx.action.beans.sdsg.activityCode.ActionConfigBean;
import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;
import cn.gyyx.action.dao.lottery.RechargeDao;
import cn.gyyx.action.dao.sdsg.activityCode.SDSGPresentLogDAO;

public class SDSGPresentLogBLL {
	private SDSGPresentLogDAO presentLogDAO = new SDSGPresentLogDAO();

	/**
	 * 判断每天是否有抽奖资格
	 * 
	 * @param actioinCode
	 * @param account
	 * @param endTime
	 * @param startTime
	 * @return
	 */
	public int hasQualification(int actionCode, String account, String endTime, String startTime) {
		return presentLogDAO.presentCountDay(actionCode, account, endTime, startTime);
	}

	/**
	 * 判断是否中奖
	 * 
	 * @param actioinCode
	 * @param account
	 * @return
	 */
	public PresentLogBean hasPrize(int actionCode, String account, String presentType) {
		return presentLogDAO.hasPrize(actionCode, account, presentType);
	}

	public Integer isActivation(int gameId, int userId) {
		return presentLogDAO.isActivation(gameId, userId);
	}

	/**
	 * 激活
	 * 
	 * @param actionCode
	 * @param account
	 * @param presentType
	 * @param serverID
	 * @param serverName
	 * @return
	 */
	public void updateActivation(int actionCode, String account, String presentType, String serverID, String serverName,
			String activeCode) {
		presentLogDAO.updateActivation(actionCode, account, presentType, serverID, serverName, activeCode);
	}

	public String getCardCode(int userCode, int actionCode, String type) {
		return new RechargeDao().getCardCode(userCode, actionCode, type);
	}

	public boolean isExist(int actionCode, String account, String presentType) {
		int result = presentLogDAO.isExist(actionCode, account, presentType);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void updateGameActivityCode(int gameId, String activeCode, String serverID, String accountName, Date date,
			Integer userId) {
		presentLogDAO.updateGameActivityCode(gameId, activeCode, serverID, accountName, date, userId);
	}

	public void insertPrizeLog(String account, int serverId, String serverName, String presentName, String drawTime,
			String drawIp) {
		presentLogDAO.insertPrizeLog(account, serverId, serverName, presentName, drawTime, drawIp);
	}

	public boolean isExistActiveCode(String activeCode) {
		int result = presentLogDAO.isExistActiveCode(activeCode);
		if (result > 0) {
			return false;
		} else {
			return true;
		}
	}

	public ActionConfigBean getActionMsg(int code) {
		return presentLogDAO.getActionMsg(code);
	}

	public void updateGameActivityCodeStatus(String cardCode) {
		presentLogDAO.updateGameActivityCodeStatus(cardCode);

	}

	public boolean isRightActiveCode(String activeCode) {
		return presentLogDAO.isRightActiveCode(activeCode)>0;
	}

}
