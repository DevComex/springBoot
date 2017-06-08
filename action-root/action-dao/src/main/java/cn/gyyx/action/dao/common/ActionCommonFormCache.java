package cn.gyyx.action.dao.common;

import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONObject;
import cn.gyyx.action.beans.common.ActionCommonFormBean;
import cn.gyyx.action.beans.common.ActionCommonFormBean.Codeable;

public class ActionCommonFormCache<T extends Codeable> implements IActionCommonFormBean{
	private long cacheDelayTime = 5 * 60 * 1000;
	private long lastCacheTime = 0;
	private List<T> cache = new LinkedList<T>();
	ActionCommonFormDAO dao;

	public ActionCommonFormCache() {
		dao = new ActionCommonFormDAO();
	}
	
	public ActionCommonFormCache(long cacheDelay) {
		cacheDelayTime = cacheDelay;
		dao = new ActionCommonFormDAO();
	}
	
	public ActionCommonFormCache(ActionCommonFormDAO dao,long cacheDelay) {
		cacheDelayTime = cacheDelay;
		this.dao = dao;
	}
	
	@Override
	public void insertActionCommonFormBean(ActionCommonFormBean bean) {
		dao.insertActionCommonFormBean(bean);
	}

	@Override
	public ActionCommonFormBean selectActionCommonFormBeanBycode(int code) {
		return dao.selectActionCommonFormBeanBycode(code);
	}

	@Override
	public void updateActionCommonFormBean(ActionCommonFormBean bean) {
		dao.updateActionCommonFormBean(bean);
	}

	@Override
	public void deleteActionCommonFormBeanBycode(int code) {
		dao.deleteActionCommonFormBeanBycode(code);
	}
	@Override
	public List<ActionCommonFormBean> selectActionCommonFormByActionAndAccountCode(
			int actionCode, int accountCode) {
		return dao.selectActionCommonFormByActionAndAccountCode(actionCode, accountCode);
	}
	@Override
	public List<ActionCommonFormBean> selectActionCommonFormByActionCode(
			int actionCode) {
		return dao.selectActionCommonFormByActionCode(actionCode);
	}
	public synchronized List<T> selectAllBeanByActionCode(int actionCode,Class<? extends T> clazz) {
		if (System.currentTimeMillis() - lastCacheTime > cacheDelayTime) {
			lastCacheTime = System.currentTimeMillis();
			List<ActionCommonFormBean> actionBeans = dao.selectActionCommonFormByActionCode(actionCode);
			cache = new LinkedList<T>();
			for(ActionCommonFormBean common:actionBeans) {
				T object = getObjFromJson(common.getJsonStr(),clazz);
				if(object != null) {
					object.setCode(common.getCode());
					cache.add(object);
				}
			}
		}
		List<T> result = new LinkedList<T>();
		result.addAll(cache);
		return result;
	}
	@SuppressWarnings("unchecked")
	private T getObjFromJson(String jsonStr,Class<? extends T> clazz) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			return (T) JSONObject.toBean(jsonObj, clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
