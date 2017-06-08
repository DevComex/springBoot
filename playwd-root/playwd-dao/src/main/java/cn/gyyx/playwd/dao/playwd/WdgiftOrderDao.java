 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日下午3:12:28
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.dao.playwd;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.WdgiftOrderBean;

/**
 * 虚拟物品订单
 * @author lidudi
 *
 */
public interface WdgiftOrderDao {
	
	 /**
     * 增加虚拟物品订单
     * @param record
     * @return
     */
    int insertWdgiftOrder(WdgiftOrderBean record);

    /**
	 * 获取要发送到游戏的订单列表
	 * @param itemNum 每次获取的条数
	 * @param maxTryNum 最大重试次数
	 * @return 订单列表
	 */
	List<WdgiftOrderBean> getOrderListToSend(@Param("itemNum") int itemNum,@Param("maxTryNum") int maxTryNum);

	/**
	 * 根据ID获取订单
	 * @param code 订单ID
	 * @return 订单
	 */
	WdgiftOrderBean getOrderByCode(@Param("code") Integer code);

	/**
	 * 更新订单状态
	 * @param status 订单状态
	 */
	int updateOrderStatus(@Param("code") int code,@Param("status") String status);

	/**
	 * 更新重试信息
	 * @param retryCount 重试次数
	 * @param retryTime 下次重试时间
	 * @return
	 */
	int updateOrderRetryInfo(@Param("code") int code,@Param("retryCount") Integer retryCount,@Param("retryTime") Date retryTime);
}
