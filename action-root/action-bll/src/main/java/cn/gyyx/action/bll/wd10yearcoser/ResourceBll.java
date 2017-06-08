package cn.gyyx.action.bll.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.dao.wd10yearcoser.ResourceDao;

public class ResourceBll {
	private ResourceDao resourceDao = new ResourceDao();
	
	
	/***
	 * 添加资源
	 * 
	 * @param resource
	 */
	public void addResource(ResourceBean resource) {
		resourceDao.addResource(resource);
	}
	
	/***
	 * 根据code删除资源
	 * 
	 * @param code
	 */
	public void deleteResourceByCode(int code){
		resourceDao.deleteResourceByCode(code);
	}
	
	/***
	 * 根据code查找资源
	 * 
	 * @param code
	 */
	public ResourceBean findResourceByCode(int code){
		return resourceDao.findResourceByCode(code);
	}
	
	/***
	 * 根据code查找资源
	 * 
	 * @param code
	 */
	public ResourceBean findResourceByCode(int code, SqlSession session){
		return resourceDao.findResourceByCode(code, session);
	}

	/***
	 * 查找推荐列表
	 *
	 * @param code
	 */
	public List<ResourceBean> findShowResources(String type){
		return resourceDao.findShowResources(type);
	}
	
	/***
	 * 根据查找条件查找自己的列表
	 *
	 * @param code
	 */
	public List<ResourceBean> findTaResourcesByPage(ResourceBean resource,int pageSize, int pageIndex){
		return resourceDao.findTaResourcesByPage(resource, pageSize, pageIndex);
	}
	
	/***
	 * 根据查找条件查找自己的列表
	 *
	 * @param code
	 */
	public List<ResourceBean> findMyselfResourcesByPage(ResourceBean resource,int pageSize, int pageIndex){
		return resourceDao.findMyselfResourcesByPage(resource, pageSize, pageIndex);
	}
	
	/***
	 * 根据查找条件查找自己的列表
	 *
	 * @param code
	 */
	public List<ResourceBean> findOtherResourcesByPage(ResourceBean resource,int pageSize, int pageIndex){
		return resourceDao.findOtherResourcesByPage(resource, pageSize, pageIndex);
	}
	
	/***
	 * 根据查找条件查找资源数量
	 * 
	 * @param code
	 */
	public int countOtherList(ResourceBean resource){
		return resourceDao.countOtherList(resource);
	}
	
	/***
	 * 根据查找条件查找资源数量
	 * 
	 * @param code
	 */
	public int countTaList(ResourceBean resource){
		return resourceDao.countTaList(resource);
	}
	
	/***
	 * 根据查找条件查找资源数量
	 * 
	 * @param code
	 */
	public int countMyList(ResourceBean resource){
		return resourceDao.countMyList(resource);
	}
	
	/***
	 * 根据code修改资源
	 * 
	 * @param code
	 */
	public void updateResourceByCode(ResourceBean resource) {
		resourceDao.updateResourceByCode(resource);
	} 
	
	/***
	 * 根据code修改资源
	 * 
	 * @param code
	 */
	public int updateResourceByCode(ResourceBean resource, SqlSession session) {
		return resourceDao.updateResourceByCode(resource, session);
	}

	/**
	 * 后台分页-data
	 */
	public List<ResourceBean> getBackResourceListPaging(ResourceBean bean) {
		return resourceDao.getBackResourceListPaging(bean);
	}

	/**
	 * 后台分页-count
	 */
	public int getBackResourceCount(ResourceBean bean) {
		return resourceDao.getBackResourceCount(bean);
	}

	public void cancleLastTopWorksByType(int num, String type,
			SqlSession session) {
		resourceDao.cancleLastTopWorksByType(num,type,session);
	}

	public void batchCheckWorks(List<Integer> ids, String state) {
		resourceDao.batchCheckWorks(ids,state);
	} 
}
