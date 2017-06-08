package cn.gyyx.action.bll.exchange;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;

public interface IActionExchangeBLL {
	
	// 获得兑换奖品
	ActionExchangePO getOne(ActionExchangePO params);
	
	// 获得兑换奖品列表
	List<ActionExchangePO> get(ActionExchangePO params);
	
	// 更新兑换奖品数量
	int put(ActionExchangePO params, SqlSession session);
}
