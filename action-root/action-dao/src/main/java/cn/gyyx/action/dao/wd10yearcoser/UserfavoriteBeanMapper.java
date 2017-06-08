package cn.gyyx.action.dao.wd10yearcoser;

import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserfavoriteBeanMapper {
    int countByExample(UserfavoriteBeanExample example);

    int deleteByExample(UserfavoriteBeanExample example);

    int deleteByPrimaryKey(Integer code);

    int insert(UserfavoriteBean record);

    int insertSelective(UserfavoriteBean record);

    List<UserfavoriteBean> selectByExample(UserfavoriteBeanExample example);

    UserfavoriteBean selectByPrimaryKey(Integer code);

    int updateByExampleSelective(@Param("record") UserfavoriteBean record, @Param("example") UserfavoriteBeanExample example);

    int updateByExample(@Param("record") UserfavoriteBean record, @Param("example") UserfavoriteBeanExample example);

    int updateByPrimaryKeySelective(UserfavoriteBean record);

    int updateByPrimaryKey(UserfavoriteBean record);
}