package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.RecommendContentBean;

/**
 * 推荐区域信息数据访问层
 * @author Administrator
 *
 */
public interface RecommendContentMapper {

    /**
     * 添加推荐区域信息
     * @param model
     * @return
     */
    int insert(RecommendContentBean model);
    
    /**
     * 根据推荐位ID查询推荐区域信息列表
     * @param code
     * @return
     */
    List<RecommendContentBean> getList(Integer code);
    
    /**
     * 根据推荐位ID查询推荐区域信息总条数
     * @param code
     * @return
     */
    Integer getCount(Integer code);    	 
    
    /**
     * 查询最大的排序号
     * @param code
     * @return
     */
    Integer getDisplayOrder(Integer code);
    
    /**
     * 更新推荐区域信息
     * @param model
     * @return
     */
    Integer update(RecommendContentBean model);
    
    /**
     * 根据code查询推荐区域信息
     * @param code
     * @return
     */
    RecommendContentBean getInfo(RecommendContentBean model);
    
    /**
     * 置顶
     * @param displayOrder
     * @return
     */
    Integer moveTop(Map<String,Integer> map);
    
    /**
     * 置底
     * @param displayOrder
     * @return
     */
    Integer moveBottom(Map<String,Integer> map);
    
    /**
     * 查询最小排序号
     * @param recommendSoltId
     * @return
     */
    Integer getMinDisplayOrder(Integer recommendSoltId);
    
    /**
     * 更新显示状态
     * @param model
     * @return
     */
    Integer updateDisplay(RecommendContentBean model);
    
    /**
     * 更新排序顺序
     * @param model
     * @return
     */
    Integer updateDisplayOrder(RecommendContentBean model);
    
    /**
     * 根据推荐位置和内容id删推荐区域信息 
     * @param contentId
     * @param slotId
     * @return
     */
    int updateIsDeleteBySlotIdAndContentId(@Param("contentId") int contentId,
    		@Param("slotId") int slotId);
    
    /**
     * 按照推荐位获取推荐区域列表
     * @param slotId
     * @param topNumber 前几条
     * @return
     */
    List<RecommendContentBean> selectSlotContentList(@Param("slotId") int slotId,@Param("topNumber") int topNumber);
}