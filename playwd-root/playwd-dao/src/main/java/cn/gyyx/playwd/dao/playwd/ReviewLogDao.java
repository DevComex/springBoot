package cn.gyyx.playwd.dao.playwd;

import cn.gyyx.playwd.beans.playwd.ReviewLogBean;

public interface ReviewLogDao {
    int deleteByCode(Integer code);

    int insert(ReviewLogBean record);

    ReviewLogBean selectByCode(Integer code);

    int updateByPrimaryKeySelective(ReviewLogBean record);

    int updateByPrimaryKey(ReviewLogBean record);
}