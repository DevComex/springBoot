package cn.gyyx.playwd.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.dao.playwd.CategoryDao;

/**
 * 
  * <p>
  *   CategoryBll描述
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@Component
public class CategoryBll {
    @Autowired
    CategoryDao categoryDao;

    public List<CategoryBean> getCategory(String contentType) {
        return categoryDao.getCategory(contentType);
    }

    public List<CategoryBean> getParentCategory(String contentType) {
        return categoryDao.getParentCategory(contentType);
    }

    public List<CategoryBean> getChildCategory(int categoryInt, String contentType) {
        return categoryDao.getChildCategory(categoryInt,contentType);
    }
    
    /**
     * 根据子ID查询父类
     * @param subCode
     * @return
     */
    public CategoryBean getParentCategoryBySubCode(int subCode) {
        return categoryDao.getParentCategoryBySubCode(subCode);
    }

    /**
     * 获取分类信息
     * @param code
     * @return
     */
    public CategoryBean getCategroyByCode(int code) {
                return categoryDao.selectCategroyByCode(code);
        }

    /**
     * 获取一级分类列表
     * @param type
     * @return
     */
    public List<CategoryBean> getFirstCategoryListByContentType(String contentType) {
            return categoryDao.getFirstCategoryListByContentType(contentType);
    }

    /**
     * 获取子分类列表
     * @param pid
     * @return
     */
    public List<CategoryBean> getSubCategoryList(int pid) {
            return categoryDao.getSubCategoryList(pid);
    }

    /**
     * 编辑分类
     * @param bean
     */
    public int update(CategoryBean bean) {
            return categoryDao.update(bean);
    }

    /**
     * 添加分类
     * @param title
     * @param contentType
     * @param parentId
     */
    public int insert(String title, String contentType, Integer parentId) {
            CategoryBean bean = new CategoryBean();
            bean.setTitle(title);
            bean.setContentType(contentType);
            bean.setIsDelete(false);
            bean.setGameId(2);
            bean.setParentId(parentId);
            return categoryDao.insert(bean);
    }

    /**
     * 删除分类
     * @param code
     */
    public int delete(int code) {
            return categoryDao.updateIsDelete(code);
    }
 
}
