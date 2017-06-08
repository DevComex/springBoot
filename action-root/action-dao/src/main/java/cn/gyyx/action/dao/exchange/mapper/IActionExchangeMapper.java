package cn.gyyx.action.dao.exchange.mapper;

import java.util.List;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;

public interface IActionExchangeMapper {

	List<ActionExchangePO> select(ActionExchangePO params);
	
	// 更新兑换奖品数量
	int updateCount(ActionExchangePO params);
}
