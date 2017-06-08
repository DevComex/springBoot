 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月12日下午2:47:02
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.bll.RecommendSlotBll;

/**
 * 推荐位管理
 * @author lidudi
 *
 */
@Service
public class RecommendSlotService {
	
	/**
	 *推荐位管理 
	 */
	private RecommendSlotBll recommendSlotBll;
	
	/**
	 * 获取推荐位列表
	 * @param contentType
	 * @return
	 */
	public List<RecommendSlotBean> getRecommendSlotList(String contentType) {
		if(contentType.equals("all")){
		    List<RecommendSlotBean> list =recommendSlotBll.getRecommendSlotGroupList();
		    return list;
		}
		List<RecommendSlotBean>recommendList=recommendSlotBll.getRecommendSlotList(contentType);
		if(!contentType.equals("novel")){
		    List<RecommendSlotBean>recommendListNoContentType=recommendSlotBll.getRecommendSlotList("");
		    List<RecommendSlotBean>recommendListWebsite=recommendSlotBll.getRecommendSlotList("website");
		    //将首页推荐位和图文推荐位合并
		    recommendList.addAll(recommendListNoContentType);
		    recommendList.addAll(recommendListWebsite);
		}
		return recommendList;
	}

	@Autowired
	public void setRecommendSlotBll(RecommendSlotBll recommendSlotBll) {
		this.recommendSlotBll = recommendSlotBll;
	}
}
