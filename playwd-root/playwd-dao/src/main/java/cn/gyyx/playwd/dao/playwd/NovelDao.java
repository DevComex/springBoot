package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.NovelBean;

/**
 * 
  * <p>
  *   NovelBeanMapper描述
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public interface NovelDao {
    //插入
    int insert(NovelBean record);
    //获取by id
    NovelBean get(Integer code);
    //更新
    int update(NovelBean record);
    //根据条件查询单一实体
    NovelBean selectNovel(NovelBean record);
    //根据条件查询单一实体列表
    List<NovelBean> selectNovelList(NovelBean record);
    List<Map<String, Object>> selectAllNovelList(Map<String, Object> map);
    int selectAllNovelListCount(Map<String, Object> map);
    NovelBean selectGuanNovel(@Param("name")String name);
    int updateShowAndhidden(NovelBean bean);
    int getNovelManagementCount(@Param("recommendSlotId")Integer recommendSlotId);
    List<Map<String, Object>> getNovelManagementList(@Param("recommendSlotId")Integer recommendSlotId,
            @Param("startSize")int startSize, @Param("endSize")int endSize);
}