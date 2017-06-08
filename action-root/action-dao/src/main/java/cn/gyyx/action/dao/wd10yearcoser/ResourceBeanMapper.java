package cn.gyyx.action.dao.wd10yearcoser;

import cn.gyyx.action.beans.wd10yearcoser.PageHelper;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBeanExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ResourceBeanMapper {
    int countByExample(ResourceBeanExample example);

    int deleteByExample(ResourceBeanExample example);

    int deleteByPrimaryKey(Integer code);

    int insert(ResourceBean record);

    int insertSelective(ResourceBean record);

    List<ResourceBean> selectByExample(ResourceBeanExample example);

    List<ResourceBean> findShowResources(@Param("num") int nums, @Param("type") String type);
   
    List<ResourceBean> selectMyselfByPage(PageHelper pageHelper);
    
    List<ResourceBean> selectOtherByPage(PageHelper pageHelper);

    ResourceBean selectByPrimaryKey(Integer code);

    int updateByExampleSelective(@Param("record") ResourceBean record, @Param("example") ResourceBeanExample example);

    int updateByExample(@Param("record") ResourceBean record, @Param("example") ResourceBeanExample example);

    int updateByPrimaryKeySelective(ResourceBean record);

    int updateByPrimaryKey(ResourceBean record);

	List<ResourceBean> getBackResourceListPaging(ResourceBean bean);

	int getBackResourceCount(ResourceBean bean);

	void cancleLastTopWorksByType(Map<String, Object> params);

	void batchCheckWorks(@Param("items") List<Integer> ids, @Param("state")String state);
}