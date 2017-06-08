 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月1日下午4:00:07
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.RecommendBean;
import cn.gyyx.playwd.dao.playwd.RecommendDao;

/**
 * 推荐功能
 * @author lidudi
 *
 */
@Component
public class RecommendBll {

	//推荐信息类
	@Autowired
	public RecommendDao recommendDao;

	/**
	 * 增加推荐信息
	 * @param bean
	 * @return
	 */
	public int addRecommend(String contentType,Integer contentId,Integer slotId,
			String title,String cover,Integer prizeId) {
		RecommendBean bean=new RecommendBean();
		bean.setContentType(contentType);
		bean.setContentId(contentId);
		bean.setSlotId(slotId);
		bean.setTitle(title);
		bean.setCover(cover);
		bean.setCreateTime(new Date());
		bean.setPrizeId(prizeId==null?0:prizeId);
		return recommendDao.insertRecommend(bean);
	}
	
	/**
	 * 获取推荐信息的推荐位
	 * @param contentId
	 * @return
	 */
	public List<Integer>getSlotsByContentId(int contentId,String contentType){
		List<Integer> resultIntegers= recommendDao.selectSlotsByContentId(contentId,contentType);
		if(resultIntegers==null||resultIntegers.size()==0){
			return new ArrayList<Integer>();
		}
		return resultIntegers;
	}
	
	/**
	 * 根据 contentId和slotId删除推荐信息
	 * @param contentId
	 * @param locationId
	 * @return
	 */
	public boolean changeIsDeleteByContentIdAndSlotId(int contentId,int slotId) {
		return recommendDao.updateIsDeleteByContentIdAndSlotId(contentId, slotId)>0;
	}
}
