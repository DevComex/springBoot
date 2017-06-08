 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日下午2:21:32
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.dao.playwd;

import java.util.List;

import cn.gyyx.playwd.beans.playwd.UserTitleBean;


/**
 * 用户称号信息
 * @author lidudi
 *
 */
public interface UserTitleDao {
	
	/**
	 * 增加用户称号信息
	 * @param record
	 * @return
	 */
	int insertUserTitle(UserTitleBean record);

	/**
	 * 获取用户有效的称号列表
	 * @param code
	 * @return
	 */
	List<String> getValidTitleListByUserId(int userId);
}
