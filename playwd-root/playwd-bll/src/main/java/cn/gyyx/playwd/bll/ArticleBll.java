package cn.gyyx.playwd.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.dao.playwd.ArticleDao;
import cn.gyyx.playwd.utils.DateTools;

@Component
public class ArticleBll {
	
	ArticleDao articleDao;

	public List<ArticleBean> dataList(ArticleBean beano) {
		return null;
	}

	/**
	 * 根据ID查询文章
	 * 
	 * @param id
	 */
	public ArticleBean getArticleById(int id) {
		return articleDao.selectByPrimaryKey(id);
	}

	/**
	 * 根据ID更新文章状态
	 * 
	 * @param id
	 * @param hidden
	 */
	public int editArticleStatusById(int id, String status) {
		return editArticleStatusById(id, status,"",0);
	}
	
	/**
	 * 根据ID更新文章状态
	 * 
	 * @param id
	 * @param status：recommended
	 * @param recommendDetail
	 * @param prizeId
	 * @return
	 */
	public int editArticleStatusById(int id, String status,String recommendDetail,int prizeId) {
		return articleDao.updateArticleStatusById(id, status,recommendDetail,prizeId);
	}

	/**
	 * 
	 * <p>
	 * 保存文章
	 * </p>
	 *
	 * @action lihu 2017年3月1日 下午7:58:51 描述
	 *
	 * @param articleBean
	 *            void
	 * @param userId 
	 * @param account 
	 */
	public void insertArticle(ArticleBean articleBean, String account, Integer userId) {
	        articleBean.setCreateTime(new Date());
	        articleBean.setStatus(ArticleBean.State.unreviewd.name());
	        articleBean.setAccount(account);
	        articleBean.setUserId(userId);
	        articleBean.setAuthorType(ArticleBean.AuthorType.PLAYER.getIndex());
		articleDao.insertSelective(articleBean);
	}
	/**
	 * 
	  * <p>
	  *    按照标题查询
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月13日 下午6:53:38 描述
	  *
	  * @param title
	  * @return boolean
	 */
	public boolean findCountByTitle(String title) {
		return articleDao.selectByTitle(title) > 0;
	}

	/**
	 * 
	 * @param type
	 *            文章标题 title,上传账号 account,昵称 author
	 * @param key
	 *            搜索内容
	 * @param displayStatus
	 *            展示状态 全部 -1,隐藏 0,展示 1
	 * @param firstCategoryId
	 *            一级分类
	 * @param secondCategoryId
	 *            二级分类
	 * @param authorType
	 *            发布人 玩家player,官方official,全部all
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param status
	 *            审核状态 待审核unreviewd,审核通过(展示)passed,审核不通过failed,通过但不显示hidden,
	 *            通过且推荐recommended
	 * @return
	 */
	public List<ArticleBean> getList(String type, String key,
			int displayStatus, int firstCategoryId, int secondCategoryId,
			String authorType, String startTime, String endTime, String status,
			int pageIndex, int pageSize) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", key);
		map.put("firstCategoryId", firstCategoryId);
		map.put("secondCategoryId", secondCategoryId);
		map.put("authorType", authorType);

		map.put("startTime", startTime);
		// 处理结束日期,默认当前日期加一天
		Date date = DateTools.strToDate(endTime);
		Date nextDate = DateTools.getNextDate(date);
		endTime = DateTools.formatDate(nextDate, "yyyy-MM-dd");
		map.put("endTime", endTime);		
		
		if (status != null) {

			if(status.equals("")){
				status="all";
			}		
		
			List<String> li=new ArrayList<String>();
			
			if(status.equals("all")){
				//显示
				if (displayStatus==1) {
					li.add(State.passed.Value());
					li.add(State.recommended.Value());
					map.put("status", li);
				}
				//隐藏
				if(displayStatus==0){
					li.add(State.unreviewd.Value());
					li.add(State.failed.Value());
					li.add(State.hidden.Value());
					map.put("status", li);
				}				
			}
			
			if(status.equals(State.passed.Value())){
				//隐藏
				if(displayStatus==0){
					li.add(State.hidden.Value());
					map.put("status", li);
				}
			}
			
			if(!map.containsKey("status")){
				if (!status.equals("all")) {
					li.add(status);
					map.put("status", li);
				}
			}			
		}

		int startSize = (pageIndex - 1) * pageSize;
		int endSize =  pageSize;
		map.put("startSize", startSize);
		map.put("endSize", endSize);		
		
		return articleDao.getList(map);
	}

	/**
	 * 
	 * @param type
	 *            文章标题 title,上传账号 account,昵称 author
	 * @param key
	 *            搜索内容
	 * @param displayStatus
	 *            展示状态 全部 -1,隐藏 0,展示 1
	 * @param firstCategoryId
	 *            一级分类
	 * @param secondCategoryId
	 *            二级分类
	 * @param authorType
	 *            发布人 玩家player,官方official,全部all
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param status
	 *            审核状态 待审核unreviewd,审核通过(展示)passed,审核不通过failed,通过但不显示hidden,
	 *            通过且推荐recommended
	 * @return
	 */
	public int getCount(String type, String key, int displayStatus,
			int firstCategoryId, int secondCategoryId, String authorType,
			String startTime, String endTime, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", key);
		map.put("firstCategoryId", firstCategoryId);
		map.put("secondCategoryId", secondCategoryId);
		map.put("authorType", authorType);
		map.put("startTime", startTime);
		// 处理结束日期,默认当前日期加一天
		Date date = DateTools.strToDate(endTime);
		Date nextDate = DateTools.getNextDate(date);
		endTime = DateTools.formatDate(nextDate, "yyyy-MM-dd");
		map.put("endTime", endTime);

		if (status != null) {

			if(status.equals("")){
				status="all";
			}		
		
			List<String> li=new ArrayList<String>();
			
			if(status.equals("all")){
				//显示,审核状态:审核通过,编辑推荐
				if (displayStatus==1) {
					li.add(State.passed.Value());
					li.add(State.recommended.Value());
					map.put("status", li);
				}
				//隐藏,审核状态:未审核,审核不通过,审核通过不显示
				if(displayStatus==0){
					li.add(State.unreviewd.Value());
					li.add(State.failed.Value());
					li.add(State.hidden.Value());
					map.put("status", li);
				}
			}
			
			if(status.equals(State.passed.Value())){
				//隐藏,审核状态:审核通过不显示
				if(displayStatus==0){
					li.add(State.hidden.Value());
					map.put("status", li);
				}
			}
			
			if(!map.containsKey("status")){
				if (!status.equals("all")) {
					li.add(status);
					map.put("status", li);
				}
			}			
		}

		return articleDao.getCount(map);
	}

    /**
     * 根据code获取文章信息
     * @param code
     * @return
     */
    public ArticleBean getArticle(Integer code) {
    	ArticleBean bean = articleDao.selectByPrimaryKey(code);
    	if(bean != null&&bean.getPublishTime() != null){
    		//设置字符串时间
			bean.setPublishTimeStr(DateTools.formatDate(bean.getPublishTime(),DateTools.TIMES_PATTERN));		
		}
		return bean;
    }

    public boolean editArticle(ArticleBean articleBean) {
        return articleDao.updateByPrimaryKeySelective(articleBean)>0;
    }

    public List<ArticleBean> getArticleByUserId(Integer userId, String state, int pageIndex, int pageSize) {
        int startSize = (pageIndex - 1) * pageSize;
        int endSize =   pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("state", state);
        map.put("startSize", startSize);
        map.put("endSize", endSize);
        return articleDao.getArticleByUserId(map);
    }

    /**
     * 获取自然月内被推荐的文章
     * @param userId
     * @return
     */
    public List<ArticleBean> getListByMonthUserId(int userId) {
		return articleDao.selectListByMonthUserId(userId);
	}
       
    /**
     * 获取推荐管理列表
     * @param locationId
     * @param prizeId
     * @param authorType
     * @param pageSize
     * @param currentPage
     * @return
     */
    public List<ArticleBean> getRecommendManagementList(int locationId,int prizeId,String authorType,
    		int pageSize,int currentPage) {
		return articleDao.selectRecommendManagementList(locationId, prizeId, authorType, 
				(currentPage-1)*pageSize, pageSize);
	}
    
    /**
     * 获取推荐管理列表数量
     * @param locationId
     * @param prizeId
     * @param authorType
     * @return
     */
    public int getRecommendManagementCount(int locationId,int prizeId,String authorType) {
		return articleDao.selectRecommendManagementCount(locationId, prizeId, authorType);
	}
    
    public int getCountByUserId(Integer userId, String state) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("state", state);
        return articleDao.getCountByUserId(map);
    }
    
    public boolean insertArticleOA(ArticleBean articleBean) {
    	return articleDao.insertSelective(articleBean)>0;
    }

    public List<ArticleBean> AuthorInfo(Integer userId) {
        return articleDao.findAuthorInfoByUserId(userId);
    }
    
    /**
     * 获取推荐记录数量
     * @param prizeId
     * @param startDate
     * @param endDate
     * @return
     */
    public int getRecommendRecordCount(int prizeId, Date startDate,Date endDate) {
		return articleDao.selectRecommendRecordCount(prizeId, startDate, endDate);
	}
    
    /**
     * 获取推荐记录数量
     * @param prizeId
     * @param startDate
     * @param endDate
     * @param pageSize
     * @param currentPage
     * @return
     */
    public List<ArticleBean> getRecommendRecordList(int prizeId, Date startDate,Date endDate,int pageSize,int currentPage) {
		return articleDao.selectRecommendRecordList(prizeId, startDate, endDate, (currentPage-1)*pageSize, pageSize);
	}
    
    /**
     * 修改文章备注
     * @param contentId
     * @param remark
     * @return
     */
    public boolean changeRemark(int contentId,String remark) {
    	return articleDao.updateRemark(contentId, remark)>0;
	}
    
    public List<ArticleBean> getAtricleList(Integer categoryId, int startSize,
            int endSize, String sortType, String categoryType ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("categoryId", categoryId);
        map.put("startSize", startSize);
        map.put("endSize", endSize);
        map.put("sortType", sortType);
        map.put("categoryType", categoryType);
        return articleDao.getAtricleList(map);
    }

    public int getAtricleCount(Integer categoryId, String sortType, String categoryType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("categoryId", categoryId);
        map.put("sortType", sortType);
        map.put("categoryType", categoryType);
        return articleDao.getAtricleCount(map);
    }

    /**
     * 获取用户审核通过的图文数量
     * @param code
     */
	public int getPassedArtitleCountByUserId(int code) {
		return articleDao.getPassedArtitleCountByUserId(code);
	}

	/**
	 * 获取用户图文list
	 * @param userId
	 * @param statusList
	 * @param topN
	 * @param orderBy
	 * @return
	 */
	public List<ArticleBean> getUserArticleList(Integer userId,
			List<String> statusList, int topN, String orderBy) {
		return articleDao.getUserArticleList(userId,statusList,topN,orderBy);
	}
	
	/**
	 * 获取用户服务器信息和作者名
	 * @param userId
	 * @return
	 */
	public ArticleBean getServerNameAndAuth(int userId) {
		return articleDao.selectServerNameAndAuth(userId);
	}

    public boolean editSpecial(Integer code, String special) {
        ArticleBean articleBean = new ArticleBean(); 
        articleBean.setCode(code);
        articleBean.setSecondTitle(special);
          return articleDao.updateByPrimaryKeySelective(articleBean)>0;
    }

    /**
     * 增加浏览量
     * @param code
     */
	public int increaseViewCount(int code) {
		return articleDao.increaseViewCount(code);
	}
	
	/**
     * 增加点赞量
     * @param code
     */
	public int increaseLikeCount(int code) {
		return articleDao.increaseLikeCount(code);
	}

	/**
     * 增加收藏量
     * @param code
     */
	public int increaseCollectCount(int code) {
		return articleDao.increaseCollectCount(code);
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	@Autowired
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}
