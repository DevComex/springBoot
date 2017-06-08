package cn.gyyx.action.dao.wdlight2017;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdlight2017.LightOaBean;
import cn.gyyx.action.beans.wdlight2017.ValidWishBean;
import cn.gyyx.action.beans.wdlight2017.WishBean;
import cn.gyyx.action.beans.wdlight2017.WishBeanExample;

public interface WishBeanMapper {
    int countByExample(WishBeanExample example);

    int deleteByExample(WishBeanExample example);

    int deleteByPrimaryKey(Integer code);

    int insert(WishBean record);

    int insertSelective(WishBean record);

    List<WishBean> selectByExample(WishBeanExample example);

    WishBean selectByPrimaryKey(Integer code);

    int updateByExampleSelective(@Param("record") WishBean record, @Param("example") WishBeanExample example);

    int updateByExample(@Param("record") WishBean record, @Param("example") WishBeanExample example);

    int updateByPrimaryKeySelective(WishBean record);

    int updateByPrimaryKey(WishBean record);
    
    List<WishBean> findWishByLevel(@Param("num") int num,@Param("status") int status,@Param("level") int level);
    
    List<ValidWishBean> getValidWishBean(@Param("pageNum") int pageNum,@Param("page") int page,
    		@Param("status") int status);
    
    int getWishCount(int status);
    
    List<WishBean> findWishAll(@Param("num") int num,@Param("status") int status);
    
    int modifyWishStatusByCode(@Param("status") int status,@Param("code") int code);
    
    int modifyLightLimitNum(@Param("limitNum") int limitNum,@Param("level") int level);
    
    List<LightOaBean> getLightLevel();
    
    Map<Integer, Integer> getLightPeople();
    
    int getWishUserCountByLevel(@Param("level") int level);

}