package cn.gyyx.action.bll.qiankunlock;

import java.util.HashMap;

import cn.gyyx.action.beans.QianKunLock.QianKunLockLimitBean;
import cn.gyyx.action.beans.QianKunLock.QianKunLockModel;
import cn.gyyx.action.dao.qiankunlock.QianKunLockDAO;
import cn.gyyx.action.dao.qiankunlock.QianKunLockLimitDAO;

public class QianKunLockBLL {
	private QianKunLockDAO qianKunLockDAO = new QianKunLockDAO();
	private QianKunLockLimitDAO qianKunLockLimitDAO = new QianKunLockLimitDAO();
	public QianKunLockLimitBean getLockLimit(){
		return qianKunLockLimitDAO.selectLockLimit();
		
	}
	public QianKunLockModel selectBindAcount(int userId){
		return qianKunLockDAO.selectBindAcount(userId);
		
	}
	public QianKunLockModel SelectByEkeySn(String EkeySn){
		return qianKunLockDAO.SelectByEkeySn(EkeySn);
		
	}
	public HashMap<String,Object> addLog(int userId,String account,int serverID,String serverName,int ekeyType,String ekeySn){
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("userID", String.valueOf(userId));
		map.put("account", account);
		map.put("gameID", "2");
		map.put("serverID", serverID+"");
		map.put("serverName", serverName);
		map.put("ekeyType", ekeyType+"");
		map.put("ekeySn", ekeySn);
		map.put("errcode", 0+"");
		map.put("logCode", 0+"");
		qianKunLockDAO.insertLog(map);
		return map;
//		System.out.println(map);
//		int code = (int) map.get("logCode");
//		System.out.println(code);
	}
	public void updateLog(QianKunLockModel qianKunLockModel){
		qianKunLockDAO.updateLog(qianKunLockModel);
	}
}
