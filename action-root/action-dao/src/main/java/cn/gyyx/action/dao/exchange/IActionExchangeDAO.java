package cn.gyyx.action.dao.exchange;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;

public interface IActionExchangeDAO {

	// 获得兑换奖品列表
	List<ActionExchangePO> get(ActionExchangePO params);
	
	// 更新兑换奖品数量
	int put(ActionExchangePO params, SqlSession session);
}
