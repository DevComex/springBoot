package cn.gyyx.action.dao.activity;

import cn.gyyx.action.beans.activity.ActivityConfigBean;
import org.apache.ibatis.annotations.Param;

public interface ActivityConfigMapper {

    int insertSelective(ActivityConfigBean record);

    ActivityConfigBean selectByPrimaryKey(Integer code);

    int updateByActivityCode(ActivityConfigBean record);

    ActivityConfigBean selectBeanByActivityCode(@Param("activity_code") String activityCode);
}