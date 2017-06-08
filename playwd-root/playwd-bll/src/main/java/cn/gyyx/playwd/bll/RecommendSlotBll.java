 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月6日上午11:26:51
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.dao.playwd.RecommendSlotDao;

/**
 * 推荐位管理类
 * @author lidudi
 *
 */
@Component
public class RecommendSlotBll {

	//推荐位管理类
	@Autowired
	public RecommendSlotDao recommendSlotDao;
	
	/**
	 * 获取推荐位置列表
	 * @return
	 */
	public List<RecommendSlotBean> getRecommendSlotList(String type) {
		String slotGroup="玩家天地首页";
		
		if(type.equals("article")){
			slotGroup="图文驿站";
		}
		if(type.equals("manhua")){
			slotGroup="漫画专区";
		}
		if(type.equals("website")){
			slotGroup="问道官网";
		}
		if(type.equals("novel")){
		    slotGroup="小说模块";
		}
		
		List<RecommendSlotBean> list= recommendSlotDao.selectRecommendSlotList(slotGroup);
		if(list==null){
			return new ArrayList<RecommendSlotBean>();
		}
		return list;
	}
	
	/**
	 * 获取推荐位置信息
	 * @param code
	 * @return 
	 */
	public RecommendSlotBean getRecommendSlotByCode(int code) {
		return recommendSlotDao.selectRecommendSlotByCode(code);
	}

    public List<RecommendSlotBean> getRecommendSlotGroupList() {
        List<RecommendSlotBean> list = recommendSlotDao.getGroupList();
        for (RecommendSlotBean map : list) {
            List<RecommendSlotBean> recommendList= recommendSlotDao.selectRecommendSlotList(map.getSlotGroup());
            map.setList(recommendList);
        }
        return list;
    }
}
