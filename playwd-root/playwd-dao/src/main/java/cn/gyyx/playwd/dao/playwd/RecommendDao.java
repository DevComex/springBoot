 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月3日上午11:14:59
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.dao.playwd;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.RecommendBean;


/**
 * 推荐信息类
 * @author lidudi
 *
 */
public interface RecommendDao {
	
	/**
	 * 增加推荐信息
	 * @param record
	 * @return
	 */
	int insertRecommend(RecommendBean record);
	
	/**
	 * 获取推荐信息的推荐位
	 * @param contentId
	 * @param contentType 
	 * @return
	 */
	List<Integer>selectSlotsByContentId(@Param("contentId")int contentId, @Param("contentType")String contentType);
	
	/**
	 * 根据 contentId和slotId删除推荐信息
	 * @param contentId
	 * @param locationId
	 * @return
	 */
	int updateIsDeleteByContentIdAndSlotId(@Param("contentId") int contentId,
			@Param("slotId") int slotId);
}
