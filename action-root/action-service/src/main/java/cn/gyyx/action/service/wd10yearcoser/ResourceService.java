package cn.gyyx.action.service.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd10yearcoser.ResultBeanWithPage;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wd10yearcoser.ResourceBll;
import cn.gyyx.action.bll.wd10yearcoser.UserfavoriteBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ResourceService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ResourceService.class);
	
	private ResourceBll resourceBll = new ResourceBll();
	private UserfavoriteBll userfavoriteBll = new UserfavoriteBll();
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	
	/**
	 * 分页条件查询 去看他
	 * @param resource
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public ResultBeanWithPage<List<ResourceBean>> findTaResourcesByPage (UserInfo userInfo, ResourceBean resource,int taUserCode,int pageSize, int pageIndex) {
		ResultBeanWithPage<List<ResourceBean>> pageBean = new ResultBeanWithPage<List<ResourceBean>>();
		//设置查询条件 查找的是 去看他 中 他的用户id
		resource.setUserCode(taUserCode);
		//获取当前页数据
		List<ResourceBean> resourceList = resourceBll.findTaResourcesByPage(resource, pageSize, pageIndex);
		//登录状态下 筛选已经点赞的资源
		if (userInfo != null) {
			List<UserfavoriteBean> favoriteList = userfavoriteBll.findFavoriteByUser(userInfo.getUserId());
			//过滤是否点赞过
			for (UserfavoriteBean userfavoriteBean : favoriteList) {
				for (ResourceBean resourceBean : resourceList) {
					if (userfavoriteBean.getResourceCode().equals(resourceBean.getCode())) {
						resourceBean.setFlag(true);
						break;
					}
				}
			}
		}
		pageBean.setData(resourceList);
		//计算条数
		int count = resourceBll.countTaList(resource);
		pageBean.setTotalPage(count);
		pageBean.setIsSuccess(true);
		return pageBean;
	}
	
	/**
	 * 分页条件查询
	 * @param resource
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public ResultBeanWithPage<List<ResourceBean>> findMyselfResourcesByPage (ResourceBean resource,int pageSize, int pageIndex) {
		ResultBeanWithPage<List<ResourceBean>> pageBean = new ResultBeanWithPage<List<ResourceBean>>();
		//获取当前页数据
		List<ResourceBean> resourceList = resourceBll.findMyselfResourcesByPage(resource, pageSize, pageIndex);
		pageBean.setData(resourceList);
		//计算条数
		int count = resourceBll.countMyList(resource);
		pageBean.setTotalPage(count);
		pageBean.setIsSuccess(true);
		return pageBean;
	}
	
	/**
	 * 分页条件查询
	 * @param resource
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public ResultBeanWithPage<List<ResourceBean>> findOtherRecesByPage (ResourceBean resource,int pageSize, int pageIndex) {
		ResultBeanWithPage<List<ResourceBean>> pageBean = new ResultBeanWithPage<List<ResourceBean>>();
		//获取当前页数据
		List<ResourceBean> resourceList = resourceBll.findOtherResourcesByPage(resource, pageSize, pageIndex);
		//登录状态下 筛选已经点赞的资源
		if (resource.getUserCode() != null && resource.getUserCode() > 0) {
			List<UserfavoriteBean> favoriteList = userfavoriteBll.findFavoriteByUser(resource.getUserCode());
			if (resourceList != null && resourceList.size() > 0
					&& favoriteList != null && favoriteList.size() > 0) {
				//过滤是否点赞过
				for (UserfavoriteBean userfavoriteBean : favoriteList) {
					for (ResourceBean resourceBean : resourceList) {
						if (userfavoriteBean.getResourceCode().equals(resourceBean.getCode())) {
							resourceBean.setFlag(true);
							break;
						}
					}
				}
			}
		}
		pageBean.setData(resourceList);
		//计算条数
		int count = resourceBll.countOtherList(resource);
		pageBean.setIsSuccess(true);
		pageBean.setMessage("获取成功");
		pageBean.setTotalPage(count);
		return pageBean;
	}
}
