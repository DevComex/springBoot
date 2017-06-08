package cn.gyyx.action.dao.wd10yearcoser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wd10yearcoser.PageHelper;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBeanExample;
import cn.gyyx.action.beans.wd10yearcoser.Constants.RESOURCETYPE;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBeanExample.Criteria;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ResourceDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	//添加资源
	public void addResource(ResourceBean resource) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			mapper.insertSelective(resource);
			sqlSession.commit();
		}
	}
	
	//根据code删除资源
	public void deleteResourceByCode(int code){
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			mapper.deleteByPrimaryKey(code);
			sqlSession.commit();
		}
	}
	
	
	//根据code查找资源
	public ResourceBean findResourceByCode(int code){
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			return mapper.selectByPrimaryKey(code);
		}
	}
	
	//根据code查找资源
	public ResourceBean findResourceByCode(int code, SqlSession session){
		ResourceBeanMapper mapper = session.getMapper(ResourceBeanMapper.class);
		return mapper.selectByPrimaryKey(code);
	}
	
	//查找推荐列表
	public List<ResourceBean> findShowResources(String type){
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			int nums = 20;
			if (type.equals(RESOURCETYPE.COS_PIC.toString())) {
				nums = 4;
			} else if (type.equals(RESOURCETYPE.HANDPAINTED.toString())) {
				nums = 5;
			} else if (type.equals(RESOURCETYPE.MUSIC.toString())) {
				nums = 10;
			} else if (type.equals(RESOURCETYPE.VIDEO.toString())) {
				nums = 3;
			}
			return mapper.findShowResources(nums,type);
		}
	}

	//根据查找条件查找资源列表(去看他)
	public List<ResourceBean> findTaResourcesByPage(ResourceBean resource,int pageSize, int pageIndex){
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			PageHelper pageHelper = new PageHelper();
			if (StringUtils.isNotBlank(resource.getName())) {
				pageHelper.setName(resource.getName());
			}
			pageHelper.setUserCode(resource.getUserCode());
			if (pageSize <= 0 || pageIndex <= 0) {
				pageSize = 16;
				pageIndex = 1;
			}
			pageHelper.setType(resource.getType());
			pageHelper.setPageSize(pageSize);
			pageHelper.setPageRows(pageSize*(pageIndex-1));
			return mapper.selectOtherByPage(pageHelper);
		}
	}
	
	//根据查找条件查找资源列表
	public List<ResourceBean> findMyselfResourcesByPage(ResourceBean resource,int pageSize, int pageIndex){
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			PageHelper pageHelper = new PageHelper();
			if (StringUtils.isNotBlank(resource.getName())) {
				pageHelper.setName(resource.getName());
			}
			pageHelper.setUserCode(resource.getUserCode());
			if (pageSize <= 0 || pageIndex <= 0) {
				pageSize = 16;
				pageIndex = 1;
			}
			pageHelper.setType(resource.getType());
			pageHelper.setPageSize(pageSize);
			pageHelper.setPageRows(pageSize*(pageIndex-1));
			return mapper.selectMyselfByPage(pageHelper);
		}
	}
	
	//根据查找条件查找他人资源列表
	public List<ResourceBean> findOtherResourcesByPage(ResourceBean resource,int pageSize, int pageIndex){
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			PageHelper pageHelper = new PageHelper();
			if (StringUtils.isNotBlank(resource.getName())) {
				pageHelper.setName(resource.getName());
			}
			if (pageSize < 0 || pageIndex < 0) {
				pageSize = 16;
				pageIndex = 1;
			}
			pageHelper.setType(resource.getType());
			pageHelper.setPageSize(pageSize);
			pageHelper.setPageRows(pageSize*(pageIndex-1));
			return mapper.selectOtherByPage(pageHelper);
		}
	}
	
	
	public int countMyList(ResourceBean resource) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			ResourceBeanExample example = new ResourceBeanExample();
			//查询筛选
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(resource.getName())) {
				criteria.andNameLike("%" + resource.getName() + "%");
			}
			criteria.andTypeEqualTo(resource.getType());
			criteria.andUserCodeEqualTo(resource.getUserCode());
			int result = mapper.countByExample(example);
			return result;
		}
	}
	
	public int countOtherList(ResourceBean resource) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			ResourceBeanExample example = new ResourceBeanExample();
			//查询筛选
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(resource.getName())) {
				criteria.andNameLike("%" + resource.getName() + "%");
			}
			criteria.andCheckTypeEqualTo("CHECKED");
			criteria.andTypeEqualTo(resource.getType());
			int result = mapper.countByExample(example);
			return result;
		}
	}
	
	public int countTaList(ResourceBean resource) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			ResourceBeanExample example = new ResourceBeanExample();
			//查询筛选
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(resource.getName())) {
				criteria.andNameLike("%" + resource.getName() + "%");
			}
			if (resource.getUserCode() != null && resource.getUserCode() > 0) {
				criteria.andUserCodeEqualTo(resource.getUserCode());
			}
			criteria.andCheckTypeEqualTo("CHECKED");
			criteria.andTypeEqualTo(resource.getType());
			int result = mapper.countByExample(example);
			return result;
		}
	}
	
	//根据code修改资源
	public void updateResourceByCode(ResourceBean resource) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			mapper.updateByPrimaryKey(resource);
			sqlSession.commit();
		}
	}
	
	//根据code修改资源
	public int updateResourceByCode(ResourceBean resource, SqlSession session) {
		ResourceBeanMapper mapper = session.getMapper(ResourceBeanMapper.class);
		return mapper.updateByPrimaryKey(resource);
	}

	public List<ResourceBean> getBackResourceListPaging(ResourceBean bean) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			return mapper.getBackResourceListPaging(bean);
		}
	}

	public int getBackResourceCount(ResourceBean bean) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			return mapper.getBackResourceCount(bean);
		}
	}

	public void cancleLastTopWorksByType(int num, String type,
			SqlSession session) {
		ResourceBeanMapper mapper = session.getMapper(ResourceBeanMapper.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("num",num);
		map.put("type",type);
		
		mapper.cancleLastTopWorksByType(map);
	}

	public void batchCheckWorks(List<Integer> ids, String state) {
		try (SqlSession sqlSession = getSession()) {
			ResourceBeanMapper mapper = sqlSession.getMapper(ResourceBeanMapper.class);
			mapper.batchCheckWorks(ids,state);
			sqlSession.commit();
		}
	}
}
