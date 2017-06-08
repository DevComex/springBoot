package dao;

import beans.ArticleBean;

public interface ArticleDao {
    int deleteByPrimaryKey(Integer code);

    int insert(ArticleBean record);

    int insertSelective(ArticleBean record);

    ArticleBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ArticleBean record);

    int updateByPrimaryKeyWithBLOBs(ArticleBean record);

    int updateByPrimaryKey(ArticleBean record);
}