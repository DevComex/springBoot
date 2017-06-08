package cn.gyyx.action.dao.wdlight2017;

import cn.gyyx.action.beans.wdlight2017.LightBean;
import cn.gyyx.action.beans.wdlight2017.LightBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LightBeanMapper {
    int countByExample(LightBeanExample example);

    int deleteByExample(LightBeanExample example);

    int deleteByPrimaryKey(Integer code);

    int insert(LightBean record);

    int insertSelective(LightBean record);

    List<LightBean> selectByExample(LightBeanExample example);

    LightBean selectByPrimaryKey(Integer code);

    int updateByExampleSelective(@Param("record") LightBean record, @Param("example") LightBeanExample example);

    int updateByExample(@Param("record") LightBean record, @Param("example") LightBeanExample example);

    int updateByPrimaryKeySelective(LightBean record);

    int updateByPrimaryKey(LightBean record);
}