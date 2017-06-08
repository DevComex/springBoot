 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日上午11:05:01
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.RmbOrderBean;
import cn.gyyx.playwd.dao.playwd.RmbOrderDao;
import cn.gyyx.playwd.utils.CreateOrderId;

/**
 * 现金订单
 * @author lidudi
 *
 */
@Component
public class RmbOrderBll {

	@Autowired
	public RmbOrderDao rmbOrderDao;
	
	/**
	 * 增加现金订单
	 * @return
	 */
	public int addRmbOrder(int userId, String account,int contentId,int prizeId,
			int rmb,String contentType,String status) {
		RmbOrderBean bean=new RmbOrderBean();
		bean.setOrderId(CreateOrderId.getOrderId(account));
		bean.setContentType(contentType);
		bean.setContentId(contentId);
		bean.setPrizeId(prizeId);
		bean.setStatus(status);
		bean.setAccount(account);
		bean.setUserId(userId);
		bean.setRmb(new BigDecimal(rmb));
		return rmbOrderDao.insertRmbOrder(bean);
	}
}
