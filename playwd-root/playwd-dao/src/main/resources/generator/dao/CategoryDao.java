package dao;

import beans.CategoryBean;

public interface CategoryDao {
    int deleteByPrimaryKey(Integer code);

    int insert(CategoryBean record);

    int insertSelective(CategoryBean record);

    CategoryBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(CategoryBean record);

    int updateByPrimaryKey(CategoryBean record);
}