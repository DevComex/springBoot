 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日下午3:20:04
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.gyyx.playwd.beans.playwd.WdgiftOrderBean;
import cn.gyyx.playwd.beans.playwd.WdgiftOrderBean.GiftOrderState;
import cn.gyyx.playwd.dao.playwd.WdgiftOrderDao;
import cn.gyyx.playwd.utils.CreateOrderId;

/**
 * 虚拟物品订单
 * @author lidudi
 *
 */
@Component
public class WdgiftOrderBll {
	
	@Autowired 
	public WdgiftOrderDao wdgiftOrderDao;
	
	/**
	 * 增加虚拟物品订单
	 * @param bean
	 * @return
	 */
	public int addWdgiftOrder(String account,int contentId,int prizeId,int gameId,
			int serverId,String serverName,String gift,String contentType,int userId){
		WdgiftOrderBean bean=new WdgiftOrderBean();		
		if(!StringUtils.isEmpty(contentType)){
			bean.setContentType(contentType);
			bean.setContentId(contentId);
			bean.setPrizeId(prizeId);
		}	
		bean.setAccount(account);
		bean.setUserId(userId);
		bean.setOrderId(CreateOrderId.getOrderId(account));
		bean.setGameId(gameId);
		bean.setServerId(serverId);
		bean.setServerName(serverName);
		bean.setGift(gift);
		bean.setStatus(GiftOrderState.waitgrant.Value());
		return wdgiftOrderDao.insertWdgiftOrder(bean);
	}

	/**
	 * 获取要发送到游戏的订单列表 
	 * 条件：状态为非成功且未达到最大重试次数且到达下次重试时间
	 * @param itemNum 每次获取的条数
	 * @param maxTryNum 最大重试次数
	 * @return 订单列表
	 */
	public List<WdgiftOrderBean> getOrderListToSend(int itemNum, int maxTryNum) {
		return wdgiftOrderDao.getOrderListToSend(itemNum,maxTryNum);
	}

	/**
	 * 根据ID获取订单
	 * @param code 订单ID
	 * @return 订单
	 */
	public WdgiftOrderBean getOrderByCode(Integer code) {
		return wdgiftOrderDao.getOrderByCode(code);
	}

	/**
	 * 更新订单状态
	 * @param code code
	 * @param status 订单状态
	 */
	public int updateOrderStatus(int code,String status) {
		return wdgiftOrderDao.updateOrderStatus(code,status);
	}

	/**
	 * 更新重试信息
	 * @param code code
	 * @param retryCount 重试次数
	 * @param retryTime 下次重试时间
	 * @return
	 */
	public int updateOrderRetryInfo(int code,Integer retryCount, Date retryTime) {
		return wdgiftOrderDao.updateOrderRetryInfo(code,retryCount,retryTime);
	}
}
