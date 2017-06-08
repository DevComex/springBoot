package dao;

import beans.PlaywdCategoryBean;

public interface PlaywdCategoryBeanMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(PlaywdCategoryBean record);

    int insertSelective(PlaywdCategoryBean record);

    PlaywdCategoryBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(PlaywdCategoryBean record);

    int updateByPrimaryKey(PlaywdCategoryBean record);
}