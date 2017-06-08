 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日上午11:03:32
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.dao.playwd;

import cn.gyyx.playwd.beans.playwd.RmbOrderBean;

/**
 * 现金订单类
 * @author lidudi
 *
 */
public interface RmbOrderDao {
	
	/**
	 * 增加现金订单
	 * @param record
	 * @return
	 */
	int insertRmbOrder(RmbOrderBean record);
}
