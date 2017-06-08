 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月1日下午3:52:03
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;

/**
 * 推荐位
 * @author lidudi
 *
 */
public interface RecommendSlotDao {
	
	/**
	 * 获取推荐位置列表
	 * @return
	 */
	List<RecommendSlotBean> selectRecommendSlotList(String slotGroup);
	
	/**
	 * 获取推荐位置信息
	 * @param code
	 * @return
	 */
	RecommendSlotBean selectRecommendSlotByCode(int code);

    List<RecommendSlotBean> getGroupList();
}
