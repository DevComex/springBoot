package cn.gyyx.wd.wanjia.cartoon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaComment;

public interface WanwdManhuaCommentMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(WanwdManhuaComment record);

    int insertSelective(WanwdManhuaComment record);

    WanwdManhuaComment selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(WanwdManhuaComment record);

    int updateByPrimaryKey(WanwdManhuaComment record);

	List<WanwdManhuaComment> findDetailsParentList(Map<String, Object> map);

	List<WanwdManhuaComment> findDetailsChildList(@Param("parentCode")Integer parentCode);

	int findDetailsParentListCount(Map<String,Object> map);
}