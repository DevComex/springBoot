package cn.gyyx.playwd.dao.playwd;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.CategoryBean;

public interface CategoryDao {

    int insert(CategoryBean record);

    CategoryBean selectCategroyByCode(Integer code);

    List<CategoryBean> getCategory(@Param("contentType") String contentType);

    List<CategoryBean> getParentCategory(@Param("contentType")String contentType);

    List<CategoryBean> getChildCategory(@Param("categoryInt")int categoryInt,@Param("contentType")String contentType);

        CategoryBean getParentCategoryBySubCode(int subCode);

        /**
         * 获取一级分类
         * @param code
         * @return
         */
        List<CategoryBean> getFirstCategoryListByContentType(@Param("contentType") String contentType);

        /**
         * 获取子分类
         * @param code
         * @return
         */
        List<CategoryBean> getSubCategoryList(@Param("pid") int pid);

        /**
         * 更新分类
         * @param code
         * @return
         */
        int update(CategoryBean bean);

        /**
         * 删除分类
         * @param code
         * @return
         */
        int updateIsDelete(int code);
}