 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日下午4:09:18
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.ItemBean;
import cn.gyyx.playwd.dao.playwd.ItemDao;

/**
 * 奖品信息
 * @author lidudi
 *
 */
@Component
public class ItemBll {

	@Autowired
	public ItemDao itemDao;
	
	/**
	 * 根据奖品名称获取奖品信息
	 * @param name
	 * @return
	 */
	public ItemBean getItemByName(String name) {
		return itemDao.selectItemByName(name);
	}
}
