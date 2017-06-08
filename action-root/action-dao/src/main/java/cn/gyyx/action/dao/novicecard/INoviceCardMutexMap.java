package cn.gyyx.action.dao.novicecard;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.novicecard.NoviceCardMutexBean;

public interface INoviceCardMutexMap {
	/**
	 * 通过当前活动标识（批次，活动编号）获取所有互斥信息
	 * @return
	 */
	public List<NoviceCardMutexBean> getMutexMes(@Param("symbol")int symbol);
}
