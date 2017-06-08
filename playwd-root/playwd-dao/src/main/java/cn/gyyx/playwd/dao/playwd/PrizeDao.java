 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月2日上午11:28:46
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.dao.playwd;

import java.util.List;

import cn.gyyx.playwd.beans.playwd.PrizeBean;


/**
 * 奖励物品
 * @author lidudi
 *
 */
public interface PrizeDao {
	
	/**
	 * 获取奖励物品列表
	 * @param contentType 分类
	 * @return
	 */
	List<PrizeBean> selectPrizeListByContentType(String contentType);
	
	/**
	 * 获取奖励物品信息
	 * @param prizeCode 奖励code
	 * @return
	 */
	PrizeBean selectPrizeByPrizeCode(int prizeCode);
}
