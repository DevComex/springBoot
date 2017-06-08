 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日下午2:11:24
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.dao.playwd.UserIdAccountDao;

/**
 * 账号信息
 * @author lidudi
 *
 */
@Component
public class UserIdAccountBll {

	@Autowired
	public UserIdAccountDao userIdAccountDao;
	
	/**
	 * 获取账号信息
	 * @param userId
	 * @return
	 */
	public String getAccountByUserId(int userId) {
		return userIdAccountDao.selectAccountByUserId(userId);
	}
}
