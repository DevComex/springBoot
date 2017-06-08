package cn.gyyx.action.bll.exchange.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;
import cn.gyyx.action.bll.exchange.IActionExchangeBLL;
import cn.gyyx.action.dao.exchange.IActionExchangeDAO;
import cn.gyyx.action.dao.exchange.impl.ActionExchangeDAOImpl;

public class ActionExchangeBLLImpl implements IActionExchangeBLL {
	
	private IActionExchangeDAO actionExchangeDAO = new ActionExchangeDAOImpl();
	
	// 获得兑换奖品
	@Override
	public ActionExchangePO getOne(ActionExchangePO params) {
		List<ActionExchangePO> poList = this.get(params);
		
		if (poList != null && poList.size() > 0) {
			if (poList.size() > 1) {
				/*Collections.sort(poList, new Comparator<ActionExchangePO>() {
					public int compare(ActionExchangePO arg0, ActionExchangePO arg1) {
						return arg1.getCode().compareTo(arg0.getCode());
					}
				});*/
			}
			
			return poList.get(0);
		}
		
		return null;
	}
		
	// 获得兑换奖品列表
	@Override
	public List<ActionExchangePO> get(ActionExchangePO params) {
		return actionExchangeDAO.get(params);
	}
		
	// 更新兑换奖品数量
	@Override
	public int put(ActionExchangePO params, SqlSession session) {
		if (null != params && null != params.getModifyAt()) {
			params.setModifyAt(Calendar.getInstance().getTime());
		}

		return actionExchangeDAO.put(params, session);
	}
}
