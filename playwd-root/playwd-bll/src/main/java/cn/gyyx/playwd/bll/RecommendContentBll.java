package cn.gyyx.playwd.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.dao.playwd.RecommendContentMapper;

/**
 * 推荐区域信息
 * 
 * @author yangteng
 *
 */
@Component
public class RecommendContentBll {

	@Autowired
	public RecommendContentMapper recommendContentMapper;

	/**
	 * 查询推荐区域信息列表
	 * 
	 * @param code
	 *            推荐位ID
	 * @return
	 */
	public List<RecommendContentBean> getList(int code) {
		List<RecommendContentBean> list= recommendContentMapper.getList(code);
		if(list==null){
			return new ArrayList<RecommendContentBean>();			
		}
		return list;
	}

	/**
	 * 查询推荐区域信息总条数
	 * 
	 * @param code
	 * @return
	 */
	public Integer getCount(int code) {
		return recommendContentMapper.getCount(code);
	}

	/**
	 * 添加推荐区域信息
	 * 
	 * @param model
	 * @return
	 */
	public Integer add(RecommendContentBean model) {
		// 查询最大的排序号
		Integer number = recommendContentMapper.getDisplayOrder(model.getRecommendSlotId());
		if (number == null) {
			number = 0;
		} else {
			number++;
		}
		model.setDisplayOrder(number);

		return recommendContentMapper.insert(model);
	}
	
	/**
	 * 更新推荐区域信息
	 * @param model
	 * @return
	 */
	public Integer update(RecommendContentBean model){
		return recommendContentMapper.update(model);
	}
	
	/**
	 * 根据code查询推荐区域信息
	 * @param code
	 * @return
	 */
	public RecommendContentBean getInfo(Integer code){
		
		RecommendContentBean model = new RecommendContentBean();
		model.setCode(code);
		
		return recommendContentMapper.getInfo(model);
	}
	
	/**
	 * 查询最大的排序
	 * @param recommendSlotId
	 * @return
	 */
	public Integer getDisplayOrder(Integer recommendSlotId){
		Integer number = recommendContentMapper
				.getDisplayOrder(recommendSlotId);
		if (number == null)
			return 0;
		return number;
	}
	
	/**
	 * 查询推荐区域信息
	 * @param reommendSoltId 推荐位ID
	 * @param displayOrder 排序号
	 * @return
	 */
	public RecommendContentBean getInfo(Integer recommendSlotId,Integer displayOrder){		
		
		RecommendContentBean model=new RecommendContentBean();
		model.setRecommendSlotId(recommendSlotId);
		model.setDisplayOrder(displayOrder);
		
		return recommendContentMapper.getInfo(model);		
	}
	
	/**
	 * 置顶
	 * @param displayOrder
	 * @return
	 */
	public Integer moveTop(Integer displayOrder,Integer recommendSlotId){
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("displayOrder", displayOrder);
		map.put("recommendSlotId", recommendSlotId);
		
		return recommendContentMapper.moveTop(map);
	}
	
	/**
	 * 置底
	 * @param displayOrder
	 * @return
	 */
	public Integer moveBottom(Integer displayOrder,Integer recommendSlotId){
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("displayOrder", displayOrder);
		map.put("recommendSlotId", recommendSlotId);
		
		return recommendContentMapper.moveBottom(map);
	}
	
	/**
	 * 查询最小排序号
	 * @param recommendSlotId
	 * @return
	 */
	public Integer getMinDisplayOrder(Integer recommendSlotId){		
		Integer number = recommendContentMapper
				.getMinDisplayOrder(recommendSlotId);
		if (number == null)
			return 0;
		return number;
	}

	/**
	 * 更新显示状态
	 * @param model
	 * @return
	 */
	public Integer updateDisplay(RecommendContentBean model) {
		Integer number = recommendContentMapper
				.updateDisplay(model);
		if (number == null)
			return 0;
		return number;
	}
	
	/**
	 * 更新排序顺序
	 * @param model
	 * @return
	 */
	public Integer updateDisplayOrder(RecommendContentBean model){
		Integer number = recommendContentMapper
				.updateDisplayOrder(model);
		if (number == null)
			return 0;
		return number;
	}
	
	/**
	 * 根据推荐位置和内容id删推荐区域信息 
	 * @param contentId
	 * @param slotId
	 * @return
	 */
	public boolean changeIsDeleteBySlotIdAndContentId(int contentId,int slotId ) {
		return recommendContentMapper.updateIsDeleteBySlotIdAndContentId(contentId, slotId)>0;
	}
	
	/**
	 * 按照推荐位获取推荐区域列表
	 * @param slotId
     * @param topNumber 前几条
	 * @return
	 */
	public List<RecommendContentBean> getSlotContentList(int slotId,int topNumber) {
		List<RecommendContentBean> beans=recommendContentMapper.selectSlotContentList(slotId,topNumber);
		if(beans==null){
			return new ArrayList<RecommendContentBean>();
		}
		return beans;	
	}
}
