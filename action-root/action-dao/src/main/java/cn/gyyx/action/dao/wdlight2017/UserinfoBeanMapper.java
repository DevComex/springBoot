package cn.gyyx.action.dao.wdlight2017;

import cn.gyyx.action.beans.wdlight2017.UserinfoBean;
import cn.gyyx.action.beans.wdlight2017.UserinfoBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserinfoBeanMapper {
    int countByExample(UserinfoBeanExample example);

    int deleteByExample(UserinfoBeanExample example);

    int deleteByPrimaryKey(Integer code);

    int insert(UserinfoBean record);

    int insertSelective(UserinfoBean record);

    List<UserinfoBean> selectByExample(UserinfoBeanExample example);

    UserinfoBean selectByPrimaryKey(Integer code);

    int updateByExampleSelective(@Param("record") UserinfoBean record, @Param("example") UserinfoBeanExample example);

    int updateByExample(@Param("record") UserinfoBean record, @Param("example") UserinfoBeanExample example);

    int updateByPrimaryKeySelective(UserinfoBean record);

    int updateByPrimaryKey(UserinfoBean record);
}