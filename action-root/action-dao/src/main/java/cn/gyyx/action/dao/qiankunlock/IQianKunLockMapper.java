package cn.gyyx.action.dao.qiankunlock;

import java.util.HashMap;

import cn.gyyx.action.beans.QianKunLock.QianKunLockModel;

public interface IQianKunLockMapper {
	public QianKunLockModel selectBindAcount(int userId);
	public QianKunLockModel SelectByEkeySn(String EkeySn);
	public void insertLog(HashMap<String, Object> map);
	public void updateLog(QianKunLockModel qianKunLockModel);
}
